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
        
        // initialize sample data
        Lamp lamp1 = new RGBLamp();
        Lamp lamp2 = new RGBLamp();
        Lamp lamp3 = new RGBLamp();
        Lamp lamp4 = new RGBLamp();
        
        List<Lamp> livingRoomLamps = new ArrayList<Lamp>();
        livingRoomLamps.add(lamp1);
        livingRoomLamps.add(lamp2);
        
        List<Lamp> bedRoomLamps = new ArrayList<Lamp>();
        bedRoomLamps.add(lamp3);
        bedRoomLamps.add(lamp4);
        
        
        Room livingRoom = new Room("Wohnzimmer", livingRoomLamps);
        livingRoom.addProfile(new Profile("Chillen"));
        livingRoom.addProfile(new Profile("Essen"));
        
        Room bedRoom = new Room("Schlafzimmer", bedRoomLamps);
        bedRoom.addProfile(new Profile("Einschlafen"));
        bedRoom.addProfile(new Profile("Aufwachen"));
        
        roomList.add(livingRoom);
        roomList.add(bedRoom);
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

    // returns a list of all profiles in the house
    public List<Profile> getAllProfiles() {
    	List<Profile> allProfiles = new ArrayList<Profile>();
    	
    	for (Room room : roomList) {
    		for (Profile profile : room.getProfiles()){
    			allProfiles.add(profile);
    		}
    	}
    	
        return allProfiles;
    }

    // ---------- GETTER & SETTER ----------
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Room> getRooms() {
    	return roomList;
    }
}