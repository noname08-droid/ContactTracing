package com.example.drawerapplication.ui.admin;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;
import com.example.drawerapplication.ui.information.DatabaseHelper;

import java.util.ArrayList;

public class TimeDateActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    RecyclerView timeAndDate;
    ArrayList<String> id, name,time, date;
    TimeAndDateAdapter timeAndDateAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeanddate_list);
        timeAndDate = (RecyclerView) findViewById(R.id.timeAndDate);


        populateTimeAndDate();
    }


    private void populateTimeAndDate() {
        mDatabaseHelper = new DatabaseHelper(TimeDateActivity.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        time = new ArrayList<>();
        date = new ArrayList<>();

        displayData();
        timeAndDateAdapter = new TimeAndDateAdapter(TimeDateActivity.this,id,name,time,date);
        timeAndDate.setAdapter(timeAndDateAdapter);
        timeAndDate.setLayoutManager(new LinearLayoutManager(TimeDateActivity.this));
    }

    private void displayData() {
        Cursor cursor = mDatabaseHelper.getTimeAndDateData();
        if (cursor.getCount() == 0) {
            Toast.makeText(TimeDateActivity.this, "No Data found", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {

                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                time.add(cursor.getString(2));
                date.add(cursor.getString(3));

            }
        }
    }
}
