package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView ivLogo;
    Animation fade1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v   .setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        applyAnimation();
        moveToOnboard();
    }
    private void moveToOnboard() {
        new Handler().postDelayed(()->{
            startActivity(new Intent(MainActivity.this, OnboardPage.class));
            finish();
        }, 5000);
    }
    private void applyAnimation() {

        ivLogo.setAnimation(fade1);
    }
    private void init()
    {
        ivLogo=findViewById(R.id.ivLogo);
        fade1= AnimationUtils.loadAnimation(this,R.anim.fade1);
    }
}