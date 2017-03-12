package com.example.bazinga.babyhealth.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bazinga.babyhealth.R;
import com.example.bazinga.babyhealth.service.DrawTemperatureService;
import com.example.bazinga.babyhealth.service.LongRunningService;
import com.example.bazinga.babyhealth.ui.ContentActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by bazinga on 2017/3/8.
 */

public class DataFragment extends Fragment {

    private  List<Entry> entries = new ArrayList<Entry>();

    private LineChart chart;

    private BarChart mBarChart;

    private PieChart mChart;

    private Button navButton;

    public DrawerLayout drawerLayout;

    private Button backHome;

    public SwipeRefreshLayout swipeRefresh;

    public Button moreRecommend;

    static float CurrentTime = 1f;

    private TextView degree_text;

    private TextView degree_warning;

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        public void run () {

            if (degree_text!=null){

                Random random = new Random();

                float value = random.nextFloat()/10;

                float intvalue = random.nextInt(3);

                float num = 35f+intvalue+value;

                if (num >37f) {

                    degree_warning.setTextColor(Color.RED);

                    degree_warning.setText("报警了 温度过高");

                    showNotification(getContext());
                }

                else{

                    degree_warning.setTextColor(Color.GREEN);

                    degree_warning.setText("温度正常");
                }



                degree_text.setText(num+" ");

                addLineData();

                initLineChart(entries);

                setPieData();

                swipeRefresh.setRefreshing(false);

            }


            handler.postDelayed(this,10000);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.data_fragment, container, false);

        bindView(view);

        //startService();

        initLinedatas();

        initLineChart(entries);

        setPieData();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initClick();

        handler.postDelayed(runnable,5000);

    }

    private void initClick() {

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                addLineData();

                initLineChart(entries);

                setPieData();

                swipeRefresh.setRefreshing(false);

            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        moreRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              FragmentManager fragmentManager =  getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction().show(new RecommendFragment());

            }
        });

    }

    private void bindView(View view) {

        degree_warning = (TextView)view.findViewById(R.id.info_text) ;

        degree_text = (TextView)view.findViewById(R.id.degree_text);

        moreRecommend = (Button)view.findViewById(R.id.recommend_more) ;

        chart = (LineChart) view.findViewById(R.id.chart);

        mChart = (PieChart) view.findViewById(R.id.pieChartSleep);

        navButton = (Button) view.findViewById(R.id.nav_button) ;

        //注意这里找的是父Activity的控件

        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        backHome = (Button) view.findViewById(R.id.titleBackButton) ;

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);


    }

    private void initLineChart( List<Entry> entries) {

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
        Highlight highlight = new Highlight(36.5f, 0,0);
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
        LimitLine ll = new LimitLine(37f, "高烧报警");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(2f);
        ll.setTextColor(Color.WHITE);
        ll.setTextSize(12f);
        leftAxis.addLimitLine(ll);

        YAxis leftAxis1 =  chart.getAxisLeft();
        LimitLine ll1 = new LimitLine(34f, "低烧报警");
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
        entries.add(new Entry(0f,35.2f));
        entries.add(new Entry(1f,35.1f));
        entries.add(new Entry(2f,36.2f));
        entries.add(new Entry(3f,35.2f));
        entries.add(new Entry(4f,35.2f));
        entries.add(new Entry(5f,35.2f));
        entries.add(new Entry(6f,35.2f));
        entries.add(new Entry(7f,35.2f));
        entries.add(new Entry(8f,35.2f));
        entries.add(new Entry(9f,35.2f));
        entries.add(new Entry(10f,36.4f));
        entries.add(new Entry(11f,35.4f));
        entries.add(new Entry(12f,37.4f));
        entries.add(new Entry(13f,33.4f));
        entries.add(new Entry(14f,34.4f));
        entries.add(new Entry(15f,35.4f));
        entries.add(new Entry(16f,36.9f));
        entries.add(new Entry(17f,34.4f));
        entries.add(new Entry(18f,37.5f));
        entries.add(new Entry(19f,34.9f));
        entries.add(new Entry(20f,35.1f));
        entries.add(new Entry(21f,36.1f));
        entries.add(new Entry(22f,36.7f));
        entries.add(new Entry(23f,36.9f));
        entries.add(new Entry(24f,36.2f));
    }

    private void addLineData(){

        entries.clear();

        int a = 1;

        for (int i=0 ;i<24;i++){

            if ( a == 1 )

                 a = -1;
            else

                 a = 1;

            Random random = new Random();

            float data = 35+random.nextFloat()-a;

            entries.add(new Entry(CurrentTime,data));

            CurrentTime = CurrentTime + 1f;

        }

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

    private void showNotification(Context context) {

        if (context != null){

            Notification.Builder builder = new Notification.Builder(context);

            builder.setTicker("宝健康: 有新的提醒");//通知栏的预览文字

            builder.setSmallIcon(R.mipmap.baby);//图标

            builder.setContentTitle("亲爱的妈妈");//通知的标题

            builder.setContentText("您孩子的体温不正常 请您赶紧查看！！！");//通知的内容

            builder.setWhen(System.currentTimeMillis());//设置通知时间为当前系统时间

            builder.setVibrate(new long[] {0, 1000, 1000, 1000});//为通知设置震动,4个时间参数，单位为毫秒。

            //第1个参数是延迟振动时间，第2个是振动时间，第3个是静止时间，第4个是静止后又振动的时间
            //上边这个振动就是收到通知后，手机立刻振动1秒，停止振动1秒，接着再振动1秒。
            // Uri soundUri = Uri.fromFile("路径");
            // builder.setSound(soundUri);//设置指定路径下的通知提示音

            builder.setDefaults(Notification.DEFAULT_ALL);//通知的提示音铃声振动全部采用系统默认值。
            //也可以单独设定Notification.DEFAULT_LIGHTS或Notification.DEFAULT_SOUND或Notification.DEFAULT_VIBRATE
            //设置默认后，自定义的铃声和振动都无效。

            Notification notification = builder.build();//获取Notification的实例

            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            manager.notify(1, notification);


        }

    }
}
