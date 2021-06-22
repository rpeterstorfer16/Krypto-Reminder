package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

import Activities.LeftFragment;
import Activities.MainActivity;

public class AlertService extends Service {

    private static final String CHANNEL_ID = "Chalnel1";
    private static String TAG = AlertService.class.getSimpleName();
    private Thread worker;

    private NotificationManagerCompat notificationManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Service: onStartCommand");
        if (worker.getState().equals(Thread.State.NEW)) {
            if (!worker.isAlive()) {
                worker.start();
            }
        }


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Service started");
        worker = new Thread(this::doWork);
        notificationManager = NotificationManagerCompat.from(this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroyed");
        worker.interrupt();
        worker = null;
        super.onDestroy();
    }


    private void doWork() {
        try {
            Log.d(TAG, "do work entered");
            Thread.sleep(5 * 3000);

            LeftFragment lf = LeftFragment.getInstance();


            ArrayList<Coin> coins = MainActivity.getInstance().getCoins(100, MainActivity.getInstance().getFiatname());

            for (Coin coin : coins) {
                for (Alert alert : lf.getAlerts()) {
                    if (alert.getCoinName().equals(coin.getCoinName())) {
                        alert.setCurrentPrice(coin.getCurrentPrice());
                    }
                }
            }

            for (Alert alert : lf.getAlerts()) {
                if (alert != null) {
                    if (alert.getStatus().equals(Status.ACTIVE)) {
                        if (alert.getLowerHigher().equals("lower")) {
                            if (alert.getCurrentPrice() > alert.getPriceAlert()) {
                                sendOnChannel1(alert.getCoinName(), alert.getPriceAlert());
                            }
                        } else if (alert.getLowerHigher().equals("higher")) {
                            if (alert.getCurrentPrice() < alert.getPriceAlert()) {
                                sendOnChannel1(alert.getCoinName(), alert.getPriceAlert());

                            }
                        }

                    }

                }

            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Thread end: thread-name: " + Thread.currentThread().getName());
    }

    /*public void buildNotification() {
         NotificationCompat.Builder builder = new NotificationCompat.Builder(AlertService.this)
                 .setSmallIcon(R.drawable.ic_alert)
                 .setContentTitle("Krypto-Reminder")
                 .setContentText("An alert hit the target")
                 .setAutoCancel(true);

         Intent intent = new Intent(AlertService.this, NotificationsActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         intent.putExtra("message", "Es geht oida");

        PendingIntent pendingIntent = PendingIntent.getActivity(AlertService.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

    }*/

    public void sendOnChannel1(String coinName, double alertAt) {
        Notification notification = new NotificationCompat.Builder(this, Notifications.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alert)
                .setContentTitle("Target has been hit")
                .setContentText(coinName + " has hit your target at: " + alertAt)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }


}
