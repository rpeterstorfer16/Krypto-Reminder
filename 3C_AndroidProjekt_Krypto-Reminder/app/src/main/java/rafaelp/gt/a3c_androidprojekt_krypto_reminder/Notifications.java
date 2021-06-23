package rafaelp.gt.a3c_androidprojekt_krypto_reminder;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


import java.util.Collections;


public class Notifications extends Application {
public static final String CHANNEL_1_ID = "channel1";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,"channel1", NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("An alert has hit your target");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannels(Collections.singletonList(channel1));

        }

    }
}