package com.example.bazinga.babyhealth;

import android.view.MotionEvent;

import com.github.mikephil.charting.listener.ChartTouchListener;

/**
 * Created by bazinga on 2017/3/5.
 */

public interface OnChartGestureListener {
    /**
     * Callbacks when the chart is single-tapped.
     *
     * @param me
     */
    void onChartSingleTapped(MotionEvent me);

}
