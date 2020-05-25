package com.blackcat.covidtracker.Helpers;

import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class XAxisLabels extends ValueFormatter {

    private LineChart chart;
    private String init_date ;
    private int datalen;
    long startdate;
    private String reqex;

    public  XAxisLabels( LineChart chart, String init_date, int datalen , String regex){
        this.chart = chart;
        this.init_date = init_date;
        this.datalen = datalen;
        this.reqex = regex;

        try {
            SimpleDateFormat format = new SimpleDateFormat(regex);
            Date date  = format.parse(init_date);
            this.startdate = date.getTime();
        } catch (ParseException e) {
            Log.d("errorr",e.getMessage());
        }

    }

    @Override
    public String getFormattedValue(float value) {

        Date date = new Date((long) (startdate + value*1000*60*60*24));
        SimpleDateFormat format = new SimpleDateFormat(" dd/MM/yy");
        return format.format(date);
    }
}
