package com.example.goingonce.Adapters;

import android.app.Dialog;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.goingonce.R;
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
        final String postID=itemDets.get(position).getItemId();
        final String itemName=itemDets.get(position).getItemName();
        final String description=itemDets.get(position).getDescription();
        final String baseBid=itemDets.get(position).getBaseBid();
        final String startTime=itemDets.get(position).getStartTime();
        final String endTime=itemDets.get(position).getEndTime();

        Toast.makeText(mContext,itemName,Toast.LENGTH_SHORT).show();

        holder.txtitemName.setText(itemName);
        holder.txtitemDesc.setText(description);
        holder.txtbaseBid.setText(baseBid);
        holder.txtstartTime.setText(startTime);
        holder.txtendTime.setText(endTime);

        Picasso.get().load(itemDets.get(position).getImageUrl()).into(holder.imageView);

        holder.bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.didBid){
                    createDialog(holder);
                    Toast.makeText(mContext,"Place Bid Button",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext,"Your bid is: "+holder.bidAmt,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createDialog(MyViewHolder holder) {
        mDialog=new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.bid_dialog);
        mDialog.setTitle("Bid");

        btnEnterBid=mDialog.findViewById(R.id.btnBidDiag);
        final EditText editBidAmt=mDialog.findViewById(R.id.editEnterBid);

        btnEnterBid.setEnabled(true);

        btnEnterBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editBidAmt.getText().toString().isEmpty()){
                    holder.bidAmt=editBidAmt.getText().toString().trim();
                    holder.didBid=true;
                    Toast.makeText(mContext,"Your Bid is: "+holder.bidAmt,Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }
        });

        mDialog.show();
    }

    @Override
    public int getItemCount() {
        return itemDets.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout view;
        TextView txtitemName,txtitemDesc,txtbaseBid,txtstartTime,txtendTime;
        ImageView imageView;
        Button bidBtn;
        boolean didBid;
        String bidAmt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=(LinearLayout)itemView.findViewById(R.id.view);
            txtitemName=(TextView)itemView.findViewById(R.id.item_name);
            txtitemDesc =(TextView) itemView.findViewById(R.id.description);
            txtbaseBid = (TextView) itemView.findViewById(R.id.base_bid);
            txtstartTime = (TextView) itemView.findViewById(R.id.start_time);
            txtendTime = (TextView) itemView.findViewById(R.id.end_time);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            bidBtn = (Button) itemView.findViewById(R.id.placeBid);
            didBid=false;
            bidAmt="";
        }
    }

}
