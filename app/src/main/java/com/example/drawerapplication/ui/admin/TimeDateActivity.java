package com.example.drawerapplication.ui.admin;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;
import com.example.drawerapplication.ui.information.CustomAdapter;
import com.example.drawerapplication.ui.information.DatabaseHelper;
import com.example.drawerapplication.ui.information.showInfoActivity;

import java.util.ArrayList;

public class TimeDateActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    RecyclerView timeAndDate;
    ArrayList<String> id, name,time, date;
    TimeAndDateAdapter timeAndDateAdapter;
    String searchName;
    EditText search_Name;
    TextView storageId, storageName, storageTime, storageDate;
    Button searchNow;
    private Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeanddate_list);
        timeAndDate = (RecyclerView) findViewById(R.id.timeAndDate);
        search_Name = (EditText) findViewById(R.id.searchName);
        searchNow = (Button) findViewById(R.id.btnsearch);
        storageId = findViewById(R.id.storageID);
        storageName = findViewById(R.id.storageName);
        storageTime = findViewById(R.id.storageTime);
        storageDate = findViewById(R.id.storageDate);

        mDatabaseHelper = new DatabaseHelper(TimeDateActivity.this);

        DatabaseHelper db = new DatabaseHelper(this);

        //EditText to Search!;
        if (search_Name.getText() == timeAndDateAdapter.id) {

            cursor = db.getName(search_Name, storageName, storageTime, storageDate);

            if (cursor.getCount() == 0) {
                Toast.makeText(TimeDateActivity.this, "noData", Toast.LENGTH_SHORT).show();
            } else {
                //Button to search!;
                searchNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        while (cursor.moveToNext()) {
                            storageId.setText(cursor.getString(0));
                            storageName.setText(cursor.getString(1));
                            storageTime.setText(cursor.getString(2));
                            storageDate.setText(cursor.getString(3));
                        }

                    }
                });
            }
        }

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
