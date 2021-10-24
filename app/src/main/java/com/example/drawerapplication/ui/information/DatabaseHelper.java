package com.example.drawerapplication.ui.information;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private Context context;
    private static final String TABLE_NAME = "INFO";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "ADDRESS";
    private static final String COL4 = "AGE";
    private static final String COL5 = "CONTACT";
    private static final String COL6 = "DATE";
    private static final String COL7 = "TIME";

    private static final String TABLE_NAME2 = "TIME";
    private static final String T2COL1 = "ID";
    private static final String T2COL0 = "NAME";
    private static final String T2COL2 = "DATE_IN";
    private static final String T2COL3 = "Time_IN";


    public DatabaseHelper(@Nullable FragmentActivity context) {
        super(context, TABLE_NAME, null, 2);
        this.context = context;
    }



    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " +
                COL5 + " INTEGER, " + COL6 + " TEXT, " + COL7 + " TEXT);";
        sqLiteDatabase.execSQL(createTable);

        String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " (ID INTEGER, " + T2COL0 + " TEXT,"
                + T2COL2 + " TEXT, " + T2COL3 + " TEXT);";
        sqLiteDatabase.execSQL(createTable2);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String name, String address, String age, Long contact, String date, String time){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, String.valueOf(name));
        contentValues.put(COL3, String.valueOf(address));
        contentValues.put(COL4, String.valueOf(age));
        contentValues.put(COL5, String.valueOf(contact));
        contentValues.put(COL6, String.valueOf(date));
        contentValues.put(COL7, String.valueOf(time));

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean addTimeInData( Integer id, String Name, String timeIN, String timeIN2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(T2COL1, Integer.parseInt(String.valueOf(id)));
        contentValues.put(T2COL0, String.valueOf(Name));
        contentValues.put(T2COL2, String.valueOf(timeIN));
        contentValues.put(T2COL3, String.valueOf(timeIN2));

        long result = db.insert(TABLE_NAME2, null, contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getTimeAndDateData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getID(String id, TextView txtName, TextView txtAge, TextView txtAddress, TextView txtContact, ImageView txtImage) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                "ID" + "=?", new String[]{String.valueOf(id)});

        return res;
    }

    public Cursor getName(TextView id, TextView storageName, TextView storageTime, TextView storageDate){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE "
                + "ID" + "=?", new String[]{String.valueOf(id)});
        return res;
    }

}
