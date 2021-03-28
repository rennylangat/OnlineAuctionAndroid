package com.example.goingonce.Adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.R;
import com.example.goingonce.models.ItemDets;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ItemDets> itemDets;

    public ItemAdapter(Context context,ArrayList<ItemDets> dets){
        this.mContext=context;
        this.itemDets=dets;

    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_item_post,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, int position) {
        final String price=holder.txtPrice.getText().toString();
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        CoordinatorLayout view;
        ImageView imgItem,imgAdd,imgMinus,imgBack,imgMore;
        Button btnBid;
        TextView txtType,txtLocation,txtTitle,txtPrice,txtDesc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view=itemView.findViewById(R.id.view1);
            txtType=itemView.findViewById(R.id.txtCat);
            txtLocation=itemView.findViewById(R.id.txtLocation);
            txtTitle=itemView.findViewById(R.id.txtItemTitle);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtDesc=itemView.findViewById(R.id.txtItemDesc);
            imgItem=itemView.findViewById(R.id.imgItem);
            imgAdd=itemView.findViewById(R.id.btnAdd);
            imgMinus=itemView.findViewById(R.id.minusBtn);
            imgBack=itemView.findViewById(R.id.bckBtn);
            imgMore=itemView.findViewById(R.id.moreBtn);
            btnBid=itemView.findViewById(R.id.btnBid);
        }
    }
}
