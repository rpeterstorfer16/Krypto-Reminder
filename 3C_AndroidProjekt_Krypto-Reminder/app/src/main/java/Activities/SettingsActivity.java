package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.FiatCurrencyServerTask;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.FiatFromUser;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;

public class SettingsActivity extends AppCompatActivity {

    ArrayList<FiatFromUser> currencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fillSpinner();

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    //go to TransactionsActivity
                    case R.id.page_1:
                        Intent intent = new Intent(SettingsActivity.this, TransactionActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        break;

                    case R.id.page_2:
                        Intent intent1 = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);

                        break;

                    //go to ConverterActivity
                    case R.id.page_3:
                        Intent intent2 = new Intent(SettingsActivity.this, ConverterActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);

                        break;
                }

                return false;
            }

        });
    }

    public void fillSpinner() {
        MainActivity ma = MainActivity.getInstance();

        currencies = getCurrency();
        String[] currenciesForView = new String[currencies.size()+1];
        int j = 1;
        currenciesForView[0] = "Currency from GPS location";
        for (FiatFromUser currenciesStrings : currencies) {
            currenciesForView[j] = currenciesStrings.getName();
            j++;
        }

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currenciesForView);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.settingsSpinner);
        sItems.setAdapter(adapter);*/
    }

    public ArrayList<FiatFromUser> getCurrency() {
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

                currencies.add(new FiatFromUser(currencyArray[0], currencyArray[1], (currencyArray[2])));

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
}
