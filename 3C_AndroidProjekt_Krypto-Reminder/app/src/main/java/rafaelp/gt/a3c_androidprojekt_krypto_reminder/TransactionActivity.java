package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TransactionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        bnv.setSelectedItemId(R.id.page_1);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this,SettingsActivity.class));
                overridePendingTransition(0,0);

            }
        });


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    // go to MainActivity
                    case R.id.page_2:
                        Intent intent = new Intent(TransactionActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        break;

                    // go to ConverterActivity
                    case R.id.page_3:
                        Intent intent1 = new Intent(TransactionActivity.this, ConverterActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        break;
                }

                return false;
            }

        });

        //Animation for FloatingActionButton

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);

        Animation rotateForward = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_forward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.startAnimation(rotateForward);


                startActivity(new Intent(TransactionActivity.this, AddTransactionActivity.class));


            }
        });
    }
}
