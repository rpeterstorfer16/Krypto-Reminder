package rafaelp.gt.a3c_androidproject_krypto_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()) {
                    //go to TransactionsActivity
                    case R.id.page_1:
                        Intent intent = new Intent(SettingsActivity.this, TransactionActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);

                        break;

                    case R.id.page_2:
                        Intent intent1 = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);

                        break;

                    //go to ConverterActivity
                    case R.id.page_3:
                        Intent intent2 = new Intent(SettingsActivity.this, ConverterActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);

                        break;
                }

                return false;
            }

        });
    }
}
