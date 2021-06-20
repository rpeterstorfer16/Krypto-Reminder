package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import Activities.LeftFragment;
import Activities.MainActivity;

public class AlertService extends Service {

    private static String TAG = AlertService.class.getSimpleName();
    private Thread worker;
    private ArrayList<Alert> alerts = new ArrayList<>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Service: onStartCommand");

        if (intent.hasExtra("startNewThread")) {
            if (!worker.isAlive()) worker.start();
        }

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
            LeftFragment lf = LeftFragment.getInstance();
            MainActivity ma = new MainActivity();


            ArrayList<Coin> coins = ma.getCoins(100, ma.getFiatname());

            for (Alert alert : lf.getAlerts())
                for (Coin c : coins) {
                    if (c.getCoinName().equals(alert.getCoinName())) {
                        alert.setCurrentPrice(c.getCurrentPrice());
                    }
                }

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for (Alert alert : lf.getAlerts()) {
                if (alert != null) {
                    if (alert.getStatus().equals(Status.ACTIVE)) {
                        if (alert.getLowerHigher().equals("lower")) {
                            if (alert.getCurrentPrice() > alert.getPriceAlert()) {
                                Log.d(TAG, alert.getCoinName() + alert.getLowerHigher());
                            }
                        } else if (alert.getLowerHigher().equals("higher")) {
                            if (alert.getCurrentPrice() < alert.getPriceAlert()) {
                                Log.d(TAG, alert.getCoinName() + alert.getLowerHigher());
                            }
                        }

                    }

                }

            }

            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Thread end: thread-name: "
                + Thread.currentThread().getName());
    }


}
