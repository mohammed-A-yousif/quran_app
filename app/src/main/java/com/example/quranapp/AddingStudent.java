package com.example.quranapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class AddingStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText student_name_editText;
    EditText student_living_editText;
    EditText student_phone_editText;
    EditText student_password_editText;
    EditText student_work_editText;
    EditText student_AcademicLevel_editText;

    String student_name_;
    String student_living_;
    String student_phone_;
    String student_password_;
    String student_work_;
    String student_AcademicLevel_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_student);

        student_name_editText = findViewById(R.id.student_name_editText);
        student_living_editText = findViewById(R.id.student_living_editText);
        student_phone_editText = findViewById(R.id.student_phone_editText);
        student_password_editText = findViewById(R.id.student_password_editText);
        student_work_editText = findViewById(R.id.student_work_editText);
        student_AcademicLevel_editText = findViewById(R.id.student_AcademicLevel_editText);

        Button addStudentButton = findViewById(R.id.add_student_button);
        Spinner addStudentSpinner = findViewById(R.id.add_student_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.Teachers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addStudentSpinner.setAdapter(adapter);
        addStudentSpinner.setOnItemSelectedListener(this);


        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addStudent();
                Intent i = new Intent(getApplicationContext(), StudentsActivity.class);
                startActivity(i);
            }
        });
    }


    private void addStudent() {

        student_name_ = student_name_editText.getText().toString();
        student_living_ = student_living_editText.getText().toString();
        student_phone_ = student_phone_editText.getText().toString();
        student_password_ = student_password_editText.getText().toString();
        student_work_ = student_work_editText.getText().toString();
        student_AcademicLevel_ = student_living_editText.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,  URLs.AddStudent + "?IdAdmin=" + SharedPrefManager.getInstance(this).getAdmin().getId() + "&IdAdmin=" + 1  + "&Name=" + student_name_  + "&Password=" + student_password_
                + "&PhoneNumber=" + student_phone_ + "&Address=" + student_living_ + "&EductionLevel=" + student_AcademicLevel_ + "&WorkPlace=" + student_work_  + "&UserType=" + 3 + "&Enabled=" + 1, null,
                (JSONObject response) -> {
                    try {
                        String name = response.getString("Name");

                        Log.d("res", response.toString());
                        onInsertSuccess();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onInsertFailed();
                    }

                    Log.d("String Response : ", ""+  response.toString());
                    Log.d("name", String.valueOf(SharedPrefManager.getInstance(this).isLoggedIn()));
                }, error -> Log.d("Error getting response", "" +error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void onInsertFailed() {
//        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Failed", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    addStudent();
                }).show();
    }

    private void onInsertSuccess() {
//        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Successfully", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, StudentsActivity.class));
        finish();
    }
}