package inf.uie.Limux.activity;

import android.R.integer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import inf.uie.Limux.R;
import inf.uie.Limux.beacon.BeaconScannerApp;
import inf.uie.Limux.beacon.ScanActivity;
import inf.uie.Limux.model.House;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

public class ShowRoomsFragment extends Fragment implements View.OnClickListener{
	
    private BeaconManager beaconManager;
    private Region region;
    private int eventNum = 1;
    
    private float[][] scanValues;
    
	// Constant Declaration
	private static final String PREFERENCE_SCANINTERVAL = "scanInterval";
	private static final String PREFERENCE_TIMESTAMP = "timestamp";
	private static final String PREFERENCE_POWER = "power";
	private static final String PREFERENCE_PROXIMITY = "proximity";
	private static final String PREFERENCE_RSSI = "rssi";
	private static final String PREFERENCE_MAJORMINOR = "majorMinor";
	private static final String PREFERENCE_UUID = "uuid";
	private static final String PREFERENCE_INDEX = "index";
    private static final String MODE_SCANNING = "Stop Scanning";
    private static final String MODE_STOPPED = "Start Scanning";
    protected static final String TAG = "ScanActivity";
    
    // Preferences - will actually have a boolean value when loaded.
    private Boolean index;
    private Boolean uuid;
	private Boolean majorMinor;
	private Boolean rssi;
	private Boolean proximity;
	private Boolean power;
	private Boolean timestamp;
	private String scanInterval;
	
    // This StringBuffer will hold the scan data for any given scan.
    private StringBuffer logString;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message inputMessage) {
//            // Gets the image task from the incoming Message object.
//            float i[][];
//            if(radarElements.isEmpty()) {
//                i = new float[][]{{1, (float) (Math.random() * 100)}, {2, (float) (Math.random() * 100)}, {3, (float) (Math.random() * 100)}};
//            } else {
//                float dis1 =0, dis2=0, dis3=0;
//                for(RadarElement radarElement : radarElements) {
//                    switch (radarElement.getId()) {
//                        case 1:
//                            dis1 = radarElement.getDistance();
//                            break;
//                        case 2:
//                            dis2 = radarElement.getDistance();
//                            break;
//                        case 3:
//                            dis3 = radarElement.getDistance();
//                    }
//                }
//
//                i = new float[][]{
//                        {1, (float)(dis1 + (Math.random()-0.5) * 5)},
//                        {2, (float)(dis2 + (Math.random()-0.5) * 5)},
//                        {3, (float)(dis3 + (Math.random()-0.5) * 5)},
//                };
//            }
//            updateValues(i);
        	
        	updateValues(scanValues);
        }

    };

    private Timer timer; // Simulation


    private List<RadarElement> radarElements;


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_show_rooms, container, false);

        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.rect);

        radarElements = new ArrayList<RadarElement>();

		PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
		BeaconScannerApp app = (BeaconScannerApp)getActivity().getApplication();
		beaconManager = app.getBeaconManager();
		//beaconManager.setForegroundScanPeriod(10);

		// Add parser for iBeacons;
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
		beaconManager.bind((BeaconConsumer) getActivity());

		region = new Region("myRangingUniqueId", null, null, null);

//        roomA = new RadarElement(getActivity(),1, "Wohnzimmer", BitmapFactory.decodeResource(getResources(), R.drawable.eating), Color.parseColor("#33B5E5"), Color.parseColor("#0099CC"));
//        roomB = new RadarElement(getActivity(),2, "Arbeitszimmer", BitmapFactory.decodeResource(getResources(), R.drawable.eating), Color.parseColor("#99CC00"), Color.parseColor("#669900"));
//        roomC = new RadarElement(getActivity(),3, "Küche", BitmapFactory.decodeResource(getResources(), R.drawable.eating), Color.parseColor("#FF4444"), Color.parseColor("#CC0000"));
//        rooms = new RadarElement[]{ roomA, roomB, roomC};
//        for(RadarElement room : rooms) {
//            room.setDistance((float)(Math.random() * 100)); // Simulation
//            room.setRadius(70); // Simulation
//
//            relativeLayout.addView(room);
//        }


