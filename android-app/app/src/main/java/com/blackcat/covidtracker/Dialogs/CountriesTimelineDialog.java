package com.blackcat.covidtracker.Dialogs;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.style.TabStopSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackcat.covidtracker.Adapters.CountryTimelineAdapter;
import com.blackcat.covidtracker.Adapters.IndiaTimelineAdapter;
import com.blackcat.covidtracker.Helpers.XAxisLabels;
import com.blackcat.covidtracker.Models.Country;
import com.blackcat.covidtracker.Models.CountryTimeline;
import com.blackcat.covidtracker.Models.IndiaTotal;
import com.blackcat.covidtracker.NetworkHelpers.RetrofitInstance;
import com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces.ServiceWorld;
import com.blackcat.covidtracker.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;
import java.util.Collection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CountriesTimelineDialog extends DialogFragment implements OnChartGestureListener {

    private ArrayList<CountryTimeline> timeline;
    private Context context;
    private CountryTimelineAdapter adapter;
    private RecyclerView recyclerView;
    private LineChart chart;
    private ImageView close;
    private String slug_country;
    private ProgressBar progressBar;
    private String country;
    private LinearLayout titleLL;

    private Retrofit retrofit;
    private ServiceWorld service;

    private XAxisLabels formatter;
    private LineData data;

    public CountriesTimelineDialog() {
    }

    public CountriesTimelineDialog(Context context , String slug_country, String country){
        this.slug_country = slug_country;
        this.context = context;
        this.country = country;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

        retrofit = RetrofitInstance.getRetrofitInstance();
        service = retrofit.create(ServiceWorld.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timeline_dialog,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int orientation = getActivity().getResources().getConfiguration().orientation;


        recyclerView = view.findViewById(R.id.indiaTimelineRecycler);
        chart = view.findViewById(R.id.indiaTimelinechart);
        progressBar = view.findViewById(R.id.progressBar);
        titleLL = view.findViewById(R.id.titleLL);


        close = view.findViewById(R.id.close);

        ((TextView)view.findViewById(R.id.country)).setText(country);

        close.setOnClickListener( v-> dismiss());

        if(orientation == Configuration.ORIENTATION_PORTRAIT){

            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (230 * scale + 0.5f);
            titleLL.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = chart.getLayoutParams();
            params.height =  pixels;
            chart.setLayoutParams(params);

        }else{

            final float scale = getContext().getResources().getDisplayMetrics().density;
            final float height = getContext().getResources().getDisplayMetrics().heightPixels;
            int pixels = (int) (20 * scale + 0.5f);
            titleLL.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = chart.getLayoutParams();
            params.height = (int) (height - pixels);
            chart.setLayoutParams(params);
        }

        chart.setOnChartGestureListener(this);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);


        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setTextColor(getActivity().getResources().getColor(R.color.themeWhite));
        chart.getAxisLeft().setTextColor(getActivity().getResources().getColor(R.color.themeWhite));
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);
        chart.setPinchZoom(true);

        chart.getLegend().setTextColor(getActivity().getResources().getColor(R.color.themeWhite));


        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        timeline = new ArrayList<>();
        adapter = new CountryTimelineAdapter(timeline);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (230 * scale + 0.5f);
            titleLL.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = chart.getLayoutParams();
            params.height =  pixels;
            chart.setLayoutParams(params);

        }else{

            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (20 * scale + 0.5f);
            float height = getContext().getResources().getDisplayMetrics().heightPixels;
            titleLL.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = chart.getLayoutParams();
            params.height = (int) (height - pixels);
            chart.setLayoutParams(params);
        }
    }

    private void getData(){

        Call<ArrayList<CountryTimeline>> call = service.getCountryData(slug_country);

        call.enqueue(new Callback<ArrayList<CountryTimeline>>() {
            @Override
            public void onResponse(Call<ArrayList<CountryTimeline>> call, Response<ArrayList<CountryTimeline>> response) {

                if(response.isSuccessful()){

                    timeline = response.body();
                    adapter.updateDataset(timeline);
                    adapter.notifyDataSetChanged();

                    formatter = new XAxisLabels(chart,timeline.get(0).getDate().substring(0,10),timeline.size(),"yyyy-MM-dd");
                    chart.getXAxis().setValueFormatter(formatter);

                    progressBar.setVisibility(View.GONE);
                    setupChart();

                }else{
                    Toast.makeText(getActivity(),"Too many Request , try again later ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CountryTimeline>> call, Throwable t) {
                Toast.makeText(getActivity(),"Something went wrong , check internet connection ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupChart(){

        ArrayList<ArrayList<Entry>> values = sortValues();
        ArrayList<ILineDataSet> datasets = new ArrayList<>();

        datasets.add(new LineDataSet(values.get(0),"Confirmed"));
        datasets.add(new LineDataSet(values.get(1),"Active"));
        datasets.add(new LineDataSet(values.get(2),"Recovered"));
        datasets.add(new LineDataSet(values.get(3),"Deceased"));

        for( ILineDataSet dataSet : datasets){

            LineDataSet d = ((LineDataSet)dataSet);
            d.setValueTextColor(getActivity().getResources().getColor(R.color.transparent));
            d.setLineWidth(1.5f);
            d.setCircleRadius(1.5f);
            d.setCircleHoleColor(getActivity().getResources().getColor(R.color.transparent));
        }

        ((LineDataSet)datasets.get(0)).setColor(getActivity().getResources().getColor(R.color.themeWhite));
        ((LineDataSet)datasets.get(0)).setCircleColor(getActivity().getResources().getColor(R.color.themeWhite));

        ((LineDataSet)datasets.get(1)).setColor(getActivity().getResources().getColor(R.color.themeYellow));
        ((LineDataSet)datasets.get(1)).setCircleColor(getActivity().getResources().getColor(R.color.themeYellow));

        ((LineDataSet)datasets.get(2)).setColor(getActivity().getResources().getColor(R.color.themeGreen));
        ((LineDataSet)datasets.get(2)).setCircleColor(getActivity().getResources().getColor(R.color.themeGreen));

        ((LineDataSet)datasets.get(3)).setColor(getActivity().getResources().getColor(R.color.themeRed));
        ((LineDataSet)datasets.get(3)).setCircleColor(getActivity().getResources().getColor(R.color.themeRed));


        data = new LineData(datasets);
        chart.setData(data);
        chart.invalidate();
    }

    private ArrayList<ArrayList<Entry>> sortValues(){

        ArrayList<ArrayList<Entry>> values = new ArrayList<>();
        values.add( new ArrayList<>());
        values.add( new ArrayList<>());
        values.add( new ArrayList<>());
        values.add( new ArrayList<>());

        for(int i = 0 ; i < timeline.size() ; i++ ){

            CountryTimeline stat = timeline.get(i);

            values.get(0).add(new Entry(i,stat.getConfirmed()));
            values.get(1).add(new Entry(i,stat.getConfirmed() - stat.getRecovered() - stat.getDeaths()));
            values.get(2).add(new Entry(i,stat.getRecovered()));
            values.get(3).add(new Entry(i,stat.getDeaths()));

        }

        return  values;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
}
