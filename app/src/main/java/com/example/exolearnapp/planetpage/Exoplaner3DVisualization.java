package com.example.exolearnapp.planetpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.exolearnapp.R;
import com.example.exolearnapp.modelclass.ExoplanetUrlGenerator;

public class Exoplaner3DVisualization extends AppCompatActivity {

    WebView webView;




    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_exoplaner3_dvisualization);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = getIntent();
        final String planetName = i.getStringExtra("Planet Name");
        try {

            webView = findViewById(R.id.webView);



            WebView webView = findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);  // Enable DOM storage
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  // Disable cache
            webView.setWebViewClient(new WebViewClient());  // Ensure loading happens within the WebView
            webView.loadUrl(ExoplanetUrlGenerator.generateExoplanetUrl(planetName));

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}


