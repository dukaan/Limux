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
    
    protected int isActive;
    
    protected Room room;
    private Bluetooth bluetooth;

    // ---------- CONSTRUCTORS ----------
    public Lamp() {
        this.name = "";
        this.isActive = 0;
    }

    public Lamp(String name, Room room, int id) {
        this();
        this.id = id;
        this.name = name;
        this.room = room;
        room.addLamp(this);
        
    }
    
    
    // --------- METHODS ----------
    public void on() {
        String cmd = id + "#";


        Log.v("BT", "String:" + cmd);
        try {
        	Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light on");
        }
        
        this.setActive(1);
    }
    
    public void on(LampColor color) {
        String cmd = id + "#";


        Log.v("BT", "String:" + cmd);
        try {
        	Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light on");
        }
        
        this.setActive(1);
    }


    public void off() {
        String cmd = id + "0" + "#";
        // TODO uncomment when Bluetooth class is ready
        try {
            Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light off");
        }
        
        this.setActive(0);
    }
    
    // --------- SETTER&GETTER ----------
    public String getName() {
    	return name;
    }
    
    public Room getRoom() {
    	return room;
    }
    
    public int getActive() {
    	return isActive;
    }
    
    public void setActive(int value) {
        this.isActive = value;
    }
}