package com.example.bikerer;

// src/com/example/bikerer/SOSRequest.java

public class SosRequest {
    private String userEmail;
    private String timestamp;

    public SosRequest() {
        // Default constructor required for calls to DataSnapshot.getValue(SOSRequest.class)
    }

    public SosRequest(String userEmail, String timestamp) {
        this.userEmail = userEmail;
        this.timestamp = timestamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
