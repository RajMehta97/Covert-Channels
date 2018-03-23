package com.example.soundchannel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button vibrationbutton,buttonStart, buttonStop, buttonPlayLastRecordAudio,
            buttonStopPlayingRecording,vibratepattern ;
    TextView Soundlevel;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    Handler mHandler =  new Handler();
    TimerTask mTimerTask;
    //final Handler handler = new Handler();
    Timer t = new Timer();
    private int nCounter = 0;

    long init,now,time,paused;
    TextView display;
    Handler handler;
    int threshold=200;

    String sFileName="datafile.txt";
    File gpxfile;

    OutputStreamWriter outputStreamWriter;

    FileOutputStream fOut;
    //File myfile = new File("datafile.txt");
    //File folder = new File(Environment.getExternalStorageDirectory() + "/SoundTesting");


    public static final Random RANDOMA = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX=0;

    public int mycount=0;
    //public double [] amp_array;

    /*** bitstr -> STRING VARIABLE TO STORE THE BITS.
         amp -> stores sum of amplitude for interval of 60ms which will be used to calculate the average.
         mycount -> counts upto 60... once reaches 60 will now calculate the average and find if above threshold or not.
     ***/
    String bitstr="";
    double amp=0;
    Runnable rUpdateTextView = new Runnable() {
        @Override
        public void run () {
            Soundlevel.setText("Sound Level : "+ getAmplitude());

            if(mycount==60){
                amp/=60; //here amp will hold the average value.
                if(amp>threshold) { //condition to check if above threshold
                  bitstr += "1";
                }
                else{
                    bitstr+="0";
                }
                mycount=0;
                amp=0; //to calculate new interval amplitude we make amp=0


                //Toast.makeText(MainActivity.this, bitstr,
                  //      Toast.LENGTH_SHORT).show();
                //bitstr="";
            }
            else{
                //else the count will keep incrementing till 60 and amplitude wil be summed.
                mycount++;
                amp+=getAmplitude();


                //Toast.makeText(MainActivity.this, String.valueOf(getAmplitude()),
                  //      Toast.LENGTH_SHORT).show();
            }
            series.appendData(new DataPoint(lastX++,(getAmplitude()/5.00)),true,10);

            // Update your TextView every 200ms

            mHandler.postDelayed(this, 60);
        }
    };

    public void doTimerTask(){

        mTimerTask = new TimerTask() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        nCounter++;
                        // update TextView
                        display.setText("Timer: " + nCounter);
                       // writeFile(""+getAmplitude() + "   " + nCounter);
                       generateNoteOnSD(MainActivity.this,""+getAmplitude() + "   " + nCounter);
                        Log.d("TIMER", "TimerTask run");
                    }
                });
            }};

        // public void schedule (TimerTask task, long delay, long period)
        t.schedule(mTimerTask, 0,400 );  //

    }

    public void stopTask(){

        if(mTimerTask!=null){
            display.setText("Timer canceled: " + nCounter);

            Log.d("TIMER", "timer canceled");
            mTimerTask.cancel();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.button);
        buttonStop = (Button) findViewById(R.id.button2);
        buttonPlayLastRecordAudio = (Button) findViewById(R.id.button3);
        buttonStopPlayingRecording = (Button)findViewById(R.id.button4);
        vibrationbutton = (Button) findViewById(R.id.vibrate);
        vibratepattern =(Button) findViewById(R.id.vibrate_pattern);
        Soundlevel=(TextView) findViewById(R.id.soundlevel);
        display = (TextView) findViewById(R.id.chronometer);
        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);
        File root = new File(Environment.getExternalStorageDirectory(), "Sound");
        if (!root.exists()) {
            root.mkdirs();
        }

        final File file = new File(root, sFileName);

        try {
            fOut = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        GraphView graph=(GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        //customize a little bit viewport

        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMaxY(0);
        viewport.setMaxY(1000);
        viewport.setScrollable(true);

        random = new Random();

        vibrationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(4000);
            }
        });

        final long[] pattern={0,1000,100,1000,0,700,0,500};

        vibratepattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(pattern,-1);
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()) {

                    AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+CreateRandomAudioFileName(5)+null;
                            //Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        init = System.currentTimeMillis();

                        mHandler.post(rUpdateTextView);
                        doTimerTask();

                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    Toast.makeText(MainActivity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                buttonStop.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                stopTask();

                try {
                    outputStreamWriter.close();
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(MainActivity.this, "Recording Completed",
                        Toast.LENGTH_SHORT).show();

            /** Here we are displaying the content of the bitstr which holds the bits we have recorded
             **/

                Toast.makeText(MainActivity.this, bitstr,
                        Toast.LENGTH_LONG).show();
            }
        });

        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                buttonStop.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });

        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }



    public double getAmplitude() {
        if (mediaRecorder != null)
            return  mediaRecorder.getMaxAmplitude();
        else
            return 0;

    }


    public void generateNoteOnSD(Context context, String sBody) {
        try {
           outputStreamWriter.append(sBody+"\n");
            //Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

