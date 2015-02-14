package inf.uie.Limux.model;

import java.util.ArrayList;
import java.util.List;

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
    private List<Color> colorList;

    /**
     * A list of lamps used in this profile
     */
    private List<Lamp> lampList;

    /**
     * A list of rooms used in this profile
     */
    private List<Room> roomList;

    // ---------- CONSTRUCTORS ----------
    public Profile(String name) {
        this.id = instanceCounter++;
        this.name = name;

        this.colorList = new ArrayList<Color>();
        this.lampList = new ArrayList<Lamp>();
        this.roomList = new ArrayList<Room>();
    }

    // ---------- METHODS ----------
    public void enable() {
        /*
            TODO
            Hier müssen wir uns mal ein Konzept überlegen, wie wir vor dem
            Einschalten die Farbe/Helligkeit der Lampe setzen und vor allem speichern
            Vielleicht mit einer Map?
        */
        for (Lamp lamp : lampList) {
            lamp.on();
        }
    }

    public void disable() {
        for (Lamp lamp : lampList) {
            lamp.off();
        }
    }

    public void addColor(Color color) {
        colorList.add(color);
    }

    public void removeColor(Color color) {
        colorList.remove(color);
    }

    public void addLamp(Lamp lamp) {
        lampList.add(lamp);
    }

    public void removeLamp(Lamp lamp) {
        lampList.remove(lamp);
    }
    
    // ---------- GETTER&SETTER ---------- 
    
    public String getName() {
    	return name;
    }
}