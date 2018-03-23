package com.volume.checkvolumehardware;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    TextView txt;
    //MediaPlayer mediaPlayer = new MediaPlayer();
    AudioManager manager;

   /* private boolean started = false;
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //final Random random = new Random();
            //int i = random.nextInt(2 - 0 + 1) + 0;
            //random_note.setImageResource(image[i]);
            if(manager.isMusicActive()){

                txt.setText("Playing");
            }
            else{
                txt.setText("Not Playing");
            }
            if(started) {
                start();
            }
        }
    };

    public void stop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void start() {
        started = true;
        handler.postDelayed(runnable, 2000);
    }




    private Timer timer;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
//            final Random random = new Random();
  //          int i = random.nextInt(2 - 0 + 1) + 0;
   //         random_note.setImageResource(image[i]);

            if(manager.isMusicActive()){

                txt.setText("Playing");
            }
            else{
                txt.setText("Not Playing");
            }
        }
    };

    public void start() {
        if(timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 3000);
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }

    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         txt=(TextView)findViewById(R.id.text);
        manager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        if(manager.isMusicActive()){

            txt.setText("Playing");
        }
        else{
            txt.setText("Not Playing");
        }

      //  start();
        //runnable.run();

        //boolean playing = mediaPlayer.getStatus().equals(Status.PLAYING);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            Toast.makeText(MainActivity.this,"Up working",Toast.LENGTH_SHORT).show();
            txt.setText("up");
           // return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            Toast.makeText(MainActivity.this,"Down working", Toast.LENGTH_SHORT).show();
            txt.setText("down");
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}