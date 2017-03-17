package com.example.bazinga.babyhealth.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bazinga.babyhealth.R;
import com.example.bazinga.babyhealth.service.LongRunningService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    //蓝牙相关

    public static BluetoothSocket btSocket;

    private BluetoothAdapter bluetoothAdapter;

    private ArrayAdapter<String> deviceAdapter;

    private List<String> listDevices;

    private ListView listView;

    private LinearLayout btContent;

    private TextView btAllData;

    private Button openBT;

    private Button searchBT;

    final private static int MESSAGE_READ = 100;

    int i = 0;


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
        }else{

            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

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

        final View layout = inflater.inflate(R.layout.pipeline, null);//获取自定义布局

        listView = (ListView) layout.findViewById(R.id.list);
        btContent = (LinearLayout) layout.findViewById(R.id.bt_content_llt);
        btAllData = (TextView) layout.findViewById(R.id.all_data);

        btAllData.setText(btAllData.getText(), TextView.BufferType.EDITABLE);

        openBT = (Button) layout.findViewById(R.id.open_btn);
        searchBT = (Button) layout.findViewById(R.id.search_btn);

        listDevices = new ArrayList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {
            openBT.setText("关闭蓝牙");
        }
        deviceAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, listDevices);

        openBT.setOnClickListener(new BTListener());
        searchBT.setOnClickListener(new BTListener());

        listView.setAdapter(deviceAdapter);
        listView.setOnItemClickListener(new ItemClickListener());//添加监听


        builder.setView(layout);

        builder.setIcon(R.mipmap.baby);//设置标题图标

        builder.setTitle("请选择一个设备");//设置标题内容


        //确认按钮
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                choiceDevice.setText("设备已连接");

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


//    public void showDialog(){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//
//        final View layout = inflater.inflate(R.layout.device_display, null);//获取自定义布局
//
//        radioButton1 = (CheckBox)layout.findViewById(R.id.radioButton2);
//
//        radioButton2 = (CheckBox)layout.findViewById(R.id.radioButton1);
//
//        builder.setView(layout);
//
//        builder.setIcon(R.mipmap.health);//设置标题图标
//
//        builder.setTitle("请选择一个设备");//设置标题内容
//
//        LinearLayout l = (LinearLayout) layout.findViewById(R.id.device1);
//
//        l.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                if (radioButton1.isChecked())
//
//                    radioButton1.setChecked(false);
//
//                else
//
//                    radioButton1.setChecked(true);
//
//            }
//        });
//
//        LinearLayout l1 = (LinearLayout) layout.findViewById(R.id.device2);
//
//        l1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                if (radioButton2.isChecked())
//
//                    radioButton2.setChecked(false);
//
//                else
//
//                    radioButton2.setChecked(true);
//
//            }
//        });
//
//        //确认按钮
//        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//
//                choiceDevice.setText("设备已连接");
//
//                CURRENT_STATE = DEVICECONNECT;
//
//                choiceDevice.setClickable(false);
//
//                choiceDevice.setBackgroundResource(R.drawable.home_widget2);
//
//            }
//        });
//
//        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//        final AlertDialog dlg = builder.create();
//        dlg.show();
//    }

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

//蓝牙相关开始

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            //下面几行是为了在logcat里面看到搜索到的设备细节，需要的话，可以将注释打开
//            Bundle b = intent.getExtras();
//            Object[] lstName = b.keySet().toArray();
//            // 显示所有收到的消息及其细节
//            for (int i = 0; i < lstName.length; i++) {
//                String keyName = lstName[i].toString();
//                Log.e("-----" + keyName, String.valueOf(b.get(keyName)));
//            }

            //搜索设备时，取得设备的MAC地址
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String str = device.getName() + "|" + device.getAddress();
                if (listDevices.indexOf(str) == -1)// 防止重复添加
                    listDevices.add(str); // 获取设备名称和mac地址
                if (deviceAdapter != null) {
                    deviceAdapter.notifyDataSetChanged();
                }
            }
        }
    };


    /**
     * 蓝牙开启与搜索按钮点击监听
     */
    class BTListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.open_btn) {
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();//开启蓝牙
                    Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    enable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); //300秒为蓝牙设备可见时间
                    startActivity(enable);
                    openBT.setText("关闭蓝牙");

                } else {
                    bluetoothAdapter.disable();//关闭蓝牙
                    openBT.setText("开启蓝牙");
                    if (btSocket != null) {
                        try {
                            btSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (view.getId() == R.id.search_btn) {
                if (!bluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "请先开启蓝牙", Toast.LENGTH_SHORT).show();
                } else {
                    btContent.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    if (listDevices != null) {
                        listDevices.clear();
                        if (deviceAdapter != null) {
                            deviceAdapter.notifyDataSetChanged();
                        }
                    }
                    bluetoothAdapter.startDiscovery();
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(receiver, filter);

                }
            }
        }
    }

    /**
     * 蓝牙选项，listview列表点击监听
     */
    class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (!bluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "请先开启蓝牙", Toast.LENGTH_SHORT).show();
            } else {
                bluetoothAdapter.cancelDiscovery();//停止搜索
                String str = listDevices.get(position);
                String macAdress = str.split("\\|")[1];

                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAdress);
                try {
                    CURRENT_STATE = DEVICECONNECT;
                    Method clientMethod = device.getClass()
                            .getMethod("createRfcommSocket", new Class[]{int.class});
                    btSocket = (BluetoothSocket) clientMethod.invoke(device, 1);
                    connect(btSocket);//连接设备

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 连接蓝牙及获取数据
     */
    public void connect(final BluetoothSocket btSocket) {
        try {
            btSocket.connect();//连接
            if (btSocket.isConnected()) {
                Log.e("----connect--- :", "连接成功");
                Toast.makeText(getApplicationContext(), "蓝牙连接成功", Toast.LENGTH_SHORT).show();
                listView.setVisibility(View.GONE);
                btContent.setVisibility(View.VISIBLE);
                new ConnetThread().start();//通信

            } else {
                Toast.makeText(getApplicationContext(), "蓝牙连接失败", Toast.LENGTH_SHORT).show();
                btSocket.close();
                listView.setVisibility(View.VISIBLE);
                btContent.setVisibility(View.GONE);
                Log.e("--------- :", "连接关闭");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 蓝牙通信管理
     */
    private class ConnetThread extends Thread {
        public void run() {
            try {
                InputStream inputStream = btSocket.getInputStream();
                byte[] data = new byte[1024];
                int len = 0;
                String result = "";

                while (len != -1) {
                    if (inputStream.available() > 0 == false) {
                        continue;
                    } else {
                        try {
                            Thread.sleep(500);//等待0.5秒，让数据接收完整
                            len = inputStream.read(data);
                            result = URLDecoder.decode(new String(data, "utf-8"));
//                          Log.e("----result：----- :", ">>>" + result);
                            Message msg = new Message();
                            msg.what = MESSAGE_READ;
                            msg.obj = result;
                            handler.sendMessage(msg);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                inputStream.close();
                Log.e("--------- :", "关闭inputStream");
                if (btSocket != null) {
                    btSocket.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.toString());
            }
        }

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_READ:
                    String result = (String) msg.obj;
                    String data = result.split("\\r\\n")[0];
                    Log.e("----data：----- :", ">>>" + data);
                    if (i < 6) {
                        Editable text = (Editable) btAllData.getText();
                        text.append(data);
                        btAllData.setText(text + "\r\n");
                        i++;
                    } else {
                        btAllData.setText(data + "\r\n");
                        i = 0;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }


}
