package com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces;

import com.blackcat.covidtracker.Models.Country;
import com.blackcat.covidtracker.Models.CountryTimeline;
import com.blackcat.covidtracker.Models.World;
import com.blackcat.covidtracker.Models.WorldTotal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceWorld {

    @GET("world/total")
    Call<WorldTotal> getTotal();

    @GET("world/countries")
    Call<ArrayList<Country>>getAllCountriesData();

    @GET("world/country")
    Call<ArrayList<CountryTimeline>>getCountryData(@Query("country") String country);

    @GET("world/all")
    Call<World> getAll();

}
