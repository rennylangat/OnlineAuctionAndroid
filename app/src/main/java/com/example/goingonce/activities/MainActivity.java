package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.goingonce.Adapters.SingleItemAdapter;
import com.example.goingonce.R;
import com.example.goingonce.models.ItemDets;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private RecyclerView recyclerView;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<ItemDets, PostViewHolder> mAdapter;

    private ProgressBar progressBar;

    private Button btnEnterBid;
    private Dialog mDialog;

    private ArrayList<ItemDets> itemDets;
    private Context mContext=MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Posts");

        mDrawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        progressBar=findViewById(R.id.progressBarHome);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_home);

        navigationView.setNavigationItemSelectedListener(this);

        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    public  void AlertDialog(final PostViewHolder holder){

    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        recyclerView.setVisibility(View.GONE);

        FirebaseRecyclerOptions<ItemDets> options=new FirebaseRecyclerOptions.Builder<ItemDets>().setQuery(
                mDatabaseReference,ItemDets.class
        ).build();

        mAdapter=new FirebaseRecyclerAdapter<ItemDets, PostViewHolder>() {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull ItemDets model) {

            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        return true;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemDesc,baseBid,startTime,endTime;
        ImageView imageView;
        Button bidBtn;
        boolean didBid;
        String bidAmt;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }



}