package com.screen.backgroundservice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by user on 11/15/2017.
 */
public class NextPage extends Activity {
    static final int READ_BLOCK_SIZE = 100;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page);

        try {
            Toast.makeText(getApplicationContext(),"Reading is Started",Toast.LENGTH_SHORT).show();
            FileInputStream fileIn=openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
