package com.volume.colorbackground;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.widget.LinearLayout;

public class MyService extends Service {
    public MyService() {
    }

    String data;
    NextPage obj= new NextPage();
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        data= intent.getStringExtra("message");
            return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        obj.linearLayout.setBackgroundColor(Color.RED);
    }
}
