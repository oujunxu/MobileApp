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

public class Add_Expenses extends Fragment{
    private EditText title_expense, expense;
    private Spinner category_expense;
    private TextView date;
    private Button btn_add_expense;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseHelper myDB;
    private AddController addController;
    private Spinner spinner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expenses, container, false);
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
        addController.setSpinnerEXP(spinner);

    }

    public void datePicker(){
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                addController.datePickerEXP(mDisplayDate,year,month,day);
            }
        };
    }

    public void initComponents(View view){
        myDB = new DatabaseHelper(getActivity());
        spinner = (Spinner) view.findViewById(R.id.category_CB_EXP);
        mDisplayDate = (TextView) view.findViewById(R.id.date_picker_exp);
        addController = new AddController(this);
        title_expense = (EditText) view.findViewById(R.id.title_eText_exp);
        expense = (EditText) view.findViewById(R.id.expense_eText);
        category_expense = (Spinner) view.findViewById(R.id.category_CB_EXP);
        date = (TextView) view.findViewById(R.id.date_picker_exp);
        btn_add_expense = (Button) view.findViewById(R.id.btn_add_exp_DB);
    }

    public void setListener(){
        ButtonListener buttonListener = new ButtonListener();
        mDisplayDate.setOnClickListener(buttonListener);
        btn_add_expense.setOnClickListener(buttonListener);
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case(R.id.date_picker_exp):
                    addController.displayDateEXP(mDateSetListener);
                    break;
                case(R.id.btn_add_exp_DB):
                    addController.insertDataEXP(myDB,title_expense,expense,category_expense,date);
                    break;
            }
        }
    }
}
