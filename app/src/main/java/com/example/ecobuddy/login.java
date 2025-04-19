package com.example.ecobuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecobuddy.signUp;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;


public class login extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginBtn;
    TextView goToSignup;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailEditText);
        passwordInput = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.btnLogin);
        goToSignup = findViewById(R.id.textSignup);

        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email & password", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this, Homepage.class)); // Navigate to home
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Login failedddd: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        goToSignup.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, signUp.class);
            startActivity(intent);
        });
    }
}
