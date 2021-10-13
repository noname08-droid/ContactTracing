package com.example.drawerapplication.ui.information;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drawerapplication.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class showInfoActivity extends AppCompatActivity {
    TextView txtName, txtAge, txtAddress, txtContact;
    Button btnRegister;
    ImageView txtImage;
    private Bitmap bitmap;
    private QRGEncoder idEncoder;
    public Integer dataID;
    public Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);
        try {
        Intent intent = getIntent();
        dataID = Integer.parseInt(intent.getStringExtra("ayDi"));
        txtName = (TextView) findViewById(R.id.name_Text);
        txtAddress = (TextView) findViewById(R.id.address_Text);
        txtAge = (TextView) findViewById(R.id.age_Text);
        txtContact = (TextView) findViewById(R.id.contact_Text);
        txtImage = (ImageView) findViewById(R.id.qrImage);

        DatabaseHelper db = new DatabaseHelper(this);
        cursor = db.getID(dataID,txtName,txtAddress,txtAge,txtContact,txtImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        btnRegister = (Button) findViewById(R.id.btnRegister);
        if(cursor == null){
            btnRegister.setVisibility(View.VISIBLE);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            Toast.makeText(showInfoActivity.this, "Not yet registered", Toast.LENGTH_SHORT).show();
        }else {
            //Instead of retrieving data on DatabaseHelper, we can do that here
                cursor.moveToFirst();
                txtName.setText(cursor.getString(1));//columnindex 0 is for ID number
                txtAddress.setText(cursor.getString(2));
                txtAge.setText(cursor.getString(3));
                txtContact.setText(cursor.getString(4));
                txtImage.setImageResource(cursor.getPosition());
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
}
