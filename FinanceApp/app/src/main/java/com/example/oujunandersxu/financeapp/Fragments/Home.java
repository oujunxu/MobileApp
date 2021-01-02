package com.example.oujunandersxu.financeapp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oujunandersxu.financeapp.DatabaseHelper;
import com.example.oujunandersxu.financeapp.R;

public class Home extends Fragment {
    private DatabaseHelper dB;
    TextView name, inc, exp, currB, fn, ln;
    ListView listView;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        exp = (TextView) view.findViewById(R.id.total_exp);
        inc = (TextView) view.findViewById(R.id.total_inc);
        currB = (TextView) view.findViewById(R.id.current_balance);
        name = (TextView) view.findViewById(R.id.user_info_TV);
        dB = new DatabaseHelper(getActivity());
        Cursor cursor = dB.getSumOfIncome();
        Cursor cursorEXP = dB.getSumOfExpense();
        SharedPreferences myPrefs = this.getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String n1 = myPrefs.getString("last","");
        String n2 = myPrefs.getString("first","");
        //total income

        if(cursor.moveToNext()){
            String tINC = Double.toString(cursor.getDouble(0));
            inc.setText("Total income: " + tINC + " SEK");
        }

        if(cursorEXP.moveToNext()){
            String tEXP = Double.toString(cursorEXP.getDouble(0));
            exp.setText("Total expenses: " + tEXP + " SEK");
        }

        String currBalance = Double.toString(cursor.getDouble(0)-cursorEXP.getDouble(0));
        currB.setText("Your current balance is: "+currBalance + " SEK");

        name.setText("User: "+ n2 + " " +  n1);

        return view;
    }

}
