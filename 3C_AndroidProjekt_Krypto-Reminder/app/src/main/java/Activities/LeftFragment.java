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

import java.util.ArrayList;

import rafaelp.gt.a3c_androidprojekt_krypto_reminder.Alert;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.AlertRowAdapter;
import rafaelp.gt.a3c_androidprojekt_krypto_reminder.R;

public class LeftFragment extends Fragment {
    private static final String TAG = LeftFragment.class.getSimpleName();
    private ListView list;
    private static ArrayList<Alert> alerts = new ArrayList<>();
    private OnSelectionChangedListener listener;
    private ListView mListView;
    private AlertRowAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        initializeViews(view);

        return view;
    }

    private void initializeViews(View view) {
        Log.d(TAG, "initialize: entered");
        list = view.findViewById(R.id.listview);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            alerts.add((Alert) bundle.getSerializable("newAlert"));
        }

        list.setOnItemClickListener((parent, view1, position, id) -> itemSelected(position, listener));

    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: entered");
        super.onStart();
        //final ArrayAdapter<Alert> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, alerts);
        //list.setAdapter(adapter);

        mAdapter = new AlertRowAdapter(getContext(), R.layout.alertlistviewlayout, alerts);
        list.setAdapter(mAdapter);
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




}
