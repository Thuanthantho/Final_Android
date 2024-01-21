package com.example.bikerer;

public class MessageDetails {
    String id;
    String messageText;
    String email;
    public MessageDetails() {
    }

    public MessageDetails(String id, String messageText, String email) {
        this.id = id;
        this.messageText = messageText;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
