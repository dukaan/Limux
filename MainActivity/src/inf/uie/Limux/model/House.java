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
        
        // initialize sample data for testing
        LampColor red = new LampColor("Rot", 255, 0, 0);
        LampColor green = new LampColor("Gruen", 0, 255, 0);
        LampColor blue = new LampColor("Blau", 0, 0, 255);
        
        RGBLamp lamp1 = new RGBLamp("Lampe1");
        RGBLamp lamp2 = new RGBLamp("Lampe2");
        RGBLamp lamp3 = new RGBLamp("Lampe3");
        RGBLamp lamp4 = new RGBLamp("Lampe4");
        
        List<Lamp> livingRoomLamps = new ArrayList<Lamp>();
        livingRoomLamps.add(lamp1);
        livingRoomLamps.add(lamp2);
        
        List<Lamp> bedRoomLamps = new ArrayList<Lamp>();
        bedRoomLamps.add(lamp3);
        bedRoomLamps.add(lamp4);
        
        Profile chill = new Profile("Chillen");
        chill.addColorForLamp(lamp1, red);
        chill.addColorForLamp(lamp2, green);
        
        Profile eat = new Profile("Essen");
        eat.addColorForLamp(lamp2, blue);
        eat.addColorForLamp(lamp1, blue);
        
        Profile sleep = new Profile("Einschlafen");
        sleep.addColorForLamp(lamp3, red);
        
        Profile wake = new Profile("Aufwachen");
        wake.addColorForLamp(lamp3, green);
        wake.addColorForLamp(lamp4, blue);
        
        Room livingRoom = new Room("Wohnzimmer", livingRoomLamps);
        livingRoom.addProfile(chill);
        livingRoom.addProfile(eat);
        
        Room bedRoom = new Room("Schlafzimmer", bedRoomLamps);
        bedRoom.addProfile(sleep);
        bedRoom.addProfile(wake);
        
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
    
    public Profile getProfileByName(String name) {
    	Profile profile = null;
    	
    	for (Profile p : getAllProfiles()) {
    		if(p.getName().equals(name)) {
    			profile = p;
    			break;
    		} else {
    			profile = null;
    		} 
    	}
    	
    	return profile;
    }
    
    public List<Lamp> getAllLamps() {
    	List<Lamp> allLamps = new ArrayList<Lamp>();
    	
    	for (Room room : roomList) {
    		for (Lamp lamp : room.getLamps()) {
    			allLamps.add(lamp);
    		}
    	}
    	
    	return allLamps;
    }
    
    public Lamp getLampByName(String name) {
    	Lamp lamp = null;
    	
    	for (Lamp aLamp : getAllLamps()) {
    		if(aLamp.getName().equals(name)) {
    			lamp = aLamp;
    			break;
    		} else {
    			lamp = null;
    		}
    	}
    	return lamp;
    }

    // ---------- GETTER & SETTER ----------
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Room> getRooms() {
    	return roomList;
    }
}