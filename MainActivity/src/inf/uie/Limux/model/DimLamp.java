package inf.uie.Limux.model;

/**
 * @author Marcel
 */
public class DimLamp extends Lamp {

    // ---------- MEMBERS ----------
    /**
     * Brightness of the light
     */
    private int brightness;

    // ---------- CONSTRUCTORS ----------
    public DimLamp() {
        super();
        this.brightness = 0;
    }

    public DimLamp(String name) {
        super(name);
        this.brightness = 0;
    }

    // ---------- GETTER & SETTER ----------

    /**
     * sets the brightness
     *
     * @param brightness value for brightness (from 0 to 255)
     * @throws java.lang.IllegalArgumentException if param is not between 0 and 255
     */
    public void setBrightness(int brightness) {
        // verify parameter
        if (0 <= brightness && brightness <= 255) {
            this.brightness = brightness;
        } else {
            throw new IllegalArgumentException("brightness musst be between 0 and 255");
        }
    }
}