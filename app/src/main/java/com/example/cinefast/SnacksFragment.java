package com.example.cinefast;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
public class SnacksFragment extends Fragment
{
    ListView listView;
    ArrayList<Snack> snackList;
    String movieName;
    ArrayList<String> selectedSeats;
    int seatPrice;
    int seatsTotal;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_snacks,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listViewSnacks);
        Button btnGo = view.findViewById(R.id.btnGo);

        DBHelper dbHelper = new DBHelper(getContext());
        snackList = dbHelper.getAllSnacks();

        SnackAdapter adapter = new SnackAdapter(getContext(), snackList);
        listView.setAdapter(adapter);

        btnGo.setOnClickListener(v -> {
            ArrayList<String> selectedSnacks = new ArrayList<>();
            int snacksTotal = 0;

            for (Snack s : snackList) {
                if (s.getQuantity() > 0) {
                    selectedSnacks.add(s.getName() + " x" + s.getQuantity());
                    snacksTotal += s.getPrice() * s.getQuantity();
                }
            }

            int seatsTotal = (selectedSeats != null ? selectedSeats.size() : 0) * seatPrice;

            Toast.makeText(getContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();

            if (getActivity() instanceof HomePage) {
                ((HomePage) getActivity()).showTicketSummaryFragment(
                        movieName,
                        selectedSeats,
                        seatPrice,
                        selectedSnacks
                );
            }
        });
    }

    public void setData(String movieName, ArrayList<String> selectedSeats, int seatPrice, int seatsTotal) {
        this.movieName = movieName;
        this.selectedSeats = selectedSeats;
        this.seatPrice = seatPrice;
        this.seatsTotal = seatsTotal;
    }
}