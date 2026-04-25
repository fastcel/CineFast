package com.example.cinefast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.activity.OnBackPressedCallback;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    // 🔥 ADDED (Drawer + Firebase)
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;
    SharedPreferences prefs;

    HomeFragment homeFragment;
    SeatSelectionFragment seatFragment;
    SnacksFragment snacksFragment;
    TicketSummaryFragment summaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // 🔥 REQUIRED Toast + Log
        Toast.makeText(this, "Welcome to CineFAST", Toast.LENGTH_SHORT).show();
        Log.d("CineFAST", "HomePage Launched");

        // 🔥 INIT
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        auth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("cinefast_session_pref_v3", MODE_PRIVATE);

        // 🔥 Drawer menu handling
        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                showFragment(homeFragment);
            }

            else if (id == R.id.nav_bookings) {
                showLastBooking();
            }

            else if (id == R.id.nav_logout) {
                logoutUser();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // EXISTING CODE (UNCHANGED)
        homeFragment=(HomeFragment) getSupportFragmentManager().findFragmentById(R.id.homeFragment);
        seatFragment=(SeatSelectionFragment) getSupportFragmentManager().findFragmentById(R.id.seatFragment);
        snacksFragment=(SnacksFragment) getSupportFragmentManager().findFragmentById(R.id.snacksFragment);
        summaryFragment=(TicketSummaryFragment) getSupportFragmentManager().findFragmentById(R.id.summaryFragment);

        getOnBackPressedDispatcher().addCallback(this,new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed()
            {
                // 🔥 HANDLE DRAWER FIRST
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return;
                }

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

    // 🔥 ADDED: open drawer from button
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    // 🔥 ADDED: logout
    private void logoutUser() {
        auth.signOut();
        prefs.edit().clear().apply();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    // 🔥 ADDED: bookings (reuse your SharedPreferences)
    private void showLastBooking() {
        SharedPreferences sp=getSharedPreferences("LAST_BOOKING",MODE_PRIVATE);

        String movie=sp.getString("movieName",null);
        int seats=sp.getInt("seats",-1);
        int total=sp.getInt("total",-1);

        if (movie==null||seats==-1||total==-1) {
            Toast.makeText(this,"No previous booking found",Toast.LENGTH_SHORT).show();
            return;
        }

        String msg="Movie: "+movie+"\nSeats: "+seats+"\nTotal: $"+total;
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    // 🔥 YOUR ORIGINAL FUNCTIONS (UNCHANGED)

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