package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goingonce.Adapters.CartAdapter;
import com.example.goingonce.R;
import com.example.goingonce.models.CartItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCartActivity extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    public Context mContext=ViewCartActivity.this;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<CartItems> cartItems;
    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        cartItems=new ArrayList<CartItems>();
        mAuth=FirebaseAuth.getInstance();
        uID=mAuth.getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        mRef=database.getReference();

        mRef.child("Cart").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds1:snapshot.getChildren()){
                        CartItems items=ds1.getValue(CartItems.class);
                        cartItems.add(items);
                        getCartData(cartItems);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCartData(ArrayList<CartItems> cartItems) {
        recyclerView=findViewById(R.id.cartRecycler);
        cartAdapter=new CartAdapter(mContext,cartItems);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cartAdapter);
    }
}