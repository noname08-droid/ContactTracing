package com.example.drawerapplication.ui.information;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;

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

protected void getDateTime(String datetime) {
//    SimpleDateFormat dateFormat = new SimpleDateFormat(
//            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//
//    return dateFormat.format(date);
    try{
        String dateobj = "Fri Oct 15 04:45:21 EDT 2021";
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); //
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss"); // for display

        Date date = (Date) format.parse(dateobj); // parse it to date
        System.out.print(format1.format(date)); // display the date to format you like
        // the print result is : 15.10.2021. 08:45:21
    } catch (Exception e){
        System.out.print("error");
    }
}

}
