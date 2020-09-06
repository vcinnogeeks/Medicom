package com.example.medicom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginScreenDoctor extends AppCompatActivity {

    private TextView doctorId, doctorPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen_doctor);

        doctorId = findViewById(R.id.doctorId);
        doctorPass = findViewById(R.id.doctorPass);
    }

    public void initiateDoctorLogin(View view) {
        new FirestoreHandler(this).loginDoctor(doctorId.getText().toString().trim(), doctorPass.getText().toString().trim());
    }
}