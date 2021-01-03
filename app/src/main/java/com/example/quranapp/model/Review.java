package com.example.quranapp.model;

public class Review {
    private int Id;
    private String ReviewDec;
    private String NumberOfParts;

    public Review(int Id, String ReviewDec , String NumberOfParts) {
        this.Id = Id;
        this.ReviewDec = ReviewDec;
        this.NumberOfParts = NumberOfParts;
    }

    public int getId() {
        return Id;
    }

    public String getReviewDec() {
        return ReviewDec;
    }

    public String getNumberOfParts() {
        return NumberOfParts;
    }

}