package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddTransactionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        Button cancelButton = findViewById(R.id.cancelTransactionButton);
        Button addTransactionButton = findViewById(R.id.saveTransactionButton);

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(AddTransactionActivity.this, "Canceled", duration);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransactionActivity.this,SettingsActivity.class));
                overridePendingTransition(0,0);


                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(AddTransactionActivity.this, "Canceled", duration);
                toast.show();
            }
        });


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    // go to TransactionView
                    case R.id.page_1:
                        Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                        startActivity(intent);
                        toast.show();
                        overridePendingTransition(0, 0);
                        break;

                    // go to MainActivity

                    case R.id.page_2:
                        Intent intent1 = new Intent(AddTransactionActivity.this, MainActivity.class);
                        startActivity(intent1);
                        toast.show();
                        overridePendingTransition(0, 0);
                        break;

                    // go to ConverterActivity
                    case R.id.page_3:
                        Intent intent2 = new Intent(AddTransactionActivity.this, ConverterActivity.class);
                        startActivity(intent2);
                        int duration = Toast.LENGTH_SHORT;
                        toast.show();
                        overridePendingTransition(0, 0);
                        break;
                }

                return false;
            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransactionActivity.this, TransactionActivity.class));
                overridePendingTransition(0,0);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(AddTransactionActivity.this, "Canceled", duration);
                toast.show();

            }
        });

        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransactionActivity.this, TransactionActivity.class));
                overridePendingTransition(0,0);

                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(AddTransactionActivity.this, "Transaction saved", duration);
                toast.show();
            }
        });
    }
}
