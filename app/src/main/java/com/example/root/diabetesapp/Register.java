package com.example.root.diabetesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    DBHelper mydb;
    SharedPreference shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mydb = new DBHelper(this);
        shared = new SharedPreference();
        showToolbar("Registro", true);

    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void registerUser(View view){
        /*
         * RETRIEVE USER INFORMATION
         */
        String email        = ((EditText) findViewById(R.id.email_register)).getText().toString();

        Cursor cursor = mydb.getUserByEmail(email);

        if (cursor.moveToFirst()){
            Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();

        }else{
            String name         = ((EditText) findViewById(R.id.name_register)).getText().toString();
            String password     = ((EditText) findViewById(R.id.password_register)).getText().toString();
            String weight       = ((EditText) findViewById(R.id.weight_register)).getText().toString();
            String height       = ((EditText) findViewById(R.id.height_register)).getText().toString();
            String doctor_email = ((EditText) findViewById(R.id.doctor_email_register)).getText().toString();
            String doctor_cel   = ((EditText) findViewById(R.id.doctor_cel_register)).getText().toString();
            String doctor_name  = ((EditText) findViewById(R.id.doctor_name_register)).getText().toString();


            boolean res =  mydb.insertUser(name, email, password, weight, height, doctor_email, doctor_cel, doctor_name);


            if(res){
                Toast.makeText(getApplicationContext(), "Usuario: "+name+"Registrado", Toast.LENGTH_SHORT).show();

            /*
             * STORE SESSION INFO
             */
            cursor = mydb.getUserByEmail(email);
            cursor.moveToFirst();
            shared.save(this, "name", name);
            shared.save(this, "email", email);
            shared.save(this, "id", cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_ID)));
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), "ERROR AL REGISTRAR", Toast.LENGTH_SHORT).show();
            }
        }
    }

}





