package com.example.quranapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Review extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);
        CardView cardView = findViewById(R.id.review_cardView);
        Button addReviewTextButton = findViewById(R.id.add_review_textButton);
        final TextView yearTextView = findViewById(R.id.year_textView);
        CalendarView calendarView = findViewById(R.id.calenderView);
        SimpleDateFormat initial_sdf = new SimpleDateFormat("dd/MM/yyyy");
        String initialDate = initial_sdf.format(new Date(calendarView.getDate()));
        yearTextView.setText(initialDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDates = sdf.format(new Date(year - 1900, month, dayOfMonth));
                yearTextView.setText(selectedDates);
            }
        });

        addReviewTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StudentsReviews.class);
                startActivity(i);

            }
        });
    }
}
