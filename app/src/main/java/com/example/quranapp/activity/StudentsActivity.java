package com.example.quranapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.quranapp.adapter.TeacherAdapter;
import com.example.quranapp.model.Student;
import com.example.quranapp.adapter.StudentAdapter;
import com.example.quranapp.URLs;
import com.example.quranapp.ViewDialog;
import com.example.quranapp.model.Teacher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {
    private JSONArray jsonArray;

    private ArrayList<Student> listItems;

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    SharedPreferences sp;
    String nameStr, teacherNameStr, addressStr, phoneStr;
    Student item;

    //    List<Teacher> listItems;
    ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_activity);

//      @@@@@@
        sp = getSharedPreferences("StudentPrefs", Context.MODE_PRIVATE);
//      @@@@@@


//      Toolbar
        Toolbar toolbar = findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

//      ExampleList
        listItems = new ArrayList<>();

//      buildRecyclerView
        recyclerView = findViewById(R.id.students_recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new StudentAdapter(listItems);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//              ################
                item = listItems.get(position);
                nameStr = item.getName();
                teacherNameStr = item.getTeacherName();
                addressStr = item.getAddress();
                phoneStr = item.getPhone();


                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", nameStr);
                editor.putString("teacherName", teacherNameStr);
                editor.putString("address", addressStr);
                editor.putString("phone", phoneStr);

                editor.commit();


                Intent i = new Intent(getApplicationContext(), ViewStudent.class);
                startActivity(i);
                finish();
//              ################
            }
        });
        viewDialog = new ViewDialog(this);
        FloatingActionButton studentsFAB = findViewById(R.id.students_fab);
        studentsFAB.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddingStudent.class);
            startActivity(i);
            finish();
        });

        if (InternetStatus.getInstance(this).isOnline()) {
            getStudents();
        } else {
            Snackbar.make(findViewById(android.R.id.content), " غير متصل بالانترت حاليا ، الرجاء مراجعةالأنترنت ", Snackbar.LENGTH_LONG)
                    .setAction("محاولة مرة اخري", v -> getStudents()).show();
        }
    }

    public void getStudents() {
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetStudents, response -> {
            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject StudentObject = jsonArray.getJSONObject(i);
                    String Name = StudentObject.getString("Name");
                    String TeacherName = StudentObject.getString("Teacher");
                    String Address = StudentObject.getString("Address");
                    String PhoneNumber = StudentObject.getString("PhoneNumber");
                    String Date = StudentObject.getString("CreatedAt");
                    Student listItem = new Student(Name, TeacherName, Address, PhoneNumber, Date);
                    listItems.add(listItem);
                }

                adapter.notifyDataSetChanged();
                viewDialog.hideDialog();
                Log.d("res", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                viewDialog.hideDialog();
                Snackbar.make(findViewById(android.R.id.content), " تعذر عرض الدارسين " + e, Snackbar.LENGTH_LONG)
                        .setAction("محاولة مرة اخري", v -> getStudents()).show();
            }

        }, error -> {
            error.printStackTrace();
            viewDialog.hideDialog();
            Snackbar.make(findViewById(android.R.id.content), " تعذر عرض الدارسين " + error, Snackbar.LENGTH_LONG)
                    .setAction("محاولة مرة اخري", v -> getStudents()).show();
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
//                adapter.getFilter().filter(newText);
                filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String text) {
        ArrayList<Student> filteredList = new ArrayList<>();
        for (Student item : listItems) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}

