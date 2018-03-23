package com.light.colourapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.os.SystemClock.sleep;

public class Changer extends AppCompatActivity {
    public Handler mHandler = new Handler();
    Handler handler = new Handler();
    Thread mThread;
    LinearLayout linearLayout;
    int data;
    char message[];
    String messag;
    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout)findViewById(R.id.main_layout);
        Bundle bundle = getIntent().getExtras();
        messag = bundle.getString("message");
        message = messag.toCharArray();
        data = Integer.parseInt(messag);




        mThread = new Thread(new MyThread());
        mThread.start();

        mHandler = new Handler() {
            @Override
            public synchronized void handleMessage(Message msg) {
                if (msg.arg1 == 1)
                    linearLayout.setBackgroundColor(Color.RED);
                else
                    linearLayout.setBackgroundColor(Color.BLUE);
            }
        };

    }


    class MyThread implements Runnable {

        @Override
        public void run() {
             Looper.prepare();

            for(int i=0;i<message.length;i++) {

            Message msg = Message.obtain();
                msg.arg1 = (message[i]=='0')?0:1;
            mHandler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        Looper.loop();
        }
    }
}
