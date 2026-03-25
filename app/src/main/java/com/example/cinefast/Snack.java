package com.example.cinefast;

public class Snack {
    private int image;
    private String name;
    private String description;
    private int price;
    private int quantity;

    public Snack(int image, String name, String description, int price) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = 0;
    }

    public int getImage() { return image; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}