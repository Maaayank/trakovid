package com.blackcat.covidtracker.Models;

import com.google.gson.annotations.SerializedName;

public class InState {

    @SerializedName("district")
    private String district ;

    @SerializedName("confirmed")
    private int confirmed;

    @SerializedName("deceased")
    private int deceased;

    @SerializedName("recovered")
    private int recovered;

    @SerializedName("active")
    private int active;

    @SerializedName("delta")
    private Delta delta;


    public InState(String district, int confirmed, int deceased, int recovered, int active, Delta delta) {
        this.district = district;
        this.confirmed = confirmed;
        this.deceased = deceased;
        this.recovered = recovered;
        this.active = active;
        this.delta = delta;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDeceased() {
        return deceased;
    }

    public void setDeceased(int deceased) {
        this.deceased = deceased;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Delta getDelta() {
        return delta;
    }

    public void setDelta(Delta delta) {
        this.delta = delta;
    }

    public class Delta {

        @SerializedName("recovered")
        private int recovered;

        @SerializedName("deceased")
        private int deceased;

        @SerializedName("confirmed")
        private int confirmed;

        public Delta(int recovered, int deceased, int confirmed) {
            this.recovered = recovered;
            this.deceased = deceased;
            this.confirmed = confirmed;
        }

        public int getRecovered() {
            return recovered;
        }

        public void setRecovered(int recovered) {
            this.recovered = recovered;
        }

        public int getDeceased() {
            return deceased;
        }

        public void setDeceased(int deceased) {
            this.deceased = deceased;
        }

        public int getConfirmed() {
            return confirmed;
        }

        public void setConfirmed(int confirmed) {
            this.confirmed = confirmed;
        }
    }
}
