package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Coin;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Status;

public class AddAlertActivity extends AppCompatActivity {

    MainActivity ma = MainActivity.getInstance();
    ArrayList<Coin> coins;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FIATNAME = "fiat";

    public static String fiatname;
    public static boolean saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alerts);
        saved=true;
        Button cancelButton = findViewById(R.id.cancelButton);
        Button addAlertButton = findViewById(R.id.addAlertButton);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        fillInputLayout();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAlertActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(AddAlertActivity.this, "Canceled", duration);
                toast.show();
            }
        });

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    // go to TransactionView
                    case R.id.page_1:
                        Intent intent = new Intent(AddAlertActivity.this, TransactionActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        break;

                    // go to MainActivity
                    case R.id.page_2:
                        Intent intent1 = new Intent(AddAlertActivity.this, MainActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);

                        break;

                    case R.id.page_3:
                        Intent intent2 = new Intent(AddAlertActivity.this, ConverterActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);

                        break;
                }

                return false;
            }

        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAlertActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(AddAlertActivity.this, "Canceled", duration);
                toast.show();

            }
        });

        addAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView cryptocurrencyTextView = (AutoCompleteTextView) findViewById(R.id.addAlertCryptocurrency);
                TextView amountTextView = findViewById(R.id.addAlertAmount);

                ArrayList<Coin> coins = ma.coins;

                Coin selectedCoin = null;
                String cryptocurrency = String.valueOf(cryptocurrencyTextView.getText());
                double amount = Double.parseDouble(amountTextView.getText().toString());
                String higherLower = null;

                for (Coin c : coins) {
                    String coin = c.getCoinName();
                    if (coin.equals(cryptocurrency)) selectedCoin = c;
                }

                if (selectedCoin.getCurrentPrice() > amount) {
                    higherLower = "lower";
                } else if (selectedCoin.getCurrentPrice() < amount) {
                    higherLower = "higher";
                }

                DecimalFormat f = new DecimalFormat();
                f.setMaximumFractionDigits(4);
                Alert alert = new Alert(selectedCoin.getCoinName().substring(0, 1).toUpperCase() + selectedCoin.getCoinName().substring(1).toLowerCase(), ma.fiatname, higherLower, Math.floor(selectedCoin.getCurrentPrice() * 100) / 100 , amount, selectedCoin.getPriceChangedIn24(), selectedCoin.getMarketCap(), selectedCoin.getIconLink(), Status.ACTIVE);
                saved=true;
                Intent intent = new Intent(AddAlertActivity.this, MainActivity.class);
                intent.putExtra("newAlert", (Serializable) alert);
                startActivity(intent);
                overridePendingTransition(0, 0);


                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(AddAlertActivity.this, "Alert saved", duration);
                toast.show();
            }
        });
    }

    public void fillInputLayout() {
        coins = ma.coins;
        String[] coinsForView = new String[coins.size()];
        int i = 0;
        for (Coin coinStrings : coins) {
            coinsForView[i] = coinStrings.getCoinName();
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, coinsForView);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.addAlertCryptocurrency);
        textView.setAdapter(adapter);
    }


}
