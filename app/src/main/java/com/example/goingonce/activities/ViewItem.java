package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.goingonce.models.CartItems;
import com.example.goingonce.models.ItemDets;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewItem extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    public Context c=ViewItem.this;
    private ImageView imgItem,imgAdd,imgMinus,imgBack,imgMore;
    private Button btnBid;
    private TextView txtType,txtLocation,txtTitle,txtPrice,txtDesc;
    private String itemId,itemTitle,itemDesc,itemType,itemPrice,itemLocation,itemImage,priceNow,basePrice;
    private EditText txtBid;
    private String uID;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.single_item_view);
        getWindow().setStatusBarColor(Color.BLACK);
        
        setUpWidgets();
        getActivityData();

        uID= FirebaseAuth.getInstance().getCurrentUser().getUid();
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
                        priceNow= txtBid.getText().toString();
                        basePrice=dets.getBaseBid();
                    }
                }else{
                    Toast.makeText(c,"Could not get item Details",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(c,"User Cancelled",Toast.LENGTH_SHORT).show();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,"You really want more details?",Toast.LENGTH_SHORT).show();
            }
        });

        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double x=Double.parseDouble(priceNow);
                if (x> Double.parseDouble(basePrice)){
                    double y=x-1;
                    priceNow=String.valueOf(y);
                    txtBid.setText(String.valueOf(y));
                }else{
                    //Do nothing
                    Toast.makeText(c,"Hiyo ndio bei ya mwisho mkubwa",Toast.LENGTH_SHORT).show();
                }


            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double x=Double.parseDouble(priceNow)+1;
                priceNow=String.valueOf(x);
                txtBid.setText(String.valueOf(x));
            }
        });

        btnBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add item to cart with new base bid and open home page
                final String itemID=itemId;
                final String itemPrice=txtBid.getText().toString();
                addToCart(itemID,itemPrice);
                startActivity(new Intent(c,MainActivity.class));
                finish();
            }
        });


    }

    private void addToCart(String itemID, String itemPrice) {
        if (!itemID.isEmpty()&&!itemPrice.isEmpty()){
            DatabaseReference ref2=mDatabase.getReference();
            DatabaseReference newItem=ref2.child("Cart").child(uID).push();
            newItem.child("itemID").setValue(itemID);
            newItem.child("itemPrice").setValue(itemPrice);

            Toast.makeText(c,"Added to cart successfully",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(c,"A field is missing",Toast.LENGTH_SHORT).show();
        }
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