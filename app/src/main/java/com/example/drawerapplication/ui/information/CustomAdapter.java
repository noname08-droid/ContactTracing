package com.example.drawerapplication.ui.information;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;


import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    public ArrayList id, name, address, age, contact;
    public Bitmap bitmap;
    public QRGEncoder idEncoder;
    public String Put_ID;
    private ImageView ImageID;
    public ImageView ShowImageID;

    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private AppCompatActivity activity;
    List<String> recyclerListAll;

    public CustomAdapter(Context context, ArrayList id,
                         ArrayList name, ArrayList address,
                         ArrayList age, ArrayList contact){

        this.context = context;
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.contact = contact;
        this.recyclerListAll = new ArrayList<>(name);

    }

    public CustomAdapter(FragmentActivity activity) {
        this.context = activity;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.list_Id.setText(String.valueOf(id.get(position)));
        holder.list_Name.setText(String.valueOf(name.get(position)));
        holder.list_Address.setText(String.valueOf(address.get(position)));
        holder.list_Age.setText(String.valueOf(age.get(position)));
        holder.list_Contact.setText(String.valueOf(contact.get(position)));

        Put_ID = (String) id.get(position);

        if(Put_ID.length() > 0) {

            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            idEncoder = new QRGEncoder(Put_ID, null, QRGContents.Type.TEXT, smallerDimension);

            idEncoder.setColorBlack(Color.BLACK);
            idEncoder.setColorWhite(Color.WHITE);

            try {

                bitmap = idEncoder.getBitmap();
                ImageID.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        ImageID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view_template_layout = inflater.inflate(R.layout.qrimageview, null);
                ShowImageID = (ImageView) view_template_layout.findViewById(R.id.show_Images);//and set image to image view
                Button btnSaveImage = (Button) view_template_layout.findViewById(R.id.btnSaveImage);


                String stringValue = "image";
                btnSaveImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            try {

                                boolean nameSave = new QRGSaver().save(savePath, stringValue,
                                        bitmap, QRGContents.ImageType.IMAGE_JPEG);
                                String nameResult = nameSave ? "Image Saved" : "Image Not Saved";
                                Toast.makeText(activity, nameResult, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }
                    }
                });


                Put_ID = (String) id.get(position);
                if (Put_ID.length() > 0) {

                    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    idEncoder = new QRGEncoder(Put_ID, null, QRGContents.Type.TEXT, smallerDimension);

                    idEncoder.setColorBlack(Color.BLACK);
                    idEncoder.setColorWhite(Color.WHITE);

                    try {

                        bitmap = idEncoder.getBitmap();
                        ShowImageID.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


                AlertDialog.Builder builder = alertdialog.setView(view_template_layout);
                    builder.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       public TextView list_Id, list_Name, list_Address, list_Age, list_Contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_Id = itemView.findViewById(R.id.ID);
            list_Name = itemView.findViewById(R.id.list_name);
            list_Address = itemView.findViewById(R.id.list_address);
            list_Age = itemView.findViewById(R.id.list_age);
            list_Contact = itemView.findViewById(R.id.list_contact);

            ImageID = itemView.findViewById(R.id.imageView);
        }
    }

}

