package com.example.quranapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranapp.activity.TeacherActivity;
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

    ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_teacher);

        viewDialog = new ViewDialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar_teach);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("اضافة شيخ");

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        teacher_name_editText = findViewById(R.id.teacher_name_editText);
        teacher_living_editText = findViewById(R.id.teacher_living_editText);
        teacher_phone_editText = findViewById(R.id.teacher_phone_editText);
        teacher_password_editText = findViewById(R.id.teacher_password_editText);

        Button addTeacherButton = findViewById(R.id.add_teacher_button);
        addTeacherButton.setOnClickListener(v -> AddTeacher());
    }

    private void AddTeacher() {

        viewDialog.showDialog();
        teacher_name_ = teacher_name_editText.getText().toString();
        teacher_living_ = teacher_living_editText.getText().toString();
        teacher_phone_ = teacher_phone_editText.getText().toString();
        teacher_password_ = teacher_password_editText.getText().toString();

        if (!validate()) {
            return;
        }

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
                }, error -> Log.d("Error getting response", "" + error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }

    private void onInsertFailed() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Failed", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    AddTeacher();
                }).show();
    }

    private void onInsertSuccess() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Successfully", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, TeacherActivity.class));
        finish();
    }

    private boolean validate() {
        boolean valid = true;

        if (teacher_name_.length() == 0 ) {
            teacher_name_editText.setError("الرجاء ادخال رقم هاتف صالح");
            valid = false;
        } else {
            teacher_name_editText.setError(null);
        }

        if (teacher_living_.length()  == 0) {
            teacher_living_editText.setError("كلمة السر يجب ان تحتوي علي اربعة حروف علي الاقل ");
            valid = false;
        } else {
            teacher_living_editText.setError(null);
        }

        if (teacher_phone_.length()  == 0) {
            teacher_phone_editText.setError("كلمة السر يجب ان تحتوي علي اربعة حروف علي الاقل ");
            valid = false;
        } else {
            teacher_phone_editText.setError(null);
        }

        if (teacher_password_.length()  == 0) {
            teacher_password_editText.setError("كلمة السر يجب ان تحتوي علي اربعة حروف علي الاقل ");
            valid = false;
        } else {
            teacher_password_editText.setError(null);
        }

        return valid;
    }
}