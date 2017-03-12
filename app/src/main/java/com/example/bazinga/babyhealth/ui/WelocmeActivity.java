package com.example.bazinga.babyhealth.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.bazinga.babyhealth.R;

import java.util.Random;

public class WelocmeActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        public void run () {

            Intent intent = new Intent(WelocmeActivity.this,HomeActivity.class);

            startActivity(intent);

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welocme);

        setFullScreen();

        handler.postDelayed(runnable,3000);

    }

    private void setFullScreen() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
