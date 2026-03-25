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

public class NowShowingFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList=new ArrayList<>();

    public NowShowingFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_showing,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieList.add(new Movie(R.drawable.dark_knight, "The Dark Knight", "Action / 2h 32m", "https://www.youtube.com/watch?v=EXeTwQWrcwY",false));
        movieList.add(new Movie(R.drawable.inception, "Inception", "Sci-Fi / 2h 28m", "https://www.youtube.com/watch?v=YoHD9XEInc0",false));
        movieList.add(new Movie(R.drawable.interstellar, "Interstellar", "Sci-Fi / 2h 49m", "https://www.youtube.com/watch?v=zSWdZVtXT7E",false));
        adapter=new MovieAdapter(getActivity(),movieList);
        recyclerView.setAdapter(adapter);
    }
}