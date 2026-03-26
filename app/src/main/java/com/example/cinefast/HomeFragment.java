package com.example.cinefast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageButton btnMenu;

    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout=view.findViewById(R.id.tabLayout);
        viewPager=view.findViewById(R.id.viewPager);
        btnMenu=view.findViewById(R.id.btnMenu);
        HomePagerAdapter adapter=new HomePagerAdapter(this);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> tab.setText(position == 0 ? "Now Showing" : "Coming Soon")).attach();
        btnMenu.setOnClickListener(v -> {
            PopupMenu popupMenu=new PopupMenu(requireContext(),btnMenu);
            popupMenu.getMenu().add("View Last Booking");
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("View Last Booking"))
                {
                    showLastBooking();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }
    private void showLastBooking()
    {
        SharedPreferences sp=requireContext().getSharedPreferences("LAST_BOOKING",Context.MODE_PRIVATE);
        String movie=sp.getString("movieName",null);
        int seats=sp.getInt("seats",-1);
        int totalPrice=sp.getInt("total",-1);
        if (movie==null||seats==-1||totalPrice==-1)
        {
            Toast.makeText(requireContext(),"No previous booking found",Toast.LENGTH_SHORT).show();
            return;
        }
        String message="Movie: "+movie+"\n"+"Seats: "+seats+"\n"+"Total Price: $"+totalPrice;
        new AlertDialog.Builder(requireContext())
                .setTitle("Last Booking")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}