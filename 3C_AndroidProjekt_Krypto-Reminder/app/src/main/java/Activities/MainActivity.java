package Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.AlertService;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Coin;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.CryptocurrencyServerTask;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.FiatCurrencyServerTask;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.FiatFromUser;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.DetailedActivity;

public class MainActivity extends AppCompatActivity implements LeftFragment.OnSelectionChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RightFragment rightFragment;
    private boolean showRight = false;
    private static final int RQ_ACCESS_FINE_LOCATION = 123;
    private boolean isGpsAllowed = false;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private double lat;
    private double lon;
    private FiatFromUser ffu;
    protected ArrayList<Coin> coins;
    private static MainActivity instance;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FIATNAME = "fiatname";
    protected String fiatname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        registerSystemService();
        checkPermissionGPS();

        initializeView();

        loadData();
        coins = getCoins(100, fiatname);


        // Declaration and setting of BottomNavigationBar

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        bnv.setSelectedItemId(R.id.page_2);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    //go to TransactionsActivity
                    case R.id.page_1:
                        Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        break;

                    //go to ConverterActivity
                    case R.id.page_3:
                        Intent intent1 = new Intent(MainActivity.this, ConverterActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);

                        break;
                }

                return false;
            }

        });

        // Animation for FloatingActionButton

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);

        Animation rotateForward = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_forward);

        //startService();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.startAnimation(rotateForward);
                // switching to AddAlertActivity
                startActivity(new Intent(MainActivity.this, AddAlertActivity.class));


            }
        });
        startService();


    }


    public ArrayList<Coin> getCoins(int amountOfCoins, String currency) {
        String coin = "";
        ArrayList<Coin> coinList = new ArrayList<>();

        CryptocurrencyServerTask task = new CryptocurrencyServerTask();
        try {
            String result = task.execute("https://api.coinstats.app/public/v1/coins?skip=0&limit=" + amountOfCoins + "&currency=" + currency).get();
            JSONObject js = new JSONObject(result);

            JSONArray ja = js.getJSONArray("coins");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject js2 = ja.getJSONObject(i);
                coin += js2.getString("id");
                coin += ";";
                coin += js2.getString("icon");
                coin += ";";
                coin += js2.getString("price");
                coin += ";";
                coin += js2.getString("priceChange1d");
                coin += ";";
                coin += js2.getString("marketCap");

                String[] coinArray = coin.split(";");

                coinList.add(new Coin(coinArray[0], coinArray[1], Double.parseDouble(coinArray[2]), Double.parseDouble(coinArray[3]), Double.parseDouble(coinArray[4])));

                coin = "";

            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return coinList;

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


       /* if (ffu == null) {
            ffu = getFiat(lat, lon);
            testView.setText(ffu.toString());

        } else {
            testView.setText(ffu.toString());
        }*/


    }


    public static MainActivity getInstance() {
        return instance;
    }


    private void initializeView() {
        Log.d(TAG, "initializeView: entered");
        rightFragment = (RightFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragRight);
        showRight = rightFragment != null && rightFragment.isInLayout();
    }


    private void callRightActivity(int pos, String alert) {
        Log.d(TAG, "callRightActivity: entered");
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("pos", pos);
        intent.putExtra("alert", alert);
        startActivity(intent);
    }

    @Override
    public void onSelectionChanged(int pos, Alert alert) {
        if (showRight) rightFragment.show(pos, alert.toString());
        else callRightActivity(pos, alert.toString());
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        fiatname = sharedPreferences.getString(FIATNAME, "");
    }

    public String getFiatname() {
        return fiatname;
    }

    public void startService() {
        LeftFragment lf = LeftFragment.getInstance();
        Log.d(TAG, "startService: entered");
        Intent intent = new Intent(this, AlertService.class);

        startService(intent);
        }

    }



    /*public void stopService(View view) {
        Log.d(TAG, "stopService: entered");
        Intent intent = new Intent(this, AlertService.class);
        stopService(intent);
    }*/





