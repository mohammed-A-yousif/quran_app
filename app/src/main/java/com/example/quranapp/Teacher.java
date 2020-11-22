package com.example.quranapp;


public class Teacher {
    int id;
    String name;
    String phone;
    String date;


    public Teacher(int id, String name, String phone, String date) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getId() {
        return id;
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
