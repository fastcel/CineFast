package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePage extends AppCompatActivity {

    Button btnBook;
    Button btnYoutube;

    Button btnBook1;
    Button btnYoutube1;

    Button btnBook2;
    Button btnYoutube2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        btnBook.setOnClickListener(v->{
            Intent intent=new Intent (this,BookingPage.class);
            intent.putExtra("moviename","The Dark Night");
            startActivity(intent);
        });

        btnYoutube.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/watch?v=EXeTwQWrcwY"));
            startActivity(intent);
        });

        btnBook1.setOnClickListener(v->{
            Intent intent=new Intent (this,BookingPage.class);
            intent.putExtra("moviename","Inception");
            startActivity(intent);
        });

        btnYoutube1.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/watch?v=YoHD9XEInc0"));
            startActivity(intent);
        });

        btnBook2.setOnClickListener(v->{
            Intent intent=new Intent (this,BookingPage.class);
            intent.putExtra("moviename","Interstellar");
            startActivity(intent);
        });

        btnYoutube2.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/watch?v=zSWdZVtXT7E"));
            startActivity(intent);
        });
    }

    private void init()
    {
        btnBook=findViewById(R.id.btnBook);
        btnYoutube=findViewById(R.id.btnYoutube);
        btnBook1=findViewById(R.id.btnBook1);
        btnYoutube1=findViewById(R.id.btnYoutube1);
        btnBook2=findViewById(R.id.btnBook2);
        btnYoutube2=findViewById(R.id.btnYoutube2);
    }
}