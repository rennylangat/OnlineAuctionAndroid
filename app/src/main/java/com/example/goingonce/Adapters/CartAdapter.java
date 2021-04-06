package com.example.goingonce.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.R;
import com.example.goingonce.activities.ViewItem;
import com.example.goingonce.models.CartItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context c;
    private ArrayList<CartItems> cartItems;

    public CartAdapter(Context c, ArrayList<CartItems> cartItems) {
        this.c = c;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(c).inflate(R.layout.cart_item_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        final String itemID=cartItems.get(position).getItemID();
        final String itemName=cartItems.get(position).getItemName();
        final String type=cartItems.get(position).getType();
        final String baseBid=cartItems.get(position).getItemPrice();
        final String location=cartItems.get(position).getLocation();
        final String endTime=cartItems.get(position).getEndTime();

        holder.txtItemName.setText(itemName);
        holder.txtbaseBid.setText("Ksh "+baseBid);
        holder.txtLocation.setText(location);
        holder.txtendTime.setText(endTime);
        holder.txtType.setText(type);

        Picasso.get().load(cartItems.get(position).getImageUrl()).into(holder.itmImg);

        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(c);
                builder.setTitle("Delete Item");
                builder.setMessage("Are you sure you want to remove item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(c,"Item Deleted Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(c,"We shall not delete your item then",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,ViewItem.class);
                intent.putExtra("postId",itemID);
                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CoordinatorLayout view;
        TextView txtItemName,txtType,txtbaseBid,txtLocation,txtendTime;
        ImageView itmImg,deleteImg;
        String bidAmt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=(CoordinatorLayout) itemView.findViewById(R.id.view2);
            txtItemName=(TextView)itemView.findViewById(R.id.item_name);
            txtType=itemView.findViewById(R.id.all_itemType);
            txtbaseBid = (TextView) itemView.findViewById(R.id.base_bid);
            txtLocation = (TextView) itemView.findViewById(R.id.item_Location);
            txtendTime = (TextView) itemView.findViewById(R.id.endTime);
            itmImg = (ImageView) itemView.findViewById(R.id.image_view);
            deleteImg=(ImageView)itemView.findViewById(R.id.deleteImg);
        }
    }
}
