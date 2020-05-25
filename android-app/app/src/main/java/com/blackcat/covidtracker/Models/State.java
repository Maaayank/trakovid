package com.blackcat.covidtracker.Models;

import com.google.gson.annotations.SerializedName;

public class State {

    @SerializedName("state")
    private String State;

    @SerializedName("statecode")
    private String StateCode;

    @SerializedName("deltaconfirmed")
    private int NewConfirmed;

    @SerializedName("confirmed")
    private int TotalConfirmed;

    @SerializedName("deltadeaths")
    private int NewDeaths;

    @SerializedName("deaths")
    private int TotalDeaths;

    @SerializedName("deltarecovered")
    private int NewRecovered;

    @SerializedName("recovered")
    private int TotalRecovered;

    @SerializedName("lastupdatedtime")
    private String Date;

    @SerializedName("statenotes")
    private String notes;

    public State(String state, String stateCode, int newConfirmed, int totalConfirmed, int newDeaths, int totalDeaths, int newRecovered, int totalRecovered, String date) {
        State = state;
        StateCode = stateCode;
        NewConfirmed = newConfirmed;
        TotalConfirmed = totalConfirmed;
        NewDeaths = newDeaths;
        TotalDeaths = totalDeaths;
        NewRecovered = newRecovered;
        TotalRecovered = totalRecovered;
        Date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStateCode() {
        return StateCode;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }

    public int getNewConfirmed() {
        return NewConfirmed;
    }

    public void setNewConfirmed(int newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public int getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public int getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        NewDeaths = newDeaths;
    }

    public int getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public int getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(int newRecovered) {
        NewRecovered = newRecovered;
    }

    public int getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
