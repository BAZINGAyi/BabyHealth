package com.example.bazinga.babyhealth.ui;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.bazinga.babyhealth.R;
import com.example.bazinga.babyhealth.fragment.AdoutFragment;
import com.example.bazinga.babyhealth.fragment.DataFragment;
import com.example.bazinga.babyhealth.fragment.OutdataFragment;
import com.example.bazinga.babyhealth.fragment.RecommendFragment;
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
import java.util.Random;

public class ContentActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private FragmentManager fragmentManager;

    private DataFragment dataFragment;

    private RecommendFragment recommendFragment;

    private AdoutFragment adoutFragment;

    private OutdataFragment outdataFragment;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setFullScreen();

       initNavigationView();

        fragmentManager = getSupportFragmentManager();

        selectTab(0,fragmentManager.beginTransaction());


    }

    private void initNavigationView() {

        navigationView = (NavigationView)findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }


    private void setFullScreen() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else{

            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_data) {

            item.setChecked(true);

            selectTab(0,fragmentManager.beginTransaction());

        } else if (id == R.id.nav_output) {

            item.setChecked(true);

            selectTab(1,fragmentManager.beginTransaction());

        } else if (id == R.id.nav_recommend) {

            item.setChecked(true);

            selectTab(2,fragmentManager.beginTransaction());

        } else if (id == R.id.nav_about) {

            item.setChecked(true);

            selectTab(3,fragmentManager.beginTransaction());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void selectTab(int i,  FragmentTransaction fragmentTransaction) {

        hideAllFragment(fragmentTransaction);

        switch (i) {

            case 0:
                if (dataFragment == null) {
                    dataFragment = new DataFragment();
                 //   fg_DIS.setArguments(bundle);
                    fragmentTransaction.add(R.id.main_fragment_replace, dataFragment);
                } else {
                    fragmentTransaction.show(dataFragment);
                }
                break;

            case 1:

                if (outdataFragment == null) {
                    outdataFragment = new OutdataFragment();
                    //   fg_DIS.setArguments(bundle);
                    fragmentTransaction.add(R.id.main_fragment_replace, outdataFragment);
                } else {
                    fragmentTransaction.show(outdataFragment);
                }

//                img_my.setImageResource(R.mipmap.my_bottom_press);
//                if (fg_MY == null) {
//                    fg_MY = new MyFragment();
//                    fg_MY.setArguments(bundle);
//                    fragmentTransaction.add(R.id.content_layout, fg_MY);
//                } else {
//                    fragmentTransaction.show(fg_MY);
//                }
                break;

            case 2:

                if (recommendFragment == null) {
                    recommendFragment = new RecommendFragment();
                    //   fg_DIS.setArguments(bundle);
                    fragmentTransaction.add(R.id.main_fragment_replace, recommendFragment);
                } else {
                    fragmentTransaction.show(recommendFragment);
                }
//                img_search.setImageResource(R.mipmap.search_bottom_press);
//                if (fg_SEA == null) {
//                    fg_SEA = new SearchFragment();
//                    fg_SEA.setArguments(bundle);
//                    fragmentTransaction.add(R.id.content_layout, fg_SEA);
//                } else {
//                    fragmentTransaction.show(fg_SEA);
//                }
                break;
            case 3:

                if (adoutFragment == null) {
                    adoutFragment = new AdoutFragment();
                    //   fg_DIS.setArguments(bundle);
                    fragmentTransaction.add(R.id.main_fragment_replace, adoutFragment);
                } else {
                    fragmentTransaction.show(adoutFragment);
                }

//                img_message.setImageResource(R.mipmap.message_bottom_press);
//                if (fg_MESS == null) {
//                    fg_MESS = new MessageFragment();
//                    fg_MESS.setArguments(bundle);
//                    fragmentTransaction.add(R.id.content_layout, fg_MESS);
//                } else {
//                    fragmentTransaction.show(fg_MESS);
//                }
                break;
            default:
                break;
        }
        //千万不要忘了提交
        fragmentTransaction.commit();
    }


    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (dataFragment != null) fragmentTransaction.hide(dataFragment);
        if (recommendFragment != null) fragmentTransaction.hide(recommendFragment);
        if (outdataFragment != null) fragmentTransaction.hide(outdataFragment);
        if (adoutFragment != null) fragmentTransaction.hide(adoutFragment);
    }



}
