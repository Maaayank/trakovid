package com.blackcat.covidtracker.Helpers;

import com.blackcat.covidtracker.Models.Country;

import java.util.ArrayList;

public class SortArrays {

    public static ArrayList<Country> sortAllCountries(ArrayList<Country> countries){

        ArrayList<Country> sortedCountries = new ArrayList<>();

        int max, maxi;
        int size = countries.size();
        for(int i = 0;i < size ;i++){
            max = 0;
            maxi = -1;
            for(int j = 0 ; j < countries.size() ; j ++ ){

                Country country = countries.get(j);

                if(country.getTotalConfirmed() >= max){
                    max = country.getTotalConfirmed();
                    maxi = j;
                }
            }

            sortedCountries.add(countries.remove(maxi));
        }
        return sortedCountries;
    }
}
