package com.example.bazinga.babyhealth.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bazinga.babyhealth.R;
import com.example.bazinga.babyhealth.service.LongRunningService;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private Button button ;

    private Button choiceDevice;

    private Button setAlarm;

    static int DEVICECONNECT = 1;

    static int DEVICEDiSCONNECT = 0;

    static int CURRENT_STATE = 0;

    private CheckBox radioButton2;

    private CheckBox radioButton1;

    private Button settingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setFullScreen();

        setContentView(R.layout.activity_home);

        bindView();



    }

    private void setFullScreen() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void alarmSet(int seconds) {

        Intent intent = new Intent(this, LongRunningService.class);

        intent.putExtra("allTime",seconds);

        startService(intent);

    }

    private void bindView() {

        button = (Button)findViewById(R.id.startContentButton);

        choiceDevice = (Button)findViewById(R.id.connectButton);

        setAlarm = (Button)findViewById(R.id.setAlarm);

        settingButton = (Button)findViewById(R.id.settingButton);


        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this , SettingActivity.class);

                startActivity(intent);

            }
        });


        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogAlarm();

            }
        });

        choiceDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CURRENT_STATE == 1){

                    Intent intent = new Intent(HomeActivity.this , ContentActivity.class);

                    startActivity(intent);

                }else{

                    Toast.makeText(getApplication(), "请连接设备", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View layout = inflater.inflate(R.layout.device_display, null);//获取自定义布局

        radioButton1 = (CheckBox)layout.findViewById(R.id.radioButton2);

        radioButton2 = (CheckBox)layout.findViewById(R.id.radioButton1);

        builder.setView(layout);

        builder.setIcon(R.mipmap.health);//设置标题图标

        builder.setTitle("请选择一个设备");//设置标题内容

        LinearLayout l = (LinearLayout) layout.findViewById(R.id.device1);

        l.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (radioButton1.isChecked())

                    radioButton1.setChecked(false);

                else

                    radioButton1.setChecked(true);

            }
        });

        LinearLayout l1 = (LinearLayout) layout.findViewById(R.id.device2);

        l1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (radioButton2.isChecked())

                    radioButton2.setChecked(false);

                else

                    radioButton2.setChecked(true);

            }
        });

        //确认按钮
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                choiceDevice.setText("设备已连接");

                CURRENT_STATE = DEVICECONNECT;

                choiceDevice.setClickable(false);

                choiceDevice.setBackgroundResource(R.drawable.home_widget2);

            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        final AlertDialog dlg = builder.create();
        dlg.show();
    }

    public void showDialogAlarm(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View layout = inflater.inflate(R.layout.device_alarm, null);//获取自定义布局

        builder.setView(layout);

        builder.setIcon(R.mipmap.health);//设置标题图标

        builder.setTitle("请选择时间");//设置标题内容

        final TimePicker timePicker ;

        timePicker = (TimePicker)layout.findViewById(R.id.timePicker) ;

        //确认按钮
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {


                int hour = new Date().getHours();

                int minute = new Date().getMinutes();

                int setHour =  timePicker.getCurrentHour();

                int setminute =  timePicker.getCurrentMinute();

                if (setHour < hour || (setHour == hour)&&(setminute < minute) )

                    Toast.makeText(HomeActivity.this,"您选择的时间不正确",Toast.LENGTH_LONG).show();

                else{

                    int seconds =   (setHour - hour) * 60 * 60 *1000 + (setminute - minute)
                            * 60 * 1000  ;

                    Log.d("HomeActivity", " "+new Date().getHours() +" "+ new Date().getMinutes()
                            +"  " +new Date().getSeconds());

                    Log.d("HomeActivity", " "+hour +" "+ minute
                            +"  ");

                    Toast.makeText(HomeActivity.this,"闹钟已设置成功",Toast.LENGTH_LONG).show();

                    alarmSet(seconds);

                }

            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        final AlertDialog dlg = builder.create();

        dlg.show();
    }



}
