package com.learn.writing_to_file;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeToFile("12345abcdefg");
        readFromFile();
    }

    public void writeToFile(String str){
        try {
            Toast.makeText(getApplicationContext(),"Writing is started",Toast.LENGTH_SHORT).show();
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(str);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(){
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






























/*
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    File path = new File(Environment.getExternalStorageDirectory(),"volumedata");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeToFile("12345abcdefg");
        readFromFile();
    }

    public void writeToFile(String data)
    {
        // Get the directory for the user's public pictures directory.

        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
            Toast.makeText(getApplicationContext(),"path created:"+ path ,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"path already exist:"+ path ,Toast.LENGTH_LONG).show();
        }

        final File file = new File(path, "config.txt");

        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            Toast.makeText(getApplicationContext(),"content is writing"+ file ,Toast.LENGTH_LONG).show();
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void readFromFile() {
        try {
            final File file = new File(path, "config.txt");
            Toast.makeText(getApplicationContext(),"Reading Started"+ file ,Toast.LENGTH_SHORT).show();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line ,line1 = "";

                while ((line = br.readLine()) != null) {
                    line1 += line;
                }
                Toast.makeText(getApplicationContext(),"Content: "+ line1 ,Toast.LENGTH_SHORT).show();
                br.close();
            }
            catch (IOException e) {
                String error = "";
                error = e.getMessage();
            }


            /*InputStream instream = openFileInput(String.valueOf(path));
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line, line1 = "";
                try {
                    while ((line = buffreader.readLine()) != null)
                        line1 += line;
                    Toast.makeText(getApplicationContext(),"Content: "+ line1 ,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//comment here
        } catch (Exception e) {
            String error = "";
            error = e.getMessage();
        }
    }
}
*/