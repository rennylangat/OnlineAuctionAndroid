package com.example.goingonce.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goingonce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
    private Button signUpBtn;

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

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            String mail=txtMail.getText().toString();
            String pass=txtPass.getText().toString();
            String phone=txtPhone.getText().toString();
            @Override
            public void onClick(View v) {
                if (!mail.isEmpty() && !phone.isEmpty() && !pass.isEmpty()){
                    mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String uID=mAuth.getCurrentUser().getUid();
                                Map<String,Object> map=new HashMap<>();
                                map.put("Email",mail);
                                map.put("Phone",phone);
                                mReference.child("Users").child(uID).setValue(map);

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
                }
            }
        });
    }
}