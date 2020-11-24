package com.example.quranapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends AppCompatActivity implements StudentAdapter.StudentAdapterListener {

    private StudentAdapter adapter;
    private JSONArray jsonArray;

    List<Student> listItems ;
    ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_activity);

        Toolbar toolbar = findViewById(R.id.students_toolbar);
        setSupportActionBar(toolbar);
        viewDialog = new ViewDialog(this);

        RecyclerView recyclerView = findViewById(R.id.students_recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        FloatingActionButton studentsFAB = findViewById(R.id.students_fab);
        studentsFAB.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddingStudent.class);
            startActivity(i);
        });


        adapter = new StudentAdapter(listItems, this);
        recyclerView.setAdapter(adapter);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        getStudents();
    }


    public void getStudents(){
         viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetStudents, response -> {
            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject StudentObject = jsonArray.getJSONObject(i);
                    String Name = StudentObject.getString("Name");
                    String PhoneNumber = StudentObject.getString("PhoneNumber");
                    String Date = StudentObject.getString("CreatedAt");
                    Student listItem = new Student(Name, PhoneNumber, Date);
                    listItems.add(listItem);
                }

                adapter.notifyDataSetChanged();
                viewDialog.hideDialog();
                Log.d("res", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                viewDialog.hideDialog();
                Snackbar.make(findViewById(android.R.id.content), "Couldn't get Teacher " + e , Snackbar.LENGTH_LONG)
                        .setAction("Retry", v -> getStudents()).show();
            }

        }, error -> {
            error.printStackTrace();
            viewDialog.hideDialog();
            Snackbar.make(findViewById(android.R.id.content), "Couldn't get Teacher " + error , Snackbar.LENGTH_LONG)
                    .setAction("Retry", v -> getStudents()).show();
        });

        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onStudentSelected(Student student) {
        Toast.makeText(getApplicationContext(), "Selected: " + student.getName() + ", " + student.getPhone(), Toast.LENGTH_LONG).show();
    }
}

