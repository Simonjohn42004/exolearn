package com.example.exolearnapp.homescreen;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.exolearnapp.R;
import com.example.exolearnapp.learningmodel.activities.RoadmapActivity;
import com.example.exolearnapp.searchscreen.ExoplanetSearch;

public class MainHomePage extends AppCompatActivity {

    Button learnExoplanetBtn;
    Button exploreUniverseBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        learnExoplanetBtn = findViewById(R.id.learnAboutExoplanetsButton);
        learnExoplanetBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainHomePage.this, ExoplanetSearch.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.enter_from_right, R.anim.exit_to_left);
            startActivity(i);
        });

        exploreUniverseBtn = findViewById(R.id.exploreTheUniverseButton);
        exploreUniverseBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainHomePage.this, RoadmapActivity.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.enter_from_right, R.anim.exit_to_left);
            startActivity(i);
        });

    }
}