package com.example.root.diabetesapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    DBHelper mydb;
    SharedPreference shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mydb = new DBHelper(this);
        shared = new SharedPreference();
        showToolbar("Perfil", true);

        /*
         * Load user information
         */
        Cursor cursor = mydb.getUserByEmail(shared.getValue(this, "email"));
        cursor.moveToFirst();

        ((EditText) findViewById(R.id.name_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_NAME)));
        ((EditText) findViewById(R.id.email_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_EMAIL)));
        ((EditText) findViewById(R.id.weight_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_WEIGHT)));
        ((EditText) findViewById(R.id.height_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_HEIGHT)));
        ((EditText) findViewById(R.id.doctor_email_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_DOCTOR_EMAIL)));
        ((EditText) findViewById(R.id.doctor_cel_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_DOCTOR_CEL)));
        ((EditText) findViewById(R.id.doctor_name_profile)).setText(cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_DOCTOR_NAME)));
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void saveProfile(View v){
        Cursor cursor = mydb.getUserByEmail(shared.getValue(this, "email"));
        cursor.moveToFirst();

        String name = ((EditText) findViewById(R.id.name_profile)).getText().toString();
        String email = ((EditText) findViewById(R.id.email_profile)).getText().toString();
        String weight = ((EditText) findViewById(R.id.weight_profile)).getText().toString();
        String height = ((EditText) findViewById(R.id.height_profile)).getText().toString();
        String doctor_email = ((EditText) findViewById(R.id.doctor_email_profile)).getText().toString();
        String doctor_cel = ((EditText) findViewById(R.id.doctor_cel_profile)).getText().toString();
        String doctor_name = ((EditText) findViewById(R.id.doctor_name_profile)).getText().toString();

        mydb.updateUser(cursor.getInt(cursor.getColumnIndex(DBHelper.USERS_COLUMN_ID)), name, email, weight, height, doctor_email, doctor_cel, doctor_name);

        shared.save(this, "name", name);
        shared.save(this, "email", email);

        Toast.makeText(getApplicationContext(), "Perfil Actualizado", Toast.LENGTH_SHORT).show();
    }
}
