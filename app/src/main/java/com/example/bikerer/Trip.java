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

    //Nho generate getter khong thi se ko gui duoc du lieu len realtime database

    public String getUserEmail() {
        return userEmail;
    }

    public String getDestination() {
        return destination;
    }

    public String getDistance() {
        return distance;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getPrice() {
        return price;
    }
}
