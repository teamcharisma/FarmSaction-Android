package io.github.teamcharisma.farmsaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class DashboardActivity extends AppCompatActivity {

    LinearLayout dashboard;
    LineChart overallStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard = findViewById(R.id.dashboardList);
        overallStats = findViewById(R.id.overallStats);

        //customize overallChart
        overallStats.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        overallStats.getAxisRight().setEnabled(false);
        overallStats.setTouchEnabled(false);
        LinearLayout.LayoutParams overallStats_param = new LinearLayout.LayoutParams(0, 600);
        overallStats_param.weight = 1;
        overallStats.setLayoutParams(overallStats_param);
        //fetch data and update overallStats
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
        dcb.build();
    }
}
