package Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import java.util.concurrent.ExecutionException;

import Data.Alert;
import RowAdapter.AlertRowAdapter;
import Data.Coin;
import Tasks.FiatCurrencyServerTask;
import Data.FiatFromUser;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;

public class LeftFragment extends Fragment implements AlertRowAdapter.customButtonListener {
    private static final String TAG = LeftFragment.class.getSimpleName();
    protected ListView list;
    protected static ArrayList<Alert> alerts = new ArrayList<>();
    private OnSelectionChangedListener listener;
    protected static AlertRowAdapter mAdapter;
    private ArrayList<FiatFromUser> currencies;
    private static LeftFragment instance;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        initializeViews(view);

        Button refreshButton = view.findViewById(R.id.refreshCurrentButton);


        if (readList(this.getContext()) != null) {
            alerts = readList(this.getContext());
        }
        //mAdapter.notifyDataSetChanged();


        refreshButton.setOnClickListener(v -> {
            if (!alerts.isEmpty()) {
                MainActivity ma = MainActivity.getInstance();
                ArrayList<Coin> refreshPrices = ma.getCoins(100, ma.fiatname);
                for (int i = 0; i < alerts.size(); i++) {
                    for (Alert oldPrice : alerts) {
                        for (Coin newPrice : refreshPrices) {
                            if (oldPrice.getCoinName().equals(newPrice.getCoinName().substring(0, 1).toUpperCase() + newPrice.getCoinName().substring(1).toLowerCase())) {
                                oldPrice.setCurrentPrice(Math.floor(newPrice.getCurrentPrice() * 100) / 100);
                                oldPrice.setMarketCap(newPrice.getMarketCap());
                                oldPrice.setPriceChanged(newPrice.getPriceChangedIn24());
                                mAdapter.notifyDataSetChanged();
                                writeList(view.getContext(), alerts);
                            }
                        }

                    }

                }

            }

        });

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
                alerts.add((Alert) bundle.getSerializable("newAlert"));
                MainActivity.getInstance().stopService();
                AddAlertActivity.saved = false;
                writeList(this.getContext(), alerts);
                MainActivity.getInstance().startService();

            }
        }

        mAdapter = new AlertRowAdapter(this.getContext(), R.layout.alertlistviewlayout, alerts);
        list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setCustomButtonListner(this.getInstance());

    }

    @Override
    public void onButtonClickListner(int position, String value) {
        alerts.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.getInstance(), "Alert " + (position + 1) + " was deleted" + "\n(" + value + ")", Toast.LENGTH_LONG).show();
        writeList(this.getContext(), alerts);

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

    public static void writeList(Context c, ArrayList<Alert> list) {
        try {
            FileOutputStream fos = c.openFileOutput("Alerts", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(list);
            Log.d(TAG, "list saved");
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Alert> readList(Context c) {
        try {
            FileInputStream fis = c.openFileInput("Alerts");
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<Alert> list = (ArrayList<Alert>) is.readObject();
            is.close();
            Log.d(TAG, "list read sucessfully");
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public AlertRowAdapter getmAdapter() {
        return mAdapter;
    }


}
