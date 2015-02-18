package inf.uie.Limux.model;

/**
 * @author Marcel
 */
public abstract class Lamp {
    // ---------- STATIC MEMBERS -----------
    private static int instanceCounter;

    // ---------- MEMBERS ----------
    /**
     * ID of the Lamp
     */
    private int id;

    /**
     * Name of the lamp
     */
    private String name;
    
    private Room room;

    // ---------- CONSTRUCTORS ----------
    public Lamp() {
        this.id = instanceCounter++;
        this.name = "";
    }

    public Lamp(String name, Room room) {
        this();
        this.name = name;
        this.room = room;
        room.addLamp(this);
    }

    // --------- METHODS ----------
    public void on() {
        // TODO
    }

    public void off() {
        // TODO
    }
    
    // --------- SETTER&GETTER ----------
    public String getName() {
    	return name;
    }
    
    public Room getRoom() {
    	return room;
    }
}