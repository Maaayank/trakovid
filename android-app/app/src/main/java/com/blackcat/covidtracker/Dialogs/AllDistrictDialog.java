package com.blackcat.covidtracker.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackcat.covidtracker.Adapters.InStateAdapter;
import com.blackcat.covidtracker.Models.InState;
import com.blackcat.covidtracker.Models.State;
import com.blackcat.covidtracker.NetworkHelpers.RetrofitInstance;
import com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces.ServiceIndia;
import com.blackcat.covidtracker.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllDistrictDialog extends DialogFragment {

    private Context context;
    private State state;
    private Retrofit retrofit;
    private ServiceIndia service;
    private RecyclerView recyclerView;
    private InStateAdapter adapter;
    private ArrayList<InState> districts;
    private ProgressBar progressBar;
    private int rank;
    private ImageView close;
    private TextView title , date , nConfirmed , tConfirmed , nActive , tActive , nRecovered , tRecovered , nDeceased , tDeceased , trank ;



    public AllDistrictDialog(State state, int rank, Context context){
        this.state = state;
        this.rank = rank;
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

        retrofit = RetrofitInstance.getRetrofitInstance();
        service = retrofit.create(ServiceIndia.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_district_dialog,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        districts = new ArrayList<>();

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
        trank = view.findViewById(R.id.rank);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.inStateRecycler);
        close = view.findViewById(R.id.close);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new InStateAdapter(districts);
        recyclerView.setAdapter(adapter);

        title.setText(state.getState());
        nConfirmed.setText(state.getNewConfirmed()+ "");
        tConfirmed.setText(state.getTotalConfirmed() + "");
        nDeceased.setText(state.getNewDeaths() + "");
        tDeceased.setText(state.getTotalDeaths() + "");
        nRecovered.setText(state.getNewRecovered() + "");
        tRecovered.setText(state.getTotalRecovered()  + "");
        tActive.setText(state.getTotalConfirmed() - state.getTotalRecovered() - state.getTotalDeaths() + "");
        nActive.setText(state.getNewConfirmed() - state.getNewRecovered() - state.getNewDeaths() + "");
        date.setText(state.getDate().substring(0,10));
        trank.setText(rank + "");

        getDistrictData();

        close.setOnClickListener( v->{
            dismiss();
        });
    }

    private void getDistrictData(){

        Call<ArrayList<InState>> call = service.getAllDistrict(state.getState());

        call.enqueue(new Callback<ArrayList<InState>>() {
            @Override
            public void onResponse(Call<ArrayList<InState>> call, Response<ArrayList<InState>> response) {

                if(response.isSuccessful()){

                    districts = response.body();
                    adapter.updateList(districts);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }else
                    Toast.makeText(context,"Too many request , retry later",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<InState>> call, Throwable t) {
                Toast.makeText(context,"Something went wrong , Retry again ... ",Toast.LENGTH_LONG).show();
            }
        });
    }
}
