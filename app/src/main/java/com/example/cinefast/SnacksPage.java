package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SnacksPage extends AppCompatActivity {

    Button btnInc;
    Button btnDec;
    TextView tvQuantity;
    Button btnInc1;
    Button btnDec1;
    TextView tvQuantity1;
    Button btnInc2;
    Button btnDec2;
    TextView tvQuantity2;
    Button btnInc3;
    Button btnDec3;
    TextView tvQuantity3;
    Button btnGo;

    ArrayList<String> reservedSeats;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_snacks_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets)->{
            Insets systemBars=insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom);
            return insets;
        });
        init();
        btnGo.setOnClickListener(v ->
        {
            Intent intent=new Intent(this,ConfirmationPage.class);
            intent.putStringArrayListExtra("reservedSeats", reservedSeats);
            String movieName=getIntent().getStringExtra("moviename");
            intent.putExtra("moviename", movieName);

            ArrayList<String> snackNames=new ArrayList<>();
            ArrayList<Integer> snackQty=new ArrayList<>();
            ArrayList<Double> snackPrice=new ArrayList<>();
            double pricePopcorn=4.99;
            double priceNachos=7.99;
            double priceDrinks=5.99;
            double priceCandy=6.99;

            if (getQuantity(tvQuantity)>0)
            {
                snackNames.add("Popcorn");
                snackQty.add(getQuantity(tvQuantity));
                snackPrice.add(pricePopcorn);
            }
            if (getQuantity(tvQuantity1)>0)
            {
                snackNames.add("Nachos");
                snackQty.add(getQuantity(tvQuantity1));
                snackPrice.add(priceNachos);
            }
            if (getQuantity(tvQuantity2)>0)
            {
                snackNames.add("Soft Drinks");
                snackQty.add(getQuantity(tvQuantity2));
                snackPrice.add(priceDrinks);
            }
            if (getQuantity(tvQuantity3)>0)
            {
                snackNames.add("Candy Mix");
                snackQty.add(getQuantity(tvQuantity3));
                snackPrice.add(priceCandy);
            }

            ArrayList<String> snackPriceStr=new ArrayList<>();
            for (double p:snackPrice) snackPriceStr.add(String.valueOf(p));
            intent.putStringArrayListExtra("snackNames",snackNames);
            intent.putIntegerArrayListExtra("snackQty",snackQty);
            intent.putStringArrayListExtra("snackPrice",snackPriceStr);
            startActivity(intent);
        });
    }
    private void init()
    {
        reservedSeats = getIntent().getStringArrayListExtra("reservedSeats");
        String movieName=getIntent().getStringExtra("moviename");
        btnInc=findViewById(R.id.btnInc);
        btnDec=findViewById(R.id.btnDec);
        btnInc1=findViewById(R.id.btnInc1);
        btnDec1=findViewById(R.id.btnDec1);
        btnInc2=findViewById(R.id.btnInc2);
        btnDec2=findViewById(R.id.btnDec2);
        btnInc3=findViewById(R.id.btnInc3);
        btnDec3=findViewById(R.id.btnDec3);
        tvQuantity=findViewById(R.id.tvQuantity);
        tvQuantity1=findViewById(R.id.tvQuantity1);
        tvQuantity2=findViewById(R.id.tvQuantity2);
        tvQuantity3=findViewById(R.id.tvQuantity3);
        btnGo=findViewById(R.id.btnGo);
        btnDec.setEnabled(getQuantity(tvQuantity)>0);
        btnDec1.setEnabled(getQuantity(tvQuantity1)>0);
        btnDec2.setEnabled(getQuantity(tvQuantity2)>0);
        btnDec3.setEnabled(getQuantity(tvQuantity3)>0);

        setupCounter(btnInc,btnDec,tvQuantity);
        setupCounter(btnInc1,btnDec1,tvQuantity1);
        setupCounter(btnInc2,btnDec2,tvQuantity2);
        setupCounter(btnInc3,btnDec3,tvQuantity3);
    }
    private void setupCounter(Button btnInc,Button btnDec,TextView tvQuantity)
    {
        btnInc.setOnClickListener(v ->
        {
            int qty=getQuantity(tvQuantity);
            qty++;
            tvQuantity.setText(String.valueOf(qty));
            btnDec.setEnabled(true);
        });

        btnDec.setOnClickListener(v ->
        {
            int qty=getQuantity(tvQuantity);
            if (qty>0)
            {
                qty--;
                tvQuantity.setText(String.valueOf(qty));
                if (qty==0)
                {
                    btnDec.setEnabled(false);
                }
            }
        });
    }

    private int getQuantity(TextView tv)
    {
        try {
            return Integer.parseInt(tv.getText().toString());
        } catch (NumberFormatException e)
        {
            return 0;
        }
    }

}