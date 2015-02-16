package inf.uie.Limux.model;

/**
 * @author Marcel
 */
public class RGBLamp extends Lamp {
    // ---------- MEMBERS ----------
    /**
     * Color of the light
     */
    private LampColor color;

    // ---------- CONSTRUCTORS ----------
    public RGBLamp() {
        super();
        this.color = LampColor.WHITE;
    }

    public RGBLamp(String name, Room room) {
        super(name, room);
        this.color = LampColor.WHITE;
    }

    // --------- GETTER & SETTER ----------
    public void setColor(LampColor color) {
        this.color = color;
    }
    
    public LampColor getLampColor() {
    	return color;
    }
}