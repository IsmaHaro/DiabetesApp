package com.example.root.diabetesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeActivity extends Fragment{

    DBHelper mydb;
    SharedPreference shared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.activity_welcome,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mydb = new DBHelper(getActivity());
        shared = new SharedPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle("DiabetesApp");

        ((TextView) getView().findViewById(R.id.current_date_welcome)).setText("Fecha: "+currentDateandTime.toString());
        ((TextView) getView().findViewById(R.id.welcome_username)).setText("Bienvenido: "+shared.getValue(getActivity(), "name"));
    }

}
