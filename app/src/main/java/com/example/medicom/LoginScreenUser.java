package com.example.medicom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreenUser extends AppCompatActivity {

    private EditText userId, userPass;
    private FirestoreHandler firestoreHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen_user);

        userId = findViewById(R.id.userId);
        userPass = findViewById(R.id.userPass);
        firestoreHandler = new FirestoreHandler(this);
    }

    private boolean validateInputs() {

        if (userPass.getText().toString().trim().equals("")) {
            makeToast("Please enter your password");
            return false;
        }

        if (!userId.getText().toString().trim().contains("@")) {
            makeToast("Please enter a valid email address");
        }

        if (userId.getText().toString().trim().equals("")) {
            makeToast("Please enter your email");
            return false;
        }
        return true;
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void initiateUserLogin(View view) {
        if(validateInputs()) {
            firestoreHandler.loginUser(userId.getText().toString(), userPass.getText().toString());
        }
    }

    public void initiateUserSignup(View view) {
        if (validateInputs())
            firestoreHandler.createUser(userId.getText().toString(), userPass.getText().toString());
    }
}