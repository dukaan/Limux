package inf.uie.Limux.model;

/**
 * @author Marcel
 */
public class Color {
    // ---------- STATIC MEMBERS ----------
    /**
     * Counts how much instances have been initiated. Used for id incrementation
     */
    private static int instanceCounter;

    // sample colors
    public final static Color WHITE;

    static {
        WHITE = new Color("white", 255, 255, 255);
    }

    // ---------- MEMBERS ----------
    /**
     * ID of the color
     */
    private int id;

    /**
     * Name of the color
     */
    private String name;

    /**
     * RGB value of the color
     */
    private int red;

    private int green;

    private int blue;

    // ---------- CONSTRUCTORS ----------

    /**
     * Creates a new Color.
     *
     * @param r red value of color (from 0 to 255)
     * @param g green value of color (from 0 to 255)
     * @param b blue value of color (from 0 to 255)
     * @throws java.lang.IllegalArgumentException if params are not in the given range
     */
    public Color(int r, int g, int b) {
        this.id = instanceCounter++;
        this.name = "";

        // verify rgb value
        if (0 <= r && r <= 255) {
            this.red = r;
        } else {
            throw new IllegalArgumentException("R must be between 0 and 255");
        }

        if (0 <= g && g <= 255) {
            this.green = g;
        } else {
            throw new IllegalArgumentException("G must be between 0 and 255");
        }

        if (0 <= b && b <= 255) {
            this.blue = b;
        } else {
            throw new IllegalArgumentException("B must be between 0 and 255");
        }
    }

    public Color(String name, int r, int g, int b) {
        this(r, g, b);
        this.name = name;
    }

    // ---------- GETTER & SETTER ----------
    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}