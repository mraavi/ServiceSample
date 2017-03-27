package com.appcrops.sampleservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */


public class MyIntentServiceBroadcaster extends IntentService {
    private String TAG = this.getClass().getName();
    public static final String PROGRESS_ACTION = "COM.SAMPLE_ACTION_PROGRESS";

    public MyIntentServiceBroadcaster() {
        super("MyIntentServiceBroadcaster");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        for (int i=1; i<= 100; i++) {
            if ( i%20 == 0) {
                Log.d(TAG, "onHandleIntent: " + i);
                Intent in = new Intent(PROGRESS_ACTION);
                in.putExtra("progress", i);
                sendBroadcast(in);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
