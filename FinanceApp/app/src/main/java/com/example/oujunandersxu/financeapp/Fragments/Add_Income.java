package com.example.oujunandersxu.financeapp.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oujunandersxu.financeapp.DatabaseHelper;
import com.example.oujunandersxu.financeapp.R;


/**
 * Created by Oujun Anders Xu on 2017-09-18.
 */

public class Add_Income extends Fragment{
    private static final String TAG2 = "Add_Income";
    EditText title_income, income;
    Spinner category_income;
    Button btn_add_income;
    TextView date;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseHelper myDB;
    private AddController addController;
    private Spinner spinner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_income, container, false);
        callMethod(view);
        return view;
    }

    public void callMethod(View view){
        initComponents(view);
        setSpinner();
        setListener();
        datePicker();
    }

    public void setSpinner(){
        addController.setSpinnerINC(spinner);
    }

    public void datePicker(){
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                addController.datePickerINC(mDisplayDate,year,month,day);
            }
        };
    }

    public void initComponents(View view){
        addController = new AddController(this);
        myDB = new DatabaseHelper(getActivity());
        title_income = (EditText) view.findViewById(R.id.title_eText_inc);
        income = (EditText) view.findViewById(R.id.income_eText);
        category_income = (Spinner) view.findViewById(R.id.category_CB_INC);
        date = (TextView) view.findViewById(R.id.date_picker);
        btn_add_income = (Button) view.findViewById(R.id.button_add_income_DB);
        mDisplayDate = (TextView) view.findViewById(R.id.date_picker);
        spinner = (Spinner) view.findViewById(R.id.category_CB_INC);

    }

    public void setListener(){
        ButtonListener buttonListener = new ButtonListener();
        btn_add_income.setOnClickListener(buttonListener);
        mDisplayDate.setOnClickListener(buttonListener);
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case(R.id.date_picker):
                    addController.displayDateINC(mDateSetListener);
                    break;
                case(R.id.button_add_income_DB):
                    addController.insertDataINC(myDB,title_income,income,category_income,date);
                    break;
            }
        }
    }
}
