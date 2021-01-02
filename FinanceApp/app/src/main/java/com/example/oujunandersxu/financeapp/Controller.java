package com.example.oujunandersxu.financeapp;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oujunandersxu.financeapp.Fragments.Add_Expenses;
import com.example.oujunandersxu.financeapp.Fragments.Add_Income;
import com.example.oujunandersxu.financeapp.Fragments.Expenses;
import com.example.oujunandersxu.financeapp.Fragments.Home;
import com.example.oujunandersxu.financeapp.Fragments.Income;
import com.example.oujunandersxu.financeapp.Fragments.Settings;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Oujun Anders Xu on 2017-10-17.
 */

public class Controller  {
    MainActivity mainActivity;

    Income income;
    ListView listView;
    TextView from_tv, to_tv;
    DatePickerDialog.OnDateSetListener mDateSetListener_from;
    DatePickerDialog.OnDateSetListener mDateSetListener_to;

    Boolean isBetween;
    Boolean isBetweenEx;

    Expenses expenses;
    ListView listViewE;
    TextView from_tvE, to_tvE;
    DatePickerDialog.OnDateSetListener mDateSetListener_fromE;
    DatePickerDialog.OnDateSetListener mDateSetListener_toE;


    /**
     *
     * @param mainActivity
     */
    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public Controller(Income income,ListView listView, TextView from_tv, DatePickerDialog.OnDateSetListener mDateSetListener_from,
                      DatePickerDialog.OnDateSetListener mDateSetListener_to, TextView to_tv){
        this.income = income;
        this.listView=listView;
        this.from_tv = from_tv;
        this.to_tv = to_tv;
        this.mDateSetListener_from = mDateSetListener_from;
        this.mDateSetListener_to = mDateSetListener_to;
    }

    public Controller(Expenses expenses,ListView listView, TextView from_tv, DatePickerDialog.OnDateSetListener mDateSetListener_from,
                      DatePickerDialog.OnDateSetListener mDateSetListener_to, TextView to_tv){
        this.expenses = expenses;
        this.listViewE=listView;
        this.from_tvE = from_tv;
        this.to_tvE = to_tv;
        this.mDateSetListener_fromE = mDateSetListener_from;
        this.mDateSetListener_toE = mDateSetListener_to;
    }



