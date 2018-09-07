package com.example.tanvir.weatherreport.models.needy_models;

public class Forecast {
    private String day;
    private String minTemp;
    private String maxTemp;
    private String date;
    private String  image;

    public String getImage() {
        return image;
    }

    public Forecast(String day, String minTemp, String maxTemp, String date, String image) {
        this.day = day;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.date = date;
        this.image = image;

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
