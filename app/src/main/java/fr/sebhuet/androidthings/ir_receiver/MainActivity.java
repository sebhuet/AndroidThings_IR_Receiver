package fr.sebhuet.androidthings.ir_receiver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 */
public class MainActivity extends Activity {
    private static final String TAG = fr.sebhuet.androidthings.ir_receiver.MainActivity.class.getSimpleName();

    private Gpio mButtonGpio;
    private Gpio mIRReceiverGpio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting ButtonActivity");

        PeripheralManagerService service = new PeripheralManagerService();
        try {
            glueButton(service);
            glueIRReceiver(service);
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    private void glueButton(PeripheralManagerService service) throws IOException {
        String pinName = BoardDefaults.getGPIOForButton();
        mButtonGpio = service.openGpio(pinName);
        mButtonGpio.setDirection(Gpio.DIRECTION_IN);
        mButtonGpio.setEdgeTriggerType(Gpio.EDGE_FALLING);
        mButtonGpio.registerGpioCallback(new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                Log.i(TAG, "GPIO changed, button pressed");
                // Return true to continue listening to events
                return true;
            }
        });
    }

    private void glueIRReceiver(PeripheralManagerService service) throws IOException {
        String pinName = BoardDefaults.getGPIOForIrReceiver();
        mIRReceiverGpio = service.openGpio(pinName);
        mIRReceiverGpio.setDirection(Gpio.DIRECTION_IN);
        mIRReceiverGpio.setEdgeTriggerType(Gpio.EDGE_FALLING);
        mIRReceiverGpio.registerGpioCallback(new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                Log.i(TAG, "GPIO changed, IR Received pressed");
                // Return true to continue listening to events
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mButtonGpio != null) {
            // Close the Gpio pin
            Log.i(TAG, "Closing Button GPIO pin");
            try {
                mButtonGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            } finally {
                mButtonGpio = null;
            }
        }
    }
}