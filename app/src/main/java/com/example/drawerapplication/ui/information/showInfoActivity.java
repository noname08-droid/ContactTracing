package com.example.drawerapplication.ui.information;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drawerapplication.R;

public class showInfoActivity extends AppCompatActivity {
    TextView txtName, txtAge, txtAddress, txtContact;
    ImageView txtImage;
    DatabaseHelper databaseHelper;
//asdfoiutwo
//    String dataID = getIntent().getExtras().getString("ResultID");
    Bundle extras = getIntent().getExtras();
    String dataID = extras.getString("ResultID");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);
        txtName = (TextView) findViewById(R.id.name_Text);
        txtAddress = (TextView) findViewById(R.id.address_Text);
        txtAge = (TextView) findViewById(R.id.age_Text);
        txtContact = (TextView) findViewById(R.id.contact_Text);
        txtImage = (ImageView) findViewById(R.id.qrImage);



//        databaseHelper.getID(dataID, txtName, txtAddress,
//                txtAge, txtContact);

        Toast.makeText(showInfoActivity.this, "value of id" + dataID, Toast.LENGTH_SHORT).show();
    }
}
