package inf.uie.Limux.model;

/**
 * @author Marcel
 */
public class RGBLamp extends Lamp {
    // ---------- MEMBERS ----------
    /**
     * Color of the light
     */
    private Color color;

    // ---------- CONSTRUCTORS ----------
    public RGBLamp() {
        super();
        this.color = Color.WHITE;
    }

    public RGBLamp(String name) {
        super(name);
        this.color = Color.WHITE;
    }

    // --------- GETTER & SETTER ----------
    public void setColor(Color color) {
        this.color = color;
    }
}