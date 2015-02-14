package inf.uie.Limux.model;

import java.util.List;

/**
 * @author Marcel
 */
public class Room {
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
        // TODO
    }

    // ---------- METHODS ----------
    public void allLampsOff() {
        // TODO
    }

    public void allLampsOn() {
        // TODO
    }
}