package com.example.quranapp.activity;

import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranapp.model.Admin;
import com.example.quranapp.R;
import com.example.quranapp.SharedPrefManager;
import com.example.quranapp.URLs;
import com.example.quranapp.ViewDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    String PhoneNumber;
    String Password;

    ViewDialog viewDialog;

    EditText phoneEditText;
    EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, DashboardActivity.class));
            return;
        }

        viewDialog = new ViewDialog(this);

        phoneEditText = findViewById(R.id.input_text_phonenumber);
        passEditText = findViewById(R.id.input_text_password);

        Button loginButton = (Button) findViewById(R.id.btn_login);

        loginButton.setOnClickListener(v -> {

            PhoneNumber = phoneEditText.getText().toString();
            Password = passEditText.getText().toString();

            Sigin(PhoneNumber, Password);

        });
    }

    private void Sigin(String phoneNumber, String password) {
        if (!validate()) {
            return;
        }
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URLs.Login + "?PhoneNumber=" + phoneNumber + "&Password=" + password,
                null, (JSONObject response) -> {
            try {

                Admin admin = new Admin(response.getInt("IdAdmin"), response.getInt("UserType"),
                        response.getString("Name"), response.getString("PhoneNumber"));

                SharedPrefManager.getInstance(getApplicationContext()).adminLogin(admin);
                onSiginSuccess();

            } catch (JSONException e) {
                e.printStackTrace();
                onSiginFailed();
            }

        }, error -> Log.d("Error getting response", "" + error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }

    private void onSiginFailed() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "فشل تسجيل الدخول", Snackbar.LENGTH_LONG)
                .setAction("محاولة مرة اخري", v -> {
                    Sigin(PhoneNumber, Password);
                }).show();
    }

    private void onSiginSuccess() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "تم تسجيل الدخول بنجاح", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private boolean validate() {
        boolean valid = true;

        if (PhoneNumber.length() == 0 && !Patterns.PHONE.matcher(phoneEditText.getText().toString()).matches()) {
            phoneEditText.setError("الرجاء ادخال رقم هاتف صالح");
            valid = false;
        } else {
            phoneEditText.setError(null);
        }

        if (Password.length()  == 0  && Password.length() < 4  &&!isValidPassword(Password)){
            passEditText.setError("كلمة السر يجب ان تحتوي علي اربعة حروف علي الاقل ");
            valid = false;
        } else {
            passEditText.setError(null);
        }

        return valid;
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}