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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Movie> movieList=new ArrayList<>();
        try {
            InputStream is=requireContext().getAssets().open("movies.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            String json=new String(buffer,StandardCharsets.UTF_8);
            JSONArray array=new JSONArray(json);
            for (int i=0;i<array.length(); i++) {
                JSONObject obj=array.getJSONObject(i);
                boolean isComingSoon=obj.getBoolean("isComingSoon");
                if (isComingSoon) {
                    movieList.add(new Movie(
                            obj.getString("poster"),
                            obj.getString("name"),
                            obj.getString("genre"),
                            obj.getString("trailerUrl"),
                            true
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter=new MovieAdapter(getActivity(), movieList);
        recyclerView.setAdapter(adapter);
    }
}