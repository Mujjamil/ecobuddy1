package com.example.ecobuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ecobuddy.R;
import com.example.ecobuddy.login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.app.ecobuddy.R.layout.activity_main); // Layout must exist: res/layout/activity_main.xml

        // Splash delay and redirection
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish(); // Prevent going back to splash screen
        }, 3000); // Delay for 3 seconds
    }
}
