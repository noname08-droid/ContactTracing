package com.example.drawerapplication.ui.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawerapplication.R;
import com.example.drawerapplication.ui.information.CustomAdapter;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder3>{

    Context context;
    public ArrayList resultId, resultName, resultTime, resultDate;

    public ResultAdapter(Context context, ArrayList resultId, ArrayList resultName, ArrayList resultTime, ArrayList resultDate) {
        this.context = context;
        this.resultId = resultId;
        this.resultName = resultName;
        this.resultTime = resultTime;
        this.resultDate = resultDate;
    }



    @NonNull
    @Override
    public CustomAdapter.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.resultlayout, parent, false);

        return new CustomAdapter.MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder3 holder, int position) {
        holder.resultId.setVisibility(View.INVISIBLE);
        holder.resultId.setText(String.valueOf(resultId.get(position)));
        holder.resultName.setText(String.valueOf(resultName.get(position)));
        holder.resultTime.setText(String.valueOf(resultTime.get(position)));
        holder.resultDate.setText(String.valueOf(resultDate.get(position)));
    }

    @Override
    public int getItemCount() {
        return resultId.size();
    }
}
