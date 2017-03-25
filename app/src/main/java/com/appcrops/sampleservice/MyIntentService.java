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
public class MyIntentService extends IntentService {
    private final String TAG = this.getClass().getName();

    public MyIntentService() {
        super("MyIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        if (intent != null) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

            }
        }
    }

}
