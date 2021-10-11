package com.example.drawerapplication.ui.information;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drawerapplication.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class showInfoActivity extends AppCompatActivity {
    TextView txtName, txtAge, txtAddress, txtContact;
    ImageView txtImage;
    private Bitmap bitmap;
    private QRGEncoder idEncoder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);

        Intent intent = getIntent();
        Integer dataID =Integer.parseInt(intent.getStringExtra("ayDi"));

        txtName = (TextView) findViewById(R.id.name_Text);
        txtAddress = (TextView) findViewById(R.id.address_Text);
        txtAge = (TextView) findViewById(R.id.age_Text);
        txtContact = (TextView) findViewById(R.id.contact_Text);
        txtImage = (ImageView) findViewById(R.id.qrImage);

//Instead of retrieving data on DatabaseHelper, we can do that here
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getID(dataID,txtName,txtAddress,txtAge,txtContact,txtImage);
        cursor.moveToFirst();

        txtName.setText(cursor.getString(1));//columnindex 0 is for ID number
        txtAddress.setText(cursor.getString(2));
        txtAge.setText(cursor.getString(3));
        txtContact.setText(cursor.getString(4));
        txtImage.setImageResource(cursor.getPosition());

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
