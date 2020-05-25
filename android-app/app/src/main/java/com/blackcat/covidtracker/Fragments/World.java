package com.blackcat.covidtracker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.covidtracker.Adapters.AllCountriesAdapter;
import com.blackcat.covidtracker.Helpers.SortArrays;
import com.blackcat.covidtracker.Models.Country;
import com.blackcat.covidtracker.Models.WorldTotal;
import com.blackcat.covidtracker.NetworkHelpers.RetrofitInstance;
import com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces.ServiceWorld;
import com.blackcat.covidtracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class World extends Fragment {

    private Retrofit retrofit;
    private ServiceWorld service;
    private ArrayList<Country> countries,sortedCountries;
    private TextView title , date , nConfirmed , tConfirmed , nActive , tActive , nRecovered , tRecovered , nDeceased , tDeceased ;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView allCountriesRecycler;
    private AllCountriesAdapter adapter;

    public World() {
        // Required empty public constructor
    }

    public static World newInstance() {
        World fragment = new World();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            retrofit = RetrofitInstance.getRetrofitInstance();
            service = retrofit.create(ServiceWorld.class);

            countries = new ArrayList<>();
            sortedCountries = new ArrayList<>();

            getAllStat();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_world, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.cardTitle);
        date = view.findViewById(R.id.date);
        nConfirmed = view.findViewById(R.id.nconfirmed);
        tConfirmed = view.findViewById(R.id.confirmed);
        nActive = view.findViewById(R.id.nactive);
        tActive = view.findViewById(R.id.active);
        nRecovered = view.findViewById(R.id.nrecovered);
        tRecovered = view.findViewById(R.id.recovered);
        nDeceased = view.findViewById(R.id.ndeaths);
        tDeceased = view.findViewById(R.id.deaths);
        allCountriesRecycler = view.findViewById(R.id.allCountriesRecycler);
        progressBar = view.findViewById(R.id.progressBar);
        refreshLayout = view.findViewById(R.id.refreshLayout);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                countries = new ArrayList<>();
                sortedCountries = new ArrayList<>();
                adapter.UpdateDataset(sortedCountries);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.VISIBLE);
//                getWorldStats();
//                getAllCountriesStats();
                getAllStat();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        allCountriesRecycler.setLayoutManager(linearLayoutManager);

        adapter = new AllCountriesAdapter(countries,getContext());
        allCountriesRecycler.setAdapter(adapter);

        date.setText( (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
    }

    private void getWorldStats(){

        Call<WorldTotal> call = service.getTotal();

        call.enqueue(new Callback<WorldTotal>() {
            @Override
            public void onResponse(Call<WorldTotal> call, Response<WorldTotal> response) {

                refreshLayout.setRefreshing(false);
                if(response.isSuccessful()) {

                    WorldTotal worldTotal = response.body();

                    title.setText("World Stats ");
                    nConfirmed.setText(worldTotal.getnConfirmed() + "");
                    nDeceased.setText(worldTotal.getnDeaths() + "");
                    nRecovered.setText(worldTotal.getnRecovered() + "");
                    tConfirmed.setText(worldTotal.gettConfirmed() + "");
                    tDeceased.setText(worldTotal.gettDeaths() + "");
                    tRecovered.setText(worldTotal.gettRecovered() + "");
                    nActive.setText(worldTotal.getnConfirmed() - worldTotal.getnRecovered() - worldTotal.getnDeaths() + "");
                    tActive.setText(worldTotal.gettConfirmed() - worldTotal.gettRecovered() - worldTotal.gettDeaths() + "");

                }else{
                    Toast.makeText(getContext(),"Too many Requests , Retry later ... ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WorldTotal> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Something went wrong , Check your internet connection",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getAllCountriesStats(){

        Call<ArrayList<Country>> call = service.getAllCountriesData();

        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {

                if(response.isSuccessful()){

                    countries = response.body();
                    sortedCountries = SortArrays.sortAllCountries(countries);
                    adapter.UpdateDataset(sortedCountries);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(getContext(),"Too many Requests , Retry later ... ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong , Check your internet connection",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllStat(){

        Call<com.blackcat.covidtracker.Models.World>call = service.getAll();

        call.enqueue(new Callback<com.blackcat.covidtracker.Models.World>() {
            @Override
            public void onResponse(Call<com.blackcat.covidtracker.Models.World> call, Response<com.blackcat.covidtracker.Models.World> response) {

                refreshLayout.setRefreshing(false);

                if(response.isSuccessful()){

                    com.blackcat.covidtracker.Models.World data = response.body();

                    WorldTotal worldTotal = data.getGlobal();

                    title.setText("World Stats ");
                    nConfirmed.setText(worldTotal.getnConfirmed() + "");
                    nDeceased.setText(worldTotal.getnDeaths() + "");
                    nRecovered.setText(worldTotal.getnRecovered() + "");
                    tConfirmed.setText(worldTotal.gettConfirmed() + "");
                    tDeceased.setText(worldTotal.gettDeaths() + "");
                    tRecovered.setText(worldTotal.gettRecovered() + "");
                    nActive.setText(worldTotal.getnConfirmed() - worldTotal.getnRecovered() - worldTotal.getnDeaths() + "");
                    tActive.setText(worldTotal.gettConfirmed() - worldTotal.gettRecovered() - worldTotal.gettDeaths() + "");


                    countries = data.getCountries();
                    sortedCountries = SortArrays.sortAllCountries(countries);
                    adapter.UpdateDataset(sortedCountries);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);


                }else{
                    Toast.makeText(getContext(),"Something went wrong , Check your internet connection",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<com.blackcat.covidtracker.Models.World> call, Throwable t) {

                refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Something went wrong , Check your internet connection",Toast.LENGTH_LONG).show();
            }
        });
    }
}
