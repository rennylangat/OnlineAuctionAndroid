package com.example.goingonce.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goingonce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private EditText txtMail,txtPass,txtPhone;
    private MaterialButton signUpBtn;
    private TextView txtLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference();

        txtMail=findViewById(R.id.txtMail);
        txtPass=findViewById(R.id.txtPass);
        txtPhone=findViewById(R.id.txtPhone);
        signUpBtn=findViewById(R.id.btnRegister);
        txtLoginPage=findViewById(R.id.txtLogin);

        txtLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail=txtMail.getText().toString();
                final String pass=txtPass.getText().toString();
                final String phone=txtPhone.getText().toString();
                if (!mail.isEmpty() && !phone.isEmpty() && !pass.isEmpty()){
                    mAuth.createUserWithEmailAndPassword(mail.trim(),pass.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String uID=mAuth.getCurrentUser().getUid();
                                Map<String,Object> map=new HashMap<>();
                                map.put("Email",mail);
                                map.put("Phone",phone);
                                mReference.child("Users").child(uID).setValue(map);
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                mAuth.signOut();
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(),"Task failed Miserably",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Error due to:" +e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Please make sure all fields are filled",Toast
                    .LENGTH_SHORT).show();
                }
            }
        });
    }
}