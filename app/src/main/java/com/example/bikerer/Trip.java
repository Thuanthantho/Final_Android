package com.example.bikerer;

public class Trip {
    String id;
    String userEmail;
    String destination;
    String distance;
    String vehicle;
    String price;
    public Trip(String id,String userEmail, String destination, String distance,String vehicle, String price) {
        this.id = id;
        this.userEmail = userEmail;
        this.destination = destination;
        this.distance = distance;
        this.vehicle = vehicle;
        this.price = price; 
    }

    public String getId() {
        return id;
    }
}
