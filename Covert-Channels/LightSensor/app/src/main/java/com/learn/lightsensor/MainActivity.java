package com.learn.lightsensor;

import android.os.Bundle;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    ProgressBar lightMeter;
    TextView textMax, textReading;
    TextView bitstr;
    float counter;
    //Button read;
    TextView display;
    String bits="";
    boolean mustReadSensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        counter = 0;
        //read = (Button) findViewById(R.id.bStart);
        display = (TextView) findViewById(R.id.tvDisplay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightMeter = (ProgressBar)findViewById(R.id.lightmeter);
        textMax = (TextView)findViewById(R.id.max);
        textReading = (TextView)findViewById(R.id.reading);

        bitstr= (TextView) findViewById(R.id.bit);
        SensorManager sensorManager
                = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor
                = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null){
            Toast.makeText(getApplicationContext(),"No Light Sensor! quit-", Toast.LENGTH_LONG).show();
        }else{
            float max =  lightSensor.getMaximumRange();
            lightMeter.setMax((int)max);
            textMax.setText("Max Reading(Lux): " + String.valueOf(max));

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    mustReadSensor = true;
                }
            }, 0, 15000);
            sensorManager.registerListener(lightSensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_UI);

        }
    }

    SensorEventListener lightSensorEventListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub

            if (!mustReadSensor) {
                return;
            }
            mustReadSensor = false;
            if(event.sensor.getType()==Sensor.TYPE_LIGHT) {
                final float currentReading = event.values[0];
                lightMeter.setProgress((int) currentReading);
                textReading.setText("Current Reading(Lux): " + String.valueOf(currentReading));
                if(currentReading>35){
                    bits+="1";
                }
                else{
                    bits+="0";
                }

                bitstr.setText("Bit Stream: " +bits);
              /*  try{
                read.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        display.setText("" + String.valueOf(currentReading));
                    }
                });
            }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Exception Error Caught",Toast.LENGTH_SHORT).show();
                }*/
            }


        }

    };
}