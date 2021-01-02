package com.example.oujunandersxu.financeapp.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.oujunandersxu.financeapp.DatabaseHelper;
import com.example.oujunandersxu.financeapp.R;

/**
 * Created by Oujun Anders Xu on 2017-11-01.
 */

public class AddController {
    private Add_Expenses add_expenses;
    private Add_Income add_income;

    public AddController(Add_Expenses add_expenses) {
        this.add_expenses = add_expenses;
    }

    public AddController(Add_Income add_income) {
        this.add_income = add_income;
    }

//EXPENDITURE------------------------------------------------------------------------------------------------------------------------------
    public void displayDateEXP(DatePickerDialog.OnDateSetListener mDateSetListener) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                add_expenses.getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void insertDataEXP(DatabaseHelper myDB, EditText title_expense, EditText expense, Spinner category_expense, TextView date) {
        boolean isInserted = myDB.insertData2(
                title_expense.getText().toString(),
                Double.parseDouble(expense.getText().toString()),
                category_expense.getSelectedItem().toString(),
                date.getText().toString()
        );
        if(isInserted == true) {
            Toast.makeText(add_expenses.getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();
            Fragment fragment = new Expenses();
            FragmentTransaction transaction = add_expenses.getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, fragment);
            transaction.commit();
        }
        else
            Toast.makeText(add_expenses.getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    public void datePickerEXP(TextView mDisplayDate, int year, int month, int day) {
        month = month +1;
        String realMonth = String.format("%02d", month);
        String realDate = String.format("%02d", day);
        String date = year + "-" + realMonth + "-" + realDate;
        mDisplayDate.setText(date);
    }

    public void setSpinnerEXP(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                add_expenses.getActivity(),R.array.expense_category,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //INCOME-------------------------------------------------------------------------------------------------

    public void datePickerINC(TextView mDisplayDate, int year, int month, int day) {
        month = month +1;
        String realMonth = String.format("%02d", month);
        String realDate = String.format("%02d", day);
        String date = year + "-" + realMonth + "-" + realDate;
        mDisplayDate.setText(date);
    }

    public void displayDateINC(DatePickerDialog.OnDateSetListener mDateSetListener) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                add_income.getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    public void insertDataINC(DatabaseHelper myDB, EditText title_income, EditText income, Spinner category_income, TextView date) {
        boolean isInserted = myDB.insertData(
                title_income.getText().toString(),
                Double.parseDouble(income.getText().toString()),
                category_income.getSelectedItem().toString(),
                date.getText().toString()
        );
        if(isInserted == true) {
            Toast.makeText(add_income.getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();
            Fragment fragment = new Income();
            FragmentTransaction transaction = add_income.getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, fragment);
            transaction.commit();
        }
        else
            Toast.makeText(add_income.getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    public void setSpinnerINC(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                add_income.getActivity(),R.array.income_category,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
