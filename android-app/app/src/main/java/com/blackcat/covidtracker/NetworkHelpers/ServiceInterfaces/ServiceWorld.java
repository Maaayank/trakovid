package com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces;

import com.blackcat.covidtracker.Models.World;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceWorld {

    @GET("world/total")
    Call<World> getTotal();

//    @GET("world/countries")
//    Call<>getAllCountriesData();
//
//    @GET("world/country")
//    Call<>getCountryData(@Query("country") String country);

}
