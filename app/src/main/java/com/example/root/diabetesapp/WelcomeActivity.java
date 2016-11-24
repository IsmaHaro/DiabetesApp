package com.example.root.diabetesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DBHelper mydb;
    SharedPreference shared;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //showToolbar("", false);

        mydb = new DBHelper(this);
        shared = new SharedPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        ((TextView) findViewById(R.id.current_date_welcome)).setText("Fecha: "+currentDateandTime.toString());
        ((TextView) findViewById(R.id.welcome_username)).setText("Bienvenido: "+shared.getValue(this, "name"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent;

        switch (id){
            case R.id.nav_profile:
                intent = new Intent(this, Profile.class);
                startActivity(intent);
                break;

            case R.id.nav_grafic:
                intent = new Intent(this, Grafic.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void saveGlucose(View v){
        Cursor cursor = mydb.getUserByEmail(shared.getValue(this, "email"));
        cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndex(DBHelper.USERS_COLUMN_ID));
        String weight = cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_WEIGHT));
        String height = cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_HEIGHT));

        EditText descriptionE = (EditText) findViewById(R.id.description_welcome);
        EditText glucoseE    = (EditText) findViewById(R.id.glucose_welcome);

        String description = descriptionE.getText().toString();
        String glucose     = glucoseE.getText().toString();

        descriptionE.setText("");
        glucoseE.setText("");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        int radio_selected = ((RadioGroup) findViewById(R.id.type_welcome)).getCheckedRadioButtonId();

        String type = "";

        switch(radio_selected){
            case R.id.post:
                type = "post";
                ((RadioButton) findViewById(R.id.post)).setChecked(false);
                break;
            case R.id.pre:
                type = "pre";
                ((RadioButton) findViewById(R.id.pre)).setChecked(false);
                break;
        }


        mydb.insertMeasurement(id, description, glucose, type, weight, height, currentDateandTime);

        Toast.makeText(this,"Agregado medición de glucosa",Toast.LENGTH_SHORT).show();
    }


    private void selectItem(int position) {
        //Toast.makeText(this,"Position: "+position,Toast.LENGTH_SHORT).show();

        position++;
        Intent intent;

        switch (position){
            case 1:
                intent = new Intent(this, Profile.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, Grafic.class);
                startActivity(intent);
                break;
        }
    }


}
