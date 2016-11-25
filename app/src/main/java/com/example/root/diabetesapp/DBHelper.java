package com.example.root.diabetesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDataBase.db";

    /*
     * Tabla de Usuarios
     */
    public static final String USERS_TABLE_NAME          = "users";
    public static final String USERS_COLUMN_ID           = "id";
    public static final String USERS_COLUMN_NAME         = "name";
    public static final String USERS_COLUMN_EMAIL        = "email";
    public static final String USERS_COLUMN_PASSWORD     = "password";
    public static final String USERS_COLUMN_WEIGHT       = "weight";
    public static final String USERS_COLUMN_HEIGHT       = "height";
    public static final String USERS_COLUMN_DOCTOR_EMAIL = "doctor_email";
    public static final String USERS_COLUMN_DOCTOR_CEL   = "doctor_cel";
    public static final String USERS_COLUMN_DOCTOR_NAME  = "doctor_name";

    /*
     * Tabla de Medidas
     */
    public static final String MEASUREMENTS_TABLE_NAME         = "measurements";
    public static final String MEASUREMENTS_COLUMN_ID          = "id";
    public static final String MEASUREMENTS_COLUMN_USER_ID     = "user_id";
    public static final String MEASUREMENTS_COLUMN_DESCRIPTION = "description";
    public static final String MEASUREMENTS_COLUMN_GLUCOSE     = "glucose";
    public static final String MEASUREMENTS_COLUMN_WEIGHT      = "weight";
    public static final String MEASUREMENTS_COLUMN_TYPE        = "type";
    public static final String MEASUREMENTS_COLUMN_HEIGHT      = "height";
    public static final String MEASUREMENTS_COLUMN_DATE        = "date";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE  users " +
                        "(id           INTEGER PRIMARY KEY, " +
                        " name         TEXT," +
                        " email        TEXT, " +
                        " password     TEXT," +
                        " weight       TEXT," +
                        " height       TEXT," +
                        " doctor_email TEXT," +
                        " doctor_cel   TEXT," +
                        " doctor_name  TEXT)"
        );

        db.execSQL(
                "CREATE TABLE  measurements " +
                        "(id          INTEGER PRIMARY KEY, " +
                        " user_id     INTEGER," +
                        " description TEXT, " +
                        " glucose     REAL," +
                        " type        TEXT," +
                        " weight      TEXT," +
                        " height      TEXT," +
                        " date        TEXT)"
        );
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS measurements");
        onCreate(db);
    }

    public boolean insertUser (String name, String email, String password, String weight, String height, String doctor_email, String doctor_cel, String doctor_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("weight", weight);
        contentValues.put("height", height);
        contentValues.put("doctor_email", doctor_email);
        contentValues.put("doctor_cel", doctor_cel);
        contentValues.put("doctor_name", doctor_name);
        db.insert("users", null, contentValues);
        return true;
    }

    public boolean insertMeasurement(int user_id, String description, String glucose, String type, String weight, String height, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", user_id);
        contentValues.put("description", description);
        contentValues.put("glucose", glucose);
        contentValues.put("type", type);
        contentValues.put("weight", weight);
        contentValues.put("height", height);
        contentValues.put("date", date);
        db.insert("measurements", null, contentValues);
        return true;
    }

    public Cursor getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM users WHERE id="+id+"", null );
        return res;
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM users WHERE email='"+email+"'", null );
        return res;
    }

    public Cursor getUserLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM users WHERE email='"+email+"' AND password='"+password+"'", null );
        return res;
    }

    public Cursor getMeasurementById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM measurements WHERE id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateUser(Integer id, String name, String email, String weight, String height, String doctor_email, String doctor_cel, String doctor_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("weight", weight);
        contentValues.put("height", height);
        contentValues.put("doctor_email", doctor_email);
        contentValues.put("doctor_cel", doctor_cel);
        contentValues.put("doctor_name", doctor_name);

        db.update("users", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public Integer deleteUserById(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteUserByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users",
                "email = ? ",
                new String[] { email.toString() });
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM users", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(USERS_COLUMN_NAME)));
            res.moveToNext();
        }

        return array_list;
    }

    public ArrayList<String> getAllMeasurementsOfAUser(String id) {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM measurements WHERE user_id = " + id, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MEASUREMENTS_COLUMN_GLUCOSE)));
            res.moveToNext();
        }

        return array_list;
    }

    public ArrayList<String> getAllMeasurementsOfAUserHistoric(String id) {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM measurements WHERE user_id = " + id, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String cad = "Glucosa: "+res.getString(res.getColumnIndex(MEASUREMENTS_COLUMN_GLUCOSE));
            cad += "\nTipo de Medición (comida): "+res.getString(res.getColumnIndex(MEASUREMENTS_COLUMN_TYPE));
            cad += "\nFecha: "+res.getString(res.getColumnIndex(MEASUREMENTS_COLUMN_DATE));
            cad += "\nComentario: "+res.getString(res.getColumnIndex(MEASUREMENTS_COLUMN_DESCRIPTION));

            array_list.add(cad);
            res.moveToNext();
        }

        return array_list;
    }
}
