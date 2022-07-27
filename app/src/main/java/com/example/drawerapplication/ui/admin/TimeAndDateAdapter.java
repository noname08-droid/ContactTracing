package com.example.drawerapplication.ui.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;
import com.example.drawerapplication.ui.information.CustomAdapter;

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

        holder.myid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view_template_layout = inflater.inflate(R.layout.timeanddate_list, null);

                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                holder.mDisplay = wm.getDefaultDisplay();

                Button searchNow = (Button) view_template_layout.findViewById(R.id.btnsearch);
                EditText editSearch = (EditText) view_template_layout.findViewById(R.id.editSearch);
                TextView list = (TextView) view_template_layout.findViewById(R.id.list);

                list.setVisibility(View.INVISIBLE);
                searchNow.setVisibility(View.VISIBLE);
                editSearch.setVisibility(View.VISIBLE);

//                String IDValue = String.valueOf(id.get(position));

                    searchNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(context, ResearchResult.class);
                            intent.putExtra("newID", String.valueOf(editSearch.getText()));
                            context.startActivity(intent);

                        }
                    });

                AlertDialog.Builder builder = alertdialog.setView(view_template_layout);
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

}