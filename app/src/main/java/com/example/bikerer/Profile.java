package com.example.bikerer;

public class Profile {
    String id;
    String userEmail;
    String name;
    String age;
    String gender;

    public Profile(){
    }

    public Profile(String id, String name, String age, String gender, String userEmail) {
        this.id = id;
        this.userEmail = userEmail;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
