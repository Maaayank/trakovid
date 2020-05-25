package com.blackcat.covidtracker.Models;

import com.google.gson.annotations.SerializedName;

public class IndiaTotal {

    private String date ;

    @SerializedName("totalrecovered")
    private int tRecovered;

    @SerializedName("totaldeceased")
    private int tDeaths;

    @SerializedName("totalconfirmed")
    private int tConfirmed;

    @SerializedName("dailyrecovered")
    private int nRecovered;

    @SerializedName("dailyconfirmed")
    private int nConfirmed;

    @SerializedName("dailydeceased")
    private int nDeaths;

    public IndiaTotal(String date, int tRecovered, int tDeaths, int tConfirmed, int nRecovered, int nConfirmed, int nDeaths) {
        this.date = date;
        this.tRecovered = tRecovered;
        this.tDeaths = tDeaths;
        this.tConfirmed = tConfirmed;
        this.nRecovered = nRecovered;
        this.nConfirmed = nConfirmed;
        this.nDeaths = nDeaths;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int gettRecovered() {
        return tRecovered;
    }

    public void settRecovered(int tRecovered) {
        this.tRecovered = tRecovered;
    }

    public int gettDeaths() {
        return tDeaths;
    }

    public void settDeaths(int tDeaths) {
        this.tDeaths = tDeaths;
    }

    public int gettConfirmed() {
        return tConfirmed;
    }

    public void settConfirmed(int tConfirmed) {
        this.tConfirmed = tConfirmed;
    }

    public int getnRecovered() {
        return nRecovered;
    }

    public void setnRecovered(int nRecovered) {
        this.nRecovered = nRecovered;
    }

    public int getnConfirmed() {
        return nConfirmed;
    }

    public void setnConfirmed(int nConfirmed) {
        this.nConfirmed = nConfirmed;
    }

    public int getnDeaths() {
        return nDeaths;
    }

    public void setnDeaths(int nDeaths) {
        this.nDeaths = nDeaths;
    }
}
