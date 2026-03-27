package com.example.cinefast;

public class Movie {
    private int posterResId;
    private String name;
    private String genre;
    private String trailerUrl;
    private boolean isComingSoon;

    public Movie(int posterResId,String name,String genre,String trailerUrl,boolean isComingSoon) {
        this.posterResId=posterResId;
        this.name=name;
        this.genre=genre;
        this.trailerUrl=trailerUrl;
        this.isComingSoon=isComingSoon;
    }
    public int getPosterResId() { return posterResId; }
    public boolean isComingSoon() { return isComingSoon; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getTrailerUrl() { return trailerUrl; }
}