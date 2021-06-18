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

import Activities.MainActivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class TransactionRowAdapter extends BaseAdapter {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;


    public TransactionRowAdapter(Context ctx, int layoutId, List<Transaction> transactions) {
        this.transactions = (ArrayList<Transaction>) transactions;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int i) {
        return transactions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Transaction transaction = transactions.get(i);
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;
        ImageView iv = listItem.findViewById(R.id.transactionRowImageView);
        Picasso.get().load(transaction.getIconLink()).into(iv);
        ((TextView) listItem.findViewById(R.id.rowTransactionAmountAndCoinnameView)).setText(transaction.getAmountOfCoins() + " "+transaction.getCoinName());
        ((TextView) listItem.findViewById(R.id.rowTransactionTransactionAndDateView)).setText(transaction.getTypeOfTransaction() + " am " + transaction.getDate());


        return listItem;
    }
}