//        roomA.setPhi(0);
//        roomB.setPhi(90);
//        roomC.setPhi(180);

        // Simulation
		/*
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendMessage(Message.obtain());
            }
        }, 2000, 1000);*/

		return rootView;
	}

    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
		getScanButton().setText(MODE_STOPPED);
		getScanButton().setOnClickListener(this);
		/*
		Button startScan = (Button) getView().findViewById(R.id.radarScan);
		startScan.setOnClickListener((android.view.View.OnClickListener) this);
		*/
    }

    public void updateValues(float[][] roomDistances) {
        List<RadarElement> newRadarElements = new ArrayList<RadarElement>();
        List<Float> roomList = new ArrayList<Float>();

        // Write IDs in roomList
        for(float[] room : roomDistances) {
            roomList.add(room[0]);
        }

        // Copy old RadarElements
        for(RadarElement radarElement : radarElements) {
            for(float roomId : roomList) {
                if(radarElement.getId() == roomId) {
                    newRadarElements.add(radarElement);
                    roomList.remove(new Float(roomId));
                    break;
                }
            }
        }

        // create new
        for(float roomId : roomList) {
            String name = "";
            RadarElement radarElement = new RadarElement(getActivity(), (int)roomId, name);
            radarElement.setOnClickListener(this);
            newRadarElements.add(radarElement);

            Log.d("Radar", "Create new room");
            RelativeLayout relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.rect);
            relativeLayout.addView(radarElement);
        }

        radarElements = newRadarElements;

        // get closest room
        float minDis[] = {-1, 1000};
        for(int i = 0; i < roomDistances.length; i++) {
            if(roomDistances[i][1] < minDis[1]) {
                minDis[0] = roomDistances[i][0];
                minDis[1] = roomDistances[i][1];
            }
        }
        Log.d("Radar", "closest room " + minDis[0] + " : " + minDis[1]);

        // set phi
        double gradPerElement = 2*Math.PI / radarElements.size();
        for (RadarElement radarElement : radarElements) {
            radarElement.setPhi((radarElement.getId()-1) * gradPerElement);
        }

        // set distances
        for(RadarElement radarElement : radarElements) {
            for(float[] room : roomDistances) {
                Log.d("Radar", "if "+ radarElement.getId() + " == " + room[0] + " == "+ minDis[0]);
                if(radarElement.getId() == room[0] && room[0] == minDis[0]) {
                    radarElement.setDistance(0);
                    radarElement.setRadius(100);
                } else if(radarElement.getId() == room[0]) {
                    float dis = room[1];
                    if(dis *4 < 170 ) {
                        dis = (float)(170/4);
                    }
                    radarElement.setDistance(dis);
                    radarElement.setRadius(70);
                }
            }
            radarElement.invalidate();
        }
    }


    private class RadarElement extends View {

        private final String[][] colors = {
                {"#33B5E5", "#0099CC"},
                {"#99CC00", "#669900"},
                {"#FF4444", "#CC0000"},
                {"#AA66CC", "#9933CC"},
                {"#FFBB33", "#FF8800"}
        };
        
        private final Bitmap[] icons = {
        		BitmapFactory.decodeResource(getResources(), R.drawable.livingroom_icon),
        		BitmapFactory.decodeResource(getResources(), R.drawable.bedroom_icon)
        };

        // Variables
        private int id;
        private String name;

        private volatile float distance;
        private double phi;
        private float radius;

        private Bitmap icon;
        private Paint paintFill;
        private Paint paintStroke;

        // Constructors
        public RadarElement(Context context, int id, String name) {
            super(context);

            this.id = id;
            this.name = name;
            this.radius = 70;

            int colorFill = Color.parseColor(colors[id-1][0]);
            int colorStroke = Color.parseColor(colors[id-1][1]);

            paintFill = new Paint();
            paintFill.setStyle(Paint.Style.FILL);
            paintFill.setColor(colorFill);

            paintStroke = new Paint();
            paintStroke.setStyle(Paint.Style.STROKE);
            paintStroke.setStrokeWidth(3);
            paintStroke.setAntiAlias(true);
            paintStroke.setColor(colorStroke);
            
            icon = icons[id-1];
        }

        // Methods
        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            float x = (float)(4 * distance * Math.cos(phi)) + getWidth()/2;
            float y = (float)(4 * distance * Math.sin(phi)) + getHeight()/2;

            canvas.drawCircle(x, y, radius, paintFill);
            canvas.drawCircle(x, y, radius, paintStroke);
            canvas.drawBitmap(icon, x - icon.getWidth()/2, y - icon.getHeight()/2, paintFill);
        }

        // Getter & Setter


        @Override
        public int getId() {
            return id;
        }

        public float getDistance() {
            return distance;
        }

        public synchronized void setDistance(float distance) {
            if(0 <= distance && distance <= 100) {
                this.distance = distance;
            }
        }

        public void setPhi(double phi) {
            this.phi = phi;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }
    }


    private class Circle extends View {
        private Animation anim;
        Paint paint = new Paint();

        Bitmap bitmap;

        float centerX;
        float centerY;
        float offsetX;
        float offsetY;

        RotateAnimation rotate;
        AlphaAnimation blend;
        ScaleAnimation scale;
        AnimationSet spriteAnimation;

        public Circle (Context context) {
            super(context);


            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eating);
            offsetX = bitmap.getWidth() / 2;
            offsetY = bitmap.getHeight() / 2;
        }

        public Circle (Context context, PointF center) {
            this(context);
            centerX = center.x;
            centerY = center.y;
        }


        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = 100;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#CD5C5C"));

            if (spriteAnimation == null) {
                centerX = canvas.getWidth() / 2;
                centerY = canvas.getHeight() / 2;
                createAnimation(canvas);
            }

            canvas.drawCircle(x / 2, y / 2, radius, paint);
