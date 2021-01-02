package com.example.quranapp.model;

public class Student {
    String name;
    String phone;
    String date;


    public Student(String name, String phone, String date) {
        this.name = name;
        this.phone = phone;
        this.date = date;
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
