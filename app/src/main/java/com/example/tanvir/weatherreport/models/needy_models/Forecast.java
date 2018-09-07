package com.example.tanvir.weatherreport.models.needy_models;

public class Forecast {
    private String day;
    private String minTemp;
    private String maxTemp;
    private String date;

    public Forecast(String day, String minTemp, String maxTemp, String date) {
        this.day = day;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getDate() {
        return date;
    }
}
