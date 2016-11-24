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
    public static final String MEASUREMENTS_COLUMN_HEIGHT      = "height";
    public static final String MEASUREMENTS_COLUMN_DATE        = "date";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact (String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(USERS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}