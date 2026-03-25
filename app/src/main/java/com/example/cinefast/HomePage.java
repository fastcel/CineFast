package com.example.cinefast;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

    public void showSeatFragment(String movieName) {
        seatFragment.setMovieName(movieName);
        showFragment(seatFragment);
    }
    public void showSnacksFragment(String movieName, int seats, int total) {
        Bundle bundle = new Bundle();
        bundle.putString("movieName", movieName);
        bundle.putInt("seats", seats);
        bundle.putInt("total", total);

        snacksFragment.setArguments(bundle);
        showFragment(snacksFragment);
    }
    private void showFragment(Fragment fragmentToShow)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(homeFragment)
                .hide(seatFragment)
                .hide(snacksFragment)
                .hide(summaryFragment)
                .show(fragmentToShow)
                .commit();
    }
}