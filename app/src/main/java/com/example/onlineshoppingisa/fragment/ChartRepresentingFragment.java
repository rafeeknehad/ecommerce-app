package com.example.onlineshoppingisa.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.onlineshoppingisa.ChartRepresentingFragmentModel;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.FlowChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChartRepresentingFragment extends Fragment {

    private static final String TAG = "ChartRepresentingFragme";
    private HashMap<String, HashMap<String, String>> yearHash;
    private HashMap<String, String> monthHash;
    private BarChart barChart;

    public ChartRepresentingFragment() {
        // Required empty public constructor
        yearHash = new HashMap<>();
        monthHash = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ChartRepresentingFragmentModel model = ViewModelProviders.of(this).get(ChartRepresentingFragmentModel.class);
        model.getAllChartOfUserOrder().observe(this, flowCharts -> {
            Log.d(TAG, "onChanged: ///// " + flowCharts.size());
            Collections.sort(flowCharts);
            initData(flowCharts);
        });
        View view = inflater.inflate(R.layout.fragment_chart_representing, container, false);
        barChart = view.findViewById(R.id.fragment_chart_representing);
        return view;
    }

    private void initData(List<FlowChart> flowCharts) {
        List<BarEntry> entries = new ArrayList<>();
        for (FlowChart chart : flowCharts) {
            Date date = chart.getDate();
            int year = date.getYear();
            int month = date.getMonth();
            if (monthHash.containsKey(String.valueOf(month + 1))) {
                String currentValue = monthHash.get(String.valueOf((month + 1)));
                if (currentValue != null) {
                    monthHash.put(String.valueOf(month + 1), String.valueOf(Integer.parseInt(currentValue) + 1));
                }
            } else {
                monthHash.put(String.valueOf(month + 1), "1");
            }
            yearHash.put(String.valueOf(year + 1900), monthHash);
        }
        for (Map.Entry<String, HashMap<String, String>> entry : yearHash.entrySet()) {
            String key = entry.getKey();
            for (Map.Entry<String, String> entry1 : entry.getValue().entrySet()) {
                //String keyOfMonth = entry1.getKey();
                String counter = entry1.getValue();
                entries.add(new BarEntry(Float.parseFloat(key), Integer.parseInt(counter)));
            }
        }
        BarDataSet barDataSet = new BarDataSet(entries, "Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(R.color.colorLayerDark);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar chart example");
        barChart.animateY(2000);

    }
}