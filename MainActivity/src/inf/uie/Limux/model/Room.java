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
    public Room(String name, List<Lamp> lamps) {
        this.id = instanceCounter++;
        this.name = name;
        this.lampList = lamps;
        this.profileList = new ArrayList<Profile>();
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
    
    public List<Profile> getProfiles() {
    	return profileList;
    }
    
    public void removeProfile(Profile profile) {
    	profileList.remove(profile);
    }
    
    public void addProfile(Profile profile) {
    	profileList.add(profile);
    }
    
    // ---------- GETTER&SETTER ---------- 
    
    public String getName() {
    	return name;
    }
    
    public List<Lamp> getLamps() {
    	return lampList;
    }
}