package inf.uie.Limux.model;

import java.io.IOException;

import android.util.Log;
import inf.uie.Limux.bluetooth.Bluetooth;

/**
 * @author Marcel
 */
public class RGBLamp extends Lamp {
    // ---------- MEMBERS ----------
    /**
     * Color of the light
     */
    private LampColor color;
    
    private Bluetooth bluetooth;

    // ---------- CONSTRUCTORS ----------
    public RGBLamp() {
        super();
        this.color = LampColor.WHITE;
    }

    public RGBLamp(String name, Room room, int id) {
        super(name, room, id);
        this.color = LampColor.WHITE;
    }

    // ---------- OVERRIDES ----------
    @Override
    public void on() {
        String cmd = id + color.getColorCodeAsString() + "#";
        Log.v("ArduinoBT", "RGB ist " + cmd);
        // TODO uncomment when Bluetooth class is ready
        try {
            Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light on");
        }
    }
    
    public void on(LampColor color) {
        String cmd = id + color.getColorCodeAsString() + "#";
        Log.v("ArduinoBT", "RGB ist " + cmd);
        // TODO uncomment when Bluetooth class is ready
        try {
            Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light on");
        }
    }

    // ---------- GETTER & SETTER ----------
    public void setColor(LampColor color) {
        this.color = color;
    }
    
    public LampColor getLampColor() {
    	return color;
    }
}