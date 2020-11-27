package com.example.quranapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class AddingTeacher extends AppCompatActivity {

    EditText teacher_name_editText;
    EditText teacher_living_editText;
    EditText teacher_phone_editText;
    EditText teacher_password_editText;


    String teacher_name_;
    String teacher_living_;
    String teacher_phone_;
    String teacher_password_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_teacher);


        teacher_name_editText = findViewById(R.id.teacher_name_editText);
        teacher_living_editText = findViewById(R.id.teacher_living_editText);
        teacher_phone_editText = findViewById(R.id.teacher_phone_editText);
        teacher_password_editText = findViewById(R.id.teacher_password_editText);

        Button addTeacherButton = findViewById(R.id.add_teacher_button);

        addTeacherButton.setOnClickListener(v -> AddTeacher());
    }

    private void AddTeacher() {

        teacher_name_ = teacher_name_editText.getText().toString();
        teacher_living_ = teacher_living_editText.getText().toString();
        teacher_phone_ = teacher_phone_editText.getText().toString();
        teacher_password_ = teacher_password_editText.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.AddTeacher + "?IdAdmin=" + SharedPrefManager.getInstance(this).getAdmin().getId() + "&Name=" + teacher_name_ + "&Password=" + teacher_password_
                + "&PhoneNumber=" + teacher_phone_ + "&Address=" + teacher_living_ + "&UserType=" + 2 + "&Enabled=" + 1, null,
                (JSONObject response) -> {
                    try {
                        String name = response.getString("Name");

                        Log.d("res", response.toString());
                        onInsertSuccess();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onInsertFailed();
                    }

                    Log.d("String Response : ", "" + response.toString());
                    Log.d("name", String.valueOf(SharedPrefManager.getInstance(this).isLoggedIn()));
                }, error -> Log.d("Error getting response", "" + error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }

    private void onInsertFailed() {
//        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Failed", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    AddTeacher();
                }).show();
    }

    private void onInsertSuccess() {
//        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Successfully", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, TeacherActivity.class));
        finish();
    }
}