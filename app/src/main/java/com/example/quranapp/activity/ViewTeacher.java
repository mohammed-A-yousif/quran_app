package com.example.quranapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quranapp.R;

public class ViewTeacher extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teacher);

        //      Toolbar
        Toolbar toolbar = findViewById(R.id.view_teacher_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        //    EditText
        EditText name_editText = findViewById(R.id.name_editText);
        EditText address_editText = findViewById(R.id.address_editText);
        EditText phone_editText = findViewById(R.id.phone_editText);
        EditText password_editText = findViewById(R.id.password_editText);

//      instantiate SharedPreferences
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TeacherPrefs", Context.MODE_PRIVATE);

        String name = sp.getString("name", "");
        String address = sp.getString("address", "");
        String phone = sp.getString("phone", "");

        name_editText.setHint("الاسم: "+name+" ؟!");
        address_editText.setHint("السكن: "+address+" ؟!");
        phone_editText.setHint("الهاتف: "+phone+" ؟!");
        password_editText.setHint("كلمة السر: "+"******"+" ؟!");

    }

}

