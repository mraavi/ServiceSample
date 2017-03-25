package com.appcrops.sampleservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentBindService extends IntentService {
    private final String TAG = this.getClass().getName();
    private int randomInt;
    MyBinder mMyBinder = new MyBinder();

    public class MyBinder extends Binder {
        MyIntentBindService getService() {
            return MyIntentBindService.this;
        }
    }

    public MyIntentBindService() {
        super("MyIntentBindService");
        randomInt = generateRandomNumber();
        Log.d(TAG, "MyIntentBindService: " + randomInt);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        if (intent != null) {

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mMyBinder;
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(1000);
    }

    public String sendMeSomething() {
        return "Hello I am " + TAG + " #" + randomInt;
    }
}
