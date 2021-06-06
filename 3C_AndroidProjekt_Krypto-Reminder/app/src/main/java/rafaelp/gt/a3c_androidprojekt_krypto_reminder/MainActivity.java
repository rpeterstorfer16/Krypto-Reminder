package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextView testView = findViewById(R.id.testtest);
        // testView.setText(getCoins(10,"EUR").toString());

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);

            }
        });


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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.startAnimation(rotateForward);
                // switching to AddAlertActivity
                startActivity(new Intent(MainActivity.this, AddAlertActivity.class));


            }
        });
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


}