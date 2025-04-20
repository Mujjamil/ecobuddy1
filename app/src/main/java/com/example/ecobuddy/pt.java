package com.example.ecobuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class pt extends AppCompatActivity {
    private ImageView home,rewards,progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pt);
        home = findViewById(R.id.home);
        progress = findViewById(R.id.progress);
        rewards = findViewById(R.id.reward);

        rewards.setOnClickListener(v -> {
            Intent intent = new Intent(pt.this, rewards.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
        progress.setOnClickListener(v -> {
            Intent intent = new Intent(pt.this, emission.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
        home.setOnClickListener(v -> {
            Intent intent = new Intent(pt.this, Homepage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });


    }
}
