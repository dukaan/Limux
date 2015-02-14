package inf.uie.Limux.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcel
 */
public class Room {
    // ---------- STATIC MEMBERS -----------
    /**
     * Counts how much instances have been initiated. Used for id incrementation
     */
    private static int instanceCounter;

    // ---------- MEMBERS -----------
    /**
     * ID of the room
     */
    private int id;

    /**
     * Name of the room
     */
    private String name;

    /**
     * A list with all the lamps in this room
     */
    private List<Lamp> lampList;

    /**
     * A list with all the profiles defined for this room
     */
    private List<Profile> profileList;

    // ---------- CONSTRUCTORS ----------
    public Room() {
        this.id = instanceCounter++;
        this.name = "";
        this.lampList = new ArrayList<Lamp>();
        this.profileList = new ArrayList<Profile>();
    }

    public Room(String name) {
        this();
        this.name = name;
    }

    // ---------- METHODS ----------

    /**
     * Turns all lamps in this room off
     */
    public void allLampsOff() {
        for (Lamp lamp : lampList) {
            lamp.off();
        }
    }

    /**
     * Turns all lamps in this room on
     */
    public void allLampsOn() {
        for (Lamp lamp : lampList) {
            lamp.on();
        }
    }
}