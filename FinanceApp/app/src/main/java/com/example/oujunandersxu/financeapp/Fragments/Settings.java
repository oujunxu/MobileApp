package com.example.oujunandersxu.financeapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oujunandersxu.financeapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oujun Anders Xu on 2017-09-20.
 */

public class Settings extends Fragment {
    private EditText firstName_ET, surName_ET;
    private TextView fn,ln;
    private Button btn_addName, load;
    private TextView listV;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        final View view2 = inflater.inflate(R.layout.nav_header_main,container,false);
        firstName_ET = (EditText) view.findViewById(R.id.editText_name);
        surName_ET = (EditText) view.findViewById(R.id.editText_surname);
        btn_addName = (Button) view.findViewById(R.id.btn_save_name);
        listV = (TextView) view2.findViewById(R.id.list_nav_header);


        btn_addName.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               saveInfo();
                                           }
                                       }
        );

        return view;
    }



    public void saveInfo(){
        SharedPreferences myPrefs = this.getActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("first", firstName_ET.getText().toString());
        editor.putString("last", surName_ET.getText().toString());
        editor.apply();

        if(firstName_ET.getText().toString().isEmpty() && surName_ET.getText().toString().isEmpty())
            Toast.makeText(getActivity(),"You forgot to write your name and surname" ,Toast.LENGTH_LONG).show();
        else if(firstName_ET.getText().toString().isEmpty())
            Toast.makeText(getActivity(),"Mr/Mrs "+ surName_ET.getText() + " you forgot to write your first name please rewrite again." ,Toast.LENGTH_LONG).show();
        else if(surName_ET.getText().toString().isEmpty())
            Toast.makeText(getActivity(),firstName_ET.getText() + " you forgot to write your surname please rewrite again." ,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getActivity(),"Welcome " + firstName_ET.getText() + " "+ surName_ET.getText() ,Toast.LENGTH_LONG).show();
    }



}
