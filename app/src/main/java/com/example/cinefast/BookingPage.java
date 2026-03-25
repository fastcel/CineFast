package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class BookingPage extends AppCompatActivity {

    GridLayout glSeats;
    TextView tvName;
    TextView tvTotalPrice;
    TextView tvNumseats;
    final int TICKET_PRICE=16;
    ImageView ivBack;
    ArrayList<TextView> selectedSeats = new ArrayList<>();
    com.google.android.material.button.MaterialButton btnSnacks;
    com.google.android.material.button.MaterialButton btnBookseats;
    private final String[] seatLabels = {
            "A1", "A2", "A3", "A4", "A5", "A6",
            "B1", "B2", "B3", "B4", "B5", "B6",
            "C1", "C2", "C3", "C4", "C5", "C6",
            "D1", "D2", "D3", "D4", "D5", "D6",
            "E1", "E2", "E3", "E4", "E5", "E6"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        glSeats=findViewById(R.id.glSeats);
        btnSnacks=findViewById(R.id.btnSnacks);
        btnBookseats=findViewById(R.id.btnBookseats);
        btnSnacks.setEnabled(false);
        tvName=findViewById(R.id.tvName);
        int[] bookedSeats={2,5,12};
        tvTotalPrice=findViewById(R.id.tvTotalPrice);
        tvNumseats=findViewById(R.id.tvNumseats);
        ivBack=findViewById(R.id.IVBack);
        if(tvName!=null)
        {
            String name=getIntent().getStringExtra("moviename");
            tvName.setText(name);
        }
        for (int i=0; i<glSeats.getChildCount();i++)
        {
            final TextView seat=(TextView) glSeats.getChildAt(i);
            boolean isBooked = false;
            for (int b:bookedSeats)
            {
                if (i==b)
                {
                    isBooked=true;
                    break;
                }
            }

            if (isBooked)
            {
                seat.setBackground(ContextCompat.getDrawable(this,R.drawable.seat_booked));
                seat.setClickable(false);
            }
            else
            {
                seat.setBackground(ContextCompat.getDrawable(this,R.drawable.seat_available));
                seat.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        toggleSeat(seat);
                    }
                });
            }
        }
        btnSnacks.setOnClickListener(v ->
        {
            ArrayList<String> reservedSeatLabels = new ArrayList<>();
            for (TextView seat:selectedSeats)
            {
                int index = glSeats.indexOfChild(seat);
                reservedSeatLabels.add("Row " + seatLabels[index].charAt(0) + " Seat " + seatLabels[index].substring(1));
            }
            Intent intent = new Intent(this, SnacksPage.class);
            intent.putStringArrayListExtra("reservedSeats",reservedSeatLabels);
            String movieName = getIntent().getStringExtra("moviename");
            intent.putExtra("moviename",movieName);
            startActivity(intent);
        });

        ivBack.setOnClickListener(v ->
        {
            finish();
        });

        btnBookseats.setOnClickListener(v ->
        {
            if (selectedSeats.isEmpty())
            {
                Toast.makeText(BookingPage.this,
                        "Please select at least one seat to proceed",Toast.LENGTH_SHORT).show();
                return;
            }
            ArrayList<String> reservedSeatLabels = new ArrayList<>();
            for (TextView seat:selectedSeats) {
                int index=glSeats.indexOfChild(seat);
                reservedSeatLabels.add("Row "+seatLabels[index].charAt(0)+" Seat "+seatLabels[index].substring(1));
            }

            Intent intent=new Intent(this,ConfirmationPage.class);
            intent.putStringArrayListExtra("reservedSeats",reservedSeatLabels);
            String movieName=getIntent().getStringExtra("moviename");
            intent.putExtra("moviename",movieName);
            startActivity(intent);
        });

    }

    private void toggleSeat(TextView seat)
    {
        if (selectedSeats.contains(seat))
        {
            selectedSeats.remove(seat);
            seat.setBackground(ContextCompat.getDrawable(this,R.drawable.seat_available));
        }
        else
        {
            selectedSeats.add(seat);
            seat.setBackground(ContextCompat.getDrawable(this,R.drawable.seat_selected));
        }
        btnSnacks.setEnabled(!selectedSeats.isEmpty());
        int numberOfSeats=selectedSeats.size();
        int totalPrice=numberOfSeats*TICKET_PRICE;
        tvNumseats.setText("Seats: " + numberOfSeats);
        tvTotalPrice.setText("Total: $" + totalPrice);
    }

}
