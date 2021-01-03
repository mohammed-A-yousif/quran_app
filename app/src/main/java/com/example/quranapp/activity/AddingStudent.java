package com.example.quranapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranapp.R;
import com.example.quranapp.SharedPrefManager;
import com.example.quranapp.URLs;
import com.example.quranapp.ViewDialog;
import com.example.quranapp.model.Teacher;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    ViewDialog viewDialog;
    private JSONArray jsonArray;
    List<Teacher> listItems ;

    private int IdTeacher;
    Spinner addStudentSpinner;

    private ArrayList<String> TeacherArray;
    private List<Teacher> TeacherList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_student);

        Toolbar toolbar = findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        student_name_editText = findViewById(R.id.student_name_editText);
        student_living_editText = findViewById(R.id.student_living_editText);
        student_phone_editText = findViewById(R.id.student_phone_editText);
        student_password_editText = findViewById(R.id.student_password_editText);
        student_work_editText = findViewById(R.id.student_work_editText);
        student_AcademicLevel_editText = findViewById(R.id.student_AcademicLevel_editText);

        Button addStudentButton = findViewById(R.id.add_student_button);
        addStudentSpinner = findViewById(R.id.add_student_spinner);

        viewDialog = new ViewDialog(this);
        listItems = new ArrayList<>();

        TeacherArray = new ArrayList<>();
        GetTeacher();

        addStudentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
                IdTeacher = listItems.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Snackbar.make(findViewById(android.R.id.content), "Please Choose Teacher !!! ", Snackbar.LENGTH_LONG)
                        .show();
            }
        });


        addStudentButton.setOnClickListener(v -> {

            addStudent();
            Intent i = new Intent(getApplicationContext(), StudentsActivity.class);
            startActivity(i);
        });
    }


    public void GetTeacher(){
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetTeachers, response -> {
            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject TeacherObject = jsonArray.getJSONObject(i);
                    String Name = TeacherObject.getString("Name");
                    String PhoneNumber = TeacherObject.getString("PhoneNumber");
                    int Id = TeacherObject.getInt("IdTeacher");
                    String Date = TeacherObject.getString("CreatedAt");
                    Teacher listItem = new Teacher(Id , Name, Name, PhoneNumber, Date);
                    listItems.add(listItem);
                    TeacherArray.add(listItem.getName());
                }

                addStudentSpinner.setAdapter(new ArrayAdapter<>(AddingStudent.this, R.layout.spinner_item, TeacherArray));
                viewDialog.hideDialog();
                Log.d("res", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                viewDialog.hideDialog();
                Snackbar.make(findViewById(android.R.id.content), "تعذر عرض الشيوخ " + e , Snackbar.LENGTH_LONG)
                        .setAction(" محاولة مرة اخري ", v -> GetTeacher()).show();
            }

        }, error -> {
            error.printStackTrace();
            viewDialog.hideDialog();
            Snackbar.make(findViewById(android.R.id.content), "تعذر عرض الشيوخ  " + error , Snackbar.LENGTH_LONG)
                    .setAction(" محاولة مرة اخري ", v -> GetTeacher()).show();
        });


        requestQueue.add(stringRequest);

    }


    private void addStudent() {

        student_name_ = student_name_editText.getText().toString();
        student_living_ = student_living_editText.getText().toString();
        student_phone_ = student_phone_editText.getText().toString();
        student_password_ = student_password_editText.getText().toString();
        student_work_ = student_work_editText.getText().toString();
        student_AcademicLevel_ = student_living_editText.getText().toString();


        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,  URLs.AddStudent + "?IdAdmin=" + SharedPrefManager.getInstance(this).getAdmin().getId() + "&IdTeacher=" + IdTeacher  + "&Name=" + student_name_  + "&Password=" + student_password_
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
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "adding task failed", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    addStudent();
                }).show();
    }

    private void onInsertSuccess() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "adding task done", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, StudentsActivity.class));
        finish();
    }
}