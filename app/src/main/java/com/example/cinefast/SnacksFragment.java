package com.example.cinefast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    ListView listView;
    ArrayList<Snack> snackList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_snacks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listViewSnacks);

        snackList = new ArrayList<>();

        snackList.add(new Snack(R.drawable.popcorn, "Popcorn", "Large Buttered", 499));
        snackList.add(new Snack(R.drawable.nachos, "Nachos", "With Cheese Dip", 799));
        snackList.add(new Snack(R.drawable.drinks, "Soft Drinks", "Large Any Flavor", 599));
        snackList.add(new Snack(R.drawable.candymix, "Candy Mix", "Assorted Candies", 699));

        SnackAdapter adapter = new SnackAdapter(getContext(), snackList);
        listView.setAdapter(adapter);
    }
}