package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatExtras;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ConfirmationPage extends AppCompatActivity {

    TextView tvName1;
    LinearLayout llTickets;
    LinearLayout llSnacks;
    LinearLayout llGrandTotal;
    ImageView IVBack;
    com.google.android.material.button.MaterialButton btnSend;
    static final double TICKET_PRICE=16.0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();


        btnSend.setOnClickListener(v ->
        {
            StringBuilder ticketDetails=new StringBuilder();
            ArrayList<String> reservedSeats=getIntent().getStringArrayListExtra("reservedSeats");
            double ticketTotal=0;

            if (reservedSeats!=null && !reservedSeats.isEmpty())
            {
                ticketDetails.append("Seats:\n");
                for (String seat:reservedSeats)
                {
                    ticketDetails.append(seat).append("\n");
                }

                ticketTotal=reservedSeats.size()*TICKET_PRICE;
            }
            ticketDetails.append("\nTotal Price: $").append(String.format("%.2f", ticketTotal));

            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,ticketDetails.toString());
            startActivity(Intent.createChooser(intent,"Share Ticket Via"));
        });

        IVBack.setOnClickListener(v->{
            finish();
        });

    }
    private void init()
    {
        tvName1=findViewById(R.id.tvName1);
        llTickets=findViewById(R.id.llTickets);
        llSnacks=findViewById(R.id.llSnacks);
        llGrandTotal=findViewById(R.id.llGrandTotal);
        IVBack=findViewById(R.id.IVBack);
        String movieName=getIntent().getStringExtra("moviename");
        btnSend=findViewById(R.id.btnSend);
        if (movieName!=null)
        {
            tvName1.setText(movieName);
        }
        ArrayList<String> reservedSeats = getIntent().getStringArrayListExtra("reservedSeats");
        double ticketTotal=0;
        if (reservedSeats!=null)
        {
            for (String seat:reservedSeats)
            {
                LinearLayout row=new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                TextView tvSeat=new TextView(this);
                tvSeat.setText(seat);
                tvSeat.setTextColor(getResources().getColor(android.R.color.white));
                tvSeat.setTextSize(18f);
                tvSeat.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                ));

                TextView tvPrice=new TextView(this);
                tvPrice.setText("$" + String.format("%.2f", TICKET_PRICE));
                tvPrice.setTextColor(getResources().getColor(android.R.color.white));
                tvPrice.setTextSize(18f);
                row.addView(tvSeat);
                row.addView(tvPrice);
                llTickets.addView(row);
                ticketTotal+=TICKET_PRICE;
            }
        }
        ArrayList<String> snackNames = getIntent().getStringArrayListExtra("snackNames");
        ArrayList<Integer> snackQty = getIntent().getIntegerArrayListExtra("snackQty");
        ArrayList<String> snackPriceStr = getIntent().getStringArrayListExtra("snackPrice");

        double totalSnackCost=0;

        if (snackNames!=null && snackQty!=null && snackPriceStr!=null)
        {
            for (int i=0; i<snackNames.size();i++)
            {
                String name=snackNames.get(i);
                int qty=snackQty.get(i);
                double price=Double.parseDouble(snackPriceStr.get(i))*qty;
                LinearLayout row=new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                TextView tvSnack=new TextView(this);
                tvSnack.setText(name+" x" + qty);
                tvSnack.setTextColor(getResources().getColor(android.R.color.white));
                tvSnack.setTextSize(18f);
                tvSnack.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                ));

                TextView tvSnackPrice=new TextView(this);
                tvSnackPrice.setText("$"+String.format("%.2f", price));
                tvSnackPrice.setTextColor(getResources().getColor(android.R.color.white));
                tvSnackPrice.setTextSize(18f);
                row.addView(tvSnack);
                row.addView(tvSnackPrice);
                llSnacks.addView(row);
                totalSnackCost+=price;
            }
        }
        double total=ticketTotal+totalSnackCost;
        LinearLayout totalRow=new LinearLayout(this);
        totalRow.setOrientation(LinearLayout.HORIZONTAL);
        totalRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView tvTotalLabel=new TextView(this);
        tvTotalLabel.setText("Total");
        tvTotalLabel.setTextColor(getResources().getColor(android.R.color.white));
        tvTotalLabel.setTextSize(20f);
        tvTotalLabel.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        ));
        TextView tvTotalAmount = new TextView(this);
        tvTotalAmount.setText("$"+String.format("%.2f",ticketTotal+totalSnackCost));
        tvTotalAmount.setTextColor(getResources().getColor(android.R.color.white));
        tvTotalAmount.setTextSize(20f);
        tvTotalAmount.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        totalRow.addView(tvTotalLabel);
        totalRow.addView(tvTotalAmount);
        llGrandTotal.addView(totalRow);
    }

}