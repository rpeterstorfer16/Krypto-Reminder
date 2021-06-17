package Activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Coin;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Transaction;

public class AddTransactionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        Button cancelButton = findViewById(R.id.cancelTransactionButton);
        Button addTransactionButton = findViewById(R.id.applySettingsButton);

        fillInputLayout();

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(AddTransactionActivity.this, "Canceled", duration);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransactionActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);


                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(AddTransactionActivity.this, "Canceled", duration);
                toast.show();
            }
        });

        Button sellTransactionButton = findViewById(R.id.sellTransactionButton);
        Button buyTransactionButton = findViewById(R.id.buyTransactionButton);

        sellTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = MainActivity.getInstance();
                AutoCompleteTextView addAlertAmount = findViewById(R.id.addAlertAmount);
                double amount = Double.parseDouble(String.valueOf(addAlertAmount.getText()));

                TextView AddTransactionCryptocurrency = findViewById(R.id.textViewCryptocurrency);
                String cryptocurrency = String.valueOf(AddTransactionCryptocurrency.getText());

                Coin selectedCoin = null;

                for (Coin c : ma.coins) {
                    String coin = c.getCoinName();
                    if (coin.equals(cryptocurrency)) selectedCoin = c;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                Transaction transaction = new Transaction("Verkauft",amount, selectedCoin.getCoinName(), selectedCoin.getIconLink(), sdf.format(new Date()));

                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                intent.putExtra("transaction", (Serializable) transaction);
                startActivity(intent);
                overridePendingTransition(0, 0);


                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(AddTransactionActivity.this, "Transaction saved", duration);
                toast.show();


            }
        });

        buyTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = MainActivity.getInstance();
                AutoCompleteTextView addAlertAmount = findViewById(R.id.addAlertAmount);
                double amount = Double.parseDouble(String.valueOf(addAlertAmount.getText()));

                TextView AddTransactionCryptocurrency = findViewById(R.id.textViewCryptocurrency);
                String cryptocurrency = String.valueOf(AddTransactionCryptocurrency.getText());

                Coin selectedCoin = null;

                for (Coin c : ma.coins) {
                    String coin = c.getCoinName();
                    if (coin.equals(cryptocurrency)) selectedCoin = c;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                Transaction transaction = new Transaction("Gekauft",amount, selectedCoin.getCoinName(), selectedCoin.getIconLink(), sdf.format(new Date()));

                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                intent.putExtra("transaction", (Serializable) transaction);
                startActivity(intent);
                overridePendingTransition(0, 0);


                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(AddTransactionActivity.this, "Transaction saved", duration);
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
                overridePendingTransition(0, 0);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(AddTransactionActivity.this, "Canceled", duration);
                toast.show();

            }
        });


    }

    public void fillInputLayout() {
        MainActivity ma = MainActivity.getInstance();
        ArrayList<Coin> coins = ma.coins;
        String[] coinsForView = new String[coins.size()];
        int i = 0;
        for (Coin coinStrings : coins) {
            coinsForView[i] = coinStrings.getCoinName();
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, coinsForView);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.textViewCryptocurrency);
        textView.setAdapter(adapter);
    }
}
