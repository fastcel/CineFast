package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFragment extends Fragment {

    private TextView tvName, tvSeats, tvPrice;
    private GridLayout gridLayout;

    private int seatPrice = 500;
    private List<TextView> selectedSeats = new ArrayList<>();

    private String movieName = "";
    private boolean isComingSoon = false;
    private String trailerUrl = "";

    public SeatSelectionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_seat_selection,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        tvName=view.findViewById(R.id.tvName);
        tvSeats=view.findViewById(R.id.tvNumseats);
        tvPrice=view.findViewById(R.id.tvTotalPrice);
        gridLayout=view.findViewById(R.id.glSeats);
    }
    public void setMovieData(String name,boolean isComingSoon,String trailerUrl)
    {
        this.movieName=name;
        this.isComingSoon=isComingSoon;
        this.trailerUrl=trailerUrl;
        if (getView()!=null)
        {
            tvName.setText(name);
            applyMovieTypeUI();
        }
    }
    private void applyMovieTypeUI()
    {
        if (getView()==null) return;
        MaterialButton btnSnacks=getView().findViewById(R.id.btnSnacks);
        MaterialButton btnBook=getView().findViewById(R.id.btnBookseats);
        tvName.setText(movieName);
        selectedSeats.clear();
        updateUI();
        for (int i=0;i<gridLayout.getChildCount();i++)
        {
            View seat=gridLayout.getChildAt(i);
            if (seat instanceof TextView)
            {
                TextView seatView=(TextView) seat;
                if (!isComingSoon)
                {
                    seatView.setEnabled(true);
                    seatView.setAlpha(1f);
                    seatView.setOnClickListener(v -> toggleSeat(seatView));
                } else
                {
                    seatView.setEnabled(false);
                    seatView.setAlpha(0.5f);
                    seatView.setOnClickListener(null);
                }
            }
        }
        if (isComingSoon) {
            btnBook.setText("Coming Soon");
            btnBook.setEnabled(false);
            btnSnacks.setText("Watch Trailer");
            btnSnacks.setOnClickListener(v ->
            {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(trailerUrl));
                startActivity(intent);
            });

        }
        else
        {
            btnBook.setText("Book Seats");
            btnBook.setEnabled(true);
            btnSnacks.setText("Proceed to Snacks");
            btnSnacks.setOnClickListener(v ->
            {
                if (getActivity() instanceof HomePage)
                {
                    ((HomePage) getActivity()).showSnacksFragment(
                            movieName,
                            selectedSeats.size(),
                            selectedSeats.size()*seatPrice
                    );
                }
            });
            btnBook.setOnClickListener(v ->
            {
                Toast.makeText(getContext(),"Booking Confirmed!",Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void toggleSeat(TextView seat)
    {
        if (selectedSeats.contains(seat))
        {
            selectedSeats.remove(seat);
            seat.setBackgroundResource(R.drawable.seat_available);
        } else
        {
            selectedSeats.add(seat);
            seat.setBackgroundResource(R.drawable.seat_selected);
        }
        updateUI();
    }
    private void updateUI() {
        int count=selectedSeats.size();
        int total=count*seatPrice;
        tvSeats.setText("Seats: " + count);
        tvPrice.setText("Total: Rs " + total);
    }
}