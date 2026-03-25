package com.example.cinefast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ComingSoonFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList=new ArrayList<>();

    public ComingSoonFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_coming_soon,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieList.add(new Movie(
                R.drawable.dune,
                "Dune Part 2",
                "Sci-Fi / Coming Soon",
                "https://www.youtube.com/watch?v=Way9Dexny3w",true
        ));

        movieList.add(new Movie(
                R.drawable.insidious,
                "Insidious 3",
                "Horror / Coming Soon",
                "https://www.youtube.com/watch?v=zuZnRUcoWos",true
        ));

        movieList.add(new Movie(
                R.drawable.barbie,
                "Barbie 2",
                "Fantasy / Coming Soon",
                "https://www.youtube.com/watch?v=pBk4NYhWNMM",true
        ));
        adapter=new MovieAdapter(getActivity(), movieList);
        recyclerView.setAdapter(adapter);
    }
}