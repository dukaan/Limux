package inf.uie.Limux.beacon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import inf.uie.Limux.R;
import inf.uie.Limux.activity.MainActivity;
import inf.uie.Limux.model.House;
import inf.uie.Limux.model.LampColor;
import inf.uie.Limux.model.Profile;
import inf.uie.Limux.model.Room;
import inf.uie.Limux.bluetooth.Bluetooth;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Adapted from original code written by D Young of Radius Networks.
 * @author
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
	private Bluetooth bluetooth;
	private Profile currentProfile = null;
	
	private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.beacon_activity_scan);

		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(0xFF275B7A));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(0xFF275B7A));

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
		getScanButton().setAlpha(0.3f);

		myHouse = House.getInstance();
		
		
		try {
			bluetooth = Bluetooth.getInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextView placeholder = new TextView(this);
		placeholder.setText("No profiles nearby.");
		placeholder.setTextColor(Color.WHITE);
		((GridLayout) findViewById(R.id.profilesGrid)).addView(placeholder);

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
							((GridLayout) findViewById(R.id.profilesGrid)).removeAllViews();

							// iterate over all profiles of a room and add a button for each profile
							for(Profile profile : room.getProfiles()) {
								Button profileButton = new Button(ScanActivity.this);
								RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(600, 600);
								profileButton.setLayoutParams(rl);
								profileButton.setText(profile.getName());
								
								profileButton.setTextColor(Color.WHITE);
								
								profileButton.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
								//GradientDrawable gd = (GradientDrawable) profileButton.getBackground();
								
								/*
								// create gradient background for profile buttons
								ArrayList<Integer> colorList = new ArrayList<Integer>();
								for(LampColor color : profile.getUsedColors()) {
									colorList.add(Color.argb(255, color.getRed(), color.getGreen(), color.getBlue()));
								}
								
								int[] colorsInt = new int[colorList.size()];
								
								for(int i = 0; i<colorList.size(); i++) {
										colorsInt[i] = colorList.get(i);
										//Log.i("Colors: ", profile.getName() + ": " + colorsInt[i]);
								}

								if(colorsInt.length > 1) {
									GradientDrawable gd = new GradientDrawable(Orientation.LEFT_RIGHT, colorsInt);
									gd.setStroke(6, Color.WHITE);
									gd.setShape(GradientDrawable.OVAL);
									profileButton.setBackground(gd);
								} else {
									((GradientDrawable) profileButton.getBackground()).setColor(colorsInt[0]);
								}*/
								
								// set backgroundcolor to rgblamp color
								if (profile.getActiveLamps().contains(myHouse.getLampByName("Lampe1"))) {
									LampColor lcolor = profile.getLampWithColorMap().get(myHouse.getLampByName("Lampe1"));
									((GradientDrawable) profileButton.getBackground()).setColor(Color.argb(255, lcolor.getRed(), lcolor.getGreen(), lcolor.getBlue()));
								} else {
									((GradientDrawable) profileButton.getBackground()).setColor(Color.WHITE);
									profileButton.setTextColor(Color.BLACK);
								}
								
								profileButton.setOnClickListener(profileButtonClickListener);
								((GridLayout) findViewById(R.id.profilesGrid)).addView(profileButton);
							}
						}
					}
				} else {
					for (Room room : myHouse.getRooms()) {
						if(room.getName().contains("Schlafzimmer")) {
							// remove all existing buttons before adding new ones
							((GridLayout) findViewById(R.id.profilesGrid)).removeAllViews();

							// iterate over all profiles of a room and add a button for each profile
							for(Profile profile : room.getProfiles()) {
								Button profileButton = new Button(ScanActivity.this);
								RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(600, 600);
								profileButton.setLayoutParams(rl);
								profileButton.setText(profile.getName());
								
								profileButton.setTextColor(Color.WHITE);
								
								profileButton.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
								//GradientDrawable gd = (GradientDrawable) profileButton.getBackground();
								
								/*
								// create gradient background for profile buttons
								ArrayList<Integer> colorList = new ArrayList<Integer>();
								for(LampColor color : profile.getUsedColors()) {
									colorList.add(Color.argb(255, color.getRed(), color.getGreen(), color.getBlue()));
								}
								
								int[] colorsInt = new int[colorList.size()];
								
								for(int i = 0; i<colorList.size(); i++) {
										colorsInt[i] = colorList.get(i);
										//Log.i("Colors: ", profile.getName() + ": " + colorsInt[i]);
								}

								if(colorsInt.length > 1) {
									GradientDrawable gd = new GradientDrawable(Orientation.LEFT_RIGHT, colorsInt);
									gd.setStroke(6, Color.WHITE);
									gd.setShape(GradientDrawable.OVAL);
									profileButton.setBackground(gd);
								} else {
									((GradientDrawable) profileButton.getBackground()).setColor(colorsInt[0]);
								}*/
								
								// set backgroundcolor to rgblamp color
								if (profile.getActiveLamps().contains(myHouse.getLampByName("Lampe1"))) {
									LampColor lcolor = profile.getLampWithColorMap().get(myHouse.getLampByName("Lampe1"));
									((GradientDrawable) profileButton.getBackground()).setColor(Color.argb(255, lcolor.getRed(), lcolor.getGreen(), lcolor.getBlue()));
								} else {
									((GradientDrawable) profileButton.getBackground()).setColor(Color.WHITE);
									profileButton.setTextColor(Color.BLACK);
								}
								
								profileButton.setOnClickListener(profileButtonClickListener);
								((GridLayout) findViewById(R.id.profilesGrid)).addView(profileButton);
							}
						}
					}
				}
			}
		});
		
		ImageButton offButton = (ImageButton) findViewById(R.id.offButton);
		offButton.setOnClickListener(closeButtonClickListener);
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

	// onClickListener for every profile button
	OnClickListener profileButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String profileName = ((Button) v).getText().toString();

			currentProfile = myHouse.getProfileByName(profileName);
			int lamp = currentProfile.getActiveLamps().iterator().next().getActive();
			Log.v("BT", "Lampe: " + lamp);
			Log.v("BT", "NET: " + currentProfile.getActiveLamps().iterator().next());
			if (lamp == 0) {
				currentProfile.enable();
			} else {
				currentProfile.disable();
			}
		}
	};

	// onClickListener for every profile button
		OnClickListener closeButtonClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("BT", "Scan: " + "off");
				House.getInstance().allLampsOff();
			}
		};

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
                		logToText(minBeacon.getId1().toString());
            		} else if(minBeacon.getId1().toString().contains("fe0239fe")) {
            			logToTextView("Schlafzimmer (Duc)");
            			logToText(minBeacon.getId1().toString());
            		} else {
            			logToTextView("No room nearby.");
            			logToText("No room nearby");
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
		return (Button)findViewById(R.id.radarScan);
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

    private void logToText(final String line) {
    	runOnUiThread(new Runnable() {
    	    public void run() {
    	    	TextView title = (TextView) ScanActivity.this.findViewById(R.id.infoText);
    	    	title.setText(line);
    	    }
    	});
    }

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
