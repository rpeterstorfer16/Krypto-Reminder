package Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;


public class RightFragment extends Fragment {
    public final static String TAG = RightFragment.class.getSimpleName();
    ImageView iconView;
    private TextView currentPriceTextView;
    private TextView PriceAlertTextView;
    private TextView PriceChangedTextView;
    private TextView MarkedCapTextView;

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
        PriceAlertTextView = view.findViewById(R.id.PriceAlertTextView);
        PriceChangedTextView = view.findViewById(R.id.PriceChangedTextView);
        MarkedCapTextView = view.findViewById(R.id.MarkedCapTextView);



    }

    public void show(int pos, Alert alert) {
        Log.d(TAG, "show: entered");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: entered");
    }
}