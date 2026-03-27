package com.example.cinefast;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.activity.OnBackPressedCallback;

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
        homeFragment=(HomeFragment) getSupportFragmentManager().findFragmentById(R.id.homeFragment);
        seatFragment=(SeatSelectionFragment) getSupportFragmentManager().findFragmentById(R.id.seatFragment);
        snacksFragment=(SnacksFragment) getSupportFragmentManager().findFragmentById(R.id.snacksFragment);
        summaryFragment=(TicketSummaryFragment) getSupportFragmentManager().findFragmentById(R.id.summaryFragment);
        getOnBackPressedDispatcher().addCallback(this,new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed()
            {
                if (summaryFragment!=null && summaryFragment.isVisible()) {
                    showFragment(snacksFragment);
                } else if (snacksFragment!=null && snacksFragment.isVisible()) {
                    showFragment(seatFragment);
                } else if (seatFragment!=null && seatFragment.isVisible()) {
                    showFragment(homeFragment);
                } else if (homeFragment!=null && homeFragment.isVisible()) {
                    finish();
                }
            }
        });
    }
    public void showSeatFragment(String movieName, boolean isComingSoon, String trailerUrl) {
        seatFragment.setMovieData(movieName,isComingSoon,trailerUrl);
        showFragment(seatFragment);
    }
    public void showSnacksFragment(String movieName,ArrayList<String> selectedSeats,int seatPrice,int seatsTotal)
    {
        snacksFragment.setData(movieName,selectedSeats,seatPrice,seatsTotal);
        showFragment(snacksFragment);
    }
    public void showTicketSummaryFragment(String movieName,ArrayList<String>selectedSeats,int seatPrice,ArrayList<String> snacks)
    {
        summaryFragment.setData(movieName,selectedSeats,seatPrice,snacks);
        showFragment(summaryFragment);
    }
    public void saveLastBooking(String movieName,int seats,int total)
    {
        SharedPreferences prefs=getSharedPreferences("LAST_BOOKING",MODE_PRIVATE);
        prefs.edit()
                .putString("movieName", movieName)
                .putInt("seats", seats)
                .putInt("total", total)
                .apply();
    }
    private void showFragment(Fragment fragmentToShow)
    {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.hide(homeFragment)
                .hide(seatFragment)
                .hide(snacksFragment)
                .hide(summaryFragment)
                .show(fragmentToShow)
                .commit();
    }
}