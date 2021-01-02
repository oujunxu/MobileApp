package com.example.oujunandersxu.financeapp.Fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oujunandersxu.financeapp.Controller;
import com.example.oujunandersxu.financeapp.R;

import java.util.ArrayList;


public class Income extends Fragment {
    private Button btn_add, btn_between, btn_reset;
    Controller controller;
    TextView from_tv,to_tv;
    DatePickerDialog.OnDateSetListener mDateSetListener_from;
    DatePickerDialog.OnDateSetListener mDateSetListener_to;
    ListView listView;
    Boolean isBetween=false;
    ArrayList list;

    public Income() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_income, container, false);
        init(view);
        getAllData();
        datePicker();
        setBtnListener();
        setItemClickListener();
        setTouchListener();
        return view;
    }

    void setBtnListener(){
        ButtonListener buttonListener = new ButtonListener();
        btn_add.setOnClickListener(buttonListener);
        btn_between.setOnClickListener(buttonListener);
        btn_reset.setOnClickListener(buttonListener);
    }

    void setItemClickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isBetween==false) //if its not between dates
                    getSelected(""+(position+1));
                else if(isBetween==true) {
                    list = controller.getIdOfItem(from_tv.getText().toString(), to_tv.getText().toString());
                    getSelected((String) list.get(position));
                }
            }
        });
    }

    void setTouchListener(){
        listView.setOnTouchListener(new ListView.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
    }

    void init(View view){
        btn_add = (Button) view.findViewById(R.id.btn_add_income);
        from_tv = (TextView) view.findViewById(R.id.date_from_income);
        to_tv = (TextView) view.findViewById(R.id.date_to_income);
        btn_between = (Button) view.findViewById(R.id.btn_get_between);
        listView = (ListView) view.findViewById(R.id.listView_inc_hist);
        btn_reset = (Button) view.findViewById(R.id.btn_reset);
        controller = new Controller(this,listView, from_tv, mDateSetListener_from, mDateSetListener_to, to_tv);
    }

    void getAllData(){
        controller.getAllData();
    }

    void getBetween(String from, String to){
        controller.getBetween(from,to);
    }

    void getSelected(String i){
        controller.getSelectedIncome(i);
    }

    public void datePicker(){
        controller.datePicker();
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_add_income:
                    controller.transToAddINC();
                    break;
                case R.id.btn_get_between:
                    getBetween(from_tv.getText().toString(),to_tv.getText().toString());
                    isBetween = controller.getIsBetween();
                    break;
                case R.id.btn_reset:
                    getAllData();
                    isBetween=false;
                    break;

            }
        }
    }
}