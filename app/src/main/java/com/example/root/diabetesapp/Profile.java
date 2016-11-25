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
import android.widget.Toast;


public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DBHelper mydb;
    SharedPreference shared;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mydb = new DBHelper(this);
        shared = new SharedPreference();
     //   showToolbar("Perfil", false);

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
            case R.id.nav_welcome:
                intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_historic:
                intent = new Intent(this, HistoricActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_profile:
                intent = new Intent(this, Profile.class);
                startActivity(intent);
                break;

            case R.id.nav_grafic:
                intent = new Intent(this, Grafic.class);
                startActivity(intent);
                break;

            case R.id.nav_logout:
                shared.clearSharedPreference(this);
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
