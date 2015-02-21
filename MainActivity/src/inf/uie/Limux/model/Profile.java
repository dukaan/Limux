package inf.uie.Limux.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.R.integer;
import android.app.UiAutomation.OnAccessibilityEventListener;
import android.graphics.Color;
import android.util.Log;

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
    private HashSet<LampColor> usedColorsSet;

    /**
     * A list of active lamps in this profile
     */
    private HashSet<Lamp> activeLampsSet;

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

        this.usedColorsSet = new HashSet<LampColor>();
        this.activeLampsSet = new HashSet<Lamp>();
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
        for (Lamp lamp : activeLampsSet) {
            lamp.on(lampWithColorMap.get(lamp)); 
            lamp.setActive(1);
        }
    }

    public void disable() {
        for (Lamp lamp : activeLampsSet) {
            lamp.off();
            lamp.setActive(0);
        }
    }

    public void addColorForLamp(RGBLamp lamp, LampColor color) {
    	
    	if(lamp instanceof RGBLamp) {
    		lamp.setColor(color);
    	} else {
    		// when lamp is no RGBLamp TODO
    	}
    	
    	lampWithColorMap.put(lamp, color);
        usedColorsSet.add(color);
    	activeLampsSet.add(lamp);
    }

    public void removeColorOfLamp(RGBLamp lamp) {
    	LampColor removedColor = lampWithColorMap.get(lamp);
    	
    	if (removedColor != null) {	
        	lampWithColorMap.remove(lamp);
        	activeLampsSet.remove(lamp);
        	
        	// after deleting last lamp no item is left in map, so the color has to be removed here
        	if(lampWithColorMap.isEmpty()) usedColorsSet.remove(removedColor);
        	
        	// iterate over all used colors of the profile, if color of deactivated lamp is still used by another lamp, keep it, otherwise delete it from usedColorsList
        	for(LampColor color : lampWithColorMap.values()) {
        		if(color.getColorCodeAsString().equals(removedColor.getColorCodeAsString())) break;
        		usedColorsSet.remove(removedColor);
        	}
    	}

    }
    
    public void addRoom(Room room) {
    	roomList.add(room);
    }
    
    // ---------- GETTER&SETTER ---------- 
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    } 
    
    public HashSet<Lamp> getActiveLamps() {
    	return activeLampsSet;
    }
    
    public HashSet<LampColor> getUsedColors() {
    	return usedColorsSet;
    }
    
    public LampColor getColorByName(CharSequence charSequence) {
    	for (LampColor color : getUsedColors()) {
    		if(color.getName().equals(charSequence)) {
    			return color;
    		}
    	}
    	return null;
    }
    
    public Map<Lamp, LampColor> getLampWithColorMap() {
    	return lampWithColorMap;
    }

    public List<Room> getRooms() {
    	return roomList;
    }
}