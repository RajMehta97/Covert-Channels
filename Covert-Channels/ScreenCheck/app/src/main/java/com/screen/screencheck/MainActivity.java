package com.screen.screencheck;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button start,stop;
    String str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

        start=(Button)findViewById(R.id.start);
        stop=(Button)findViewById(R.id.stop);

        start.setOnClickListener(
                new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startService(new Intent(this, MyService.class));
            }
        });
    }

    //public void StartClicked(){

    //}



    protected void onPause() {
        // WHEN THE SCREEN IS ABOUT TO TURN OFF
        setContentView(R.layout.activity_main);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isInteractive();
        if(isScreenOn){
            str+="1";
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }
        else
        {
            str+="0";
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }

        super.onPause();
    }

    protected void onResume() {
        // ONLY WHEN SCREEN TURNS ON
        setContentView(R.layout.activity_main);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isInteractive();
        if(isScreenOn){
            str+="1";
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }
        else
        {
            str+="0";
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}
