package com.example.ecobuddy;

import static com.example.ecobuddy.R.*;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;




public class Homepage extends AppCompatActivity {

    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001;
    private static final int ACTIVITY_RECOGNITION_REQUEST_CODE = 101;

    private TextView textSteps, textDistance;
    private LineChart lineChart;

    private FitnessOptions fitnessOptions;
    private OnDataPointListener stepListener;
    private OnDataPointListener distanceListener;
    private ImageView rewards,transport,progress;

    private int totalSteps = 0;
    private float totalDistance = 0f;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint);





        textSteps = findViewById(R.id.textFootprintValue);
        textDistance = findViewById(R.id.textDistance);
        lineChart = findViewById(R.id.lineChart);
        rewards = findViewById(id.reward);
        progress = findViewById(id.progress);
        transport = findViewById(id.publicTransport);






        findViewById(id.progress).setOnClickListener(v -> {
            Intent intent = new Intent(this, emission.class);
            intent.putExtra("walking_distance", totalDistance);
            intent.putExtra("selected_day", getCurrentDay());
            startActivity(intent);
            Log.d("Homepage", "Sent to emission: walking_distance=" + totalDistance + " km, day=" + getCurrentDay());
        });
         rewards.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, com.example.ecobuddy.rewards.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
            transport.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, pt.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });


        // Permission for activity recognition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    ACTIVITY_RECOGNITION_REQUEST_CODE);
        }

        // Google Fit Permissions
        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
                .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions
            );
        } else {
            subscribeToStepCount();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getCurrentDay() {
        LocalDate today = LocalDate.now();
        String day = today.getDayOfWeek().toString(); // e.g., "MONDAY"
        return day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase(); // e.g., "Monday"

    }

    private void subscribeToStepCount() {
        stepListener = dataPoint -> {
            for (Field field : dataPoint.getDataType().getFields()) {
                int step = dataPoint.getValue(field).asInt();
                totalSteps += step;
                float distanceKm = totalSteps * 0.002f; // 500 steps = 1 km
                runOnUiThread(() -> {
                    textSteps.setText(totalSteps + " steps");
                    textDistance.setText(String.format("%.2f km", distanceKm));
                    updateChart(distanceKm);
                });
            }
        };

        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .add(
                        new SensorRequest.Builder()
                                .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                                .setSamplingRate(5, TimeUnit.SECONDS)
                                .build(),
                        stepListener
                )
                .addOnSuccessListener(unused -> Log.i("GoogleFit", "Step listener registered"))
                .addOnFailureListener(e -> Log.e("GoogleFit", "Failed to register step listener", e));
    }



    private void updateChart(float distance) {
        LineData data = lineChart.getData();
        if (data == null) {
            data = new LineData();
            lineChart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        if (set == null) {
            set = new LineDataSet(null, "Distance (km)");
            ((LineDataSet) set).setColor(Color.GREEN);
            ((LineDataSet) set).setDrawCircles(true);
            ((LineDataSet) set).setLineWidth(2f);
            data.addDataSet(set);
        }

        data.addEntry(new Entry(data.getEntryCount(), distance), 0);
        data.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE && resultCode == RESULT_OK) {
            subscribeToStepCount();

        }
    }


}