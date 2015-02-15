package inf.uie.Limux.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcel
 */
public class Profile {
    // ---------- STATIC MEMBERS ----------
    /**
     * Counts how much instances have been initiated. Used for id incrementation
     */
    private static int instanceCounter;

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
     * A list of colors available/used in this profile
     */
    private List<LampColor> usedColorsList;

    /**
     * A list of active lamps in this profile
     */
    private List<Lamp> activeLampsList;

    /**
     * A list of rooms used in this profile
     */
    private List<Room> roomList;
    
    /**
     * HashMap that keeps lamp as key and chosen color as value
     */
    private HashMap<Lamp, LampColor> lampWithColorMap;

    // ---------- CONSTRUCTORS ----------
    public Profile(String name) {
        this.id = instanceCounter++;
        this.name = name;

        this.usedColorsList = new ArrayList<LampColor>();
        this.activeLampsList = new ArrayList<Lamp>();
        this.roomList = new ArrayList<Room>();
        
        lampWithColorMap = new HashMap<Lamp, LampColor>();
    }

    // ---------- METHODS ----------
    public void enable() {
        /*
            TODO
            Hier müssen wir uns mal ein Konzept überlegen, wie wir vor dem
            Einschalten die Farbe/Helligkeit der Lampe setzen und vor allem speichern
            Vielleicht mit einer Map?
        */
        for (Lamp lamp : activeLampsList) {
            lamp.on();
        }
    }

    public void disable() {
        for (Lamp lamp : activeLampsList) {
            lamp.off();
        }
    }

    public void addColorForLamp(Lamp lamp, LampColor color) {
    	if(lamp instanceof RGBLamp) {
    		RGBLamp rgbLamp = (RGBLamp) lamp;
    		rgbLamp.setColor(color);
    	} else {
    		// when lamp is no RGBLamp TODO
    	}
    	
    	lampWithColorMap.put(lamp, color);
        usedColorsList.add(color);
    	activeLampsList.add(lamp);
    }

    public void removeColorOfLamp(Lamp lamp) {
    	LampColor removedColor = lampWithColorMap.get(lamp);
    	lampWithColorMap.remove(lamp);
    	activeLampsList.remove(lamp);
    	
    	// iterate over all used colors of the profile, if color of deactivated lamp is still used by another lamp, keep it, otherwise delete it from usedColorsList
    	for(LampColor color : lampWithColorMap.values()) {
    		if(color.getColorCodeAsString().equals(removedColor.getColorCodeAsString())) break;
    		usedColorsList.remove(removedColor);
    	}
    }
    
    // ---------- GETTER&SETTER ---------- 
    
    public String getName() {
    	return name;
    }
    
    public List<Lamp> getActiveLamps() {
    	return activeLampsList;
    }
    
    public List<LampColor> getUsedColors() {
    	return usedColorsList;
    }
}