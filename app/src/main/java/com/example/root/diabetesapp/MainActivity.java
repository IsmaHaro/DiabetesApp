package com.example.root.diabetesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper mydb;
    SharedPreference shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DBHelper(this);
        shared = new SharedPreference();
        setContentView(R.layout.activity_main);
    }


    protected void registerView(View v){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    protected void Login(View v){
        String email    = ((EditText) findViewById(R.id.email_login)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_login)).getText().toString();

        Cursor cursor = mydb.getUserLogin(email, password);

        if(cursor.moveToFirst()){
            /*
             * Login
             */
            shared.save(this, "name", cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_NAME)));
            shared.save(this, "email", email);

            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}
