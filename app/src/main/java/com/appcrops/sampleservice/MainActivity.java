package com.appcrops.sampleservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appcrops.sampleservice.MyIntentBindService.MyBinder;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    private MyIntentBindService mMyIntentBindService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startServiceBtn = (Button) findViewById(R.id.start_service_btn);
        Button stopServiceBtn = (Button) findViewById(R.id.stop_service_btn);
        Button colorChangeBtn = (Button) findViewById(R.id.color_change_btn) ;
        Button startIntentBtn = (Button) findViewById(R.id.start_intent_service_btn);
        Button stopIntentBtn = (Button) findViewById(R.id.stop_intent_service_btn);
        Button bindServiceBtn = (Button) findViewById(R.id.bind_service_btn);
        final TextView bindServiceTxt = (TextView) findViewById(R.id.bind_service_txt);
        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, MyService.class));
            }
        });

        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        });
        colorChangeBtn.setOnClickListener(new View.OnClickListener() {
            int currentColor = Color.RED;
            @Override
            public void onClick(View view) {
                if (currentColor == Color.RED) {
                    currentColor = Color.GREEN;
                } else if (currentColor == Color.GREEN) {
                    currentColor = Color.BLUE;
                } else if (currentColor == Color.BLUE) {
                    currentColor = Color.RED;
                }

                view.setBackgroundColor(currentColor);
            }
        });

        startIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, MyIntentService.class));
            }
        });

        stopIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, MyIntentService.class));
            }
        });

        bindServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( mMyIntentBindService != null) {
                    String msg = mMyIntentBindService.sendMeSomething();
                    bindServiceTxt.setText(msg);
                }
            }
        });

        bindService(new Intent(MainActivity.this, MyIntentBindService.class), mServiceConnection, Context.BIND_AUTO_CREATE);

    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            MyBinder myBinder = (MyBinder) iBinder;
            mMyIntentBindService = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
            mMyIntentBindService = null;
        }
    };
}