//            canvas.drawText("Wohnzimmer", x / 2, y / 2, paint);
            canvas.drawBitmap(bitmap, centerX - offsetX, centerY - offsetY, paint);

        }

        private void createAnimation(final Canvas canvas) {

            rotate = new RotateAnimation(0, 360, centerX, centerY);
            rotate.setRepeatMode(Animation.REVERSE);
            rotate.setRepeatCount(Animation.INFINITE);
            scale = new ScaleAnimation(0, 2, 0, 2, centerX, centerY);
            scale.setRepeatMode(Animation.REVERSE);
            scale.setRepeatCount(Animation.INFINITE);
            scale.setInterpolator(new AccelerateDecelerateInterpolator());

            spriteAnimation = new AnimationSet(true);
            // spriteAnimation.addAnimation(rotate);
            spriteAnimation.addAnimation(scale);
            spriteAnimation.setDuration(10000L);

            startAnimation(spriteAnimation);

        }


    }
    
	/**
	 * Start and stop scanning, and toggle button label appropriately.
	 */
	public void toggleScanState() {
		Button scanButton = getScanButton();
		String currentState = scanButton.getText().toString();
		if (currentState.equals(MODE_SCANNING)) {
			stopScanning(scanButton);
		} else {
			startScanning(scanButton);
		}
	}
    

	/**
	 * start looking for beacons.
	 */
	private void startScanning(Button scanButton) {

		// Set UI elements to the correct state.
		scanButton.setText(MODE_SCANNING);

		// Reset event counter
		eventNum = 1;
		// Get current values for logging preferences
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	    HashMap <String, Object> prefs = new HashMap<String, Object>();
	    prefs.putAll(sharedPrefs.getAll());

	    index = (Boolean)prefs.get(PREFERENCE_INDEX);
	    uuid = (Boolean)prefs.get(PREFERENCE_UUID);
		majorMinor = (Boolean)prefs.get(PREFERENCE_MAJORMINOR);
		rssi = (Boolean)prefs.get(PREFERENCE_RSSI);
		proximity = (Boolean)prefs.get(PREFERENCE_PROXIMITY);
		power = (Boolean)prefs.get(PREFERENCE_POWER);
		timestamp = (Boolean)prefs.get(PREFERENCE_TIMESTAMP);
		scanInterval = (String)prefs.get(PREFERENCE_SCANINTERVAL);

		// Get current background scan interval (if specified)
		if (prefs.get(PREFERENCE_SCANINTERVAL) != null) {
			beaconManager.setBackgroundBetweenScanPeriod(Long.parseLong(scanInterval));
		}



		// Initialize scan log
		logString = new StringBuffer();

		//Start scanning again.
        beaconManager.setRangeNotifier(new RangeNotifier() {
        	@Override
        	public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        		int min_rssi = -100;
        		Beacon minBeacon = null;
        		
        		int room1 = 0;
        		int room2 = 0;
        		
        		int rssi1 = 0;
        		int rssi2 = 0;
        		
        		ArrayList<Integer> beaconIDs = null;
        		ArrayList<Integer> rssiValues = null;
        		
        		if (beacons.size() > 0) {
        			Iterator <Beacon> beaconIterator = beacons.iterator();
        			while (beaconIterator.hasNext()) {
        				Beacon beacon = beaconIterator.next();
        				//logBeaconData(beacon); old line to log data
        				// decide which beacon is the nearest
        				if(beacon.getRssi() > min_rssi) {
        					minBeacon = beacon;
        					min_rssi = beacon.getRssi();
        				}
        				
        				
        				beaconIDs = new ArrayList<Integer>();
        				
                		if(beacon.getId1().toString().contains("adfaff29-f929-20f9-9f30-90470085cafb")) {
                			room1 = 1;
                			rssi1 = beacon.getRssi() * (-1);
                		} else if(beacon.getId1().toString().contains("fe0239fe")) {
                			room2 = 2;
                			rssi2 = beacon.getRssi() * (-1);
                		}
                			
        			    rssiValues = new ArrayList<Integer>();
        			    rssiValues.add(-1 * beacon.getRssi());
        			    
        			}
        		}
        		
        		if(room1 != 0 && room2 != 0 && rssi1 != 0 && rssi2 != 0)
        			createFloatArray(room1, room2, rssi1, rssi2);
        		
                mHandler.sendMessage(Message.obtain());
        		
        		Log.v("Beaconwerte", "Beacon adfaff: " + room1 + " - " + rssi1 + "||" + "Beacon fe023: " + room2 + " - " + rssi2);
        		
        		Log.v("BeaconTest", "" + min_rssi);
        	}
        });


        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
        	// TODO - OK, what now then?
        }

	}

	/**
	 * Stop looking for beacons.
	 */
	private void stopScanning(Button scanButton) {
		scanButton.setText(MODE_STOPPED);
		try {
			beaconManager.stopRangingBeaconsInRegion(region);
		} catch (RemoteException e) {
				// TODO - OK, what now then?
		}
	}

	/**
	 * @return
	 */
	private Button getScanButton() {
		return (Button)getView().findViewById(R.id.radarScan);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() == R.id.radarScan) {
			toggleScanState();
		} else if (v instanceof RadarElement) {
			Log.v("IDs", ""+v.getId());
		}
			
	}
	
    private void createFloatArray(final float room1, final float room2, final float rssi1, final float rssi2) {
    	getActivity().runOnUiThread(new Runnable() {
    	    public void run() {
                scanValues = new float[][]{
                        {room1, rssi1},
                        {room2, rssi2},
                        };
    	    }
    	});
    }
    
}
