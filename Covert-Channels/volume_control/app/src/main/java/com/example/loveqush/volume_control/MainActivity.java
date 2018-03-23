package com.example.loveqush.volume_control;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {


    private AudioManager audioManager;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    Button mButton;
    EditText mEdit;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted


                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;

        }

        mButton = (Button) findViewById(R.id.b1);
        mEdit = (EditText) findViewById(R.id.input);

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        String p = mEdit.getText().toString();
                        volumecheck(p);
                    }
                });


    }

    void volumecheck(String lol) {


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), AudioManager.FLAG_SHOW_UI);


        audioManager.setStreamVolume(AudioManager.STREAM_RING, 2, AudioManager.FLAG_SHOW_UI);

        for (int i = 0; i < lol.length(); ++i) {

            sleep(1000);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, Character.getNumericValue(lol.charAt(i)), AudioManager.FLAG_SHOW_UI);
            //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Character.getNumericValue(lol.charAt(i)), AudioManager.FLAG_SHOW_UI);

        }


    }
}
