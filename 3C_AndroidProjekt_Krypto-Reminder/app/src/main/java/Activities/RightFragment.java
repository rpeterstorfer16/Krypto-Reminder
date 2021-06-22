package Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;


public class RightFragment extends Fragment {
    public final static String TAG = RightFragment.class.getSimpleName();
    ImageView iconView;
    private TextView currentPriceTextView;
    private TextView priceAlertTextView;
    private TextView priceChangedTextView;
    private TextView markedCapTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        intializeViews(view);
        return view;
    }
    private void intializeViews(View view) {
        Log.d(TAG, "intializeViews: entered");
        iconView = view.findViewById(R.id.CryptoCurrencyimageView);
        currentPriceTextView = view.findViewById(R.id.currentPriceTextView);
        priceAlertTextView = view.findViewById(R.id.PriceAlertTextView);
        priceChangedTextView = view.findViewById(R.id.PriceChangedTextView);
        markedCapTextView = view.findViewById(R.id.MarkedCapTextView);



    }

    public void show(int pos, Alert alert) {
        Log.d(TAG, "show: entered");
        Picasso.get().load(alert.getCryptoIcon()).into(iconView);
        currentPriceTextView.setText("Current price: "+alert.getCurrentPrice());
        priceAlertTextView.setText("Price alert at: " + alert.getPriceAlert());
        priceChangedTextView.setText("Price changed(24h): " + alert.getPriceChanged());
        markedCapTextView.setText("Market capitalisation: " + alert.getMarketCap());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: entered");
    }
}