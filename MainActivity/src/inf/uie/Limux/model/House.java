package inf.uie.Limux.model;

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
     * Name of the house
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
    private House () {
        // TODO
    }

    // ---------- STATIC METHODS ----------
    /**
     * Get the instance of the singleton
     * @return instance
     */
    public static House getInstance () {
        if (House.instance == null) {
            House.instance = new House ();
        }
        return House.instance;
    }

    // ---------- METHODS ----------
    public void addProfile(Profile profile) {
        // TODO
    }

    public void allLampsOff() {
        // TODO
    }

    public void allLampsOn() {
        // TODO
    }

    public List<Profile> getAllProfiles() {
        // TODO
        return null;
    }

    public void removeProfile(Profile profile) {
        // TODO
    }
}