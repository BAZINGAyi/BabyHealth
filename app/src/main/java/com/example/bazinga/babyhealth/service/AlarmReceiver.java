package com.example.bazinga.babyhealth.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.bazinga.babyhealth.R;

import java.util.Date;

/**
 * Created by bazinga on 2017/3/10.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        new Thread(new Runnable() {
            @Override
            public void run() {
//                Log.d("LongRunningService", "executed at " + new Date().
//                        toString());

                showNotification(context);

            }
        }).start();

    }

    private void showNotification(Context context) {

        Notification.Builder builder = new Notification.Builder(context);

        builder.setTicker("宝健康: 有新的提醒");//通知栏的预览文字

        builder.setSmallIcon(R.mipmap.baby);//图标

        builder.setContentTitle("亲爱的妈妈");//通知的标题

        builder.setContentText("您的孩子该进餐了 不要忘记哦！！！");//通知的内容

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