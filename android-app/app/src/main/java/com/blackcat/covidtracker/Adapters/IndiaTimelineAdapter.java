package com.blackcat.covidtracker.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blackcat.covidtracker.Fragments.India;
import com.blackcat.covidtracker.Models.IndiaTotal;
import com.blackcat.covidtracker.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class IndiaTimelineAdapter extends RecyclerView.Adapter<IndiaTimelineAdapter.IndiaTimelineViewHolder>{

    ArrayList<IndiaTotal> timeline ;

    public IndiaTimelineAdapter( ArrayList<IndiaTotal> timeline){
        this.timeline = new ArrayList<>(timeline);
        Collections.reverse(this.timeline);
    }

    @NonNull
    @Override
    public IndiaTimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_timeline,parent,false);
        return  new IndiaTimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndiaTimelineViewHolder holder, int position) {

        IndiaTotal dayStatus = timeline.get(position);

        holder.date.setText(dayStatus.getDate());
        holder.tActive.setText(dayStatus.gettConfirmed() - dayStatus.gettRecovered() - dayStatus.gettDeaths() + "");
        holder.tConfirmed.setText(dayStatus.gettConfirmed() + "");
        holder.tDeceased.setText(dayStatus.gettDeaths() + "");
        holder.tRecovered.setText(dayStatus.gettRecovered() + "");

    }

    @Override
    public int getItemCount() {
        return timeline.size();
    }

    class IndiaTimelineViewHolder extends RecyclerView.ViewHolder{

        TextView  date , tConfirmed , tActive , tRecovered , tDeceased  ;

        public IndiaTimelineViewHolder(@NonNull View view) {
            super(view);

            date = view.findViewById(R.id.date);
            tConfirmed = view.findViewById(R.id.confirmed);
            tActive = view.findViewById(R.id.active);
            tRecovered = view.findViewById(R.id.recovered);
            tDeceased = view.findViewById(R.id.deaths);

        }
    }


}
