package com.blackcat.covidtracker.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blackcat.covidtracker.Models.InState;
import com.blackcat.covidtracker.R;

import java.util.ArrayList;

public class InStateAdapter extends RecyclerView.Adapter<InStateAdapter.InstateViewHolder> {

    private ArrayList<InState> districts ;

    public InStateAdapter(ArrayList<InState> districts){
        this.districts = districts;
    }

    @NonNull
    @Override
    public InstateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_all_stats,parent,false);
        return new InstateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstateViewHolder holder, int position) {

        InState district = districts.get(position);
        InState.Delta delta =  district.getDelta();

        holder.title.setText(district.getDistrict());
        holder.nConfirmed.setText(delta.getConfirmed() + "");
        holder.nDeceased.setText(delta.getDeceased() + "");
        holder.nRecovered.setText(delta.getRecovered() + "");
        holder.nActive.setText(delta.getConfirmed() - delta.getDeceased() - delta.getRecovered() + "");
        holder.tConfirmed.setText(district.getConfirmed() + "");
        holder.tDeceased.setText(district.getDeceased() + "");
        holder.tRecovered.setText(district.getRecovered() + "");
        holder.tActive.setText(district.getActive() + "");
        holder.rank.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return districts.size();
    }


    public void updateList(ArrayList<InState> list){
        districts = new ArrayList<>(list);
    }

    class InstateViewHolder extends RecyclerView.ViewHolder{

        TextView title , date , nConfirmed , tConfirmed , nActive , tActive , nRecovered , tRecovered , nDeceased , tDeceased , rank ;
        CardView cardView ;

        InstateViewHolder(@NonNull View view) {
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

            date.setVisibility(View.GONE);
        }
    }
}
