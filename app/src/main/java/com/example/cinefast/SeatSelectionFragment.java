package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFragment extends Fragment {

    private TextView tvName,tvSeats,tvPrice;
    private GridLayout gridLayout;
    private List<TextView>selectedSeats=new ArrayList<>();
    private List<String> selectedSeatNames=new ArrayList<>();
    private List<String> occupiedSeats=new ArrayList<>();

    private int seatPrice=500;
    private String movieName="";
    private boolean isComingSoon=false;
    private String trailerUrl="";
    public SeatSelectionFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selection,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName=view.findViewById(R.id.tvName);
        tvSeats=view.findViewById(R.id.tvNumseats);
        tvPrice=view.findViewById(R.id.tvTotalPrice);
        gridLayout=view.findViewById(R.id.glSeats);
        ImageView ivBack=view.findViewById(R.id.IVBack);
        ivBack.setOnClickListener(v ->
        {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
    }
    public void setMovieData(String name,boolean isComingSoon,String trailerUrl) {
        this.movieName=name;
        this.isComingSoon=isComingSoon;
        this.trailerUrl=trailerUrl;
        if (getView()!=null)
        {
            tvName.setText(name);
            applyMovieTypeUI();
        }
    }
    private void applyMovieTypeUI() {
        if (getView()==null) return;
        MaterialButton btnSnacks=getView().findViewById(R.id.btnSnacks);
        MaterialButton btnBook = getView().findViewById(R.id.btnBookseats);
        selectedSeats.clear();
        selectedSeatNames.clear();
        updateUI();
        if (!isComingSoon) {
            btnBook.setEnabled(false);
            btnSnacks.setEnabled(false);
        }
        initOccupiedSeats();
        for (int i=0; i<gridLayout.getChildCount();i++)
        {
            View seatView = gridLayout.getChildAt(i);
            if (!(seatView instanceof TextView)) continue;
            TextView seat=(TextView) seatView;
            String row=String.valueOf((char) ('A'+i/6));
            int col=(i%6)+1;
            String seatName=row+col;
            seat.setText("");
            if (occupiedSeats.contains(seatName))
            {
                seat.setEnabled(false);
                seat.setBackgroundResource(R.drawable.seat_booked);
                seat.setAlpha(0.5f);
                seat.setOnClickListener(null);
            } else if (!isComingSoon)
            {
                seat.setEnabled(true);
                seat.setBackgroundResource(R.drawable.seat_available);
                seat.setAlpha(1f);
                seat.setOnClickListener(v->toggleSeat(seat,seatName,btnBook,btnSnacks));
            } else {
                seat.setEnabled(false);
                seat.setAlpha(0.5f);
                seat.setOnClickListener(null);
            }
        }

        if (isComingSoon) {
            btnBook.setText("Coming Soon");
            btnBook.setEnabled(false);
            btnSnacks.setText("Watch Trailer");
            btnSnacks.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(trailerUrl))));
        } else {
            btnBook.setText("Book Seats");
            btnSnacks.setText("Proceed to Snacks");
            btnSnacks.setOnClickListener(v -> {
                if (getActivity() instanceof HomePage) {
                    ArrayList<String> seatsToPass=new ArrayList<>(selectedSeatNames);
                    int seatsTotal=selectedSeats.size()*seatPrice;
                    ((HomePage) getActivity()).showSnacksFragment(
                            movieName,
                            seatsToPass,
                            seatPrice,
                            seatsTotal
                    );
                }
            });

            btnBook.setOnClickListener(v -> {
                if (getActivity() instanceof HomePage) {
                    ArrayList<String> seatsToPass=new ArrayList<>(selectedSeatNames);
                    int total=selectedSeats.size()*seatPrice;
                    Toast.makeText(getContext(),"Booking Confirmed!",Toast.LENGTH_SHORT).show();
                    ((HomePage) getActivity()).showTicketSummaryFragment(
                            movieName,
                            seatsToPass,
                            seatPrice,
                            new ArrayList<>()
                    );
                    ((HomePage) getActivity()).saveLastBooking(movieName,selectedSeats.size(),total);
                }
            });
        }
    }

    private void toggleSeat(TextView seat,String seatName,MaterialButton btnBook,MaterialButton btnSnacks) {
        if (selectedSeats.contains(seat))
        {
            selectedSeats.remove(seat);
            selectedSeatNames.remove(seatName);
            seat.setBackgroundResource(R.drawable.seat_available);
        } else {
            selectedSeats.add(seat);
            selectedSeatNames.add(seatName);
            seat.setBackgroundResource(R.drawable.seat_selected);
        }
        updateUI();
        boolean hasSelection=!selectedSeats.isEmpty();
        btnBook.setEnabled(hasSelection);
        btnSnacks.setEnabled(hasSelection);
    }

    private void initOccupiedSeats() {
        occupiedSeats.clear();
        occupiedSeats.add("A1");
        occupiedSeats.add("B3");
        occupiedSeats.add("C5");
    }

    private void updateUI() {
        int count = selectedSeats.size();
        int total = count * seatPrice;
        tvSeats.setText("Seats: " + count);
        tvPrice.setText("Total: Rs " + total);
    }
}