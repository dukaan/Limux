package inf.uie.Limux.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a singleton
 *
 * @author Marcel
 */
public class House {

    // ---------- STATIC MEMBERS ----------
    /**
     * Holds the instance of this class
     */
    private static House instance;

    // ---------- MEMBERS ----------
    /**
     * ID of the house
     */
    private int id;

    /**
     * A name for a house .... like a pet
     */
    private String name;

    /**
     * A list of all the rooms
     */
    private List<Room> roomList;

    // ---------- CONSTRUCTORS ----------

    /**
     * Singleton Constructor
     */
    private House() {
        // random id -> house is singleton
        this.id = 0;
        this.name = "";
        this.roomList = new ArrayList<Room>();
    }

    // ---------- STATIC METHODS ----------

    /**
     * Get the instance of the singleton
     *
     * @return instance
     */
    public static House getInstance() {
        if (House.instance == null) {
            House.instance = new House();
        }
        return House.instance;
    }

    // ---------- METHODS ----------
    public void addProfile(Profile profile) {
        // TODO
    }

    /**
     * Turns all lamps in the house off
     */
    public void allLampsOff() {
        for (Room room : roomList) {
            room.allLampsOff();
        }
    }

    /**
     * Turns all lamps in the house on
     */
    public void allLampsOn() {
        for (Room room : roomList) {
            room.allLampsOn();
        }
    }

    public List<Profile> getAllProfiles() {
        // TODO
        return null;
    }

    public void removeProfile(Profile profile) {
        // TODO
    }

    // ---------- GETTER & SETTER ----------
    public void setName(String name) {
        this.name = name;
    }
}