package com.screen.backgroundservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 11/15/2017.
 */
public class myService extends Service {

    public static final long SCREEN_CHECK_INTERVAL = 10 * 1000;
    String str="";
    Runnable myRunnable;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    TimerTask mTimerTask;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if(mTimer!=null){
            mTimer.cancel();
        }
        else
            mTimer = new Timer();

        mTimer.scheduleAtFixedRate(new TimerDisplayTimerTask(),0,SCREEN_CHECK_INTERVAL);
    }

    class TimerDisplayTimerTask extends TimerTask{
        @Override
        public void run() {
            mHandler.post(myRunnable =new Runnable() {
                @Override
                public void run() {
                    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    boolean isScreenOn = pm.isInteractive();
                    if (isScreenOn) {
                        str += "1";
                    } else {
                        str += "0";
                    }
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

    }

    public void stopTask() {

        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        mHandler.removeCallbacks(myRunnable);
    }

    @Override
    public void onDestroy() {
        stopTask();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        try {
         //   Toast.makeText(getApplicationContext(),"Writing is started",Toast.LENGTH_SHORT).show();
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(str);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/*
    public void doTimerTask() {
        mTimerTask = new TimerTask() {
            public void run() {
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = pm.isInteractive();
                if(isScreenOn){
                    str+="1";
                }
                else
                {
                    str+="0";
                }

            }};
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        t.schedule(mTimerTask, 0,400);
    }
*/