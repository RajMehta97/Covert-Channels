package com.learn.screendetection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

       String pattern="";
       TextView txt;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            txt=(TextView) findViewById(R.id.Screen_text);

            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            BroadcastReceiver mReceiver = new ScreenReceiver();
            registerReceiver(mReceiver, filter);
        }
        @Override
        protected void onPause() {
            // WHEN THE SCREEN IS ABOUT TO TURN OFF
            if (ScreenReceiver.wasScreenOn) {
                pattern+="0";
                // THIS IS THE CASE WHEN ONPAUSE() IS CALLED BY THE SYSTEM DUE TO A SCREEN STATE CHANGE
                //Toast.makeText(getApplicationContext(), "Screen Turned off", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), pattern, Toast.LENGTH_SHORT).show();

                txt.setText(pattern);
            } else {
                // THIS IS WHEN ONPAUSE() IS CALLED WHEN THE SCREEN STATE HAS NOT CHANGED
            }
            super.onPause();
        }

        @Override
        protected void onResume() {
            // ONLY WHEN SCREEN TURNS ON
            if (!ScreenReceiver.wasScreenOn) {
                // THIS IS WHEN ONRESUME() IS CALLED DUE TO A SCREEN STATE CHANGE
            } else {
                // THIS IS WHEN ONRESUME() IS CALLED WHEN THE SCREEN STATE HAS NOT CHANGED
                pattern+="1";
                //Toast.makeText(getApplicationContext(), "Screen Turned on", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), pattern, Toast.LENGTH_SHORT).show();
                txt.setText(pattern);
            }
            super.onResume();
        }


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = null;
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
        if (!screenOn) {
            // YOUR CODE
            Toast.makeText(getApplicationContext(), "Screen Turned off", Toast.LENGTH_SHORT).show();
        } else {
            // YOUR CODE
            Toast.makeText(getApplicationContext(), "Screen Turned on", Toast.LENGTH_SHORT).show();
        }

    }*/
}