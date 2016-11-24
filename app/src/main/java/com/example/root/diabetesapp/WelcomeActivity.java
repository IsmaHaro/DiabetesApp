package com.example.root.diabetesapp;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    DBHelper mydb;
    SharedPreference shared;
    private String[] mPlaneTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mydb = new DBHelper(this);
        shared = new SharedPreference();
        ((TextView) findViewById(R.id.welcome_username)).setText(shared.getValue(this, "name"));

        mPlaneTitles = getResources().getStringArray(R.array.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, mPlaneTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Toast.makeText(this,"Position: "+position,Toast.LENGTH_SHORT).show();
    }


}
