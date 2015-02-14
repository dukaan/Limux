package inf.uie.Limux.model;

import inf.uie.Limux.bluetooth.Bluetooth;

import java.util.List;

/**
 * @author Marcel
 */
public class Profile {
    // ---------- MEMBERS -----------
    /**
     * ID of the profile
     */
    private int id;

    /**
     * name of profile
     */
    private String name;

    /**
     * TODO Was macht der genau hier?
     */
    private Bluetooth bluetoothAdapter;

    /**
     * A list of colors available/used in this profile
     */
    private List<Color> colorList;

    /**
     * A list of lamps used in this profile
     */
    private List<Lamp> lampList;

    /**
     * A list of rooms used in this profile
     */
    private List<Room> roomList;

    // ---------- CONSTRUCTORS ----------
    public Profile() {
        // TODO
    }

    // ---------- METHODS ----------
    public void enable() {
        // TODO
    }

    public void disable() {
        // TODO
    }

    public void addColor() {
        // TODO
    }

    public void removeColor() {
        // TODO
    }

    public void addLamp() {
        // TODO
    }

    public void removeLamp() {
        // TODO
    }
}