package com.example.bazinga.babyhealth;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.*;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  List<Entry> entries = new ArrayList<Entry>();

    private  LineChart chart;

    private BarChart mBarChart;

    private PieChart mChart;

    private Button navButton;

    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setFullScreen();

        bindView();

        initLinedatas();

        initLineChart();

        setPieData();

    }

    private void setPieData() {

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(38.5f, "深睡状态"));

        entries.add(new PieEntry(6.7f, "哭喊状态"));

        entries.add(new PieEntry(24.0f, "正常状态"));

        entries.add(new PieEntry(30.8f, "其它状态"));


        PieDataSet set = new PieDataSet(entries, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.rgb(205, 205, 205));

        colors.add(Color.rgb(114, 188, 223));

        colors.add(Color.rgb(255, 123, 124));

        colors.add(Color.rgb(57, 135, 200));

        set.setColors(colors);

        PieData data = new PieData(set);

        Legend le = mChart.getLegend();      //设置说明

        le.setTextColor(Color.WHITE);

        mChart.animateY(2000, Easing.EasingOption.Linear);

        mChart.animateX(2000, Easing.EasingOption.Linear);

        mChart.setData(data);

        mChart.invalidate();

    }





    private void bindView() {

        chart = (LineChart) findViewById(R.id.chart);

        mChart = (PieChart) findViewById(R.id.pieChartSleep);

        navButton = (Button)findViewById(R.id.nav_button) ;

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void setFullScreen() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initLineChart() {

        LineDataSet dataSet = new LineDataSet(entries, "温度警告");
        //dataSet.set

        dataSet.setDrawCircles(true);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.rgb(255,255,255));
        dataSet.setCubicIntensity(0.9f);

        dataSet.setHighlightEnabled(true);          //设置对数据集显示高亮
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setColors(Color.WHITE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setHighLightColor(Color.GREEN);      //设置高亮的颜色
        dataSet.setDrawValues(true);                 //高亮的点显示值
        dataSet.setLabel("温度变化");
      //  dataSet.setCircleColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        //chart.setOnChartGestureListener(this);
        //chart.setScaleXEnabled(false);           //禁止x轴方向的变化
        chart.setDoubleTapToZoomEnabled(true);      //双击是否放大
        chart.setHighlightPerDragEnabled(false);    //设置高亮的连续可拖动的突出显示
        chart.setHighlightPerTapEnabled(true);      //设置每次点击显示高亮
        chart.setMaxHighlightDistance(1000);        //设置选择高亮的区域范围


        //设置显示高亮的默认值
        Highlight highlight = new Highlight(100f, 0,0);
        chart.highlightValue(highlight, false);

        //设置横轴
        drawLineAxis();

        chart.animateY(2000, Easing.EasingOption.Linear);

        chart.animateX(2000, Easing.EasingOption.Linear);

        Legend le = chart.getLegend();      //设置说明

        le.setTextColor(Color.WHITE);

        chart.invalidate(); // refresh
    }

    private void drawLineAxis() {
        //设置横轴
        YAxis leftAxis =  chart.getAxisRight();
        LimitLine ll = new LimitLine(140f, "高烧报警");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(2f);
        ll.setTextColor(Color.WHITE);
        ll.setTextSize(12f);
        leftAxis.addLimitLine(ll);

        YAxis leftAxis1 =  chart.getAxisLeft();
        LimitLine ll1 = new LimitLine(40f, "低烧报警");
        ll1.setLineColor(Color.RED);
        ll1.setLineWidth(2f);
        ll1.setTextColor(Color.WHITE);
        ll1.setTextSize(12f);
        leftAxis1.addLimitLine(ll1);

        //YAxis left = chart.getAxisLeft();
        //left.setDrawLabels(false); // no axis labels
       // left.setDrawAxisLine(false); // no axis line
      //  left.setDrawGridLines(false); // no grid lines
      //  left.setDrawZeroLine(true); // draw a zero line
        chart.getAxisLeft().setEnabled(true); // no right axis
        chart.getAxisRight().setEnabled(false); // no right axis
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);


        //设置纵轴
//        XAxis leftAxis2 = chart.getXAxis();
//        LimitLine ll2 = new LimitLine(20f, "低烧报警");
//        ll2.setLineColor(Color.RED);
//        ll2.setLineWidth(4f);
//        ll2.setTextColor(Color.BLACK);
//        ll2.setTextSize(12f);
//        leftAxis2.addLimitLine(ll2);

//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.TOP);
//        xAxis.setTextColor(Color.BLUE);
//        xAxis.setTextSize(10f);
//        xAxis.setTextColor(Color.BLACK);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setDrawGridLines(false);

    }

    private void initLinedatas() {
        entries.add(new Entry(0f,22.4f));
        entries.add(new Entry(1f,25.4f));
        entries.add(new Entry(2f,30.4f));
        entries.add(new Entry(3f,12.4f));
        entries.add(new Entry(4f,100.4f));
        entries.add(new Entry(5f,222.4f));
        entries.add(new Entry(6f,22.4f));
        entries.add(new Entry(7f,25.4f));
        entries.add(new Entry(8f,30.4f));
        entries.add(new Entry(9f,12.4f));
        entries.add(new Entry(10f,100.4f));
        entries.add(new Entry(11f,222.4f));
        entries.add(new Entry(12f,22.4f));
        entries.add(new Entry(13f,25.4f));
        entries.add(new Entry(14f,30.4f));
        entries.add(new Entry(15f,12.4f));
        entries.add(new Entry(16f,100.4f));
        entries.add(new Entry(17f,222.4f));
        entries.add(new Entry(18f,22.4f));
        entries.add(new Entry(19f,25.4f));
        entries.add(new Entry(20f,30.4f));
        entries.add(new Entry(21f,12.4f));
        entries.add(new Entry(22f,100.4f));
        entries.add(new Entry(23f,222.4f));
        entries.add(new Entry(24f,100.4f));
    }


}
