package com.example.quranapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Control extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controler);
        CardView teach_cardView = findViewById(R.id.teach_cardView);
        CardView students_cardView = findViewById(R.id.students_cardView);
        CardView missions_cardView = findViewById(R.id.stud_missions_cardView);
        CardView review_cardView = findViewById(R.id.student_review_cardView);


        teach_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Teach.class);
                startActivity(i);
            }
        });

        students_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Students.class);
                startActivity(i);
            }
        });

        missions_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Missions.class);
                startActivity(i);
            }
        });

        review_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Review.class);
                startActivity(i);
            }
        });
    }
}
