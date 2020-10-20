package com.example.quranapp;

public class URLs {

    public  static String BaseUrl = "http://10.0.2.2:5000/";

    public static String Login = BaseUrl + "admin_login/";
    public static String AddTeacher = BaseUrl + "teacher_register/";


    public static String getBaseUrl() {
        return BaseUrl;
    }
}
