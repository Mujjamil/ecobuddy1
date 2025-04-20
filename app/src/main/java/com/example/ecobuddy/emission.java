package com.example.ecobuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class emission extends AppCompatActivity {
    private TextView dayText, walkingDistanceText, bikeDistanceText, publicTransportDistanceText, totalDistanceText;
    private PieChart pieChart;
    private final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private int currentDayIndex = 0; // default to Monday

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emission);

        // Initialize Views
        dayText = findViewById(R.id.dayText);
        walkingDistanceText = findViewById(R.id.walkingDistance);
        bikeDistanceText = findViewById(R.id.bikeDistance);
        publicTransportDistanceText = findViewById(R.id.publicTransportDistance);
        totalDistanceText = findViewById(R.id.totalDistanceText);
        pieChart = findViewById(R.id.pieChart);
        ImageView leftArrow = findViewById(R.id.leftArrow);
        ImageView rightArrow = findViewById(R.id.rightArrow);

        // Handle incoming Intent data
        Intent intent = getIntent();
        float walkingDistance = 0f;
        String selectedDay = null;
        if (intent != null && intent.hasExtra("walking_distance") && intent.hasExtra("selected_day")) {
            walkingDistance = intent.getFloatExtra("walking_distance", 0f);
            selectedDay = intent.getStringExtra("selected_day");
            Log.d("Emission", "Received: walking_distance=" + walkingDistance + " km, selected_day=" + selectedDay);

            // Validate inputs
            if (walkingDistance < 0) {
                Toast.makeText(this, "Invalid walking distance", Toast.LENGTH_SHORT).show();
                walkingDistance = 0f;
            }

            boolean dayFound = false;
            for (int i = 0; i < days.length; i++) {
                if (days[i].equalsIgnoreCase(selectedDay)) {
                    currentDayIndex = i;
                    dayFound = true;
                    break;
                }
            }
            if (!dayFound) {
                Toast.makeText(this, "Invalid day: " + selectedDay, Toast.LENGTH_SHORT).show();
            } else {
                saveWalkingDistance(walkingDistance, days[currentDayIndex]);
            }
        }

        // Load initial day data
        updateUIForSelectedDay();

        // Arrow navigation
        leftArrow.setOnClickListener(v -> {
            if (currentDayIndex > 0) {
                currentDayIndex--;
                updateUIForSelectedDay();
            }
        });

        rightArrow.setOnClickListener(v -> {
            if (currentDayIndex < days.length - 1) {
                currentDayIndex++;
                updateUIForSelectedDay();
            }
        });
    }

    private void saveWalkingDistance(float walkingDistance, String day) {
        SharedPreferences prefs = getSharedPreferences("emission_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(day + "_walking", walkingDistance);
        editor.apply();
        Log.d("Emission", "Saved walking distance for " + day + ": " + walkingDistance + " km");
    }

    private void setupPieChart(float walking, float bike, float publicTransport) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        if (walking > 0) entries.add(new PieEntry(walking, "Walking"));
        if (bike > 0) entries.add(new PieEntry(bike, "Bike"));
        if (publicTransport > 0) entries.add(new PieEntry(publicTransport, "Public Transport"));

        PieDataSet dataSet = new PieDataSet(entries, "Travel Modes");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        Description description = new Description();
        description.setText("Distance Share");
        pieChart.setDescription(description);

        pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void updateUIForSelectedDay() {
        String day = days[currentDayIndex];
        dayText.setText(day);



        SharedPreferences prefs = getSharedPreferences("emission_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("Saturday_walking", 3.5f);
        editor.putFloat("Saturday_bike", 5.0f);
        editor.putFloat("Saturday_public", 8.0f);
        editor.apply();

        float walking = prefs.getFloat(day + "_walking", 0f); // Use stored walking distance
        float bike = prefs.getFloat(day + "_bike", 0f);
        float publicTransport = prefs.getFloat(day + "_public", 0f);

        walkingDistanceText.setText(String.format("%.1f km", walking));
        bikeDistanceText.setText(String.format("%.1f km", bike));
        publicTransportDistanceText.setText(String.format("%.1f km", publicTransport));

        float total = walking + bike + publicTransport;
        totalDistanceText.setText(String.format("%.1f km", total));

        setupPieChart(walking, bike, publicTransport);
    }
}