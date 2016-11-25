package com.example.root.diabetesapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Profile extends Fragment {
    DBHelper mydb;
    SharedPreference shared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        return inflater.inflate(R.layout.activity_profile,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mydb = new DBHelper(getActivity());
        shared = new SharedPreference();

        /*
         * Load user information
         */
        Cursor cursor = mydb.getUserByEmail(shared.getValue(getActivity(), "email"));
        cursor.moveToFirst();

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Perfil");


        ((EditText) getView().findViewById(R.id.name_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_NAME)));
        ((EditText) getView().findViewById(R.id.email_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_EMAIL)));
        ((EditText) getView().findViewById(R.id.weight_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_WEIGHT)));
        ((EditText) getView().findViewById(R.id.height_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_HEIGHT)));
        ((EditText) getView().findViewById(R.id.doctor_email_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_DOCTOR_EMAIL)));
        ((EditText) getView().findViewById(R.id.doctor_cel_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_DOCTOR_CEL)));
        ((EditText) getView().findViewById(R.id.doctor_name_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_DOCTOR_NAME)));
    }

}
