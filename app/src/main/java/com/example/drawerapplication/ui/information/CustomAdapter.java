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
import android.provider.DocumentsContract;
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
import com.google.type.DateTime;
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
import com.itextpdf.text.pdf.Barcode;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

@RequiresApi(api = Build.VERSION_CODES.R)
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    public ArrayList id, name, address, age, contact, date, time;
    private Bitmap bitmap;
    private QRGEncoder idEncoder;
    public String Put_ID;
    public ImageView ImageID, ShowImageID;
//

    private String path;
    public static final int READ_PHONE = 110;
    private Display mDisplay;
    private Bitmap bitmaps;
    private int totalHeight;
    private int totalWidth;
    private String file_name = "Screenshot";
    private File myPath;
    private String imagesUri;

//
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
//                if (Build.VERSION.SDK_INT >= 23){
//                    if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//                    && context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED){
//                    }else {
////                        ActivityCompat.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
////                        ActivityCompat.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
////                                Manifest.permission.READ_EXTERNAL_STORAGE},READ_PHONE);
//                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
//                    }
//                }


                btnPrint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        btnPrint.setVisibility(View.GONE);
//
//                        takeScreenShot();
//
//                        btnPrint.setVisibility(view.VISIBLE);
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
//                                Toast.makeText(activity, nameResult, Toast.LENGTH_LONG).show();

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
//    public Bitmap getBitmapFormView(View view,  int totalHeight, int totalWidth){
//
//        Bitmap returnBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(returnBitmap);
//        Drawable bgDrawable = view.getBackground();
//
//        if (bgDrawable == null){
//            bgDrawable.draw(canvas);
//        }else{
//            canvas.drawColor(Color.WHITE);
//        }
//        view.draw(canvas);
//        return returnBitmap;
//    }
//
//    private void takeScreenShot(){
//        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/screenShot/");
//
//        if (!folder.exists()){
//            boolean success = folder.mkdir();
//        }
//        path = folder.getAbsolutePath();
//        path = path + "/" + file_name + System.currentTimeMillis() + ".pdf";
//
////        @SuppressLint("ResourceType") View u = View.inflate(context,R.id.contactTracing, null);
////        @SuppressLint("ResourceType") NestedScrollView z = (NestedScrollView) View.inflate(context, R.id.contactTracing, null);
//        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View print_layout = inflater.inflate(R.layout.print_layout, null);
//        View u = print_layout.findViewById(R.id.contactTracing);
//        View z = print_layout.findViewById(R.id.contactTracing);
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        mDisplay = wm.getDefaultDisplay();
//
////        totalHeight = z.getChildAt(0).getHeight();
////        totalWidth = z.getChildAt(0).getWidth();
//        totalWidth = z.getHeight();
//        totalHeight = z.getWidth();
//        totalWidth = u.getHeight();
//        totalHeight = u.getWidth();
//
//        String extr = Environment.getExternalStorageDirectory() + "/Contact Traicing/";
//        File file = new File(extr);
//        if (!file.exists())
//            file.mkdir();
//        String fileName = file_name + ".jpg";
//        myPath = new File(extr, fileName);
//        imagesUri = myPath.getPath();
//        bitmaps = getBitmapFormView(u, totalHeight,totalWidth);
//
//        try {
//            FileOutputStream fos = new FileOutputStream(myPath);
//            bitmaps.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            fos.flush();
//            fos.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        createPDF();
//    }
//
//    private void createPDF() {
//        PdfDocument document = new PdfDocument();
//        android.graphics.pdf.PdfDocument.PageInfo pageInfo
//                = new android.graphics.pdf.PdfDocument.PageInfo.Builder(bitmaps.getWidth(),
//                bitmaps.getHeight(), 1).create();
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//
//        Paint paint = new Paint();
//        paint.setColor(Color.parseColor("#ffffff"));
//        canvas.drawPaint(paint);
//
//        Bitmap bitmap = Bitmap.createScaledBitmap(this.bitmap, this.bitmap.getWidth(), this.bitmap.getHeight(), true);
//        paint.setColor(Color.BLUE);
//        canvas.drawBitmap(bitmap, 0,0, null);
//        document.finishPage(page);
//        File filePath = new File(path);
//        try {
//            document.writeTo(new FileOutputStream(filePath));
//        }catch (IOException e){
//            e.printStackTrace();
//            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
//        }
//        document.close();
//        if (myPath.exists())
//            myPath.delete();
//        openPdf(path);
//
//    }
//
//    private void openPdf(String path) {
//        File file = new File(path);
//        Intent target = new Intent(Intent.ACTION_VIEW);
//        target.setDataAndType(Uri.fromFile(file), "application/pdf");
//        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//        Intent intent = Intent.createChooser(target,"Open File");
//        try {
//            context.startActivity(intent);
//        }catch (ActivityNotFoundException e){
//            Toast.makeText(context, "No Apps to read PDF File", Toast.LENGTH_SHORT).show();
//        }
//    }
    private void createPDF(String id , String name, String address, String age, String contact) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        String nameOfPDF = name;
        File file = new File(pdfPath, (name+".pdf"));
//        java.io.File file = new java.io.File((context
//                .getApplicationContext().getFileStreamPath("FileName.xml")
//                .getPath()));
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0, 0, 0, 0);

        Drawable d = context.getDrawable(R.drawable.upang);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Paragraph contactTracing = new Paragraph("Contact Tracing")
                .setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);

        Paragraph group = new Paragraph("Application Created by Student of BSIT\n"+
                "Name of Advicer").setTextAlignment(TextAlignment.CENTER).setFontSize(12);
        Paragraph application = new Paragraph("Application").setBold().setFontSize(15).
                setTextAlignment(TextAlignment.CENTER);

        float[] width = {100f, 100f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Cell().add(new Paragraph("Visitor ID")));
        table.addCell(new Cell().add(new Paragraph(id)));

        table.addCell(new Cell().add(new Paragraph("NAME")));
        table.addCell(new Cell().add(new Paragraph(name)));

        table.addCell(new Cell().add(new Paragraph("ADDRESS")));
        table.addCell(new Cell().add(new Paragraph(address)));

        table.addCell(new Cell().add(new Paragraph("AGE")));
        table.addCell(new Cell().add(new Paragraph(age)));

        table.addCell(new Cell().add(new Paragraph("CONTACT")));
        table.addCell(new Cell().add(new Paragraph(contact)));


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        LocalDateTime today = LocalDateTime.now();
        table.addCell(new Cell().add(new Paragraph("DATE")));
        table.addCell(new Cell().add(new Paragraph(today.format(dateFormatter).toString())));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
        table.addCell(new Cell().add(new Paragraph("CONTACT")));
        table.addCell(new Cell().add(new Paragraph(today.format(timeFormatter).toString())));

        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(id+"\n"+name+"\n"+address+"\n"+age+"\n"+today.format(dateFormatter)+"\n"+today.format(timeFormatter));
        PdfFormXObject qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK,pdfDocument);
        Image qrCodeImage = new Image(qrCodeObject).setWidth(100).setHorizontalAlignment(HorizontalAlignment.CENTER);


        document.add(image);
        document.add(contactTracing);
        document.add(group);
        document.add(application);
        document.add(table);
        document.add(qrCodeImage);
        document.close();
        Toast.makeText(context, "Pdf Created", Toast.LENGTH_SHORT).show();
    }
}

