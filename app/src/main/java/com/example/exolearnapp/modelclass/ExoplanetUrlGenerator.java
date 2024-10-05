package com.example.exolearnapp.modelclass;

public class ExoplanetUrlGenerator {

    private static final String BASE_URL = "https://eyes.nasa.gov/apps/exo/#/planet/";

    // Method to generate the URL for a given exoplanet
    public static String generateExoplanetUrl(String planetName) {
        // Replace any spaces with underscores to match URL format
        String formattedPlanetName = planetName.trim().replace(" ", "_");
        return BASE_URL + formattedPlanetName;
    }

}

// API key
// 4rDBmP3gaNew8TggfuI7bWHGZkYrT7QRA7uBpmqd

