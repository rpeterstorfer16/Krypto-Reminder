package RowAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import Data.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class AlertRowAdapter extends BaseAdapter {
    customButtonListener customListner;
    public interface customButtonListener {
        void onButtonClickListner(int position, String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

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
        ViewHolder viewHolder;
        Alert alert = alerts.get(i);
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;
        if (view == null) {

            viewHolder = new ViewHolder();
            viewHolder.button = (Button) listItem.findViewById(R.id.buttonDelete);
            listItem.setTag(viewHolder);
        } else { viewHolder = (ViewHolder) view.getTag();
        }

            ImageView iv = listItem.findViewById(R.id.alertRowImageView);

        Picasso.get().load(alert.getCryptoIcon()).into(iv);
        ((TextView) listItem.findViewById(R.id.alertRowCoinNameTextView)).setText(alert.getCoinName());
        ((TextView) listItem.findViewById(R.id.rowAlertTargetTextView)).setText("Alert at: " + alert.getPriceAlert() + " " + alert.getCurrency());
        ((TextView) listItem.findViewById(R.id.rowAlertCurrentPriceTextView)).setText("Current price: " + alert.getCurrentPrice() + " " + alert.getCurrency());
        ((TextView) listItem.findViewById(R.id.statusTextView)).setText(String.valueOf(alert.getStatus()));


        viewHolder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(i, alert.getCoinName());
                }

            }
        });


        return listItem;


    }

    public class ViewHolder {
        Button button;
        String value;
    }





}

