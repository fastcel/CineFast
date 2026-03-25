package com.example.cinefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.GridLayout;

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

    public SeatSelectionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tvName);
        tvSeats = view.findViewById(R.id.tvNumseats);
        tvPrice = view.findViewById(R.id.tvTotalPrice);
        gridLayout = view.findViewById(R.id.glSeats);
        MaterialButton btnSnacks = view.findViewById(R.id.btnSnacks);

        if (getArguments() != null) {
            movieName = getArguments().getString("movieName", "");
            tvName.setText(movieName);
        }

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View seat = gridLayout.getChildAt(i);

            if (seat instanceof TextView) {
                TextView seatView = (TextView) seat;

                seatView.setOnClickListener(v -> toggleSeat(seatView));
            }
        }

        // Proceed to snacks
        btnSnacks.setOnClickListener(v -> {
            if (getActivity() instanceof HomePage) {
                ((HomePage) getActivity()).showSnacksFragment(
                        movieName,
                        selectedSeats.size(),
                        selectedSeats.size() * seatPrice
                );
            }
        });
    }

    private void toggleSeat(TextView seat) {
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
            seat.setBackgroundResource(R.drawable.seat_available); // default
        } else {
            selectedSeats.add(seat);
            seat.setBackgroundResource(R.drawable.seat_selected); // selected
        }

        updateUI();
    }

    public void setMovieName(String name) {
        this.movieName = name;

        if (tvName != null) { // fragment already visible
            tvName.setText(name);
        }
    }
    private void updateUI() {
        int count = selectedSeats.size();
        int total = count * seatPrice;

        tvSeats.setText("Seats: " + count);
        tvPrice.setText("Total: Rs " + total);
    }
}