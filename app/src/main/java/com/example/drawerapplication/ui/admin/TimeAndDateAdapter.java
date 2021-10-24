package com.example.drawerapplication.ui.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;
import com.example.drawerapplication.ui.information.CustomAdapter;
import com.example.drawerapplication.ui.information.DatabaseHelper;
import com.example.drawerapplication.ui.information.showInfoActivity;

import java.util.ArrayList;


public class TimeAndDateAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder2>{

    Context context;
    public ArrayList id, name,time, date;

    public TimeAndDateAdapter(Context context, ArrayList id,ArrayList name, ArrayList time, ArrayList date) {
        this.context = context;
        this.id = id;
        this.name = name;
        this.time = time;
        this.date = date;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.timedaterow, parent, false);
        return new CustomAdapter.MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder2 holder, @SuppressLint("RecyclerView") int position) {

        holder.myid.setText(String.valueOf(id.get(position)));
        holder.myname.setText(String.valueOf(name.get(position)));
        holder.mytime.setText(String.valueOf(time.get(position)));
        holder.mydate.setText(String.valueOf(date.get(position)));

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

//    public void filterList(ArrayList<String> filteredList){
//        id = filteredList;
//        notifyDataSetChanged();
//
//    }

}
