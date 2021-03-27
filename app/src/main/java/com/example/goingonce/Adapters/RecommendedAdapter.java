package com.example.goingonce.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.R;
import com.example.goingonce.models.RecommendedItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<RecommendedItems> recItems;

    public RecommendedAdapter(Context mContext, ArrayList<RecommendedItems> recItems) {
        this.mContext = mContext;
        this.recItems = recItems;
    }

    @NonNull
    @Override
    public RecommendedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recommended_items_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.MyViewHolder holder, int position) {
        String title=recItems.get(position).getItemName();
        String price="Ksh "+recItems.get(position).getItemPrice();
        String itmType=recItems.get(position).getItemType();
        String time=recItems.get(position).getDeliveryTime();
        String type=recItems.get(position).getDeliveryType();
        String img=recItems.get(position).getImageUrl();

        holder.itemName.setText(title);
        holder.itemPrice.setText(price);
        holder.itemType.setText(itmType);
        holder.deliveryTime.setText(time);
        holder.deliveryType.setText(type);
        Picasso.get().load(img).into(holder.itemImg);
    }

    @Override
    public int getItemCount() {
        return recItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImg;
        TextView itemName,itemPrice,itemType,deliveryTime,deliveryType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg=itemView.findViewById(R.id.recommendImg);
            itemName=itemView.findViewById(R.id.itemRecommName);
            itemPrice=itemView.findViewById(R.id.recommendedPrice);
            itemType=itemView.findViewById(R.id.itemType);
            deliveryTime=itemView.findViewById(R.id.reccDeliveryTime);
            deliveryType=itemView.findViewById(R.id.delivery_type);

        }
    }
}
