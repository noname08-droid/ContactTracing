package com.example.drawerapplication.ui.information;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.drawerapplication.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private Context context;
    private static final String TABLE_NAME = "INFO";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "ADDRESS";
    private static final String COL4 = "AGE";
    private static final String COL5 = "CONTACT";


    public DatabaseHelper(@Nullable FragmentActivity context) {
        super(context, TABLE_NAME, null, 1);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " INTEGER);";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String name, String address, String age, Long contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, String.valueOf(name));
        contentValues.put(COL3, String.valueOf(address));
        contentValues.put(COL4, String.valueOf(age));
        contentValues.put(COL5, String.valueOf(contact));
        Log.d(TAG, "addData: Adding " + name + address + age + contact +
                " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
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

    public Cursor getID(int id, TextView txtName, TextView txtAge, TextView txtAddress, TextView txtContact) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                "_id" + "=?", new String[]{Integer.toString(id)});

        while(res.moveToFirst()){

            txtName.setText(res.getString(Integer.parseInt(res.getString(0))));
            txtAddress.setText(res.getString(Integer.parseInt(res.getString(1))));
            txtAge.setText(res.getString(Integer.parseInt(res.getString(2))));
            txtContact.setText(res.getString(Integer.parseInt(res.getString(3))));

        }

        return res;
    }
}
