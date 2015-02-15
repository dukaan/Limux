package inf.uie.Limux.beacon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import inf.uie.Limux.R;
import inf.uie.Limux.activity.MainActivity;
import inf.uie.Limux.model.House;
import inf.uie.Limux.model.Profile;
import inf.uie.Limux.model.Room;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Adapted from original code written by D Young of Radius Networks.
 * @author dyoung, jodwyer
 *
 */
public class ScanActivity extends Activity implements BeaconConsumer {
    
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
    
    private BeaconManager beaconManager;
    private Region region; 
    private int eventNum = 1;
    
    // This StringBuffer will hold the scan data for any given scan.  
    private StringBuffer logString;
   
    // Preferences - will actually have a boolean value when loaded.
    private Boolean index;
    private Boolean uuid;
	private Boolean majorMinor;
	private Boolean rssi;
	private Boolean proximity;
	private Boolean power;
	private Boolean timestamp;
	private String scanInterval;
	
	// house variable (singleton)
	private House myHouse;
	
	// id counter to set unique id for profile buttons
	private int profileButtonId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.beacon_activity_scan);
		
		verifyBluetooth();
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		BeaconScannerApp app = (BeaconScannerApp)this.getApplication();
		beaconManager = app.getBeaconManager();
		//beaconManager.setForegroundScanPeriod(10);

		// Add parser for iBeacons;
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
		beaconManager.bind(this);
		
		region = new Region("myRangingUniqueId", null, null, null);

		// Initialize scan button.
		getScanButton().setText(MODE_STOPPED);
		
		myHouse = House.getInstance();
		
		// adding onChangeListener for TextView to show profiles of the nearest room
		TextView title = (TextView) ScanActivity.this.findViewById(R.id.roomTitle);
		title.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			// solution very cumbersome, new implementation needed, but now for testing purposes -> TODO
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.toString().equals("Wohnzimmer (yin)")) {
					for (Room room : myHouse.getRooms()) {
						if(room.getName().contains("Wohnzimmer")) {
							
							// remove all existing buttons before adding new ones
							for(int i=1;i<=profileButtonId;i++) {
								((RelativeLayout) findViewById(R.id.scanActivityLayout)).removeView(findViewById(i));
							}
							// reset button ids
							profileButtonId = 1;
							
							// iterate over all profiles of a room and add a button for each profile
							for(Profile profile : room.getProfiles()) {
								Button profileButton = new Button(ScanActivity.this);
								profileButton.setId(profileButtonId);
								RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								if(profileButtonId == 1){
									rl.addRule(RelativeLayout.BELOW, R.id.roomTitle);
								} else {
									rl.addRule(RelativeLayout.BELOW, profileButtonId - 1);
								}
								rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
								profileButton.setLayoutParams(rl);
								profileButton.setText(profile.getName());
								((RelativeLayout) findViewById(R.id.scanActivityLayout)).addView(profileButton);
								profileButtonId++;
							}
						}
					}
				} else {
					for (Room room : myHouse.getRooms()) {
						if(room.getName().contains("Schlafzimmer")) {
							
							// remove all existing buttons before adding new ones
							for(int i=1;i<=profileButtonId;i++) {
								((RelativeLayout) findViewById(R.id.scanActivityLayout)).removeView(findViewById(i));
							}	
							// reset button ids
							profileButtonId = 1;
							
							// iterate over all profiles of a room and add a button for each profile
							for(Profile profile : room.getProfiles()) {
								Button profileButton = new Button(ScanActivity.this);
								profileButton.setId(profileButtonId);
								RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								if(profileButtonId == 1){
									rl.addRule(RelativeLayout.BELOW, R.id.roomTitle);
								} else {
									rl.addRule(RelativeLayout.BELOW, profileButtonId - 1);
								}
								rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
								profileButton.setLayoutParams(rl);
								profileButton.setText(profile.getName());
								((RelativeLayout) findViewById(R.id.scanActivityLayout)).addView(profileButton);
								profileButtonId++;
							}
						}
					}
				}
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_beacon_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public void onBeaconServiceConnect() {}
    
    /**
     * 
     * @param view
     */
	public void onScanButtonClicked(View view) {
		toggleScanState();
	}
	
 	// Handle the user selecting "Settings" from the action bar.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    	case R.id.Settings:
	            // Show settings
	    		Intent api = new Intent(this, AppPreferenceActivity.class);
	            startActivityForResult(api, 0);
	            return true;
	    	case R.id.action_show_options:
	    		// launch options when actionbar button is clicked
	    		Intent options = new Intent(this, MainActivity.class);
	            startActivity(options);
	            return true;	    			    		
	        default:
	            return super.onOptionsItemSelected(item);
	     }
	 }
	
	/**
	 * Start and stop scanning, and toggle button label appropriately.
	 */
	private void toggleScanState() {
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
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);		
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
        			}
        		}
        		if(minBeacon != null) {
            		if(minBeacon.getId1().toString().contains("adfaff29-f929-20f9-9f30-90470085cafb")) {
                		logToTextView("Wohnzimmer (yin)");
            		} else {
            			logToTextView("Schlafzimmer (Duc)");
            		}
        		} 
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
		return (Button)findViewById(R.id.scanButton);
	}
	
    /**
     * @param iBeacon
     * not used right now, maybe later
     */
	private void logBeaconData(Beacon beacon) {

		StringBuffer scan = new StringBuffer();
		
		if (index.booleanValue()) {
			scan.append(eventNum++ + "");
		}				
		
		if (uuid.booleanValue()) {
			scan.append(" UUID: " + beacon.getId1());
		}		
		
		if (majorMinor.booleanValue()) {
			scan.append(" Maj. Mnr.: " + beacon.getId2() + "-" + beacon.getId3());
		}
		
		if (rssi.booleanValue()) {
			scan.append(" RSSI: " + beacon.getRssi());
		}
				
		if (proximity.booleanValue()) {
			scan.append(" Proximity: " + BeaconHelper.getProximityString(beacon.getDistance()));
		}
		
		if (power.booleanValue()) {
			scan.append(" Power: "+ beacon.getTxPower());
		}
		
		if (timestamp.booleanValue()) {
			scan.append(" Timestamp: " + BeaconHelper.getCurrentTimeStamp());
		}
	    
		//logToDisplay(scan.toString());
		scan.append("\n");
		logString.append(scan.toString());
		
	}
    

	/**
	 * line logging methods to change TextView contents
	 * */
    private void logToTextView(final String line) {
    	runOnUiThread(new Runnable() {
    	    public void run() {  
    	    	TextView title = (TextView) ScanActivity.this.findViewById(R.id.roomTitle);
    	    	title.setText(line);
    	    	setTitle(line);
    	    }
    	});
    }
    
    /**
     * verify if bluetooth is available and/or enabled, because bluetooth is required to run the app
     * */
 	private void verifyBluetooth() {

		try {
			if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Bluetooth not enabled");			
				builder.setMessage("Please enable bluetooth in settings and restart this application.");
				builder.setPositiveButton(android.R.string.ok, null);
				builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						finish();
			            System.exit(0);					
					}					
				});
				builder.show();
			}			
		}
		catch (RuntimeException e) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Bluetooth LE not available");			
			builder.setMessage("Sorry, this device does not support Bluetooth LE.");
			builder.setPositiveButton(android.R.string.ok, null);
			builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					finish();
		            System.exit(0);					
				}
				
			});
			builder.show();
			
		}
		
	}	  
    
}
