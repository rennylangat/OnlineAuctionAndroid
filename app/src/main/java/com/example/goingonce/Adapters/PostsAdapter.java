package com.example.goingonce.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.R;
import com.example.goingonce.models.ItemDets;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private ArrayList<ItemDets> itemDets;
    private Context mContext;

    public PostsAdapter(ArrayList<ItemDets> itemDets, Context context) {
        this.itemDets = itemDets;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PostsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.MyViewHolder holder, int position) {
        String postID=itemDets.get(position).getItemId();
        String itemName=itemDets.get(position).getItemName();
        String description=itemDets.get(position).getDescription();
        String baseBid=itemDets.get(position).getBaseBid();
        String startTime=itemDets.get(position).getStartTime();
        String endTime=itemDets.get(position).getEndTime();
        String imageUrl=itemDets.get(position).getImageUrl();




    }

    @Override
    public int getItemCount() {
        return itemDets.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout view;
        TextView itemName,itemDesc,baseBid,startTime,endTime;
        ImageView imageView;
        Button bidBtn;
        boolean didBid;
        String bidAmt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView.findViewById(R.id.linear_lay_home);
            itemName=itemView.findViewById(R.id.item_name);
            itemDesc = itemView.findViewById(R.id.description);
            baseBid = itemView.findViewById(R.id.base_bid);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            imageView = itemView.findViewById(R.id.image_view);
            bidBtn = itemView.findViewById(R.id.placeBid);
            didBid=false;
            bidAmt="";
        }
    }


}
