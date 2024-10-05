package com.example.exolearnapp.searchscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exolearnapp.R;
import com.example.exolearnapp.modelclass.ExoPlanets;
import com.example.exolearnapp.planetpage.Exoplaner3DVisualization;
import com.example.exolearnapp.planetpage.ExoplanetInformationPage;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

public class ExoplanetSearch extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExoplanetAdapter exoplanetAdapter;
    private List<ExoPlanets> exoplanetList = new ArrayList<>();  // Full list of exoplanets
    private List<ExoPlanets> filteredList = new ArrayList<>();   // Filtered list based on search
    private Spinner dispositionSpinner;
    private Button addDurationButton;
    private Button addPeriodButton ;
    private LinearLayout durationLayout ;
    private LinearLayout orbitalPeriodLayout ;
    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exoplanet_search);

        // Set up system window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.exoplanetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dispositionSpinner = findViewById(R.id.dispositionSpinner);
        searchEditText = findViewById(R.id.searchEditText);


        // Load and parse CSV data
        loadExoplanetData();




        filteredList.addAll(exoplanetList);
        // Set up the adapter with the original exoplanet list
        exoplanetAdapter = new ExoplanetAdapter(this, filteredList, exoplanet -> {
            Intent i = new Intent(ExoplanetSearch.this, ExoplanetInformationPage.class);
            i.putExtra("kepId", exoplanet.getKepId());
            startActivity(i);
        });


        recyclerView.setAdapter(exoplanetAdapter);

        dispositionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterExoplanets(searchEditText.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Set up search filter
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("ExoplanetSearch", "Text changed: " + s.toString());
                filterExoplanets(s.toString());  // Filter as user types
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void loadExoplanetData() {
        try {
            // Open the CSV file from assets folder
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("kepler_exoplanets.csv"));
            CSVReader csvReader = new CSVReader(inputStreamReader);
            String[] nextLine;

            // Skip metadata lines and headers
            while ((nextLine = csvReader.readNext()) != null) {
                // Skip empty lines and lines that are comments
                if (nextLine.length == 0 || nextLine[0].startsWith("#") || nextLine[0].startsWith("loc_rowid")) {
                    continue;
                }

                // Ensure we have at least enough columns to avoid ArrayIndexOutOfBoundsException
                if (nextLine.length > 7) {
                    long kepId = Long.parseLong(nextLine[1]);              // kepid
                    String koiName = nextLine[2];                          // kepoi_name
                    String keplerName = nextLine[3];                       // kepler_name (may be empty)
                    String disposition = nextLine[4];                      // koi_disposition
                    double orbitalPeriod = parseDoubleOrDefault(nextLine[7], 0); // koi_period
                    double transitDuration = parseDoubleOrDefault(nextLine[11], 0); // koi_duration

                    // Create ExoPlanets object and add to the list
                    ExoPlanets exoplanet = new ExoPlanets(
                            kepId,
                            koiName,
                            keplerName,
                            disposition,
                            orbitalPeriod,
                            0,  // transitEpoch (Not needed for display)
                            transitDuration,
                            0,  // transitDepth (Not needed for display)
                            0,  // planetaryRadius (Not needed for display)
                            0,  // equilibriumTemperature (Not needed for display)
                            0   // stellarRadius (Not needed for display)
                    );
                    exoplanetList.add(exoplanet);
                }
            }

            csvReader.close();
        } catch (IOException e) {
            Log.e("ExoplanetSearch", "Error reading CSV file", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("ExoplanetSearch", "CSV parsing error: ", e);
        } catch (CsvValidationException e) {
            Log.e("ExoplanetSearch", "CSV validation error: ", e);
        }
    }

    // Utility method to parse double safely
    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Filter the list of exoplanets based on search input and disposition
    private void filterExoplanets(String query) {
        filteredList.clear();  // Clear the filtered list before adding filtered results

        // Get the selected disposition from the spinner
        String selectedDisposition = dispositionSpinner.getSelectedItem().toString();

        for (ExoPlanets planet : exoplanetList) {
            // Filter based on the query matching keplerName or koiName
            boolean matchesName = (planet.getKeplerName() != null && planet.getKeplerName().toLowerCase().contains(query.toLowerCase())) ||
                    (planet.getKoiName() != null && planet.getKoiName().toLowerCase().contains(query.toLowerCase()));

            // Filter based on the disposition (ignoring if "ALL" is selected)
            boolean matchesDisposition = selectedDisposition.equalsIgnoreCase("ALL") ||
                    planet.getExoplanetArchiveDisposition().equalsIgnoreCase(selectedDisposition);

            // Add the planet to the filtered list if both conditions are true
            if (matchesName && matchesDisposition) {
                filteredList.add(planet);
            }
        }

        // Update the RecyclerView with the filtered list
        Log.d("ExoplanetSearch", "Filtered List Size: " + filteredList.size());
        updateList(filteredList);
    }

    // Update the list shown in RecyclerView
    public void updateList(List<ExoPlanets> filteredList) {
        exoplanetAdapter.updateList(filteredList);  // Call updateList() in the adapter
        exoplanetAdapter.notifyDataSetChanged();    // Notify adapter of data change
    }
}


