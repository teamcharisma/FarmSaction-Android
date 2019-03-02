package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout dashboard;
    LineChart overallStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard = findViewById(R.id.dashboardList);
        overallStats = findViewById(R.id.overallStats);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float dpi = metrics.density;

        //customize overallChart
        overallStats.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        overallStats.getAxisRight().setEnabled(false);
        overallStats.setTouchEnabled(false);
        overallStats.setDrawGridBackground(false);
        LinearLayout.LayoutParams overallStats_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        overallStats_param.weight = 1;
        overallStats.setLayoutParams(overallStats_param);
        overallStats.setMinimumHeight((int)(200/dpi));
        //overallStats.setMinimumWidth(600/dpi);

        //fetch data and update overallStats
        ArrayList<Entry> overallProfitRawData = new ArrayList<>();
        overallProfitRawData.add(new Entry(5,39));
        overallProfitRawData.add(new Entry(11,-22));
        overallProfitRawData.add(new Entry(13,40));
        LineDataSet overallProfitDataSet = new LineDataSet(overallProfitRawData, "Profits");
        overallProfitDataSet.setColor(getResources().getColor(R.color.colorGraphProfitLine));
        overallProfitDataSet.setCircleColor(getResources().getColor(R.color.colorGraphProfitLine));
        overallProfitDataSet.setCircleHoleColor(getResources().getColor(R.color.colorGraphProfitLine));

        ArrayList<Entry> overallRevenueRawData = new ArrayList<>();
        overallRevenueRawData.add(new Entry(5,10));
        overallRevenueRawData.add(new Entry(11,20));
        overallRevenueRawData.add(new Entry(13,30));
        LineDataSet overallRevenueDataSet = new LineDataSet(overallRevenueRawData, "Revenue");
        overallRevenueDataSet.setColor(getResources().getColor(R.color.colorGraphRevenueLine));
        overallRevenueDataSet.setCircleColor(getResources().getColor(R.color.colorGraphRevenueLine));
        overallRevenueDataSet.setCircleHoleColor(getResources().getColor(R.color.colorGraphRevenueLine));


        LineData overallData = new LineData();
        overallData.addDataSet(overallProfitDataSet);
        overallData.addDataSet(overallRevenueDataSet);
        overallStats.setData(overallData);
        overallStats.invalidate();

        //example dashboard element data
        ArrayList<Entry> linedata = new ArrayList<Entry>();
        linedata.add(new Entry(1,2));
        linedata.add(new Entry(2,4));
        linedata.add(new Entry(3, 0));
        LineDataSet linedataset = new LineDataSet(linedata, "Example");
        LineData lineData = new LineData(linedataset);

        LineChart lc = new LineChart(this);
        lc.setData(lineData);
        lc.invalidate();
        lc.setTouchEnabled(false);

        CardBuilder dcb = new CardBuilder(this, dashboard);
        dcb.setTitle("Rice");
        dcb.setGraph(lc);
        XAxis x = lc.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        lc.getAxisRight().setEnabled(false);
        dcb.setImage(R.drawable.rice);
        dcb.build();
    }

    public void onOverallClick(View v){
        Intent intent = new Intent(v.getContext(), DetailedStatsActivity.class);
        intent.putExtra("title", "overall");
        startActivity(intent);
    }
}
