package com.example.bikerer;

public class Driver {
    String id;

    String userEmail;
    String name;
    String numTrips;
    String latitude;
    String longitude;
    String vehicle;
    public Driver(String id, String userEmail, String name, String numTrips,String vehicle, String latitude, String longitude) {
        this.id = id;
        this.userEmail = userEmail;
        this.name = name;
        this.numTrips = numTrips;
        this.vehicle = vehicle;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Driver() {
    }
    public String getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getName() {
        return name;
    }

    public String getNumTrips() {
        return numTrips;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumTrips(String numTrips) {
        this.numTrips = numTrips;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}

