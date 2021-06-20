package Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.AlertRowAdapter;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Coin;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.FiatCurrencyServerTask;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.FiatFromUser;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;

public class LeftFragment extends Fragment {
    private static final String TAG = LeftFragment.class.getSimpleName();
    protected ListView list;
    protected static ArrayList<Alert> alerts = new ArrayList<>();
    private OnSelectionChangedListener listener;
    protected AlertRowAdapter mAdapter;
    private ArrayList<FiatFromUser> currencies;
    private static LeftFragment instance;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        initializeViews(view);

        return view;
    }

    private void initializeViews(View view) {
        Log.d(TAG, "initialize: entered");
        list = view.findViewById(R.id.listview);


        list.setOnItemClickListener((parent, view1, position, id) -> itemSelected(position, listener));

    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: entered");
        super.onStart();


        instance = this;

        currencies = getFiats();

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            if (AddAlertActivity.saved == true) {
                //if (!alerts.contains((Alert) bundle.getSerializable("newAlert"))) {

                alerts.add((Alert) bundle.getSerializable("newAlert"));
                AddAlertActivity.saved = false;

            }
        }

        mAdapter = new AlertRowAdapter(this.getContext(), R.layout.alertlistviewlayout, alerts);
        list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(int pos, Alert alert);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: entered");
        super.onAttach(context);
        if (context instanceof OnSelectionChangedListener) {
            listener = (OnSelectionChangedListener) context;
        } else {
            Log.d(TAG, "onAttach: Activity does not implement OnSelectionChangedListener");
        }
    }

    private void itemSelected(int position, OnSelectionChangedListener listener) {
        Alert alert = alerts.get(position);
        listener.onSelectionChanged(position, alert);
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

    public static LeftFragment getInstance() {
        return instance;
    }

    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public ArrayList<FiatFromUser> getCurrencies() {
        return currencies;
    }
}
