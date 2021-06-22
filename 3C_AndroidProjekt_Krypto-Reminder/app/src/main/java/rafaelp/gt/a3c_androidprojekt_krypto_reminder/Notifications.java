package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collections;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;

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
            channel1.setDescription("Alert has hit Target");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannels(Collections.singletonList(channel1));

        }

    }
}