package com.example.drawerapplication.ui.information;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String TABLE_DATE = "TIME";
    private static final String ID = "ID";
    private static final String DATE = "DATE";



    public NewHelper(@Nullable Context context) {
        super(context, TABLE_DATE, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_DATE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " DATE);";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE);
        onCreate(db);
    }

//    public long addDATE(String date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
//        ContentValues contentvalues = new ContentValues();
//        contentvalues.put(DATE,String.valueOf(Date.valueOf(date)));
//        long result = db.insert(TABLE_DATE, date, contentvalues);
//        return result;
//    }
public String getDateTime(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    return dateFormat.format(date);
}

}
