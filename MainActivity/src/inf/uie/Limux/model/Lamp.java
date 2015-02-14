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

    // ---------- CONSTRUCTORS ----------
    public Lamp() {
        this.id = instanceCounter++;
        this.name = "";
    }

    public Lamp(String name) {
        this();
        this.name = name;
    }

    // --------- METHODS ----------
    public void on() {
        // TODO
    }

    public void off() {
        // TODO
    }
}