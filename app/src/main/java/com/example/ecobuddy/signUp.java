package com.example.ecobuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecobuddy.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class signUp extends AppCompatActivity {

    EditText usernameEditText, emailEditText, passwordEditText, conformpasswordEditText;
    Button btnSignup;
    TextView textSignup;

    private FirebaseAuth auth; // Firebase Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Hooks
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        conformpasswordEditText = findViewById(R.id.conformpasswordEditText);
        btnSignup = findViewById(R.id.btnLogin); // make sure this is really your signup button
        textSignup = findViewById(R.id.textSignup);

        auth = FirebaseAuth.getInstance(); // Initialize Firebase

        // Register new user
        btnSignup.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = conformpasswordEditText.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(signUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(signUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(signUp.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            Toast.makeText(signUp.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(signUp.this, login.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(signUp.this, "Signup failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }
        });

        // Already have an account
        textSignup.setOnClickListener(v -> {
            startActivity(new Intent(signUp.this, login.class));
            finish();
        });
    }
}
