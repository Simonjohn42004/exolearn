package com.example.exolearnapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.exolearnapp.homescreen.MainHomePage;
import com.example.exolearnapp.login.MainLoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseApp.initializeApp(this);

        // Load fade-in animation
//        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Start the fade-in animation
//        findViewById(R.id.appNameTextView).startAnimation(fadeIn);

        // Handler to start the appropriate activity after the splash screen
        new Handler().postDelayed(() -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser() != null) {
                // User is logged in, start the main home page
                Intent mainIntent = new Intent(MainActivity.this, MainHomePage.class);
                startActivity(mainIntent);
            } else {
                // User is not logged in, start the login activity
                Intent loginIntent = new Intent(MainActivity.this, MainLoginActivity.class);
                startActivity(loginIntent);
            }
            finish(); // Close the splash screen activity
        }, SPLASH_DISPLAY_LENGTH);
    }
}
