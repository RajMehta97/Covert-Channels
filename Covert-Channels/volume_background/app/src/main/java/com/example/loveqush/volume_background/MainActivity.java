package com.example.loveqush.volume_background;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

    Button start, stop;
    public TextView text;
    Handler handler;
    public static String str = "";
    private AudioManager audio;
    public static boolean clicked = false;
    private static final String MSG_KEY = "yo";
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = (TextView) findViewById(R.id.text);
        start = (Button) findViewById(R.id.startbutton);
        stop = (Button) findViewById(R.id.stopbutton);

        Toast.makeText(getApplicationContext(), "hello ", Toast.LENGTH_SHORT).show();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handler = new Handler(Looper.getMainLooper());
                //handler.postDelayed(work, 100);
                t.start();
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                Bundle bundle = msg.getData();
                int m = bundle.getInt(MSG_KEY);
                str += String.valueOf(m);
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
            }
        };

        t = new Thread() {
            @Override
            public void run(){

                //Toast.makeText(MainActivity.this, "hello bro", Toast.LENGTH_SHORT).show();
                int previousVolume;
                audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                //obj.text.setText("Bit Stream: "+obj.str);
                int t = 0;
                while (true) {
                    previousVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
                    //str += String.valueOf(previousVolume);
                    //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

                    Message m = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt(MSG_KEY,previousVolume);
                    m.setData(bundle);
                    handler.sendMessage(m);

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                //Toast.makeText(getApplicationContext(), "bahubali", Toast.LENGTH_LONG).show();
            }
        };



        stop.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        clicked = true;
                        //stopService(mServiceIntent);
                        handler.removeCallbacksAndMessages(null);
                        t.stop();
                    }
                });

    }


}


