package com.example.quranapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


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

        TextView UserName = findViewById(R.id.text_user_name);
        String admin = SharedPrefManager.getInstance(this).getAdmin().getName();
        UserName.setText("مرحبا بك ! " + admin);

        teach_cardView.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), TeacherActivity.class);
            startActivity(i);
        });

        students_cardView.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), StudentsActivity.class);
            startActivity(i);
        });

        missions_cardView.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Missions.class);
            startActivity(i);
        });

        review_cardView.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Review.class);
            startActivity(i);
        });
    }
}
