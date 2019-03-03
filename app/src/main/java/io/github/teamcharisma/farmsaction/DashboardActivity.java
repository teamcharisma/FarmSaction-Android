package io.github.teamcharisma.farmsaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout dashboard;
    LineChart overallStats;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mDatabaseHelper = new DatabaseHelper(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

        } else {
            // Permission has already been granted
            Log.v("TAG", "LOL");
            startService(new Intent(DashboardActivity.this, SMSService.class));
        }

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

        ArrayList<Bill> bills = mDatabaseHelper.bills;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        float profits[], revenue[], losses[];
        profits = new float[13];
        revenue = new float[13];
        losses = new float[13];
        for(int i=0; i<13; i++){
            profits[i] = revenue[i] = losses [i] = 0;
        }
        for(Bill b: bills){
            if(b.date.getYear()==year) {
                int month = b.date.getMonth();
                for(float f: b.prices)
                    if(b.isSelling)
                        revenue[month]+=f;
                    else losses[month]+=f;
            }
        }
        for(int i=0; i<13; i++){
            profits[i] = revenue[i]-losses[i];
        }


        //fetch data and update overallStats
        ArrayList<Entry> overallProfitRawData = new ArrayList<>();
        ArrayList<Entry> overallRevenueRawData = new ArrayList<>();
        ArrayList<Entry> overallLossRawData = new ArrayList<>();

        for(int i=1; i<=12; i++){
            overallProfitRawData.add(i-1, new Entry(i, profits[i]));
            overallRevenueRawData.add(i-1, new Entry(i, revenue[i]));
            overallLossRawData.add(i-1, new Entry(i, losses[i]));
        }

        LineDataSet overallProfitDataSet = new LineDataSet(overallProfitRawData, "Profits");
        overallProfitDataSet.setColor(getResources().getColor(R.color.colorGraphProfitLine));
        overallProfitDataSet.setCircleColor(getResources().getColor(R.color.colorGraphProfitLine));
        overallProfitDataSet.setCircleHoleColor(getResources().getColor(R.color.colorGraphProfitLine));


        LineDataSet overallRevenueDataSet = new LineDataSet(overallRevenueRawData, "Revenue");
        overallRevenueDataSet.setColor(getResources().getColor(R.color.colorGraphRevenueLine));
        overallRevenueDataSet.setCircleColor(getResources().getColor(R.color.colorGraphRevenueLine));
        overallRevenueDataSet.setCircleHoleColor(getResources().getColor(R.color.colorGraphRevenueLine));

        LineDataSet overallLossesDataSet = new LineDataSet(overallLossRawData, "Expense");
        overallRevenueDataSet.setColor(getResources().getColor(R.color.colorGraphExpenseLine));
        overallRevenueDataSet.setCircleColor(getResources().getColor(R.color.colorGraphExpenseLine));
        overallRevenueDataSet.setCircleHoleColor(getResources().getColor(R.color.colorGraphExpenseLine));


        LineData overallData = new LineData();
        overallData.addDataSet(overallProfitDataSet);
        overallData.addDataSet(overallRevenueDataSet);
        overallData.addDataSet(overallLossesDataSet);
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

    public void onFloatIconClick(View v) {
        startActivity(new Intent(this, NewTransactionActivity.class));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startService(new Intent(DashboardActivity.this, SMSService.class));
        }
    }
}
