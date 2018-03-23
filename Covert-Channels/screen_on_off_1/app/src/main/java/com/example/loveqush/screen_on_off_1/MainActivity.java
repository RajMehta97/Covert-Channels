package com.example.loveqush.screen_on_off_1;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

    Button   mButton;
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button)findViewById(R.id.b1);
        mEdit   = (EditText)findViewById(R.id.input);

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                        String p = mEdit.getText().toString();
                        startblink(p);
                    }
                });

    // Example of a call to a native method

    }

    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private PowerManager.WakeLock mOffLock;

    public void startblink(String lol){



        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        mOffLock = mPowerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK| PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "tag");
        //       unlockScreen();
        //Toast.makeText(this, "This is my Toast message!", Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "message!", Toast.LENGTH_LONG).show();
        //sleep(15000);
        for(int i = 0; i < lol.length() ; ++i) {
//            sleep(100);

            if(lol.charAt(i)=='0'){
                sleep(15000);
                turnOffScreen();
                sleep(15000);
            }
            else if(lol.charAt(i)=='1'){
                sleep(15000);
                turnOnScreen();
                sleep(15000);

            }
            //Toast.makeText(this, "on message!", Toast.LENGTH_LONG).show();
        }
    }


    public void turnOnScreen(){
        // turn on screen

        Log.v("ProximityActivity", "ON!");


            mOffLock.acquire();
            Toast.makeText(this, "on ", Toast.LENGTH_SHORT).show();



        mOffLock.release();

    }

    public void turnOffScreen(){
        // turn off screen
//        PowerManager mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock mWakeLock;

        Log.v("ProximityActivity", "OFF!");


            mWakeLock.acquire();
            Toast.makeText(this,"off",Toast.LENGTH_SHORT).show();



        mWakeLock.release();

    }


}
