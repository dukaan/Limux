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
        // TODO
    }

    // --------- GETTER & SETTER ----------
    public void setColor(Color color) {
        this.color = color;
    }
}