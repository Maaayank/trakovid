package com.blackcat.covidtracker.Models;

import java.util.ArrayList;

public class World {

    WorldTotal global;
    ArrayList<Country> countries;

    public World(WorldTotal global, ArrayList<Country> countries) {
        this.global = global;
        this.countries = countries;
    }

    public WorldTotal getGlobal() {
        return global;
    }

    public void setGlobal(WorldTotal global) {
        this.global = global;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }
}
