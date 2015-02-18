package inf.uie.Limux.model;

import java.io.IOException;

import android.util.Log;
import inf.uie.Limux.bluetooth.Bluetooth;

/**
 * @author Marcel
 */
public class DimLamp extends Lamp {

    // ---------- MEMBERS ----------
    /**
     * Brightness of the light
     */
    private int brightness;
    
    private Bluetooth bluetooth;

    // ---------- CONSTRUCTORS ----------
    public DimLamp() {
        super();
        this.brightness = 0;
    }

    public DimLamp(String name, Room room) {
        super(name, room);
        this.brightness = 0;
    }

    // ---------- OVERRIDES ----------
    @Override
    public void on() {
    	String tempBrightness = "";
    	String cmd ="";
    	if (brightness < 100) {
    		cmd = id + "10" + brightness + "#";
    	} else {
    		cmd = id + "1" + brightness + "#";
    	}

        try {
            Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light on");
        }
    }

    // ---------- GETTER & SETTER ----------

    /**
     * sets the brightness
     *
     * @param brightness value for brightness (from 0 to 255)
     * @throws java.lang.IllegalArgumentException if param is not between 0 and 255
     */
    public void setBrightness(int brightness) {
        // verify parameter
        if (0 <= brightness && brightness <= 255) {
            this.brightness = brightness;
        } else {
            throw new IllegalArgumentException("brightness musst be between 0 and 255");
        }
    }
}