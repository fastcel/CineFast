package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference dbRef;
    TextInputEditText etName,etEmail,etPassword,etConfirmPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),(v, insets) -> {
            Insets systemBars=insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom);
            return insets;
        });
        auth=FirebaseAuth.getInstance();
        dbRef=FirebaseDatabase.getInstance().getReference("Users");
        etName=findViewById(R.id.etSignupName);
        etEmail=findViewById(R.id.etSignupEmail);
        etPassword=findViewById(R.id.etSignupPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        btnSignup=findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(v ->{
            String name=etName.getText().toString().trim();
            String email=etEmail.getText().toString().trim();
            String password=etPassword.getText().toString().trim();
            String confirmPassword=etConfirmPassword.getText().toString().trim();
            if (name.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()) {
                Toast.makeText(this,"All fields are required",Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length()<8) {
                Toast.makeText(this,"Password must be at least 8 characters",Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                return;
            }
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult->{
                        String uid=auth.getCurrentUser().getUid();
                        HashMap<String,String>user=new HashMap<>();
                        user.put("name",name);
                        user.put("email",email);
                        dbRef.child(uid).setValue(user);
                        Toast.makeText(this,"Signup Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,MainActivity.class));
                        finish();

                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show()
                    );
        });
    }
}