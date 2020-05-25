package com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces;

import com.blackcat.covidtracker.Models.InState;
import com.blackcat.covidtracker.Models.IndiaTotal;
import com.blackcat.covidtracker.Models.State;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceIndia {

    @GET("india/total")
    Call<ArrayList<IndiaTotal>>getTotal();

    @GET("india/allstates")
    Call<ArrayList<State>>getAllStates();

    @GET("india/state")
    Call<ArrayList<InState>>getAllDistrict(@Query("state") String state);

}
