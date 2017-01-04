package fr.sebhuet.androidthings.ir_receiver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 */
public class MainActivity extends Activity {
    private static final String TAG = fr.sebhuet.androidthings.ir_receiver.MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}