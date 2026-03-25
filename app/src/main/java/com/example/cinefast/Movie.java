package com.example.cinefast;

public class Movie {
    private int posterResId;
    private String name;
    private String genre;
    private String trailerUrl;

    public Movie(int posterResId, String name, String genre, String trailerUrl) {
        this.posterResId = posterResId;
        this.name = name;
        this.genre = genre;
        this.trailerUrl = trailerUrl;
    }

    public int getPosterResId() { return posterResId; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getTrailerUrl() { return trailerUrl; }
}