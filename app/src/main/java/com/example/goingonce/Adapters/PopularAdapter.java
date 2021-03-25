package com.example.goingonce.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.R;
import com.example.goingonce.models.PopularItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PopularItems> items;

    public PopularAdapter(Context mContext,ArrayList<PopularItems> popularItems) {
        this.mContext = mContext;
        this.items=popularItems;
    }
    @NonNull
    @Override
    public PopularAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.popular_items_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String itemID=items.get(position).getItemID();
        final String itemName=items.get(position).getItemName();
        final String imgUrl=items.get(position).getImageUrl();


        holder.popItemName.setText(itemName);
        Picasso.get().load(imgUrl).into(holder.popularImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, imgUrl,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView popularImg;
        TextView popItemName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            popItemName=itemView.findViewById(R.id.popularItemName);
            popularImg=itemView.findViewById(R.id.all_menu_image);
        }
    }
}
