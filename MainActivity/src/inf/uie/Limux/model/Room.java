package inf.uie.Limux.model;

import java.util.ArrayList;
import java.util.HashSet;
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
    private HashSet<Profile> profileSet;

    // ---------- CONSTRUCTORS ----------
    public Room(String name) {
        this.id = instanceCounter++;
        this.name = name;
        this.lampList = new ArrayList<Lamp>();
        this.profileSet = new HashSet<Profile>();
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
    
    public HashSet<Profile> getProfiles() {
    	return profileSet;
    }
    
    public void removeProfile(Profile profile) {
    	profileSet.remove(profile);
    }
    
    public void addProfile(Profile profile) {
    	profileSet.add(profile);
    }
    
    public void addLamp(Lamp lamp) {
    	lampList.add(lamp);
    }
    
    // ---------- GETTER&SETTER ---------- 
    
    public String getName() {
    	return name;
    }
    
    public List<Lamp> getLamps() {
    	return lampList;
    }
}