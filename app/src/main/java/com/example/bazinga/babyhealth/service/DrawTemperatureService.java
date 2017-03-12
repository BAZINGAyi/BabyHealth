package com.example.bazinga.babyhealth.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

/**
 * Created by bazinga on 2017/3/10.
 */

public class DrawTemperatureService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);


        long triggerAtTime = SystemClock.elapsedRealtime() + 3*1000;

        Intent i = new Intent(this, DrawReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);


        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }
}
