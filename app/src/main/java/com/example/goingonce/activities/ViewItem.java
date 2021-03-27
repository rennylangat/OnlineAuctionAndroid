package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goingonce.R;
import com.example.goingonce.models.ItemDets;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewItem extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    public Context c=ViewItem.this;
    private ImageView imgItem,imgAdd,imgMinus,imgBack,imgMore;
    private Button btnBid;
    private TextView txtType,txtLocation,txtTitle,txtPrice,txtDesc;
    private String itemId,itemTitle,itemDesc,itemType,itemPrice,itemLocation,itemImage;
    private EditText txtBid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.single_item_view);
        getWindow().setStatusBarColor(Color.BLACK);
        
        setUpWidgets();
        getActivityData();

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();
        mRef.child("Posts").orderByChild("itemID").equalTo(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        ItemDets dets=snapshot1.getValue(ItemDets.class);
                        itemId=dets.getItemID();
                        itemTitle=dets.getItemName();
                        itemDesc=dets.getDescription();
                        itemType=dets.getType();
                        itemPrice=dets.getBaseBid();
                        itemLocation=dets.getLocation();
                        itemImage=dets.getImageUrl();

                        txtType.setText(itemType);
                        txtLocation.setText(itemLocation);
                        txtTitle.setText(itemTitle);
                        txtPrice.setText("Ksh "+itemPrice);
                        Picasso.get().load(itemImage).into(imgItem);
                        txtBid.setText(itemPrice);
                        txtDesc.setText(itemDesc);
                    }
                }else{
                    Toast.makeText(c,"Could not get item Details",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getActivityData() {
        Bundle extras=getIntent().getExtras();
        if (extras!=null){
            itemId=getIntent().getStringExtra("postId");
        }else{
            Toast.makeText(c,"Could not get Item Details",Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpWidgets() {
        txtType=findViewById(R.id.txtCat);
        txtLocation=findViewById(R.id.txtLocation);
        txtTitle=findViewById(R.id.txtItemTitle);
        txtPrice=findViewById(R.id.txtPrice);
        txtDesc=findViewById(R.id.txtItemDesc);
        imgItem=findViewById(R.id.imgItem);
        imgAdd=findViewById(R.id.btnAdd);
        imgMinus=findViewById(R.id.minusBtn);
        imgBack=findViewById(R.id.bckBtn);
        imgMore=findViewById(R.id.moreBtn);
        btnBid=findViewById(R.id.btnBid);
        txtBid=findViewById(R.id.txtBid);

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}