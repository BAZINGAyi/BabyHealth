package com.example.bazinga.babyhealth.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.bazinga.babyhealth.R;

/**
 * Created by bazinga on 2017/3/10.
 */
public class DrawReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        Intent intent1 = new Intent(context, DrawTemperatureService.class);

        context.startService(intent1);

    }

}