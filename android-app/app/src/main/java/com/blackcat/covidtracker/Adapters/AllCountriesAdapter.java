package com.blackcat.covidtracker.Adapters;

import android.annotation.SuppressLint;
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

import com.blackcat.covidtracker.Dialogs.CountriesTimelineDialog;
import com.blackcat.covidtracker.Models.Country;
import com.blackcat.covidtracker.R;

import java.util.ArrayList;

public class AllCountriesAdapter extends RecyclerView.Adapter<AllCountriesAdapter.AllCountriesViewHolder> {

    private ArrayList<Country> allCountries ;
    private Context context;

    public AllCountriesAdapter(ArrayList<Country> allCountries, Context context) {
        this.allCountries = allCountries;
        this.context = context;
    }

    @NonNull
    @Override
    public AllCountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View countryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_all_stats,parent,false);
        return  new AllCountriesViewHolder(countryView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AllCountriesViewHolder holder, int position) {

        Country country = allCountries.get(position);

        holder.title.setText(country.getCountry());
        holder.nConfirmed.setText(country.getNewConfirmed()+ "");
        holder.tConfirmed.setText(country.getTotalConfirmed() + "");
        holder.nDeceased.setText(country.getNewDeaths() + "");
        holder.tDeceased.setText(country.getTotalDeaths() + "");
        holder.nRecovered.setText(country.getNewRecovered() + "");
        holder.tRecovered.setText(country.getTotalRecovered()  + "");
        holder.tActive.setText(country.getTotalConfirmed() - country.getTotalRecovered() - country.getTotalDeaths() + "");
        holder.nActive.setText(country.getNewConfirmed() - country.getNewRecovered() - country.getNewDeaths() + "");
        holder.date.setText(country.getDate().substring(0,10));
        holder.rank.setText(position + 1 + "");

        holder.cardView.setOnClickListener(v -> {
            CountriesTimelineDialog dialog = new CountriesTimelineDialog(context,country.getSlug(),country.getCountry());
            dialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"country-timeline");
        });
    }


    @Override
    public int getItemCount() {
        return allCountries.size();
    }

    public void UpdateDataset(ArrayList<Country> countries){
        allCountries = new ArrayList<>(countries);
    }

    static class AllCountriesViewHolder extends RecyclerView.ViewHolder{

        TextView title , date , nConfirmed , tConfirmed , nActive , tActive , nRecovered , tRecovered , nDeceased , tDeceased , rank ;
        CardView cardView ;

        AllCountriesViewHolder(@NonNull View view) {
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