package com.example.root.diabetesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    DBHelper mydb;
    SharedPreference shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mydb = new DBHelper(this);
        shared = new SharedPreference();
        ((TextView) findViewById(R.id.welcome_username)).setText(shared.getValue(this, "name"));
    }
}
