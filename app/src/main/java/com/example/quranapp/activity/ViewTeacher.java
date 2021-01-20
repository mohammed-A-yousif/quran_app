package com.example.quranapp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

//      instantiate SharedPreferences
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TeacherPrefs", Context.MODE_PRIVATE);

        String name = sp.getString("name", "");
        name_editText.setHint("الاسم: "+name+" ؟!");
    }

}

