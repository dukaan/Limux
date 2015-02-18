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

    // ---------- OVERRIDES ----------
    @Override
    public void on() {
        String cmd = id + color.getColorCodeAsString() + "#";
        // TODO uncomment when Bluetooth class is ready
        /*try {
            Bluetooth.getInstance().sendData(cmd);
        } catch (IOException e) {
            Log.e("BT", "IOException: cannot turn light on");
        }*/
    }

    // ---------- GETTER & SETTER ----------
    public void setColor(LampColor color) {
        this.color = color;
    }
    
    public LampColor getLampColor() {
    	return color;
    }
}