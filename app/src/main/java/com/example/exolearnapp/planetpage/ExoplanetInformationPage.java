package com.example.exolearnapp.planetpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.exolearnapp.R;
import com.example.exolearnapp.modelclass.ExoPlanets;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStreamReader;

public class ExoplanetInformationPage extends AppCompatActivity {

    private TextView exoPlanetName, koiName, keplerName, kepId, disposition, orbitalPeriod,
            transitEpoch, transitDuration, planetaryRadius, transitDepth, equilibriumTemp, stellarRadius;
    private Button planetVisualizationBtn;
    private String planetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exoplanet_information_page);

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        final long kepId = i.getLongExtra("kepId", 0);
        Log.d("ExoplanetInformationPage", "Received kepId: " + kepId); // Log the received kepId

        // Initialize the TextView references
        initializeTextViews();

        // Load a single exoplanet's data based on kepid
        ExoPlanets exoplanet = loadExoplanetDataFromCSV(kepId); // Example kepid, replace with actual value or parameter

        if (exoplanet != null) {
            planetName = exoplanet.getKeplerName();
            // Populate the views with the exoplanet data
            mapDataToViews(exoplanet);
        }
        if(exoPlanetName.getText().toString().isEmpty()){
            planetVisualizationBtn.setEnabled(false);
            planetVisualizationBtn.setVisibility(View.GONE);
        }

        planetVisualizationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ExoplanetInformationPage.this, Exoplaner3DVisualization.class);
                i.putExtra("Planet Name", planetName);
                startActivity(i);
            }
        });

    }

    // Initialize the TextViews
    private void initializeTextViews() {
        exoPlanetName = findViewById(R.id.exoPlanetName);
        koiName = findViewById(R.id.koiName);
        keplerName = findViewById(R.id.keplerName);
        kepId = findViewById(R.id.kepId);
        disposition = findViewById(R.id.disposition);
        orbitalPeriod = findViewById(R.id.orbitalPeriod);
        transitEpoch = findViewById(R.id.transitEpoch);
        transitDuration = findViewById(R.id.transitDuration);
        planetaryRadius = findViewById(R.id.planetaryRadius);
        transitDepth = findViewById(R.id.transitDepth);
        equilibriumTemp = findViewById(R.id.equilibriumTemp);
        stellarRadius = findViewById(R.id.stellarRadius);
        planetVisualizationBtn = findViewById(R.id.planetVisualizationButton);
    }

    // Load exoplanet data from CSV based on the provided kepId
    // Load exoplanet data from CSV based on the provided kepId
    private ExoPlanets loadExoplanetDataFromCSV(long targetKepId) {
        try {
            // Open the CSV file located in the assets folder
            InputStreamReader csvStream = new InputStreamReader(getAssets().open("kepler_exoplanets.csv"));
            CSVReader csvReader = new CSVReader(csvStream);

            // Read the header
            csvReader.readNext();

            // Read and parse each row
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                // Skip rows that don't have enough columns
                if (nextLine.length < 29) { // Ensure at least 29 columns for the requested fields
                    continue;
                }

                // Parse the row
                long kepId;
                try {
                    kepId = Long.parseLong(nextLine[1]);  // kepid
                } catch (NumberFormatException e) {
                    // Skip if kepid is not valid
                    continue;
                }

                if (kepId == targetKepId) {
                    String koiName = nextLine[2];              // kepoi_name
                    String keplerName = nextLine[3];           // kepler_name
                    String disposition = nextLine[4];          // koi_disposition
                    double orbitalPeriod = parseDoubleOrDefault(nextLine[7], 0); // koi_period
                    double transitEpoch = parseDoubleOrDefault(nextLine[8], 0);  // koi_time0bk
                    double transitDuration = parseDoubleOrDefault(nextLine[11], 0); // koi_duration
                    double transitDepth = parseDoubleOrDefault(nextLine[12], 0);  // koi_depth
                    double planetaryRadius = parseDoubleOrDefault(nextLine[16], 0); // koi_prad
                    double equilibriumTemp = parseDoubleOrDefault(nextLine[19], 0); // koi_teq
                    double stellarRadius = parseDoubleOrDefault(nextLine[28], 0);  // koi_srad

                    // Return the planet with the specified kepid
                    return new ExoPlanets(kepId, koiName, keplerName, disposition, orbitalPeriod, transitEpoch,
                            transitDuration, transitDepth, planetaryRadius, equilibriumTemp, stellarRadius);
                }
            }
            csvReader.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null; // Return null if the planet is not found
    }

    // Helper method to safely parse doubles
    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


    // Map the retrieved exoplanet data to the respective views
    @SuppressLint("SetTextI18n")
    private void mapDataToViews(ExoPlanets exoplanet) {
        exoPlanetName.setText(exoplanet.getKeplerName() != null ? exoplanet.getKeplerName() : "Unknown");
        koiName.setText("KOI Name: \n" + exoplanet.getKoiName());
        keplerName.setText("Kepler Name: \n" + (exoplanet.getKeplerName() != null ? exoplanet.getKeplerName() : "N/A"));
        kepId.setText("KepID: \n" + exoplanet.getKepId());
        disposition.setText("Exoplanet Archive Disposition: \n" + exoplanet.getExoplanetArchiveDisposition());
        orbitalPeriod.setText("Orbital Period: \n" + exoplanet.getOrbitalPeriod() + " days");
        transitEpoch.setText("Transit Epoch: \n" + exoplanet.getTransitEpoch());
        transitDuration.setText("Transit Duration: \n" + exoplanet.getTransitDuration() + " hours");
        planetaryRadius.setText("Planetary Radius: \n" + exoplanet.getPlanetaryRadius() + " Earth radii");
        transitDepth.setText("Transit Depth: \n" + exoplanet.getTransitDepth() + " ppm");
        equilibriumTemp.setText("Equilibrium Temperature: \n" + exoplanet.getEquilibriumTemperature() + " K");
        stellarRadius.setText("Stellar Radius: \n" + exoplanet.getStellarRadius() + " Solar radii");
    }
}



// 1. Discovery Details
//KOI Name: (2) The Kepler Object of Interest name.
//Kepler Name: (3) The Kepler-specific planet designation.
//KepID: (1) The Kepler Input Catalog ID for the star the exoplanet orbits.
//Exoplanet Archive Disposition: (4) The status of the exoplanet (e.g., confirmed, candidate).
//2. Orbital Characteristics
//Orbital Period [days]: (5) Time taken for the planet to complete one orbit.
//Transit Epoch [BKJD]: (6) The time of the planet's transit in Barycentric Kepler Julian Date (BKJD).
//Transit Duration [hrs]: (7) Duration of the transit across the star's disk.
//3. Planetary Properties
//Planetary Radius [Earth radii]: (9) Size of the planet compared to Earth's radius.
//Transit Depth [ppm]: (8) How much starlight is blocked during the transit, in parts per million (ppm).
//Equilibrium Temperature [K]: (10) The estimated temperature of the planet assuming no atmosphere.
//4. Stellar Properties
//Stellar Radius [Solar radii]: (11) Size of the star compared to the Sun's radius.