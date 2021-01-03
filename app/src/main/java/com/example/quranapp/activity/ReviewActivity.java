package com.example.quranapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.quranapp.R;
import com.example.quranapp.ViewDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewActivity extends AppCompatActivity {
    String DatePicked;
    ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);

        viewDialog = new ViewDialog(this);
        CardView cardView = findViewById(R.id.review_cardView);
        Button addReviewTextButton = findViewById(R.id.add_review_textButton);

        final TextView yearTextView = findViewById(R.id.year_textView);
        CalendarView calendarView = findViewById(R.id.calenderView);

        SimpleDateFormat initial_sdf = new SimpleDateFormat("dd-MM-yyyy");
        String initialDate = initial_sdf.format(new Date(calendarView.getDate()));

        DatePicked = initialDate;
        yearTextView.setText(initialDate);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
             DatePicked = sdf.format(new Date(year - 1900, month, dayOfMonth));
            yearTextView.setText(DatePicked);
        });

        addReviewTextButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentsReviews.class);
            intent.putExtra("DatePicekd", DatePicked);
            startActivity(intent);

        });
    }


}
