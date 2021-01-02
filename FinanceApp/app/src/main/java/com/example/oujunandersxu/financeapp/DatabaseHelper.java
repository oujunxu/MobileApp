package com.example.oujunandersxu.financeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;

import java.util.Date;

/**
 * Created by Oujun Anders Xu on 2017-09-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Financial.db";
    public static final String TABLE_NAME = "income_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "INCOME";
    public static final String COL_4 = "DATE_INCOME";
    public static final String COL_5 = "CATEGORY";

    public static final String TABLE_NAME2 = "expense_table";
    public static final String COL_1_EXP = "ID";
    public static final String COL_2_EXP = "TITLE";
    public static final String COL_3_EXP = "EXPENSE";
    public static final String COL_4_EXP = "DATE_EXPENSE";
    public static final String COL_5_EXP = "CATEGORY";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 14);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, INCOME DOUBLE, DATE_INCOME DATE, CATEGORY TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, EXPENSE DOUBLE, DATE_EXPENSE DATE, CATEGORY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean insertData(String title, double income, String category, String date_income){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, income);
        contentValues.put(COL_4, date_income);
        contentValues.put(COL_5, category);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData2(String title, double expense, String category, String date_expense){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_EXP, title);
        contentValues.put(COL_3_EXP, expense);
        contentValues.put(COL_4_EXP, date_expense);
        contentValues.put(COL_5_EXP, category);

        long result = db.insert(TABLE_NAME2, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY ID ASC", null) ;
        return data;
    }

    public Cursor getListContents_2(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " ORDER BY ID ASC", null);
        return data;
    }

    public Cursor getSumOfIncome(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT SUM(INCOME) FROM " + TABLE_NAME, null);
        return data;
    }

    public Cursor getSumOfExpense(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT SUM(EXPENSE) FROM " + TABLE_NAME2, null);
        return data;
    }

    public Cursor getIncomeBetween(String from, String to){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE date(DATE_INCOME) BETWEEN " + "'"+from+"' " + "AND " + "'"+to+"'" + " ORDER BY ID ASC", null) ;
        return data;
    }
    public Cursor getExpBetween(String from, String to){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM expense_table WHERE date(DATE_EXPENSE) BETWEEN " + "'"+from+"' " + "AND " + "'"+to+"'" + " ORDER BY ID ASC", null) ;
        return data;
    }

    public Cursor getIncomeSelected(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID="+id, null) ;
        return data;
    }

    public Cursor getExpenditureSelected(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE ID="+id, null) ;
        return data;
    }
}
