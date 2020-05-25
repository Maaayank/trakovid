package com.blackcat.covidtracker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.covidtracker.Adapters.AllStatesAdapter;
import com.blackcat.covidtracker.Dialogs.TimelineIndiaDialog;
import com.blackcat.covidtracker.Models.IndiaTotal;
import com.blackcat.covidtracker.Models.State;
import com.blackcat.covidtracker.NetworkHelpers.RetrofitInstance;
import com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces.ServiceIndia;
import com.blackcat.covidtracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class India extends Fragment {

    private RecyclerView allStatesRecycler;
    private Retrofit retrofit;
    private ServiceIndia service;
    private TextView title , date , nConfirmed , tConfirmed , nActive , tActive , nRecovered , tRecovered , nDeceased , tDeceased ;
    private AllStatesAdapter adapter;
    private ArrayList<State> states ;
    private ArrayList<IndiaTotal> timeSeries ;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private Button showTimeline;

    public India() {
        // Required empty public constructor
    }

    public static India newInstance() {
        India fragment = new India();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            retrofit = RetrofitInstance.getRetrofitInstance();
            service = retrofit.create(ServiceIndia.class);
            states = new ArrayList<>();

            getTotalData();
            getStateData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_india, container, false);
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
        allStatesRecycler = view.findViewById(R.id.allStateRecycler);
        progressBar = view.findViewById(R.id.progressBar);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        showTimeline = view.findViewById(R.id.timeline);
        showTimeline.setEnabled(false);

        showTimeline.setOnClickListener( v-> {

            TimelineIndiaDialog dialog = new TimelineIndiaDialog(timeSeries,getContext());
            dialog.show(getFragmentManager(),"timeSeries");

        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                progressBar.setVisibility(View.VISIBLE);
                states = new ArrayList<>();
                adapter.updateDataset(states);
                adapter.notifyDataSetChanged();

                getTotalData();
                getStateData();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        allStatesRecycler.setLayoutManager(linearLayoutManager);

        adapter = new AllStatesAdapter(states,getContext());
        allStatesRecycler.setAdapter(adapter);

        date.setText( (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));

    }

    private void getTotalData(){

        Call<ArrayList<IndiaTotal>> call = service.getTotal();
        
        call.enqueue(new Callback<ArrayList<IndiaTotal>>() {
            @Override
            public void onResponse(Call<ArrayList<IndiaTotal>> call, Response<ArrayList<IndiaTotal>> response) {

                refreshLayout.setRefreshing(false);
                if(response.isSuccessful()){
                    
                    timeSeries = response.body();
                    showTimeline.setEnabled(true);
                    setIndiaData(timeSeries.get(timeSeries.size() - 1 ));
                }else{
                    Toast.makeText(getContext(),"Too many requests , retry later ...",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IndiaTotal>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Something went wrong , Check your internet connection ",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getStateData(){

        Call<ArrayList<State>> call = service.getAllStates();

        call.enqueue(new Callback<ArrayList<State>>() {
            @Override
            public void onResponse(Call<ArrayList<State>> call, Response<ArrayList<State>> response) {

                if(response.isSuccessful()){

                    states = response.body();
                    states.remove(0);
                    adapter.updateDataset(states);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(getContext(),"Too many requests , retry later ...",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<State>> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong , Check your internet connection ",Toast.LENGTH_LONG).show();
            }
        });
     }
    
    private void setIndiaData(IndiaTotal indiaTotal){

        title.setText("India Stats ");
        nConfirmed.setText(indiaTotal.getnConfirmed() + "");
        nDeceased.setText(indiaTotal.getnDeaths() + "");
        nRecovered.setText(indiaTotal.getnRecovered() + "");
        tConfirmed.setText(indiaTotal.gettConfirmed() + "");
        tDeceased.setText(indiaTotal.gettDeaths() + "");
        tRecovered.setText(indiaTotal.gettRecovered() + "");
        nActive.setText(indiaTotal.getnConfirmed() - indiaTotal.getnRecovered() - indiaTotal.getnDeaths() + "");
        tActive.setText(indiaTotal.gettConfirmed() - indiaTotal.gettRecovered() - indiaTotal.gettDeaths() + "");
    }
}
