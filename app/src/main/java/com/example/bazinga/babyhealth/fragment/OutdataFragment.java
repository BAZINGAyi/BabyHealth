package com.example.bazinga.babyhealth.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bazinga.babyhealth.R;
import com.example.bazinga.babyhealth.excel.ExcelUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.bazinga.babyhealth.R.id.chart;

/**
 * Created by bazinga on 2017/3/10.
 */

public class OutdataFragment extends Fragment {

    @BindView(R.id.outPutButoon)
    Button outPutButoon;

    private Button navButton;

    private Button backHome;

    public DrawerLayout drawerLayout;

    private TextView textView;

    private String[] title = {"时间", "温度", "备注说明"};

    private String[] saveData;

    private File file;

    @BindView(R.id.barChart)
    BarChart chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.outdata_fragment, container, false);

        initView(view);

        ButterKnife.bind(this, view);

        initBarChart();

        return view;
    }

    private void initBarChart() {

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f,35.2f));
        entries.add(new BarEntry(1f,35.1f));
        entries.add(new BarEntry(2f,36.2f));
        entries.add(new BarEntry(3f,35.2f));
        entries.add(new BarEntry(4f,35.2f));
        entries.add(new BarEntry(5f,35.2f));
        entries.add(new BarEntry(6f,35.2f));
        entries.add(new BarEntry(7f,35.2f));
        entries.add(new BarEntry(8f,35.2f));
        entries.add(new BarEntry(9f,35.2f));
        entries.add(new BarEntry(10f,36.4f));
        entries.add(new BarEntry(11f,35.4f));
        entries.add(new BarEntry(12f,37.4f));
        entries.add(new BarEntry(13f,33.4f));
        entries.add(new BarEntry(14f,34.4f));
        entries.add(new BarEntry(15f,35.4f));
        entries.add(new BarEntry(16f,36.9f));
        entries.add(new BarEntry(17f,34.4f));
        entries.add(new BarEntry(18f,37.5f));
        entries.add(new BarEntry(19f,34.9f));
        entries.add(new BarEntry(20f,35.1f));
        entries.add(new BarEntry(21f,36.1f));
        entries.add(new BarEntry(22f,36.7f));
        entries.add(new BarEntry(23f,36.9f));
        entries.add(new BarEntry(24f,36.2f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {

        navButton = (Button) view.findViewById(R.id.nav_button);

        backHome = (Button) view.findViewById(R.id.titleBackButton);

        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        textView = (TextView) view.findViewById(R.id.title_baby);

        textView.setText("数据导出");

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
    }

    private void initExcel() {

        saveData = new String[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                "35,6", "dsd"
        };
        if (canSave(saveData)) {

            initData();
        }

    }

    @SuppressLint("SimpleDateFormat")
    public void initData() {
        file = new File(getSDPath() + "/Family");
        makeDir(file);
        ExcelUtils.initExcel(file.toString() + "/babyData.xls", title);
        ExcelUtils.writeObjListToExcel(addExcelData(), getSDPath() + "/Family/babyData.xls", getContext());
    }

    private boolean canSave(String[] data) {
        boolean isOk = false;
        for (int i = 0; i < data.length; i++) {
            if (i > 0 && i < data.length) {
                Log.e("Mainactivity", "canSave: " + data[i]);
                if (!TextUtils.isEmpty(data[i])) {
                    isOk = true;
                }
            }
        }
        return isOk;
    }


    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;

    }


    private ArrayList<ArrayList<String>> getBillData() {

        ArrayList<ArrayList<String>> datas = new ArrayList<>();

        ArrayList<String> data = new ArrayList<>();

        String d = new Date(System.currentTimeMillis()).toString();

        Log.e("MainActivity", "getBillData: " + d);

        data.add(d);

        data.add("35.6");

        data.add("体温正常");

        datas.add(data);

        return datas;
    }

    @OnClick(R.id.outPutButoon)

    void start(View view){

        initExcel();

    }

    private ArrayList<ArrayList<String>> addExcelData(){

        ArrayList<ArrayList<String>> datas = new ArrayList<>();

        int a = 1;

        for (int i=0 ;i<24;i++){

            if ( a == 1 )

                a = -1;
            else

                a = 1;

            Random random = new Random();

            final float temp = 35+random.nextFloat()-a;

            String d = new Date(System.currentTimeMillis()).toString();

            Log.e("MainActivity", "getBillData: " + d);

            ArrayList<String> data = new ArrayList<>();

            data.add(d);

            data.add(temp+"");

            if (temp > 37f)

                data.add("体温不正常");

            else

                data.add("体温正常");

            datas.add(data);

        }

        return datas;
    }

}
