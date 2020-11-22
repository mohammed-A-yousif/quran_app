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

public class TeacherActivity extends AppCompatActivity implements MyAdapter.MyAdapterListener {

    private MyAdapter adapter;
    private JSONArray jsonArray;

    List<Teacher> listItems ;
    ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activi_teach);

        Toolbar toolbar = findViewById(R.id.toolbar_teach);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_teach);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewDialog = new ViewDialog(this);

        listItems = new ArrayList<>();
        FloatingActionButton teachFAB = findViewById(R.id.teacher_fab);

        teachFAB.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddingTeacher.class);
            startActivity(i);
        });



        adapter = new MyAdapter(listItems, this);
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        GetTeacher();

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
                    String Date = TeacherObject.getString("CreatedAt");
                    Teacher listItem = new Teacher(Name, PhoneNumber, Date);
                    listItems.add(listItem);
                }

                adapter.notifyDataSetChanged();
                viewDialog.hideDialog();
                Log.d("res", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                viewDialog.hideDialog();
                Snackbar.make(findViewById(android.R.id.content), "Couldn't get Teacher " + e , Snackbar.LENGTH_LONG)
                        .setAction("Retry", v -> GetTeacher()).show();
            }

        }, error -> {
            error.printStackTrace();
            viewDialog.hideDialog();
            Snackbar.make(findViewById(android.R.id.content), "Couldn't get Teacher " + error , Snackbar.LENGTH_LONG)
                    .setAction("Retry", v -> GetTeacher()).show();
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
    public void onContactSelected(Teacher teacher) {
        Toast.makeText(getApplicationContext(), "Selected: " + teacher.getName() + ", " + teacher.getPhone(), Toast.LENGTH_LONG).show();
    }
}

