package com.example.onlineshoppingisa.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.onlineshoppingisa.ChartRepresentingFragmentModel;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.util.FlowChartRetrofit;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class ChartRepresentingFragment extends Fragment {

    private static final String TAG = "ChartRepresentingFragme";
    

    public ChartRepresentingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ChartRepresentingFragmentModel model = ViewModelProviders.of(this).get(ChartRepresentingFragmentModel.class);
        model.getAllChartOfUserOrder().observe(this, new Observer<List<FlowChartRetrofit>>() {
            @Override
            public void onChanged(List<FlowChartRetrofit> flowChartRetrofits) {
                Log.d(TAG, "onChanged: /////4444 "+flowChartRetrofits.size());
            }
        });
        View view = inflater.inflate(R.layout.fragment_chart_representing, container, false);
        BarChart barChart = view.findViewById(R.id.fragment_chart_representing);
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(2006, 100));
        entries.add(new BarEntry(2007, 200));
        entries.add(new BarEntry(2008, 300));
        entries.add(new BarEntry(2009, 400));
        entries.add(new BarEntry(2010, 500));
        entries.add(new BarEntry(2012, 50));
        entries.add(new BarEntry(2013, 150));
        entries.add(new BarEntry(2014, 420));
        entries.add(new BarEntry(2015, 475));
        entries.add(new BarEntry(2016, 508));
        entries.add(new BarEntry(2017, 660));
        entries.add(new BarEntry(2018, 550));
        entries.add(new BarEntry(2019, 630));
        entries.add(new BarEntry(2020, 470));

        BarDataSet barDataSet = new BarDataSet(entries, "Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(R.color.colorLayerDark);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar chart example");
        barChart.animateY(2000);
        return view;
    }
}