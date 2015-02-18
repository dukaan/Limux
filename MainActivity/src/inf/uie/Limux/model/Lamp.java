package inf.uie.Limux.model;

import android.util.Log;
import inf.uie.Limux.bluetooth.Bluetooth;

import java.io.IOException;

/**
 * @author Marcel
 */
public abstract class Lamp {
    // ---------- STATIC MEMBERS -----------
    private static int instanceCounter;

    // ---------- MEMBERS ----------
    /**
     * ID of the Lamp
     */
    protected int id;

    /**
     * Name of the lamp
     */
    protected String name;
    
    protected Room room;

    // ---------- CONSTRUCTORS ----------
    public Lamp() {
        this.id = instanceCounter++;
        this.name = "";
    }

    public Lamp(String name, Room room) {
        this();
        this.name = name;
        this.room = room;
        room.addLamp(this);
    }

    // --------- METHODS ----------
    public void on() {
        String cmd = id + 1 + "#";
        // TODO uncomment when Bluetooth class is ready
        /*try {
            Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light on");
        }*/
    }

    public void off() {
        String cmd = id + 0 + "#";
        // TODO uncomment when Bluetooth class is ready
        /*try {
            Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light off");
        }*/
    }
    
    // --------- SETTER&GETTER ----------
    public String getName() {
    	return name;
    }
    
    public Room getRoom() {
    	return room;
    }
}