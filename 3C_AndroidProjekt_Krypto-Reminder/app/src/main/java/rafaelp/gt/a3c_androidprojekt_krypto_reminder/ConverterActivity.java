package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ConverterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        bnv.setSelectedItemId(R.id.page_3);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConverterActivity.this,SettingsActivity.class));
                overridePendingTransition(0,0);

            }
        });


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    // go to TransactionView
                    case R.id.page_1:
                        Intent intent = new Intent(ConverterActivity.this, TransactionActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);

                        break;

                    // go to MainActivity
                    case R.id.page_2:
                        Intent intent1 = new Intent(ConverterActivity.this, MainActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);

                        break;
                }

                return false;
            }

        });
    }
}
