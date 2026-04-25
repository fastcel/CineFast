package com.example.cinefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<Booking> bookingList=new ArrayList<>();
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_booking,container,false);
    }
    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view,savedInstanceState);
        recyclerView = view.findViewById(R.id.rvBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookingAdapter(getContext(),bookingList);
        recyclerView.setAdapter(adapter);
        loadBookings();
    }

    private void loadBookings() {
        FirebaseDatabase.getInstance()
                .getReference("bookings")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookingList.clear();

                        for (DataSnapshot data:snapshot.getChildren()) {
                            Booking b=data.getValue(Booking.class);

                            if (b!=null) {
                                b.setId(data.getKey());
                                bookingList.add(b);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(
                                getContext(),
                                "Error loading bookings",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}