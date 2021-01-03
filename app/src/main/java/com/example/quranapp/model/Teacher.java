package com.example.quranapp.model;


public class Teacher {
    int id;
    String name;
    String address;
    String phone;
    String date;


    public Teacher(int id, String name, String Address, String phone, String date) {
        this.id = id;
        this.name = name;
        this.address = Address;
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

    public void setAddress(String address) {
        this.address = address;
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

    public String getAddress() {
        return address;
    }
}
