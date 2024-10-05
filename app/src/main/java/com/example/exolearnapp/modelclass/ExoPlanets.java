package com.example.exolearnapp.modelclass;

public class ExoPlanets {

    private long kepId;                       // KepID: Possibly large ID for the planet
    private String koiName;                   // KOI Name: Name assigned to the planet
    private String keplerName;                // Kepler Name: Nullable, might not exist for all planets
    private String exoplanetArchiveDisposition; // Exoplanet Archive Disposition: Planet status (e.g., confirmed, candidate)
    private double orbitalPeriod;             // Orbital Period [days]: Time it takes for the planet to orbit its star
    private double transitEpoch;              // Transit Epoch [BKJD]: The time when the planet crosses in front of its star
    private double transitDuration;           // Transit Duration [hrs]: How long the transit lasts
    private double transitDepth;              // Transit Depth [ppm]: The reduction in starlight when the planet transits
    private double planetaryRadius;           // Planetary Radius [earth radii]: Size of the planet compared to Earth
    private double equilibriumTemperature;    // Equilibrium Temperature [K]: Estimated surface temperature of the planet
    private double stellarRadius;             // Stellar Radius [solar radii]: Size of the host star compared to the Sun

    // Constructor
    public ExoPlanets(long kepId, String koiName, String keplerName, String exoplanetArchiveDisposition,
                     double orbitalPeriod, double transitEpoch, double transitDuration,
                     double transitDepth, double planetaryRadius, double equilibriumTemperature, double stellarRadius) {
        this.kepId = kepId;
        this.koiName = koiName;
        this.keplerName = keplerName;
        this.exoplanetArchiveDisposition = exoplanetArchiveDisposition;
        this.orbitalPeriod = orbitalPeriod;
        this.transitEpoch = transitEpoch;
        this.transitDuration = transitDuration;
        this.transitDepth = transitDepth;
        this.planetaryRadius = planetaryRadius;
        this.equilibriumTemperature = equilibriumTemperature;
        this.stellarRadius = stellarRadius;
    }

    public long getKepId() {
        return kepId;
    }

    public void setKepId(long kepId) {
        this.kepId = kepId;
    }

    public String getKoiName() {
        return koiName;
    }

    public void setKoiName(String koiName) {
        this.koiName = koiName;
    }

    public String getKeplerName() {
        return keplerName;
    }

    public void setKeplerName(String keplerName) {
        this.keplerName = keplerName;
    }

    public String getExoplanetArchiveDisposition() {
        return exoplanetArchiveDisposition;
    }

    public void setExoplanetArchiveDisposition(String exoplanetArchiveDisposition) {
        this.exoplanetArchiveDisposition = exoplanetArchiveDisposition;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public double getTransitEpoch() {
        return transitEpoch;
    }

    public void setTransitEpoch(double transitEpoch) {
        this.transitEpoch = transitEpoch;
    }

    public double getTransitDuration() {
        return transitDuration;
    }

    public void setTransitDuration(double transitDuration) {
        this.transitDuration = transitDuration;
    }

    public double getTransitDepth() {
        return transitDepth;
    }

    public void setTransitDepth(double transitDepth) {
        this.transitDepth = transitDepth;
    }

    public double getPlanetaryRadius() {
        return planetaryRadius;
    }

    public void setPlanetaryRadius(double planetaryRadius) {
        this.planetaryRadius = planetaryRadius;
    }

    public double getEquilibriumTemperature() {
        return equilibriumTemperature;
    }

    public void setEquilibriumTemperature(double equilibriumTemperature) {
        this.equilibriumTemperature = equilibriumTemperature;
    }

    public double getStellarRadius() {
        return stellarRadius;
    }

    public void setStellarRadius(double stellarRadius) {
        this.stellarRadius = stellarRadius;
    }
}
