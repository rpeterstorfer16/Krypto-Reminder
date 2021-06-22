package Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.AlertRowAdapter;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Transaction;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.TransactionRowAdapter;

public class TransactionActivity extends AppCompatActivity implements TransactionRowAdapter.customButtonListener {
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private ListView mListView;
    private TransactionRowAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        fillItemsList();
        bnv.setSelectedItemId(R.id.page_1);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
            }
        });


        if(readList(this) != null)
        {
            transactions = readList(this);
        }

        if(transactions != null)
        {
            mAdapter = new TransactionRowAdapter(this, R.layout.transactionlistviewlayout, transactions);
            mAdapter.setCustomButtonListner(TransactionActivity.this);
            mListView = findViewById(R.id.transactionListView);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();


        }




        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    // go to MainActivity
                    case R.id.page_2:
                        Intent intent = new Intent(TransactionActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        break;

                    // go to ConverterActivity
                    case R.id.page_3:
                        Intent intent1 = new Intent(TransactionActivity.this, ConverterActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        break;
                }

                return false;
            }

        });


        //Animation for FloatingActionButton

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);

        Animation rotateForward = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_forward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.startAnimation(rotateForward);

                startActivity(new Intent(TransactionActivity.this, AddTransactionActivity.class));

            }
        });
    }


    private void fillItemsList() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Transaction transaction = (Transaction) bundle.getSerializable("transaction");
            transactions.add(transaction);
            writeList(this, transactions);
        }
    }

    public static void writeList(Context c, ArrayList<Transaction> list) {
        try {
            FileOutputStream fos = c.openFileOutput("Transactions", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(list);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Transaction> readList(Context c) {
        try {
            FileInputStream fis = c.openFileInput("Transactions");
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<Transaction> list = (ArrayList<Transaction>) is.readObject();
            is.close();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void onButtonClickListner(int position, String value) {
        transactions.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.getInstance(), " Transaction "+ (position+1)+" was deleted \n("+value+" Transaction)", Toast.LENGTH_LONG).show();
        writeList(this,transactions);
    }
}

