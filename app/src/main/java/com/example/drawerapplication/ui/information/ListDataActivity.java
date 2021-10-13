package com.example.drawerapplication.ui.information;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {


    DatabaseHelper mDatabaseHelper;
    RecyclerView mRecyclerView;
    ArrayList<String> id, name, address, age, contact;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        populateRecyclerView();

    }

    private void populateRecyclerView() {
        mDatabaseHelper = new DatabaseHelper(ListDataActivity.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        address = new ArrayList<>();
        age = new ArrayList<>();
        contact = new ArrayList<>();

        displayData();
        customAdapter = new CustomAdapter(ListDataActivity.this,id, name, address, age, contact);
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
            }
        }


    }
}
