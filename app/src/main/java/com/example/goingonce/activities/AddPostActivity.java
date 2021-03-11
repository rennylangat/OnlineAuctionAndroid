package com.example.goingonce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.goingonce.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPostActivity extends AppCompatActivity {

    private ImageButton imgBtn;
    private EditText itmName,itmDesc,itmBaseBid,itmStartTime,itmEndTime;
    private Button submitBtn;

    private Uri imgUri;
    private StorageReference mStorageRef;
    private DatabaseReference mRef;

    private static final int GALLERY_REQUEST=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        imgBtn=(ImageButton)findViewById(R.id.imgBtn);
        itmName=findViewById(R.id.edit_item_name);
        itmDesc=findViewById(R.id.edit_desc);
        itmBaseBid=findViewById(R.id.edit_base_bid);
        itmStartTime=findViewById(R.id.edit_start_time);
        itmEndTime=findViewById(R.id.edit_end_time);
        submitBtn=findViewById(R.id.submitBtn);

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
                final String startTime=itmStartTime.getText().toString();
                final String endTime=itmEndTime.getText().toString();
                publishPost(itemName,itemDesc,baseBid,startTime,endTime);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            imgUri=data.getData();
            imgBtn.setImageURI(imgUri);
        }
    }

    private void publishPost(String itemName,String itemDesc,String baseBid,String startTime,String endTime) {
        Toast.makeText(getApplicationContext(),itemName,Toast.LENGTH_SHORT).show();
        if (!itemName.isEmpty() && !itemDesc.isEmpty() && !baseBid.isEmpty() && !startTime.isEmpty()
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
                        newPost.child("itemName").setValue(itemName);
                        newPost.child("description").setValue(itemDesc);
                        newPost.child("baseBid").setValue(baseBid);
                        newPost.child("startTime").setValue(startTime);
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