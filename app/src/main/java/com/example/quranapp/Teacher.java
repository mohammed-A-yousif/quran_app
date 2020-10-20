package com.example.quranapp;


public class Teacher {
    String name;
    String phone;
    String date;


    public Teacher(String name, String phone, String date) {
        this.name = name;
        this.phone = phone;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }
}
