package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Data.Coin;
import Tasks.FiatCurrencyServerTask;
import Data.FiatsForConverter;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;

public class ConverterActivity extends AppCompatActivity {

    ArrayList<FiatsForConverter> currencies;
    MainActivity ma = MainActivity.getInstance();
    ArrayList<Coin> coins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        bnv.setSelectedItemId(R.id.page_3);

        TextView tv = findViewById(R.id.cryptoAmountTextInputEditText);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        fillInputLayout();
        Button convertButton = findViewById(R.id.convertButton);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tv.setText(String.valueOf(calculateAmount()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConverterActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);

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
                        overridePendingTransition(0, 0);

                        break;

                    // go to MainActivity
                    case R.id.page_2:
                        Intent intent1 = new Intent(ConverterActivity.this, MainActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);

                        break;
                }

                return false;
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
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.TextViewCryptocurrencyConverter);
        textView.setAdapter(adapter);


        currencies = getCurrency();
        String[] currenciesForView = new String[currencies.size()];
        int j = 0;
        for (FiatsForConverter currenciesStrings : currencies) {
            currenciesForView[j] = currenciesStrings.getName();
            j++;
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, currenciesForView);
        AutoCompleteTextView textView1 = (AutoCompleteTextView) findViewById(R.id.TextViewConvertCurrency);
        textView1.setAdapter(adapter1);
    }

    public ArrayList<FiatsForConverter> getCurrency() {
        String currency = "";
        currencies = new ArrayList<>();

        FiatCurrencyServerTask task = new FiatCurrencyServerTask();
        try {
            String result = task.execute("https://api.coinstats.app/public/v1/fiats").get();


            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject js2 = ja.getJSONObject(i);
                currency += js2.getString("name");
                currency += ";";
                currency += js2.getString("rate");
                currency += ";";
                currency += js2.getString("symbol");

                String[] currencyArray = currency.split(";");

                currencies.add(new FiatsForConverter(currencyArray[0], Double.parseDouble(currencyArray[1]), (currencyArray[2])));

                currency = "";
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return currencies;

    }

    private double calculateAmount() throws IOException {

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.TextViewCryptocurrencyConverter);
        String cryptocurrency = "";
        if (!String.valueOf(textView.getText()).equals("")) {
            cryptocurrency = String.valueOf(textView.getText());
        }


        AutoCompleteTextView textView1 = (AutoCompleteTextView) findViewById(R.id.TextViewConvertCurrency);
        String currency = "";
        if (!String.valueOf(textView1.getText()).equals("")) {
            currency = String.valueOf(textView1.getText());
        } else return 0.0;


        ArrayList<Coin> coinsWithUserSelectedFiat = ma.getCoins(100,currency);

        Coin coin = null;
        for (Coin c : coinsWithUserSelectedFiat) {
            if (c.getCoinName().equals(cryptocurrency)) {
                coin = c;
            }
        }




        TextView amountView = findViewById(R.id.addAlertAmount);
        double amount = 0;
        if (!amountView.getText().equals("")) {
            amount = Integer.parseInt(String.valueOf(amountView.getText()));
        }

        ImageView iv = findViewById(R.id.imageViewCoinIcon);

        Picasso.get().load(coin.getIconLink()).into(iv);

        return amount / coin.getCurrentPrice();


    }
}
