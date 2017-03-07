package com.example.bazinga.babyhealth;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.*;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.entries;
import static android.R.attr.factor;
import static com.example.bazinga.babyhealth.R.id.chart;

public class MainActivity extends AppCompatActivity implements OnChartGestureListener {
    private  List<Entry> entries = new ArrayList<Entry>();
    private  Button click ;
    private  LineChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (LineChart) findViewById(R.id.chart);
        click = (Button)findViewById(R.id.returnChart);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initdatas();
        LineDataSet dataSet = new LineDataSet(entries, "温度变化");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.setOnChartGestureListener(this);
      //  chart.setScaleXEnabled(false);//禁止x轴方向的变化
        chart.setDoubleTapToZoomEnabled(true);//双击是否放大
        chart.setHighlightPerDragEnabled(false); //设置高亮的连续可拖动的突出显示
        chart.setHighlightPerTapEnabled(true);  //设置每次点击显示高亮
        chart.setMaxHighlightDistance(1000); //设置选择高亮的区域范围


        dataSet.setHighlightEnabled(true);   //设置对数据集显示高亮
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setColors(Color.RED);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setHighLightColor(Color.BLACK);
        dataSet.setDrawValues(true);//显示值

        Highlight highlight = new Highlight(500f, 0,0);

        chart.highlightValue(highlight, false);

        drawAxis();

        chart.invalidate(); // refresh

    }

    private void drawAxis() {
        YAxis leftAxis =  chart.getAxisRight();
        LimitLine ll = new LimitLine(140f, "警戒温度");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(4f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(12f);
        leftAxis.addLimitLine(ll);

    }

    private void initdatas() {
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

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        float a = me.getX();
        float b = me.getYPrecision();
        Toast.makeText(this,a+" "+b,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
      //  Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

}
