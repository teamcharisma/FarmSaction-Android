package io.github.teamcharisma.farmsaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout dashboard;
    DashboardCardBuilder dashboardElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard = findViewById(R.id.dashboardList);

        //example dashboard element data
        ArrayList<Entry> linedata = new ArrayList<Entry>();
        linedata.add(new Entry(1,2));
        linedata.add(new Entry(2,4));
        linedata.add(new Entry(3, 0));
        LineDataSet linedataset = new LineDataSet(linedata, "Example");

        ArrayList<PieEntry> linedata2 = new ArrayList<PieEntry>();
        linedata2.add(new PieEntry(1,4));
        linedata2.add(new PieEntry(2,5));
        linedata2.add(new PieEntry(3, 6));
        PieDataSet linedataset2 = new PieDataSet(linedata2, "Example");

        dashboardElement = new DashboardCardBuilder(this, dashboard);
        LineData lineData = new LineData(linedataset);
        PieData lineData2 = new PieData(linedataset2);
        dashboardElement.addLineChart(lineData);
        dashboardElement.addPieChart(lineData2);
        dashboardElement.showContent(0);
    }

    public void onActionClick(View view){
        dashboardElement.showContent(1 - dashboardElement.getCurrentContent());
    }
}
