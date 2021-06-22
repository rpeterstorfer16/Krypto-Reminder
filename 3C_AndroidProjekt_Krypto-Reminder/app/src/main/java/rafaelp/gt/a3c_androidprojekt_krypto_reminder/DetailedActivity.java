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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Activities.LeftFragment;
import Activities.MainActivity;
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

        Button backButton = findViewById(R.id.DetailedViewBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });
    }

    private void handleIntent() {
        Log.d(TAG, "handleIntent: entered");

        Intent intent = getIntent();
        if (intent == null) return;
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
        iconView = findViewById(R.id.DeatailedCryptoCurrencyimageView);
        currentPriceTextView = findViewById(R.id.DetailedcurrentPriceTextView);
        priceAlertTextView = findViewById(R.id.DetailedPriceAlertTextView);
        priceChangedTextView = findViewById(R.id.DetailedPriceChangedTextView);
        markedCapTextView = findViewById(R.id.DetailedMarkedCapTextView);

    }


}