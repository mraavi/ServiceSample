package com.appcrops.sampleservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class MyService extends Service {
    private String TAG = this.getClass().getName();

    private ServiceHandler mServiceHandler;
    private HandlerThread mHandlerThread;

    public final class ServiceHandler extends Handler {
        public ServiceHandler (Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage " + msg);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

            }
        }
    }


    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandlerThread = new HandlerThread("MyServiceHandlerThread", Process.THREAD_PRIORITY_DEFAULT);
        mHandlerThread.start();
        mServiceHandler = new ServiceHandler(mHandlerThread.getLooper());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand:: flags: " + flags + " startId:" + startId);
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
