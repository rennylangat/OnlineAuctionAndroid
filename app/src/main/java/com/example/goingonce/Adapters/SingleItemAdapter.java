package com.example.goingonce.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.models.ItemDets;

import java.util.ArrayList;

public class SingleItemAdapter extends RecyclerView.Adapter {
    private String mDataset[];
    private Context mContext;
    private ArrayList<ItemDets> itemDets;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout view;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
