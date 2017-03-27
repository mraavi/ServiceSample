package com.appcrops.sampleservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appcrops.sampleservice.MyIntentBindService.MyBinder;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    private MyIntentBindService mMyIntentBindService;
    private MyBroadcastReceiver mMyBroadcastReceiver;
    public TextView mBrocastCastProgressTxt;

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
        mBrocastCastProgressTxt = (TextView) findViewById(R.id.brodcast_receiver_txt);
        Button startBrodcastServiceBtn = (Button) findViewById(R.id.service_brodcast_btn) ;

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





        //Brocaster Service Ex

        mMyBroadcastReceiver = new MyBroadcastReceiver();

        startBrodcastServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, MyIntentServiceBroadcaster.class));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        //unregisterReceiver(mMyBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(MyIntentServiceBroadcaster.PROGRESS_ACTION);
        registerReceiver(mMyBroadcastReceiver ,intentFilter);
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

    public class MyBroadcastReceiver extends BroadcastReceiver {

        public MyBroadcastReceiver () {
            super();
        }

        @Override
        public void onReceive(Context var1, Intent intent) {
            Log.d(TAG, "MyBroadcastReceiver-onReceive");
            if (intent.getAction() == MyIntentServiceBroadcaster.PROGRESS_ACTION) {
                int proressPer = intent.getIntExtra("progress", 0);
                mBrocastCastProgressTxt.setText("Progress: " + proressPer + "%");
            }
        }
    }
}
