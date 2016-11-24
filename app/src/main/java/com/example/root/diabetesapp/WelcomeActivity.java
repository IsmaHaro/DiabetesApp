package com.example.root.diabetesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeActivity extends AppCompatActivity {
    DBHelper mydb;
    SharedPreference shared;
    private String[] mPlaneTitles;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        showToolbar("Bienvenido", false);

        mydb = new DBHelper(this);
        shared = new SharedPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        ((TextView) findViewById(R.id.current_date_welcome)).setText("Fecha: "+currentDateandTime.toString());
        ((TextView) findViewById(R.id.welcome_username)).setText("Bienvenido: "+shared.getValue(this, "name"));

        mPlaneTitles = getResources().getStringArray(R.array.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, mPlaneTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        /*
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

             Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle("Menu");
            }


             Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("");
            }
        };


        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        */

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

        mydb.insertMeasurement(id, description, glucose, weight, height, currentDateandTime);

        Toast.makeText(this,"Agregado medición de glucosa",Toast.LENGTH_SHORT).show();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
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

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }


}
