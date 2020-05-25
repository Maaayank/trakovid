package com.blackcat.covidtracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.blackcat.covidtracker.Dialogs.AllDistrictDialog;
import com.blackcat.covidtracker.Models.State;
import com.blackcat.covidtracker.R;

import java.util.ArrayList;

public class AllStatesAdapter extends  RecyclerView.Adapter<AllStatesAdapter.AllStatesViewHolder> {

    ArrayList<State> states ;
    Context context;

    public AllStatesAdapter(ArrayList<State> states, Context context) {
        this.states = states;
        this.context = context;
    }

    @NonNull
    @Override
    public AllStatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_all_stats,parent,false);
        return new AllStatesViewHolder(stateView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStatesViewHolder holder, int position) {

        State state = states.get(position);

        holder.title.setText(state.getState());
        holder.nConfirmed.setText(state.getNewConfirmed()+ "");
        holder.tConfirmed.setText(state.getTotalConfirmed() + "");
        holder.nDeceased.setText(state.getNewDeaths() + "");
        holder.tDeceased.setText(state.getTotalDeaths() + "");
        holder.nRecovered.setText(state.getNewRecovered() + "");
        holder.tRecovered.setText(state.getTotalRecovered()  + "");
        holder.tActive.setText(state.getTotalConfirmed() - state.getTotalRecovered() - state.getTotalDeaths() + "");
        holder.nActive.setText(state.getNewConfirmed() - state.getNewRecovered() - state.getNewDeaths() + "");
        holder.date.setText(state.getDate().substring(0,10));
        holder.rank.setText(position + 1 + "");

        holder.cardView.setOnClickListener(v -> {

            FragmentTransaction fragmentTransaction =  ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
            AllDistrictDialog districtDialog = new AllDistrictDialog(state,position+1,context);
            districtDialog.show(fragmentTransaction,"district");

        });

    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public void  updateDataset(ArrayList<State> list){
        states = new ArrayList<>(list);
    }

    static class AllStatesViewHolder extends RecyclerView.ViewHolder{

        TextView title , date , nConfirmed , tConfirmed , nActive , tActive , nRecovered , tRecovered , nDeceased , tDeceased , rank ;
        CardView cardView ;

        AllStatesViewHolder(@NonNull View view) {
            super(view);
            cardView = (CardView) view;
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
            rank = view.findViewById(R.id.rank);

        }
    }
}
