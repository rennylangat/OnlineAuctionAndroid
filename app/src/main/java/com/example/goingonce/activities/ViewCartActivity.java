package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        getWindow().setStatusBarColor(Color.BLACK);
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
                        getSubtotal(cartItems);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    *Method to get subtotal by looping thorough the items and adding prices to an array.
    * We shall get each item, convert it to int, store the int in an array.
    * When we get to the end of the array list, find the sum of the items in the array, find the VAT and display final price to pay
    * This final price shall stored in a variable and sent to payment activity as an Intent Extra
     */
    private void getSubtotal(ArrayList<CartItems> cartItems) {
        int x=cartItems.size();
        Toast.makeText(mContext,cartItems.get(0).getItemPrice()+" Size of arrayList "+String.valueOf(x),Toast.LENGTH_SHORT).show();
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