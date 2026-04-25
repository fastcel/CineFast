package com.example.cinefast;

public class Booking {

    private String id;
    private String movieName;
    private String poster;
    private String dateTime;
    private int tickets;
    public Booking() {}
    public Booking(String id,String movieName,String poster,String dateTime,int tickets) {
        this.id=id;
        this.movieName=movieName;
        this.poster=poster;
        this.dateTime=dateTime;
        this.tickets=tickets;
    }

    public String getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getPoster() {
        return poster;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getTickets() {
        return tickets;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMovieName(String movieName) {
        this.movieName=movieName;
    }
    public void setPoster(String poster) {
        this.poster=poster;
    }
    public void setDateTime(String dateTime) {
        this.dateTime=dateTime;
    }
    public void setTickets(int tickets) {
        this.tickets=tickets;
    }
}