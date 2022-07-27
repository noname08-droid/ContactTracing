package com.example.drawerapplication.ui.admin;

import android.content.Intent;
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

public class ResearchResult extends AppCompatActivity {

    String ResultID;
    public Cursor cursor;
    DatabaseHelper mdatabasehelper;

    RecyclerView timeandDateReycler;
    ArrayList<String> resultId, resultName, resultTime, resultDate;
    ResultAdapter resultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultrecyclerview);
        timeandDateReycler = (RecyclerView) findViewById(R.id.timeandDateReycler);

        Intent intent = getIntent();
        ResultID = String.valueOf(intent.getStringExtra("newID"));

        populateTimeAndDate();


        mdatabasehelper = new DatabaseHelper(ResearchResult.this);

        DatabaseHelper db = new DatabaseHelper(this);
        cursor = db.myResultID(ResultID, resultName,resultTime,resultDate);
        if(cursor.getCount() == 0){
            Toast.makeText(ResearchResult.this, "Not yet registered", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {
                resultId.add(cursor.getString(0));
                resultName.add(cursor.getString(1));
                resultTime.add(cursor.getString(2));
                resultDate.add(cursor.getString(3));
            }
        }


        resultAdapter = new ResultAdapter(ResearchResult.this, resultId, resultName,resultTime,resultDate);
        timeandDateReycler.setAdapter(resultAdapter);
        timeandDateReycler.setLayoutManager(new LinearLayoutManager(ResearchResult.this));
    }

    private void populateTimeAndDate() {

        mdatabasehelper = new DatabaseHelper(ResearchResult.this);
        resultId = new ArrayList<>();
        resultName = new ArrayList<>();
        resultTime = new ArrayList<>();
        resultDate = new ArrayList<>();

    }
}
