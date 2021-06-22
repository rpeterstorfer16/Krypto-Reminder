package Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Coin;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.FiatCurrencyServerTask;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.FiatFromUser;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;

public class SettingsActivity extends AppCompatActivity {

    ArrayList<FiatFromUser> currencies;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FIATNAME = "fiatname";
    public static final boolean ISCHECKED = Boolean.parseBoolean("isChecked");
    public static boolean isChecked;

    private FiatFromUser ffu;


    LeftFragment lf;
    private static final int RQ_ACCESS_FINE_LOCATION = 123;
    private boolean isGpsAllowed = false;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private double lat;
    private double lon;
    private MainActivity ma;

    public static String fiatname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        loadData();
        fillSpinner();

        ma = MainActivity.getInstance();
        ma.registerSystemService();
        ma.checkPermissionGPS();

        lf = LeftFragment.getInstance();


        Button applyButton = findViewById(R.id.applySettingsButton);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner fiatSpinner = findViewById(R.id.settingsSpinner);

                //ISCHECKED = ((Switch) findViewById(R.id.notificationsCheckBox)).isChecked();


                String fiat = fiatSpinner.getSelectedItem().toString();
                LeftFragment lf = LeftFragment.getInstance();

                if (fiat.equals("Currency from GPS location")) {
                    ffu = getFiatGPS(ma.lat, ma.lon);
                    saveData();
                    updateCurrencyGPS();

                } else {
                    getFiatSpinner(fiat);
                    saveData();
                    updateCurrency();


                }

            }
        });


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

    public void getFiatSpinner(String name) {
        currencies = getFiats();

        for (FiatFromUser fiat : currencies) {
            if (fiat.getName().equals(name)) {
                ffu = fiat;
            }
        }
    }

    public void fillSpinner() {
        currencies = getFiats();

        String[] currenciesForView;
        if (fiatname != null) {
            currenciesForView = new String[currencies.size() + 2];

            int j = 2;
            currenciesForView[0] = fiatname;
            currenciesForView[1] = "Currency from GPS location";
            for (FiatFromUser currenciesStrings : currencies) {
                currenciesForView[j] = currenciesStrings.getName();
                j++;
            }
        } else {
            currenciesForView = new String[currencies.size() + 1];

            int k = 1;
            currenciesForView[0] = "Currency from GPS location";
            for (FiatFromUser currenciesStrings : currencies) {
                currenciesForView[k] = currenciesStrings.getName();
                k++;
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currenciesForView);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.settingsSpinner);
        sItems.setAdapter(adapter);
    }

    public ArrayList<FiatFromUser> getFiats() {
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


    private void registerSystemService() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // from Api 23 and above you can call getSystemService this way:
        // locationManager = (LocationManager) getSystemService(LocationManager.class);
    }

    private void checkPermissionGPS() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    RQ_ACCESS_FINE_LOCATION);
        } else {
            gpsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != RQ_ACCESS_FINE_LOCATION) return;
        if (grantResults.length > 0 &&
                grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        } else {
            gpsGranted();
        }
    }

    private void gpsGranted() {
        isGpsAllowed = true;
        locationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLocationChanged(Location location) {
                displayLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        super.onPostResume();
        if (isGpsAllowed) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    400000,
                    2000,
                    locationListener);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        if (isGpsAllowed) locationManager.removeUpdates(locationListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void displayLocation(Location location) {
        double latNoDez = location == null ? -1 : location.getLatitude();
        double lonNoDez = location == null ? -1 : location.getLongitude();

        lat = Math.round(latNoDez * 1000) / 1000.0;
        lon = Math.round(lonNoDez * 1000) / 1000.0;


    }

    public FiatFromUser getFiatGPS(double latitute, double longitute) {
        String coin = "";
        String accessKey = "5fdb5f6c40e83447a40ea1615831570c";
        FiatFromUser fiatFromUser = null;


        FiatCurrencyServerTask task = new FiatCurrencyServerTask();
        try {

            String result = task.execute("http://api.positionstack.com/v1/reverse?" + "access_key=" + accessKey + "&query=" + latitute + "," + longitute + "&country_module=1").get();
            JSONObject js = new JSONObject(result);

            JSONArray ja = js.getJSONArray("data");

            JSONObject js2 = ja.getJSONObject(0);

            JSONObject js3 = js2.getJSONObject("country_module");

            JSONArray jsArray = js3.getJSONArray("currencies");

            JSONObject jsonObject = jsArray.getJSONObject(0);
            coin += jsonObject.getString("symbol");
            coin += ";";
            coin += jsonObject.getString("code");
            coin += ";";
            coin += jsonObject.getString("name");


            String[] coinArray = coin.split(";");

            fiatFromUser = new FiatFromUser(coinArray[0], coinArray[1], coinArray[2]);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fiatFromUser;

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //editor.putBoolean(ISCHECKED, isChecked);
        editor.putString(FIATNAME, ffu.getName());

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        fiatname = sharedPreferences.getString(FIATNAME, "");
        //isChecked = sharedPreferences.getBoolean(ISCHECKED,true);

    }

    public void updateCurrencyGPS() {

        ArrayList<Coin> coins = ma.getCoins(100, ffu.getName());

        for (int i = 0; i < lf.alerts.size(); i++) {
            Alert alert = lf.alerts.get(i);
            for (Coin c : coins)
                if (alert.getCoinName().equals(c.getCoinName().substring(0, 1).toUpperCase() + c.getCoinName().substring(1).toLowerCase())) {
                    double rate1 = 0.0;
                    for (FiatFromUser currency : lf.getFiats()) {

                        if (alert.getCurrency().equals(currency.getName())) {

                            rate1 = Double.parseDouble(currency.getRate());
                        }
                    }
                    double amount1 = alert.getPriceAlert() / rate1;
                    double amount2 = (amount1 * Double.parseDouble(ffu.getRate()));
                    alert.setCurrency(ffu.getName());
                    alert.setPriceAlert(Math.floor(amount2 * 100 / 100));
                    alert.setCurrentPrice(Math.floor(c.getCurrentPrice() * 100) / 100);
                    lf.alerts.set(i, alert);

                    lf.mAdapter.notifyDataSetChanged();
                }

        }

    }

    public void updateCurrency() {
        ArrayList<Coin> coins = ma.getCoins(100, ffu.getName());

        for (int i = 0; i < lf.alerts.size(); i++) {
            Alert alert = lf.alerts.get(i);
            for (Coin c : coins)
                if (alert.getCoinName().equals(c.getCoinName().substring(0, 1).toUpperCase() + c.getCoinName().substring(1).toLowerCase())) {
                    double rate1 = 0.0;
                    for (FiatFromUser currency : lf.getFiats()) {
                        if (alert.getCurrency().equals(currency.getName())) {
                            rate1 = Double.parseDouble(currency.getRate());
                        }
                    }
                    double amount1 = alert.getPriceAlert() / rate1;
                    double amount2 = (amount1 * Double.parseDouble(ffu.getRate()));
                    alert.setCurrency(ffu.getName());
                    alert.setPriceAlert(Math.floor(amount2 * 100 / 100));
                    alert.setCurrentPrice(Math.floor(c.getCurrentPrice() * 100) / 100);
                    lf.writeList(this.getApplicationContext(),lf.getAlerts());
                    lf.mAdapter.notifyDataSetChanged();
                }

        }
    }
}
