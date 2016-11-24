package com.example.root.diabetesapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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

        /**
         * GET USER INFORMATION
         */
        int id              = cursor.getInt(cursor.getColumnIndex(DBHelper.USERS_COLUMN_ID));
        String user_name    = cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_NAME));
        String doctor_email = cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_DOCTOR_EMAIL));
        String doctor_cel   = cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_DOCTOR_CEL));
        String doctor_name  = cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_NAME));
        String weight       = cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_WEIGHT));
        String height       = cursor.getString(cursor.getColumnIndex(DBHelper.USERS_COLUMN_HEIGHT));

        EditText descriptionE = (EditText) findViewById(R.id.description_welcome);
        EditText glucoseE     = (EditText) findViewById(R.id.glucose_welcome);

        String description = descriptionE.getText().toString();
        String glucose     = glucoseE.getText().toString();
        int glucoseI = Integer.parseInt(glucose);

        descriptionE.setText("");
        glucoseE.setText("");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        int radio_selected = ((RadioGroup) findViewById(R.id.type_welcome)).getCheckedRadioButtonId();

        String type = "";
        String message = "";
        String subject = "Nivel de glucosa - Paciente: "+user_name;

        switch(radio_selected){
            case R.id.pre:
                type = "pre";
                ((RadioButton) findViewById(R.id.pre)).setChecked(false);

                /*
                 * CHECK GLUCOSE FOR PRE
                 * RANGE > 70 AND < 100
                 */
                if(glucoseI >= 70 && glucoseI <=100){
                    Toast.makeText(this, "Usted tiene un nivel dentro de lo normal", Toast.LENGTH_SHORT).show();

                }else if(glucoseI > 100){
                    /**
                     * GLUCOSE OUT OF NORMAL
                     */
                    message = "El paciente: "+user_name+" ha presentado un nivel de glucosa ALTO: "+glucose+" mg/dl";
                    Toast.makeText(this,"Nivel de glucosa ALTO",Toast.LENGTH_SHORT).show();
                    sendsms(doctor_cel, message);
                    sendmail(doctor_email, subject, message);

                }else if(glucoseI < 70){
                    /**
                     * GLUCOSE OUT OF NORMAL
                     */
                    message = "El paciente: "+user_name+" ha presentado un nivel de glucosa BAJO: "+glucose+" mg/dl";
                    Toast.makeText(this,"Nivel de glucosa BAJO",Toast.LENGTH_SHORT).show();
                    sendsms(doctor_cel, message);
                    sendmail(doctor_email, subject, message);
                }

                break;

            case R.id.post:
                type = "post";
                ((RadioButton) findViewById(R.id.post)).setChecked(false);

                /*
                 * CHECK GLUCOSE FOR PRE
                 * RANGE > 70 AND < 140
                 */
                if(glucoseI >= 70 && glucoseI <=140){
                    Toast.makeText(this, "Usted tiene un nivel dentro de lo normal", Toast.LENGTH_SHORT).show();

                }else if(glucoseI > 140){
                    /**
                     * GLUCOSE OUT OF NORMAL
                     */
                    message = "El paciente: "+user_name+" ha presentado un nivel de glucosa ALTO: "+glucose+" mg/dl";
                    Toast.makeText(this,"Nivel de glucosa ALTO",Toast.LENGTH_SHORT).show();
                    sendsms(doctor_cel, message);
                    sendmail(doctor_email, subject, message);

                }else if(glucoseI < 70){
                    /**
                     * GLUCOSE OUT OF NORMAL
                     */
                    message = "El paciente: "+user_name+" ha presentado un nivel de glucosa BAJO: "+glucose+" mg/dl";
                    Toast.makeText(this,"Nivel de glucosa BAJO",Toast.LENGTH_SHORT).show();
                    sendsms(doctor_cel, message);
                    sendmail(doctor_email, subject, message);
                }
                break;
        }


        mydb.insertMeasurement(id, description, glucose, type, weight, height, currentDateandTime);

        //Toast.makeText(this,"Agregado medición de glucosa",Toast.LENGTH_SHORT).show();
    }


    private void sendsms(String phone,String msj){
        // SmsManager smsmanager = SmsManager.getDefault();
        //smsmanager.sendTextMessage(phone, null, msj, null, null);
        Uri uri = Uri.parse("smsto:" +phone);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", msj);
        startActivity(it);
    }


    public void sendmail(String destine, String subject, String body){
		/* es necesario un intent que levante la actividad deseada */
        Intent itSend = new Intent(android.content.Intent.ACTION_SEND);

        /* vamos a enviar texto plano a menos que el checkbox est� marcado */
        itSend.setType("plain/text");

        /* colocamos los datos para el env�o */
        itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ destine });
        itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, subject );
        itSend.putExtra(android.content.Intent.EXTRA_TEXT, body );
        /* iniciamos la actividad */
        startActivity(itSend);

    }


}
