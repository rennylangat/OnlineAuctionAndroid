package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goingonce.Auth.LoginActivity;
import com.example.goingonce.R;
import com.example.goingonce.models.ItemDets;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG="MainActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private RecyclerView recyclerView;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<ItemDets, PostViewHolder> mAdapter;
    private FirebaseAuth mFirebaseAuth;

    private ProgressBar progressBar;

    private Button btnEnterBid;
    private Dialog mDialog;
    private TextView txtLoading;

    private ArrayList<ItemDets> itemDets;
    private Context mContext=MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        Log.d(TAG,"No user Logged in.");

        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Posts");

        mDrawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        progressBar=findViewById(R.id.progressBarHome);
        txtLoading=findViewById(R.id.txtLoading);

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
        mDialog=new Dialog(MainActivity.this);
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
                    Toast.makeText(getApplicationContext(),"Your Bid is: "+holder.bidAmt,Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }
        });

        mDialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        txtLoading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        recyclerView.setVisibility(View.GONE);

        FirebaseRecyclerOptions<ItemDets> options=new FirebaseRecyclerOptions.Builder<ItemDets>().setQuery(
                mDatabaseReference,ItemDets.class
        ).build();

        mAdapter=new FirebaseRecyclerAdapter<ItemDets, PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull ItemDets model) {




                holder.itemName.setText(itemDets.get(position).getItemName());
                holder.itemDesc.setText(itemDets.get(position).getDescription());
                holder.baseBid.setText("Base Bid: Ksh."+itemDets.get(position).getBaseBid());
                holder.startTime.setText("Start Time: "+itemDets.get(position).getStartTime());
                holder.endTime.setText("End Time: "+itemDets.get(position).getEndTime());

                Picasso.get().load(itemDets.get(position).getImageUrl()).into(holder.imageView);

                holder.bidBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!holder.didBid){
                            AlertDialog(holder);
                        }else{
                            Toast.makeText(mContext,"Your bid is: "+holder.bidAmt,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(mContext).inflate(R.layout.single_item_layout,parent,false);
                return new PostViewHolder(view);
            }
        };

        mAdapter.startListening();
        recyclerView.setAdapter(mAdapter);

        txtLoading.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id=item.getItemId();

        if (id==R.id.add){
            startActivity(new Intent(mContext,AddPostActivity.class));
        }else  if(id==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
        }

        DrawerLayout drawerLayout=findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent a=new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        //super.onBackPressed();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemDesc,baseBid,startTime,endTime;
        ImageView imageView;
        Button bidBtn;
        boolean didBid;
        String bidAmt;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
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