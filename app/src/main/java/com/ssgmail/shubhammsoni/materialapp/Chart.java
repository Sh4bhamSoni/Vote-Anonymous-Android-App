package com.ssgmail.shubhammsoni.materialapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Chart extends Fragment {


    private Integer[] yData = {1, 1};
    private String[] xData = {"data A", "Data B"};
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        Log.d("chart", "onCreateView: ");
        Bundle bundle = getArguments();
        Integer count_agree = bundle.getInt("agree");
        Integer count_disagree = bundle.getInt("disagree");
        yData[0] = count_agree;
        yData[1] = count_disagree;

        pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        pieChart.setRotationEnabled(true);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleRadius(0.0f);
        addDataSet();

        return view;
    }

    private void addDataSet() {
        ArrayList<PieEntry> y = new ArrayList<>();
        ArrayList<String> x = new ArrayList<>();
        for (int i = 0; i < yData.length; i++) {
            y.add(new PieEntry(yData[i], i));
        }
        for (int i = 1; i < xData.length; i++) {

            x.add(xData[i]);
        }

        PieDataSet piedataset = new PieDataSet(y, "pie");
        piedataset.setSliceSpace(2);
        piedataset.setValueTextSize(12);

        ArrayList<Integer> color = new ArrayList<>();
        color.add(Color.GREEN);
        color.add(Color.RED);


        PieData piedata = new PieData(piedataset);
        pieChart.setData(piedata);
        piedataset.setColors(color);
        pieChart.invalidate();

    }

}
