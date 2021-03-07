package com.example.goingonce.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.R;
import com.example.goingonce.models.ItemDets;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SingleItemAdapter extends RecyclerView.Adapter<SingleItemAdapter.MyViewHolder> {
    private String [] mDataset;
    private Context mContext;
    private ArrayList<ItemDets> itemDets;

    public SingleItemAdapter(Context mContext, ArrayList<ItemDets> itemDets) {
        this.mContext = mContext;
        this.itemDets = itemDets;
    }
    public SingleItemAdapter(String [] myDataset){
        this.mDataset=myDataset;
    }

    @NonNull
    @Override
    public SingleItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemAdapter.MyViewHolder holder, int position) {
        final String itemName=itemDets.get(position).getItemName();
        final String itemDesc=itemDets.get(position).getItemDesc();
        final String itemPrice=itemDets.get(position).getItemPrice();

        holder.itemName.setText(itemName);
        holder.itemDesc.setText(itemDesc);
        holder.itemPrice.setText(itemPrice);

        Picasso.get().load(itemDets.get(position).getItemImg()).into(holder.itemImg);
    }

    @Override
    public int getItemCount() {
        return itemDets.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout view;
        TextView itemName,itemDesc,itemPrice;
        ImageView itemImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=(LinearLayout)itemView.findViewById(R.id.singleLinearLayout);
            itemName=(TextView)itemView.findViewById(R.id.txtItemTitle);
            itemDesc=(TextView)itemView.findViewById(R.id.txtItemDesc);
            itemPrice=(TextView)itemView.findViewById(R.id.txtItemBid);
            itemImg=(ImageView)itemView.findViewById(R.id.imgItem);
        }
    }
}
