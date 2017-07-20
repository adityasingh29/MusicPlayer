package com.example.adityasingh.mymusicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread thread=new Thread(){
            @Override
            public void run() {

                try {
                    sleep(2000);
                }
                catch (Exception e){

                }
                finally {
                    Intent i=new Intent(Splashscreen.this,MainActivity.class);
                    startActivity(i);
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
