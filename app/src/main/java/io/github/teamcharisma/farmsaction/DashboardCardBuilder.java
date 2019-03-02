package io.github.teamcharisma.farmsaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;

public class DashboardCardBuilder {

    LinearLayout mElement;
    Context mContext;
    ViewGroup mParent;
    LinearLayout mList;
    ArrayList<View> views;
    ArrayList<String> graphTypes;
    int currentView;

    public DashboardCardBuilder(Context context, ViewGroup parent) {
        mElement = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_dashboard_card, parent);
        mList = mElement.findViewById(R.id.graphsList);
        mContext = context;
        mParent = parent;
        views = new ArrayList<>();
        graphTypes = new ArrayList<>();
        currentView = -1;
    }

    public void addLineChart(LineData lineData){
        LineChart lineChart = new LineChart(mContext);
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.setMinimumHeight(500);
        lineChart.setMinimumWidth(500);
        views.add(lineChart);
        graphTypes.add("line");
    }

    public void addBarChart(BarData barData){
        BarChart barChart = new BarChart(mContext);
        barChart.setData(barData);
        barChart.invalidate();
        views.add(barChart);
        graphTypes.add("bar");
    }

    public void addPieChart(PieData pieData) {
        PieChart pieChart = new PieChart(mContext);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.setMinimumHeight(500);
        pieChart.setMinimumWidth(500);
        views.add(pieChart);
        graphTypes.add("pie");
    }

    public ArrayList<String> getGraphList()
    {
        return graphTypes;
    }

    public void removeContent(int index)
    {
        graphTypes.remove(index);
        views.remove(index);
        if (currentView == index) {
            currentView = 0;
            showContent(0);
        }
    }

    public void showContent(int index)
    {
        if (currentView != -1) {
            mList.removeViewAt(0);
        }
        mList.addView(views.get(index), 0);
        currentView = index;
    }

    public int getCurrentContent()
    {
        return currentView;
    }
}
