package com.example.akif.rahatla;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcome = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(welcome);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
