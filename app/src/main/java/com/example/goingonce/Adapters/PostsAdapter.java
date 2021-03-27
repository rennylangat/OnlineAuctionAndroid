package com.example.goingonce.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.R;
import com.example.goingonce.activities.ViewItem;
import com.example.goingonce.models.ItemDets;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private String [] mDataset;
    private Dialog mDialog;
    private ArrayList<ItemDets> itemDets;
    private Context mContext;
    private Button btnEnterBid;

    public PostsAdapter(ArrayList<ItemDets> itemDets, Context context) {
        this.itemDets = itemDets;
        this.mContext = context;
    }

    public PostsAdapter(String [] myDataset){
        this.mDataset=myDataset;
    }

    @NonNull
    @Override
    public PostsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_item_post,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.MyViewHolder holder, int position) {
        final String postID=itemDets.get(position).getItemID();
        final String itemName=itemDets.get(position).getItemName();
        final String type=itemDets.get(position).getType();
        final String baseBid=itemDets.get(position).getBaseBid();
        final String location=itemDets.get(position).getLocation();
        final String endTime=itemDets.get(position).getEndTime();


        holder.txtItemName.setText(itemName);
        holder.txtbaseBid.setText("Ksh "+baseBid);
        holder.txtLocation.setText(location);
        holder.txtendTime.setText(endTime);
        holder.txtType.setText(type);

        Picasso.get().load(itemDets.get(position).getImageUrl()).into(holder.imageView);

        holder.txtbaseBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ViewItem.class);
                intent.putExtra("postId",postID);
                mContext.startActivity(intent);
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ViewItem.class);
                intent.putExtra("postId",postID);
                mContext.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return itemDets.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CoordinatorLayout view;
        TextView txtItemName,txtType,txtbaseBid,txtLocation,txtendTime;
        ImageView imageView;
        String bidAmt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=(CoordinatorLayout) itemView.findViewById(R.id.view);
            txtItemName=(TextView)itemView.findViewById(R.id.item_name);
            txtType=itemView.findViewById(R.id.all_itemType);
            txtbaseBid = (TextView) itemView.findViewById(R.id.base_bid);
            txtLocation = (TextView) itemView.findViewById(R.id.item_Location);
            txtendTime = (TextView) itemView.findViewById(R.id.endTime);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            bidAmt="";
        }
    }

}
