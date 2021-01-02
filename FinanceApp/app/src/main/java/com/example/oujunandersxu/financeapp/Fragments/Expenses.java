package com.example.oujunandersxu.financeapp.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oujunandersxu.financeapp.Controller;
import com.example.oujunandersxu.financeapp.R;

import java.util.ArrayList;

public class Expenses extends Fragment {
    private Button btn_add, btn_between, btn_reset;
    Controller controller;
    TextView from_tv,to_tv;
    DatePickerDialog.OnDateSetListener mDateSetListener_from;
    DatePickerDialog.OnDateSetListener mDateSetListener_to;
    ListView listView;
    Boolean isBetween=false;
    ArrayList list;

    public Expenses(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        init(view);
        getAllData();
        datePicker();
        setBtnListener();
        setTouchListener();
        setItemClick();
        return view;
    }

    void init(View view){
        btn_add = (Button) view.findViewById(R.id.btn_add_expenses);
        from_tv = (TextView) view.findViewById(R.id.date_from_exp);
        to_tv = (TextView) view.findViewById(R.id.date_to_exp);
        btn_between = (Button) view.findViewById(R.id.btn_get_between_EXP);
        listView = (ListView) view.findViewById(R.id.listView_exp_his);
        btn_reset = (Button) view.findViewById(R.id.btn_reset_EXP);
        controller = new Controller(this,listView, from_tv, mDateSetListener_from, mDateSetListener_to, to_tv);
    }

    void setBtnListener(){
        ButtonListener buttonListener = new ButtonListener();
        btn_add.setOnClickListener(buttonListener);
        btn_between.setOnClickListener(buttonListener);
        btn_reset.setOnClickListener(buttonListener);
    }

    void setTouchListener(){
        // scrollable listview inside a scrollview.
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

    void setItemClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isBetween==false)
                    getSelected(""+(position+1));
                else if(isBetween==true) {
                    list = controller.getIdOfItemEx(from_tv.getText().toString(), to_tv.getText().toString());
                    getSelected((String) list.get(position));
                }
            }
        });
    }

    void getAllData(){
        controller.getAllDataExp();
    }

    void getBetween(String from, String to){
        controller.getBetweenExp(from,to);
    }

    public void datePicker(){
        controller.datePickerExp();
    }

    void getSelected(String i){
        controller.getSelectedExpenditure(i);
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_add_expenses:
                    controller.transToAddEXP();
                    break;
                case R.id.btn_get_between_EXP:
                    getBetween(from_tv.getText().toString(),to_tv.getText().toString());
                    isBetween= controller.getIsBetweenEx();
                    break;
                case R.id.btn_reset_EXP:
                    getAllData();
                    break;
            }
        }
    }
}
