package rafaelp.gt.a3c_androidproject_krypto_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddAlertActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alerts);

        Button cancelButton = findViewById(R.id.cancelButton);
        Button addAlertButton = findViewById(R.id.addAlertButton);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAlertActivity.this,SettingsActivity.class));
                overridePendingTransition(0,0);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(AddAlertActivity.this, "Canceled", duration);
                toast.show();
            }
        });



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAlertActivity.this, MainActivity.class));
                overridePendingTransition(0,0);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(AddAlertActivity.this, "Canceled", duration);
                toast.show();

            }
        });

        addAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAlertActivity.this, MainActivity.class));
                overridePendingTransition(0,0);

                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(AddAlertActivity.this, "Alert saved", duration);
                toast.show();
            }
        });
    }
}
