package com.example.bikerer;

public class Trip {
    String id;
    String userEmail;
    String destination;
    String distance;
    String price;
    public Trip(String id,String userEmail, String destination, String distance, String price) {
        this.id = id;
        this.userEmail = userEmail;
        this.destination = destination;
        this.distance = distance;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getDestination() {
        return destination;
    }

    public String getDistance() {
        return distance;
    }
    public String getPrice() {
        return price;
    }
}
