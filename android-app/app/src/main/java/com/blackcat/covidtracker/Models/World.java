package com.blackcat.covidtracker.Models;

import com.google.gson.annotations.SerializedName;

public class World {

    @SerializedName("TotalConfirmed")
    private int tConfirmed;

    @SerializedName("TotalDeaths")
    private int tDeaths;

    @SerializedName("TotalRecovered")
    private int tRecovered;

    @SerializedName("NewConfirmed")
    private int nConfirmed;

    @SerializedName("NewRecovered")
    private int nRecovered;

    @SerializedName("NewDeaths")
    private int nDeaths;

    public World(int tConfirmed, int tDeaths, int tRecovered, int nConfirmed, int nRecovered, int nDeaths) {
        this.tConfirmed = tConfirmed;
        this.tDeaths = tDeaths;
        this.tRecovered = tRecovered;
        this.nConfirmed = nConfirmed;
        this.nRecovered = nRecovered;
        this.nDeaths = nDeaths;
    }

    public int gettConfirmed() {
        return tConfirmed;
    }

    public void settConfirmed(int tConfirmed) {
        this.tConfirmed = tConfirmed;
    }

    public int gettDeaths() {
        return tDeaths;
    }

    public void settDeaths(int tDeaths) {
        this.tDeaths = tDeaths;
    }

    public int gettRecovered() {
        return tRecovered;
    }

    public void settRecovered(int tRecovered) {
        this.tRecovered = tRecovered;
    }

    public int getnConfirmed() {
        return nConfirmed;
    }

    public void setnConfirmed(int nConfirmed) {
        this.nConfirmed = nConfirmed;
    }

    public int getnRecovered() {
        return nRecovered;
    }

    public void setnRecovered(int nRecovered) {
        this.nRecovered = nRecovered;
    }

    public int getnDeaths() {
        return nDeaths;
    }

    public void setnDeaths(int nDeaths) {
        this.nDeaths = nDeaths;
    }


}
