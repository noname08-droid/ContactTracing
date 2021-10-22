package com.example.drawerapplication.ui.information;


import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {


    DatabaseHelper mDatabaseHelper;
    RecyclerView mRecyclerView;
    ArrayList<String> id, name, address, age, contact, date, time;
    CustomAdapter customAdapter;

    @RequiresApi(api = Build.VERSION_CODES.R)
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        populateRecyclerView();

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void populateRecyclerView() {
        mDatabaseHelper = new DatabaseHelper(ListDataActivity.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        address = new ArrayList<>();
        age = new ArrayList<>();
        contact = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();

        displayData();
        customAdapter = new CustomAdapter(ListDataActivity.this,id, name, address, age, contact, date, time);
        mRecyclerView.setAdapter(customAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListDataActivity.this));

        }

        void displayData() {
        Cursor cursor = mDatabaseHelper.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(ListDataActivity.this, "No Data found", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                address.add(cursor.getString(2));
                age.add(cursor.getString(3));
                contact.add(cursor.getString(4));
                date.add(cursor.getString(5));
                time.add(cursor.getString(6));
            }
        }


    }
}
