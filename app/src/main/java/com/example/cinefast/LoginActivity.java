package com.example.cinefast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextInputEditText etEmail, etPassword;
    Button btnLogin;
    TextView tvSignup;
    CheckBox cbRemember;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        prefs=getSharedPreferences("cinefast_session_pref_v3", MODE_PRIVATE);
        FirebaseUser user=auth.getCurrentUser();
        if (user!=null && prefs.getBoolean("isLoggedIn",false)) {
            startActivity(new Intent(this,HomePage.class));
            finish();
            return;
        }
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        tvSignup=findViewById(R.id.tvSignup);
        cbRemember=findViewById(R.id.cbRemember);
        btnLogin.setOnClickListener(v -> {
            String email=etEmail.getText()!=null?etEmail.getText().toString().trim():"";
            String password=etPassword.getText()!=null?etPassword.getText().toString().trim():"";
            if (email.isEmpty()||password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields",Toast.LENGTH_SHORT).show();
                return;
            }
            auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult->{
                        SharedPreferences.Editor editor=prefs.edit();
                        if (cbRemember.isChecked()) {
                            editor.putBoolean("isLoggedIn",true);
                            editor.putString("userEmail",email);
                        } else {
                            editor.putBoolean("isLoggedIn",false);
                        }
                        editor.apply();
                        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,HomePage.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show()
                    );
        });
        tvSignup.setOnClickListener(v->{
            startActivity(new Intent(this,SignupActivity.class));
            finish();
        });
    }
}