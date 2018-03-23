package com.volume.colorbackground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.button);
        editText = (EditText) findViewById( R.id.editText);


    }

   public void sendMsg (View view){


        String data = editText.getText().toString();
        Intent intent=new Intent(this,NextPage.class);
        intent.putExtra("message", data);
        startActivity(intent);


    }
}
