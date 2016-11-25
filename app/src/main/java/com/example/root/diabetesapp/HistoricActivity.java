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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoricActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    DBHelper mydb;
    SharedPreference shared;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
        mydb = new DBHelper(this);
        shared = new SharedPreference();

        String id = shared.getValue(this, "id");
        Cursor cursor = mydb.getUserByEmail(shared.getValue(this, "email"));
        cursor.moveToFirst();

        ArrayList<String>  glucose =  mydb.getAllMeasurementsOfAUserHistoric(shared.getValue(this, "id"));

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.list_view, glucose);

        ListView listView = (ListView) findViewById(R.id.historic_list);
        listView.setAdapter(adapter);

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


}
