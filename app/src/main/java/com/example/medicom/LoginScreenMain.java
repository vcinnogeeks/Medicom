package com.example.medicom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginScreenMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen_main);
    }

    public void showDoctorLogin(View view) {
        startActivity(new Intent(this, LoginScreenDoctor.class));
    }

    public void showUserLogin(View view) {
        startActivity(new Intent(this, LoginScreenUser.class));
    }
}