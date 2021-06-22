package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import Activities.LeftFragment;
import Activities.MainActivity;

public class AlertService extends Service {

    private static String TAG = AlertService.class.getSimpleName();
    private Thread worker;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Service: onStartCommand");
        if(worker.getState().equals(Thread.State.NEW)){
        if (!worker.isAlive()) {
            worker.start();
        }}
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Service started");
        worker = new Thread(this::doWork);
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
            Thread.sleep(2 * 3000);

            LeftFragment lf = LeftFragment.getInstance();



            ArrayList<Coin> coins = MainActivity.getInstance().getCoins(100, MainActivity.getInstance().getFiatname());

            for (Coin coin : coins)
            {
                for (Alert alert : lf.getAlerts())
                {
                    if(alert.getCoinName().equals(coin.getCoinName()))
                    {
                        alert.setCurrentPrice(coin.getCurrentPrice());
                    }
                }
            }

            for (Alert alert : lf.getAlerts()) {
                if (alert != null) {
                    if (alert.getStatus().equals(Status.ACTIVE)) {
                        if (alert.getLowerHigher().equals("lower")) {
                            Log.d(TAG, "hi");
                            if (alert.getCurrentPrice() > alert.getPriceAlert()) {

                            }
                        } else if (alert.getLowerHigher().equals("higher")) {
                            Log.d(TAG, "hilll");
                            if (alert.getCurrentPrice() < alert.getPriceAlert()) {

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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.star_big_on)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text ......"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(
                    NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/


}
