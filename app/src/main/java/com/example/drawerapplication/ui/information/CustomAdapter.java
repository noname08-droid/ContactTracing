package com.example.drawerapplication.ui.information;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

@RequiresApi(api = Build.VERSION_CODES.R)
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public Context context;
    public ArrayList id, name, address, age, contact, date, time;
    private Bitmap bitmap;
    private QRGEncoder idEncoder;
    public String Put_ID;
    public ImageView ImageID, ShowImageID;
    private Display mDisplay;
    private String savePath = Environment.getExternalStorageDirectory().getPath() +"/QRCode/";
    private AppCompatActivity activity;
    List<String> recyclerListAll;

    public CustomAdapter(Context context, ArrayList id,
                         ArrayList name, ArrayList address,
                         ArrayList age, ArrayList contact, ArrayList date, ArrayList time){

        this.context = context;
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.recyclerListAll = new ArrayList<>(name);

    }

    public CustomAdapter(FragmentActivity activity) {
        this.context = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.list_Id.setText(String.valueOf(id.get(position)));
        holder.list_Name.setText(String.valueOf(name.get(position)));
        holder.list_Address.setText(String.valueOf(address.get(position)));
        holder.list_Age.setText(String.valueOf(age.get(position)));
        holder.list_Contact.setText(String.valueOf(contact.get(position)));
        holder.addDate.setText(String.valueOf(date.get(position)));
        holder.addTime.setText(String.valueOf(time.get(position)));

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
                Button btnPrint = (Button) view_template_layout.findViewById(R.id.btnPrint);


                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                mDisplay = wm.getDefaultDisplay();


                btnPrint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = holder.list_Id.getText().toString();
                        String name = holder.list_Name.getText().toString();
                        String address = holder.list_Address.getText().toString();
                        String age = holder.list_Age.getText().toString();
                        String contact = holder.list_Contact.getText().toString();

                        try {
                            createPDF(id, name, address, age, contact);
                            Toast.makeText(context,"Saved PDF",Toast.LENGTH_SHORT);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                });



                String stringValue = holder.list_Name.getText().toString().trim();
                btnSaveImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            try {

                                boolean nameSave = new QRGSaver().save(savePath, stringValue,
                                        bitmap, QRGContents.ImageType.IMAGE_JPEG);
                                if (nameSave == true){
                                    Toast.makeText(context, "Saved Image", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Image not Saved", Toast.LENGTH_SHORT).show();
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
       public TextView addDate, addTime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_Id = itemView.findViewById(R.id.ID);
            list_Name = itemView.findViewById(R.id.list_name);
            list_Address = itemView.findViewById(R.id.list_address);
            list_Age = itemView.findViewById(R.id.list_age);
            list_Contact = itemView.findViewById(R.id.list_contact);

            addDate = itemView.findViewById(R.id.addDate);
            addTime = itemView.findViewById(R.id.addTime);
            ImageID = itemView.findViewById(R.id.imageView);

        }
    }


    private void createPDF(String id , String name, String address, String age, String contact) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        String nameOfPDF = name;
        File file = new File(pdfPath, (name+".pdf"));

        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0, 0, 0, 0);

        Drawable d = context.getDrawable(R.drawable.upanglogo);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Paragraph contactTracing = new Paragraph("Contact Tracing")
                .setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);

        Paragraph group = new Paragraph("Application Created by Student of BSIT"
        ).setTextAlignment(TextAlignment.CENTER).setFontSize(10);

        float[] width = {100f, 100f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Cell().add(new Paragraph("ID"))).setFontSize(10);
        table.addCell(new Cell().add(new Paragraph(id))).setFontSize(10);

        table.addCell(new Cell().add(new Paragraph("Name"))).setFontSize(10);
        table.addCell(new Cell().add(new Paragraph(name))).setFontSize(10);

        table.addCell(new Cell().add(new Paragraph("Address"))).setFontSize(10);
        table.addCell(new Cell().add(new Paragraph(address))).setFontSize(10);

        table.addCell(new Cell().add(new Paragraph("Age"))).setFontSize(10);
        table.addCell(new Cell().add(new Paragraph(age))).setFontSize(10);

        table.addCell(new Cell().add(new Paragraph("Contact"))).setFontSize(10);
        table.addCell(new Cell().add(new Paragraph(contact))).setFontSize(10);


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        LocalDateTime today = LocalDateTime.now();
        table.addCell(new Cell().add(new Paragraph("Date"))).setFontSize(10);
        table.addCell(new Cell().add(new Paragraph(today.format(dateFormatter).toString()))).setFontSize(10);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
        table.addCell(new Cell().add(new Paragraph("Time"))).setFontSize(10);
        table.addCell(new Cell().add(new Paragraph(today.format(timeFormatter).toString()))).setFontSize(10);

        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(id);
        PdfFormXObject qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK,pdfDocument);
        Image qrCodeImage = new Image(qrCodeObject).setWidth(100).setHorizontalAlignment(HorizontalAlignment.CENTER);


        document.add(image);
        document.add(contactTracing);
        document.add(group);
        document.add(table);
        document.add(qrCodeImage);
        document.close();
        Toast.makeText(context, "Pdf Created", Toast.LENGTH_SHORT).show();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {

        public TextView myid, myname, mytime, mydate;
        public Display mDisplay;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            myid = itemView.findViewById(R.id.myID);
            myname = itemView.findViewById(R.id.myname);
            mytime = itemView.findViewById(R.id.mytime);
            mydate = itemView.findViewById(R.id.mydate);
        }
    }

    public static class MyViewHolder3 extends RecyclerView.ViewHolder {

        public TextView resultId, resultName, resultTime, resultDate;

        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            resultId = itemView.findViewById(R.id.resultID);
            resultName = itemView.findViewById(R.id.resultName);
            resultTime = itemView.findViewById(R.id.resultTime);
            resultDate = itemView.findViewById(R.id.resultDate);
        }
    }
}

