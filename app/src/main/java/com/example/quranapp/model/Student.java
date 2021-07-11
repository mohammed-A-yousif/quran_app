package com.example.quranapp.model;

public class Student {
    String name;
    String TeacherName;
    String Address;
    String phone;
    String date;


    public Student(String name, String TeacherName, String Address, String phone, String date) {
        this.name = name;
        this.TeacherName = TeacherName;
        this.Address = Address;
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

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return name;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }
}
