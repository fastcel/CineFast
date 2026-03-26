package com.example.cinefast;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    HomeFragment homeFragment;
    SeatSelectionFragment seatFragment;
    SnacksFragment snacksFragment;
    TicketSummaryFragment summaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.homeFragment);
        seatFragment = (SeatSelectionFragment) getSupportFragmentManager().findFragmentById(R.id.seatFragment);
        snacksFragment = (SnacksFragment) getSupportFragmentManager().findFragmentById(R.id.snacksFragment);
        summaryFragment = (TicketSummaryFragment) getSupportFragmentManager().findFragmentById(R.id.summaryFragment);
    }

    public void showSeatFragment(String movieName, boolean isComingSoon, String trailerUrl) {
        seatFragment.setMovieData(movieName, isComingSoon, trailerUrl);
        showFragment(seatFragment);
    }

    public void showSnacksFragment(String movieName, ArrayList<String> selectedSeats, int seatPrice, int seatsTotal) {
        Bundle bundle = new Bundle();
        bundle.putString("movieName", movieName);
        bundle.putStringArrayList("selectedSeats", selectedSeats);
        bundle.putInt("seatPrice", seatPrice);
        bundle.putInt("total", seatsTotal);
        snacksFragment.setArguments(bundle);
        showFragment(snacksFragment);
    }

    public void showTicketSummaryFragment(String movieName, ArrayList<String> selectedSeats, int seatPrice, ArrayList<String> snacks) {
        summaryFragment.setData(movieName, selectedSeats, seatPrice, snacks);
        showFragment(summaryFragment);
    }

    public void saveLastBooking(String movieName, int seats, int total) {
        SharedPreferences prefs = getSharedPreferences("LAST_BOOKING", MODE_PRIVATE);
        prefs.edit()
                .putString("movieName", movieName)
                .putInt("seats", seats)
                .putInt("total", total)
                .apply();
    }
    public Bundle getLastBooking() {
        SharedPreferences prefs = getSharedPreferences("LAST_BOOKING", MODE_PRIVATE);
        Bundle bundle = new Bundle();
        if (prefs.contains("movieName")) {
            bundle.putString("movieName", prefs.getString("movieName", ""));
            bundle.putInt("seats", prefs.getInt("seats", 0));
            bundle.putInt("total", prefs.getInt("total", 0));
        }
        return bundle;
    }

    private void showFragment(Fragment fragmentToShow) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(homeFragment)
                .hide(seatFragment)
                .hide(snacksFragment)
                .hide(summaryFragment)
                .show(fragmentToShow)
                .commit();
    }
}