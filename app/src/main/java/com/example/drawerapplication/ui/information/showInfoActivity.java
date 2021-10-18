package com.example.drawerapplication.ui.information;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.drawerapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class showInfoActivity extends AppCompatActivity {
    TextView txtName, txtAge, txtAddress, txtContact, Date_In, newID, Time_In;
    public Button btnRegister, btnTimeIn;
    ImageView txtImage;
    private Bitmap bitmap;
    private QRGEncoder idEncoder;
    public String dataID;
    public Cursor cursor;
    private androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private InformationFragment informationFragment;
    DatabaseHelper mdatabasehelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);

        Intent intent = getIntent();
        dataID = String.valueOf(intent.getStringExtra("ayDi"));

        txtName = (TextView) findViewById(R.id.name_Text);
        txtAddress = (TextView) findViewById(R.id.address_Text);
        txtAge = (TextView) findViewById(R.id.age_Text);
        txtContact = (TextView) findViewById(R.id.contact_Text);
        txtImage = (ImageView) findViewById(R.id.qrImage);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnTimeIn = (Button) findViewById(R.id.btnTimeIn);
        Date_In = (TextView)findViewById(R.id.Date_In);
        Time_In = (TextView)findViewById(R.id.Time_In);
        newID = (TextView)findViewById(R.id.newID);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        Date_In.setText(currentDate);

        Time_In.setText(getCurrentTime());


        mdatabasehelper = new DatabaseHelper(showInfoActivity.this);
//Instead of retrieving data on DatabaseHelper, we can do that here


        DatabaseHelper db = new DatabaseHelper(this);
        cursor = db.getID(dataID,txtName,txtAddress,txtAge,txtContact,txtImage);
        if(cursor.getCount() == 0){

            btnRegister.setVisibility(View.VISIBLE);
            btnRegister.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    informationFragment = new InformationFragment();
                    fragmentTransaction.replace(R.id.new_layout, informationFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            Toast.makeText(showInfoActivity.this, "Not yet registered", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                newID.setText(cursor.getString(0));
                txtName.setText(cursor.getString(1));
                txtAddress.setText(cursor.getString(2));
                txtAge.setText(cursor.getString(3));
                txtContact.setText(cursor.getString(4));

                btnTimeIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mdatabasehelper.addTimeInData(Integer.parseInt(newID.getText().toString().trim()),
                                Date_In.getText().toString().trim(), Time_In.getText().toString().trim());

                        Toast.makeText(showInfoActivity.this, "Time In Successfully", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        }


        String data = String.valueOf(dataID);
        if(data.length() > 0) {

            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            idEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, smallerDimension);

            idEncoder.setColorBlack(Color.BLACK);
            idEncoder.setColorWhite(Color.WHITE);

            try {

                bitmap = idEncoder.getBitmap();
                txtImage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getCurrentTime(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
}
