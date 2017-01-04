package fr.sebhuet.androidthings.ir_receiver;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by sebh on 04/01/17.
 */
public class GPIO_IRReceiver {

    //region singleton
    private static GPIO_IRReceiver instance;

    private GPIO_IRReceiver(){}

    public static synchronized GPIO_IRReceiver getInstance(){
        if(instance == null){
            instance = new GPIO_IRReceiver();
        }
        return instance;
    }
    //endregion

    private Gpio mGpio;

    public void create() throws IOException {
        String pinName = BoardDefaults.getGPIOForIrReceiver();
        PeripheralManagerService service = new PeripheralManagerService();
        mGpio = service.openGpio(pinName);
        mGpio.setDirection(Gpio.DIRECTION_IN);
        mGpio.setEdgeTriggerType(Gpio.EDGE_FALLING);

        mGpio.registerGpioCallback(new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                Log.i(TAG, "GPIO changed, IR Received pressed");
                // Return true to continue listening to events
                return true;
            }
        });
    }

    public void close() {
        if (mGpio != null) {
            // Close the Gpio pin
            Log.i(TAG, "Closing Button GPIO pin");
            try {
                mGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            } finally {
                mGpio = null;
            }
        }
    }
}
