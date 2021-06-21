package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Activities.LeftFragment;
import Activities.RightFragment;

public class DetailedActivity extends AppCompatActivity {
    private static final String TAG = DetailedActivity.class.getSimpleName();
    ImageView iconView;
    private TextView currentPriceTextView;
    private TextView priceAlertTextView;
    private TextView priceChangedTextView;
    private TextView markedCapTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: entered");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);
        intializeViews();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation != Configuration.ORIENTATION_PORTRAIT) {
            finish();
            return;
        }
        handleIntent();
    }

    private void handleIntent() {
        Log.d(TAG, "handleIntent: entered");

        Intent intent = getIntent();
        if (intent == null) return;
        RightFragment rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.fragRight);
        int pos = intent.getIntExtra("pos", -1);
        Alert alert = (Alert) intent.getSerializableExtra("alert");
        //rightFragment.show(pos, alert);
        Picasso.get().load(alert.getCryptoIcon()).into(iconView);
        currentPriceTextView.setText("Current price: "+alert.getCurrentPrice());
        priceAlertTextView.setText("Price alert at: " + alert.getPriceAlert());
        priceChangedTextView.setText("Price changed(24h): " + alert.getPriceChanged());
        markedCapTextView.setText("Market capitalisation: " + alert.getMarketCap());

    }

    private void intializeViews() {
        Log.d(TAG, "intializeViews: entered");
        iconView = findViewById(R.id.CryptoCurrencyimageView);
        currentPriceTextView = findViewById(R.id.currentPriceTextView);
        priceAlertTextView = findViewById(R.id.PriceAlertTextView);
        priceChangedTextView = findViewById(R.id.PriceChangedTextView);
        markedCapTextView = findViewById(R.id.MarkedCapTextView);

    }


}