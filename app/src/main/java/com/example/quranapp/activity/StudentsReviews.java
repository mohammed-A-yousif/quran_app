package com.example.quranapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranapp.R;
import com.example.quranapp.URLs;
import com.example.quranapp.ViewDialog;
import com.example.quranapp.adapter.ReviewAdapter;
import com.example.quranapp.adapter.StudentAdapter;
import com.example.quranapp.model.Review;
import com.example.quranapp.model.Student;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentsReviews extends AppCompatActivity {

    String DatePicekd;
    ViewDialog viewDialog;

    private JSONArray jsonArray;
    List<Review> listItems ;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_reviews);

        Toolbar toolbar = findViewById(R.id.review_toolbar);
        setSupportActionBar(toolbar);

        viewDialog = new ViewDialog(this);
        Intent intent = getIntent();

        RecyclerView recyclerView = findViewById(R.id.review_recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        adapter = new ReviewAdapter(listItems, this);
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        DatePicekd = intent.getStringExtra("DatePicekd");
        GetRview(DatePicekd);

    }

    private void GetRview(String datePicked) {
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetReviews + "?ReviewDate=" + datePicked , response -> {
            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject ReviewObject = jsonArray.getJSONObject(i);
                    int IdReview = ReviewObject.getInt("IdReview");
                    String ReviewDec = ReviewObject.getString("ReviewDec");
                    String NumberOfParts = ReviewObject.getString("NumberOfParts");
                    Review listItem = new Review(IdReview, ReviewDec, NumberOfParts);
                    listItems.add(listItem);
                }

                adapter.notifyDataSetChanged();
                viewDialog.hideDialog();
                Log.d("res", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                viewDialog.hideDialog();
                Snackbar.make(findViewById(android.R.id.content), " تعذر عرض الدارسين " + e , Snackbar.LENGTH_LONG)
                        .setAction("محاولة مرة اخري", v -> GetRview(DatePicekd)).show();
            }

        }, error -> {
            error.printStackTrace();
            viewDialog.hideDialog();
            Snackbar.make(findViewById(android.R.id.content), " تعذر عرض الدارسين " + error , Snackbar.LENGTH_LONG)
                    .setAction("محاولة مرة اخري", v -> GetRview(DatePicekd)).show();
        });

        requestQueue.add(stringRequest);

    }

}

