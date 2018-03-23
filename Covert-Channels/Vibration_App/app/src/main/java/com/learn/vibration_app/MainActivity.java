package com.learn.vibration_app;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button_short,button_long;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_short=(Button) findViewById(R.id.button_short);
        button_long=(Button) findViewById(R.id.button_long);

        final Vibrator v;

        v= (Vibrator) getSystemService(VIBRATOR_SERVICE);

        button_short.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.vibrate(100);
            }
        });

        button_long.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.vibrate(500);
            }
        });
    }
}
