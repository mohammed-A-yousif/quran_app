package com.example.quranapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.quranapp.URLs.BaseUrl;

public class Login  extends AppCompatActivity {

    String PhoneNumber;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Control.class));
            return;
        }

        EditText phoneEditText = (EditText) findViewById(R.id.phone_ET);
        EditText passEditText = (EditText) findViewById(R.id.pass_ET);

        Button loginButton = (Button) findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PhoneNumber = phoneEditText.getText().toString();
                Password = passEditText.getText().toString();

                Sigin(PhoneNumber, Password);

                Intent i = new Intent(getApplicationContext(), Control.class);
                startActivity(i);
            }
        });
    }

    private void Sigin(String phoneNumber, String password) {
//        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  URLs.Login + "?PhoneNumber=" + phoneNumber + "&Password=" + password , null,
                (JSONObject response) -> {
                    try {
                        String name = response.getString("Name");
                        Admin admin = new Admin(response.getInt("IdAdmin"), response.getInt("UserType"), response.getString("Name"), response.getString("PhoneNumber"));
                        SharedPrefManager.getInstance(getApplicationContext()).adminLogin(admin);
                        onSiginSuccess();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onSiginFailed();
                    }

                    Log.d("String Response : ", ""+  response.toString());
                    Log.d("name", String.valueOf(SharedPrefManager.getInstance(this).isLoggedIn()));
                }, error -> Log.d("Error getting response", "" +error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }

    private void onSiginFailed() {
//        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Failed", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    Sigin(PhoneNumber, Password);
                }).show();
    }

    private void onSiginSuccess() {
//        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Successfully", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, Control.class));
        finish();
    }

}