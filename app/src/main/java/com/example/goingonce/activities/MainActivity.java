package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.example.goingonce.Adapters.PopularAdapter;
import com.example.goingonce.Adapters.PostsAdapter;
import com.example.goingonce.Auth.LoginActivity;
import com.example.goingonce.R;
import com.example.goingonce.Settings.SettingsActivity;
import com.example.goingonce.models.ItemDets;
import com.example.goingonce.models.PopularItems;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.type.DateTime;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG="MainActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private LinearLayoutManager linearLayoutManager;

    private DatabaseReference mDatabaseReference;
    //private FirebaseRecyclerAdapter<ItemDets, PostViewHolder> mAdapter;
    private FirebaseAuth mFirebaseAuth;

    private ProgressBar progressBar;

    private Button btnEnterBid;
    private Dialog mDialog;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private PostsAdapter postAdapter;
    private ArrayList<ItemDets> itemDets;
    private Context mContext=MainActivity.this;
    private Locale currLocale;

    private RecyclerView popularRecycler;
    private PopularAdapter popularAdapter;
    private ArrayList<PopularItems> popularItemsArrayList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(Color.BLACK);
        currLocale=getResources().getConfiguration().locale;


        popularItemsArrayList=new ArrayList<PopularItems>();
        mFirebaseAuth=FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        Log.d(TAG,"No user Logged in.");


        mFirebaseAuth=FirebaseAuth.getInstance();

        mDatabaseReference= FirebaseDatabase.getInstance().getReference();

        //setup Widgets
        mDrawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        progressBar=findViewById(R.id.progressBarHome);

        navigationView.setNavigationItemSelectedListener(this);

        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Popular Items Recycler

        //All Items Recycler
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_home);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
        itemDets=new ArrayList<ItemDets>();


        mDatabaseReference.child("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBar.setVisibility(View.INVISIBLE);

                    for (DataSnapshot ds1:snapshot.getChildren()){

                        try {
                            ItemDets dets=ds1.getValue(ItemDets.class);
                            itemDets.add(dets);
                            progressBar.setVisibility(View.INVISIBLE);

                        }catch (Exception e){
                            Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    postAdapter=new PostsAdapter(itemDets,MainActivity.this);
                    recyclerView.setAdapter(postAdapter);

                }else{
                    Toast.makeText(getApplicationContext(),"No Bids Posted Yet",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext,"Error connecting to Dbase",Toast.LENGTH_SHORT).show();
            }
        });

        mDatabaseReference.child("Popular").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        PopularItems items=snapshot1.getValue(PopularItems.class);
                        popularItemsArrayList.add(items);
                        getPopularData(popularItemsArrayList);
                    }
                }else {
                    Toast.makeText(mContext,"No Popular Items",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getPopularData(ArrayList<PopularItems> popularItems){
        popularRecycler=findViewById(R.id.popular_recycler);
        popularAdapter=new PopularAdapter(mContext,popularItems);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        popularRecycler.setLayoutManager(layoutManager);
        popularRecycler.setAdapter(popularAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        }else if (id==R.id.editItem){
            startActivity(new Intent(mContext,EditItemActivity.class));
        }else if (id==R.id.settings){
            startActivity(new Intent(mContext, SettingsActivity.class));
        }else if (id==R.id.myBids){
            startActivity(new Intent(mContext,MyBids.class));
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
        TextView itemName,itemDesc,type,baseBid,location,endTime;
        ImageView imageView;
        Button bidBtn;
        boolean didBid;
        String bidAmt;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.item_name);
            itemDesc = itemView.findViewById(R.id.description);
            type=itemView.findViewById(R.id.type);
            baseBid = itemView.findViewById(R.id.base_bid);
            location = itemView.findViewById(R.id.location);
            endTime = itemView.findViewById(R.id.end_time);
            imageView = itemView.findViewById(R.id.image_view);
            bidBtn = itemView.findViewById(R.id.placeBid);
            didBid=false;
            bidAmt="";
        }
    }
    private String getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Locale locale = getLocale(this);

        if (!locale.equals(currLocale)) {

            currLocale = locale;
            recreate();
        }
    }

    public static Locale getLocale(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String lang = sharedPreferences.getString("language", "en");
        switch (lang) {
            case "English":
                lang = "en";
                break;
            case "Spanish":
                lang = "es";
                break;
        }
        return new Locale(lang);
    }
}