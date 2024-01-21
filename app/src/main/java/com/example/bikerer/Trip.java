package com.example.bikerer;

public class Trip {
    String id;
    String userEmail;
    String destination;
    String distance;
    String vehicle;
    String price;

    String status;
    String driver;

    public Trip() {
    }

    public Trip(String id, String userEmail, String destination, String distance, String vehicle, String price, String driver, String status) {
        this.id = id;
        this.userEmail = userEmail;
        this.destination = destination;
        this.distance = distance;
        this.vehicle = vehicle;
        this.price = price;
        this.driver=driver;
        this.status = status;
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

    public String getVehicle() {
        return vehicle;
    }

    public String getPrice() {
        return price;
    }

    public String getDriver() {
        return driver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
