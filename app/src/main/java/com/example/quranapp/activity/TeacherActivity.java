package com.example.quranapp.activity;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranapp.InternetStatus;
import com.example.quranapp.R;
import com.example.quranapp.model.Teacher;
import com.example.quranapp.adapter.TeacherAdapter;
import com.example.quranapp.URLs;
import com.example.quranapp.ViewDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity implements TeacherAdapter.TeacherAdapterListener {

    private TeacherAdapter adapter;
    private JSONArray jsonArray;

    List<Teacher> listItems ;
    ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Toolbar toolbar = findViewById(R.id.toolbar_teach);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_teacher);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewDialog = new ViewDialog(this);

        listItems = new ArrayList<>();
        FloatingActionButton AddTeacherBtn = findViewById(R.id.teacher_fab);

        AddTeacherBtn.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddingTeacher.class);
            startActivity(i);
            finish();
        });



        adapter = new TeacherAdapter(listItems, this, this::onTeacherSelected);
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        if (InternetStatus.getInstance(this).isOnline()) {
            GetTeacher();
        } else {
            Snackbar.make(findViewById(android.R.id.content), " غير متصل بالانترت حاليا ، الرجاء مراجعةالأنترنت " , Snackbar.LENGTH_LONG)
                    .setAction("محاولة مرة اخري", v -> GetTeacher()).show();
        }


    }


    public void GetTeacher(){
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetTeachers, response -> {
            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject TeacherObject = jsonArray.getJSONObject(i);
                    int Id = TeacherObject.getInt("IdTeacher");
                    String Name = TeacherObject.getString("Name");
                    String Address = TeacherObject.getString("Address");
                    String PhoneNumber = TeacherObject.getString("PhoneNumber");
                    String Date = TeacherObject.getString("CreatedAt");
                    Teacher listItem = new Teacher(Id, Name,Address, PhoneNumber, Date);
                    listItems.add(listItem);
                }

                adapter.notifyDataSetChanged();
                viewDialog.hideDialog();
//                Log.d("res", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                viewDialog.hideDialog();
                Snackbar.make(findViewById(android.R.id.content), "تعذر عرض الشيوخ " + e , Snackbar.LENGTH_LONG)
                        .setAction("محاولة مرة اخري", v -> GetTeacher()).show();
            }

        }, error -> {
            error.printStackTrace();
            viewDialog.hideDialog();
            Snackbar.make(findViewById(android.R.id.content), "تعذر عرض الشيوخ " + error , Snackbar.LENGTH_LONG)
                    .setAction(" محاولة مرة اخري ", v -> GetTeacher()).show();
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
    public void onTeacherSelected(Teacher teacher) {
        Log.d("selected", teacher.getName());
        Toast.makeText(getApplicationContext(), "Selected: " + teacher.getName() + ", " + teacher.getPhone(), Toast.LENGTH_LONG).show();
    }
}

