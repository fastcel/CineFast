package com.example.cinefast;

public class Movie {
    private String poster;
    private String name;
    private String genre;
    private String trailerUrl;
    private boolean isComingSoon;

    public Movie(String poster, String name, String genre, String trailerUrl, boolean isComingSoon) {
        this.poster = poster;
        this.name = name;
        this.genre = genre;
        this.trailerUrl = trailerUrl;
        this.isComingSoon = isComingSoon;
    }

    public String getPoster() { return poster; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getTrailerUrl() { return trailerUrl; }
    public boolean isComingSoon() { return isComingSoon; }
}