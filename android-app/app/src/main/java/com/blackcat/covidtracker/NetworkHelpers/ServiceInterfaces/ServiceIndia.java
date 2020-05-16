package com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces;

import com.blackcat.covidtracker.Models.India;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceIndia {

    @GET("india/total")
    Call<ArrayList<India>>getTotal();

//    @GET("india/allstates")
//    Call<>getAllStates();
//
//    @GET("india/state")
//    Call<>getStateData(@Query("stateCode") String stateCode);


}
