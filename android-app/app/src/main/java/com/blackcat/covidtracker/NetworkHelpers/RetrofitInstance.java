package com.blackcat.covidtracker.NetworkHelpers;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit ;


    public static Retrofit getRetrofitInstance(){

        if (retrofit == null) {

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://bd79fdf8.ngrok.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return  retrofit;
    }
}