    public void onCreateMethod(){
        mainActivity.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) mainActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mainActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) mainActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mainActivity);
    }

    // Handle navigation view item clicks here.
    public boolean onNavItemSel(MenuItem item){
        int id = item.getItemId();
        SharedPreferences sPref = mainActivity.getSharedPreferences("UserData", mainActivity.MODE_PRIVATE);


        if (id == R.id.action_home && !sPref.getString("last","").isEmpty() && !sPref.getString("first","").isEmpty()) {
            Home home = new Home();
            FragmentManager fm = mainActivity.getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment, home).commit();
        }else if(id==R.id.action_Income && !sPref.getString("last","").isEmpty() && !sPref.getString("first","").isEmpty()){
            Income income = new Income();
            FragmentManager fm = mainActivity.getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment, income).commit();
        }else if(id==R.id.action_Expenses && !sPref.getString("last","").isEmpty() && !sPref.getString("first","").isEmpty()){
            Expenses exp = new Expenses();
            FragmentManager fm = mainActivity.getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment, exp).commit();
        }else if(id==R.id.action_Settings) {
            Settings set = new Settings();
            FragmentManager fm = mainActivity.getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment, set).commit();
        }

        DrawerLayout drawer = (DrawerLayout) mainActivity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * Income controller
     * ----------------------------------------------------------------------------------------------------------------------------------------------
     */

    public void getAllData(){
        DatabaseHelper dbHelper = new DatabaseHelper(income.getActivity());
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getListContents();
        if(data.getCount()==0)
            Toast.makeText(income.getActivity(),"Empty", Toast.LENGTH_LONG).show();
        else
            while(data.moveToNext()){
                theList.add(
                        data.getString(0) + "\n" +
                        "Title: "+ data.getString(1) +  "\n"+
                                "Expense: "+ data.getString(2) + " SEK" +  "\n"+
                                "Date: " + data.getString(3)
);
                ListAdapter listAdapter = new ArrayAdapter<>(income.getActivity() , android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
    }

    public void isBetween(Boolean isBetween){
        this.isBetween = isBetween;
    }

    public Boolean getIsBetween(){
        return isBetween;
    }

    public void getBetween(String from, String to){
        DatabaseHelper dbHelper = new DatabaseHelper(income.getActivity());
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getIncomeBetween(from,to);
        if(data.getCount()==0) {
            Toast.makeText(income.getActivity(), "Empty", Toast.LENGTH_LONG).show();
            isBetween(false);
        }
        else{
            while(data.moveToNext()) {
                theList.add(
                        "Title: " + data.getString(1) + "\n" +
                                "Expense: " + data.getString(2) + " SEK" + "\n" +
                                "Date: " + data.getString(3) + "\n"
                );
                ListAdapter listAdapter = new ArrayAdapter<>(income.getActivity(), android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
            isBetween(true);
        }

    }


    public ArrayList getIdOfItem(String from, String to){
        DatabaseHelper dbHelper = new DatabaseHelper(income.getActivity());
        ArrayList<String> theID = new ArrayList<>();
        Cursor data = dbHelper.getIncomeBetween(from,to);
        if(data.getCount()==0)
            Toast.makeText(income.getActivity(),"Empty", Toast.LENGTH_LONG).show();
        else
            while(data.moveToNext()) {
                theID.add(data.getString(0));
            }
        return theID;
    }

    public void getSelectedIncome(String i){
        DatabaseHelper dbHelper = new DatabaseHelper(income.getActivity());
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getIncomeSelected(Integer.parseInt(i));
        if(data.getCount()==0)
            Toast.makeText(income.getActivity(),"Empty", Toast.LENGTH_LONG).show();
        else
            while(data.moveToNext()) {
                theList.add(
                        "ID: " + data.getString(0) + "\n"+
                        "Title: " + data.getString(1) + "\n" +
                                "Expense: " + data.getString(2) + " SEK" + "\n" +
                                "Date: " + data.getString(3) + "\n" +
                                "Category: " + data.getString(4) + "\n");
                ListAdapter listAdapter = new ArrayAdapter<>(income.getActivity(), android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
    }

    public void datePicker(){

        from_tv.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                income.getActivity(),
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener_from,
                                year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                }
        );
        //set text for date
        mDateSetListener_from = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month +1;
                String realMonth = String.format("%02d", month);
                String realDate = String.format("%02d", day);
                Log.d(TAG, "onDateSet: date: " + month + "/" + day + "/" + year);
                String date = year + "-" + realMonth + "-" + realDate;
                from_tv.setText(date);
            }
        };

        to_tv.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                income.getActivity(),
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener_to,
                                year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                }
        );
        //set text for date
        mDateSetListener_to = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month +1;
                String realMonth = String.format("%02d", month);
                String realDate = String.format("%02d", day);
                Log.d(TAG, "onDateSet: date: " + month + "/" + day + "/" + year);
                String date = year + "-" + realMonth + "-" + realDate;
                to_tv.setText(date);

            }
        };
    }



    /**
     * Expenses
     * -------------------------------------------------------------------------------------------------------------------------------------------------
     */

    public void getAllDataExp(){
        DatabaseHelper dbHelper = new DatabaseHelper(expenses.getActivity());
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getListContents_2();
        if(data.getCount()==0)
            Toast.makeText(expenses.getActivity(),"Empty", Toast.LENGTH_LONG).show();
        else
            while(data.moveToNext()){
                theList.add(
                        "ID: " + data.getString(0) + "\n" +
                        "Title: "+ data.getString(1) +  "\n"+
                                "Expense: "+ data.getString(2) + " SEK" +  "\n"+
                                "Date: "+ data.getString(3) + "\n");
                ListAdapter listAdapter = new ArrayAdapter<>(expenses.getActivity() , android.R.layout.simple_list_item_1, theList);
                listViewE.setAdapter(listAdapter);
            }
    }

    public void isBetweenEx(Boolean isBetweenEx){
        this.isBetweenEx = isBetweenEx;
    }

    public Boolean getIsBetweenEx(){
        return isBetweenEx;
    }

    public void getBetweenExp(String from, String to){
        DatabaseHelper dbHelper = new DatabaseHelper(expenses.getActivity());
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getExpBetween(from,to);
        if(data.getCount()==0) {
            Toast.makeText(expenses.getActivity(), "Empty", Toast.LENGTH_LONG).show();
            isBetweenEx(false);
        }
        else {
            while (data.moveToNext()) {
                theList.add(
                        "Title: " + data.getString(1) + "\n" +
                                "Expense: " + data.getString(2) + " SEK" + "\n" +
                                "Date: " + data.getString(3) + "\n");
                ListAdapter listAdapter = new ArrayAdapter<>(expenses.getActivity(), android.R.layout.simple_list_item_1, theList);
                listViewE.setAdapter(listAdapter);
            }
            isBetweenEx(true);
        }
    }

    public ArrayList getIdOfItemEx(String from, String to){
        DatabaseHelper dbHelper = new DatabaseHelper(expenses.getActivity());
        ArrayList<String> theID = new ArrayList<>();
        Cursor data = dbHelper.getExpBetween(from,to);
        if(data.getCount()==0)
            Toast.makeText(income.getActivity(),"Empty", Toast.LENGTH_LONG).show();
        else
            while(data.moveToNext()) {
                theID.add(data.getString(0));
            }
        return theID;
    }

    public void getSelectedExpenditure(String i){
        DatabaseHelper dbHelper = new DatabaseHelper(expenses.getActivity());
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getExpenditureSelected(Integer.parseInt(i));
        if(data.getCount()==0)
            Toast.makeText(expenses.getActivity(),"Empty", Toast.LENGTH_LONG).show();
        else
            while(data.moveToNext()) {
                theList.add(
                        "ID: " + data.getString(0) + "\n" +
                        "Title: " + data.getString(1) + "\n" +
                                "Expense: " + data.getString(2) + " SEK" + "\n" +
                                "Date: " + data.getString(3) + "\n" +
                                "Category: " + data.getString(4) + "\n");
                ListAdapter listAdapter = new ArrayAdapter<>(expenses.getActivity(), android.R.layout.simple_list_item_1, theList);
                listViewE.setAdapter(listAdapter);
            }
    }

    public void datePickerExp(){
        from_tvE.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                expenses.getActivity(),
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener_fromE,
                                year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                }
        );
        //set text for date
        mDateSetListener_fromE = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month +1;
                String realMonth = String.format("%02d", month);
                String realDate = String.format("%02d", day);
                Log.d(TAG, "onDateSet: date: " + month + "/" + day + "/" + year);
                String date = year + "-" + realMonth + "-" + realDate;
                from_tvE.setText(date);
            }
        };

        to_tvE.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                expenses.getActivity(),
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener_toE,
                                year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                }
        );
        //set text for date
        mDateSetListener_toE = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month +1;
                String realMonth = String.format("%02d", month);
                String realDate = String.format("%02d", day);
                Log.d(TAG, "onDateSet: date: " + month + "/" + day + "/" + year);
                String date = year + "-" + realMonth + "-" + realDate;
                to_tvE.setText(date);

            }
        };
    }

    public void transToAddINC() {
        Fragment fr = new Add_Income();
        FragmentTransaction transaction = income.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fr);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void transToAddEXP() {
        Fragment fragment = new Add_Expenses();
        FragmentTransaction transaction = expenses.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
