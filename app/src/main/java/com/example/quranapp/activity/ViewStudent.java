package com.example.quranapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

public class ViewStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText name_editText;
    TextView teacherName_textView;
    EditText address_editText;
    EditText phone_editText;
    EditText password_editText;
    EditText work_editText;
    EditText academicLevel_editText;

    String student_name_;
    String student_living_;
    String student_phone_;
    String student_password_;
    String student_work_;
    String student_AcademicLevel_;

    ViewDialog viewDialog;
    private JSONArray jsonArray;
    List<Teacher> listItems;

    private int IdTeacher;
    Spinner editStudentSpinner;

    private ArrayList<String> TeacherArray;
    private List<Teacher> TeacherList = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_student);

//        Toolbar
        Toolbar toolbar = findViewById(R.id.view_student_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        //    EditText/TV
        name_editText = findViewById(R.id.name_editText);
        teacherName_textView = findViewById(R.id.teacherName_textView);
        address_editText = findViewById(R.id.address_editText);
        phone_editText = findViewById(R.id.phone_editText);
        password_editText = findViewById(R.id.password_editText);
        work_editText = findViewById(R.id.work_editText);
        academicLevel_editText = findViewById(R.id.edu_level_editText);

//      instantiate SharedPreferences
        SharedPreferences sp = getApplicationContext().getSharedPreferences("StudentPrefs", Context.MODE_PRIVATE);

        String name = sp.getString("name", "");
        String teacherName = sp.getString("teacherName", "");
        String address = sp.getString("address", "");
        String phone = sp.getString("phone", "");

        name_editText.setHint("الاسم: " + name + " ؟!");
        teacherName_textView.setText("تغيير الشيخ: " + teacherName + " ؟!");
        address_editText.setHint("السكن: " + address + " ؟!");
        phone_editText.setHint("الهاتف: " + phone + " ؟!");
        password_editText.setHint("كلمة السر: " + "******" + " ؟!");
        work_editText.setHint("عمل لدى: " + "طالب" + " ؟!");
        academicLevel_editText.setHint("المستوى الدراسي: " + "جامعي" + " ؟!");

        Button editStudentButton = findViewById(R.id.edit_btn);
        editStudentSpinner = findViewById(R.id.view_student_spinner);

        viewDialog = new ViewDialog(this);
        listItems = new ArrayList<>();

        TeacherArray = new ArrayList<>();
        GetTeacher();

        editStudentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.WHITE);
                ((TextView) view).setGravity(Gravity.RIGHT);
                IdTeacher = listItems.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Snackbar.make(findViewById(android.R.id.content), "الرجاء اختيار الشيخ ", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        editStudentButton.setOnClickListener(v -> {
//            addStudent();
            Snackbar.make(findViewById(android.R.id.content), " تم التعديل ", Snackbar.LENGTH_SHORT).show();

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

                editStudentSpinner.setAdapter(new ArrayAdapter<>(ViewStudent.this, R.layout.spinner_item, TeacherArray));
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

        student_name_ = name_editText.getText().toString();
        student_living_ = address_editText.getText().toString();
        student_phone_ = phone_editText.getText().toString();
        student_password_ = password_editText.getText().toString();
        student_work_ = work_editText.getText().toString();
        student_AcademicLevel_ = academicLevel_editText.getText().toString();
        if (!validate()) {
            return;
        }
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
        Snackbar.make(findViewById(android.R.id.content), "  فشل اضافة الدارس ، الرجاء اعادة المحاولة", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    addStudent();
                }).show();
    }

    private void onInsertSuccess() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "  تمت اضافة الدارس بنجاح", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, StudentsActivity.class));
        finish();
    }

    private boolean validate() {
        boolean valid = true;

        if (student_name_.length() == 0 ) {
            name_editText.setError("الرجاء ادخال اسم الدارس");
            valid = false;
        } else {
            name_editText.setError(null);
        }

        if (student_phone_.length()  == 0) {
            phone_editText.setError("الرجاء ادخال رقم الهاتف ");
            valid = false;
        } else {
            phone_editText.setError(null);
        }

        if (student_password_.length()  == 0) {
            password_editText.setError("كلمة السر يجب ان تحتوي علي اربعة حروف علي الاقل ");
            valid = false;
        } else {
            password_editText.setError(null);
        }

        return valid;
    }


}

