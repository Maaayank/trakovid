package com.blackcat.covidtracker.Models;

public class CountryTimeline {

    String country;
    String countryCode;
    long confirmed;
    long deaths;
    long recovered;
    String date;

    public CountryTimeline(String country, String countryCode, long confirmed, long deaths, long recovered, String date) {
        this.country = country;
        this.countryCode = countryCode;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

