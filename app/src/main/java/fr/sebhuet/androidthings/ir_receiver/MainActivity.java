package fr.sebhuet.androidthings.ir_receiver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

/**
 */
public class MainActivity extends Activity {
    private static final String TAG = fr.sebhuet.androidthings.ir_receiver.MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting ButtonActivity");

        try {
            GPIO_IRReceiver.getInstance().create();
            GPIO_Button.getInstance().create();
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GPIO_IRReceiver.getInstance().close();
        GPIO_Button.getInstance().close();
    }
}