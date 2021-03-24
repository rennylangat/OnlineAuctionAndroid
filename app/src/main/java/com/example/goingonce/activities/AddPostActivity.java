package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.goingonce.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {

    private ImageButton imgBtn;
    private EditText itmName,itmDesc,itmBaseBid,itemLocation,itmEndTime;
    private AutoCompleteTextView itemType;
    private Button submitBtn;
    private Context mContext=AddPostActivity.this;
    private Uri imgUri;
    private StorageReference mStorageRef;
    private DatabaseReference mRef;
    private String uID;

    private static final int GALLERY_REQUEST=1;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        getWindow().setStatusBarColor(Color.BLACK);

        uID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        imgBtn=(ImageButton)findViewById(R.id.imgBtn);
        itmName=findViewById(R.id.edit_item_name);
        itmDesc=findViewById(R.id.edit_desc);
        itmBaseBid=findViewById(R.id.edit_base_bid);
        itemLocation=findViewById(R.id.edit_location);
        itmEndTime=findViewById(R.id.edit_end_time);
        submitBtn=findViewById(R.id.submitBtn);
        itemType=findViewById(R.id.edit_item_type);

        ArrayList<String> typesList=getTypesList();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_dropdown_item,typesList);

        itemType.setAdapter(adapter);


        itmEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrTime=Calendar.getInstance();
                int hour=mCurrTime.get(Calendar.HOUR_OF_DAY);
                int minute=mCurrTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(AddPostActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        itmEndTime.setText(hourOfDay+":"+ minute);
                    }
                },hour,minute,true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });


        mStorageRef= FirebaseStorage.getInstance().getReference();
        mRef= FirebaseDatabase.getInstance().getReference().child("Posts");

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent=new Intent(Intent.ACTION_GET_CONTENT);
                imgIntent.setType("image/*");
                startActivityForResult(imgIntent,GALLERY_REQUEST);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String itemName=itmName.getText().toString();
                final String itemDesc=itmDesc.getText().toString();
                final String baseBid=itmBaseBid.getText().toString();
                final String type=itemType.getText().toString();
                final String location=itemLocation.getText().toString();
                final String endTime=itmEndTime.getText().toString();
                publishPost(itemName,type,itemDesc,baseBid,location,endTime);
            }
        });
    }

    private ArrayList<String> getTypesList() {
        ArrayList<String> types=new ArrayList<>();
        types.add("Electronics");
        types.add("Cosmetics");
        types.add("Jewellery");
        types.add("Clothing");
        types.add("Cars");
        types.add("Property");
        return  types;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            imgUri=data.getData();
            imgBtn.setImageURI(imgUri);
        }
    }

    private void publishPost(String itemName,String type,String itemDesc,String baseBid,String location,String endTime) {
        Toast.makeText(getApplicationContext(),itemName,Toast.LENGTH_SHORT).show();
        if (!itemName.isEmpty() &&!itemType.getText().toString().matches("") && !itemDesc.isEmpty() && !baseBid.isEmpty() && !location.isEmpty()
        && !endTime.isEmpty()){
            final StorageReference filePath=mStorageRef.child("AuctionImages").child(imgUri.getLastPathSegment());

            UploadTask uploadTask=filePath.putFile(imgUri);

            Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri=task.getResult();
                        String downloadUrl=downloadUri.toString();

                        DatabaseReference newPost=mRef.push();
                        String itemKey=newPost.getKey();
                        newPost.child("itemID").setValue(itemKey);
                        newPost.child("itemName").setValue(itemName);
                        newPost.child("type").setValue(type);
                        newPost.child("description").setValue(itemDesc);
                        newPost.child("baseBid").setValue(baseBid);
                        newPost.child("location").setValue(location);
                        newPost.child("endTime").setValue(endTime);
                        newPost.child("imageUrl").setValue(downloadUrl);

                        Toast.makeText(getApplicationContext(),"Item Published Successfully!",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(AddPostActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Error. Please Try again",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Please choose and image and make sure all fields are filled"+itemName,Toast.LENGTH_SHORT).show();
        }
    }
}