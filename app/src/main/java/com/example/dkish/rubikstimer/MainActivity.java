package com.example.dkish.rubikstimer;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerup, timerdown;
    long MillisecondTime, StartTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, actual, flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerup = (TextView) findViewById(R.id.timerup);
        timerdown = (TextView) findViewById(R.id.timerdown);
        handler = new Handler();
        flag = 0;

    }

    public void master(View view) {
        if (flag == 2) {
            handler.removeCallbacks(forward);//Stops the solve timer

        } else if (flag == 1) {
            handler.removeCallbacks(backward);//Stops the countdown timer

            StartTime = SystemClock.uptimeMillis();//Starts the actual solve timer
            handler.postDelayed(forward, 0);

            flag = 2;

        } else if (flag == 0) {
            StartTime = SystemClock.uptimeMillis();//Starts the countdown timer
            handler.postDelayed(backward, 0);

            flag = 1;//Sets flag 1 to detect next tap as starting the actual solve

        }


    }

    public Runnable forward = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            Seconds = (int) (MillisecondTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (MillisecondTime % 1000);

            timerup.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
    public Runnable backward = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            actual = (int) MillisecondTime;


            Seconds = 14 - (actual / 1000);

            Seconds = Seconds % 60;

            MilliSeconds = (1000-(int)(MillisecondTime% 1000));

            timerdown.setText(String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
            if(Seconds<0){
                handler.removeCallbacks(backward);
                timerdown.setText("00:00");

                StartTime = SystemClock.uptimeMillis();//Starts the actual solve timer
                handler.postDelayed(forward, 0);

                flag = 2;
            }

        }
    };
}

