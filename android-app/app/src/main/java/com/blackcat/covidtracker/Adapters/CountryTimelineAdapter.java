package com.blackcat.covidtracker.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackcat.covidtracker.Models.Country;
import com.blackcat.covidtracker.Models.CountryTimeline;
import com.blackcat.covidtracker.R;

import java.util.ArrayList;
import java.util.Collections;

public class CountryTimelineAdapter extends RecyclerView.Adapter<CountryTimelineAdapter.CountryTimelineViewHolder>{

    ArrayList<CountryTimeline> timeline ;

    public CountryTimelineAdapter(ArrayList<CountryTimeline> timeline){
        this.timeline = timeline;
    }

    public void updateDataset(ArrayList<CountryTimeline> timeline){

        this.timeline = new ArrayList<>(timeline);
        Collections.reverse(this.timeline);
    }

    @NonNull
    @Override
    public CountryTimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_timeline,parent,false);
        return new CountryTimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryTimelineViewHolder holder, int position) {

        CountryTimeline dayStatus = timeline.get(position);

        holder.tActive.setText(dayStatus.getConfirmed() - dayStatus.getRecovered() - dayStatus.getDeaths() + "");
        holder.tConfirmed.setText(dayStatus.getConfirmed() + "");
        holder.tDeceased.setText(dayStatus.getDeaths() + "");
        holder.tRecovered.setText(dayStatus.getRecovered() + "");
        holder.date.setText(dayStatus.getDate().substring(0,10));

    }

    @Override
    public int getItemCount() {
        return timeline.size();
    }

    class CountryTimelineViewHolder extends RecyclerView.ViewHolder{


        TextView date , tConfirmed , tActive , tRecovered , tDeceased  ;

        public CountryTimelineViewHolder(@NonNull View view) {
            super(view);

            date = view.findViewById(R.id.date);
            tConfirmed = view.findViewById(R.id.confirmed);
            tActive = view.findViewById(R.id.active);
            tRecovered = view.findViewById(R.id.recovered);
            tDeceased = view.findViewById(R.id.deaths);

        }
    }
}