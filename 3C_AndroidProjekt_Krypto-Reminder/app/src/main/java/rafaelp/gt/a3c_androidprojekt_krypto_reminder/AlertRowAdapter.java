package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Activities.MainActivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class AlertRowAdapter extends BaseAdapter {

    private ArrayList<Alert> alerts = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;


    public AlertRowAdapter(Context ctx, int layoutId, List<Alert> alerts) {
        this.alerts = (ArrayList<Alert>) alerts;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alerts.size();
    }

    @Override
    public Object getItem(int i) {
        return alerts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Alert alert = alerts.get(i);
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;
        ImageView iv = listItem.findViewById(R.id.transactionRowImageView);

        Picasso.get().load(alert.getCryptoIcon()).into(iv);
        ((TextView) listItem.findViewById(R.id.transactionRowCoinNameTextView)).setText(alert.getCoinName());
        ((TextView) listItem.findViewById(R.id.rowTransactionTargetTextView)).setText("Alert at: " + alert.getPriceAlert() + " " + alert.getCurrency());
        ((TextView) listItem.findViewById(R.id.rowTransactionCurrentPriceTextView)).setText("Current price: " + alert.getCurrentPrice() + " " + alert.getCurrency());


        return listItem;


    }
}

