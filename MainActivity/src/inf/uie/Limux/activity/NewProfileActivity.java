package inf.uie.Limux.activity;

import inf.uie.Limux.DimPickerActivity;
import inf.uie.Limux.R;
import inf.uie.Limux.R.id;
import inf.uie.Limux.R.layout;
import inf.uie.Limux.R.menu;
import inf.uie.Limux.model.BinLamp;
import inf.uie.Limux.model.DimLamp;
import inf.uie.Limux.model.House;
import inf.uie.Limux.model.Lamp;
import inf.uie.Limux.model.LampColor;
import inf.uie.Limux.model.Profile;
import inf.uie.Limux.model.RGBLamp;
import inf.uie.Limux.model.Room;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewProfileActivity extends Activity {
	
	private House myHouse;
	private Profile currentProfile;
	
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_profile);
		setTitle("New Profile");
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(0xFF275B7A));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(0xFF275B7A));
		
		EditText profileNameEdit = (EditText) findViewById(R.id.profileName);
		// change edit text underline color
		profileNameEdit.getBackground().setColorFilter(Color.argb(255, 89, 145, 180), Mode.SRC_ATOP);
		
		myHouse = House.getInstance();
		currentProfile = new Profile("");
		
		// prevent keyboard from opening automatically
		getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showRoomsWithLamps();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// --------- CUSTOM METHODS ---------
	
	/**
	 * rooms are hard coded for now (initialized in House.class)
	 */
	private void showRoomsWithLamps() {
		// TODO Auto-generated method stub
		
		// reset grid before adding again
		((GridLayout) findViewById(R.id.livingRoomGrid)).removeAllViews();
		((GridLayout) findViewById(R.id.bedRoomGrid)).removeAllViews();
		
		// show all lamps as buttons sorted by room
		for(Room room : myHouse.getRooms()) {
			if(room.getName() == "Wohnzimmer"){
				TextView title = (TextView) findViewById(R.id.firstRoom);
				title.setText(room.getName().toUpperCase());
				for(Lamp lamp : room.getLamps()) {
					Button lampButton = new Button(this);
					LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(250, 250);
					lampButton.setLayoutParams(rl);
					lampButton.setText(lamp.getName());
					lampButton.setTextSize(10.f);
					lampButton.setOnClickListener(lampClickListener);
					
					// coloring
					lampButton.setTextColor(Color.argb(255, 39, 72, 118));
					lampButton.setBackgroundColor(Color.argb(30, 255, 255, 255));
					
					// put label to the bottom
					lampButton.setPadding(0, 175, 0, 0);
					
					// set backgroundcolor of button according to lamp color when lamp is active
					if(checkIfActiveLamp(lamp)) {
						if(lamp.getName() == "Lampe1") {
							LampColor lampColor = currentProfile.getLampWithColorMap().get(lamp);
							int color = Color.argb(255, lampColor.getRed(), lampColor.getGreen(), lampColor.getBlue());
							lampButton.setBackgroundColor(color);
							lampButton.setTextColor(Color.argb(255, 255, 255, 255));
						} else {
							lampButton.setBackgroundColor(Color.WHITE);
							lampButton.setTextColor(Color.argb(255, 39, 72, 118));
						}
					}
					
					
					((GridLayout) findViewById(R.id.livingRoomGrid)).addView(lampButton);
				}
			} else if(room.getName() == "Schlafzimmer") {
				TextView title = (TextView) findViewById(R.id.secondRoom);
				title.setText(room.getName().toUpperCase());
				for(Lamp lamp : room.getLamps()) {
					Button lampButton = new Button(this);
					LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(250, 250);
					lampButton.setLayoutParams(rl);
					lampButton.setText(lamp.getName());
					lampButton.setTextSize(10.f);
					lampButton.setOnClickListener(lampClickListener);
					
					// coloring
					lampButton.setTextColor(Color.argb(255, 39, 72, 118));
					lampButton.setBackgroundColor(Color.argb(30, 255, 255, 255));
					
					// put label to the bottom
					lampButton.setPadding(0, 175, 0, 0);
					
					// set backgroundcolor of button according to lamp color when lamp is active
					if(checkIfActiveLamp(lamp)) {
						if(lamp.getName() == "Lampe1") {
							LampColor lampColor = currentProfile.getLampWithColorMap().get(lamp);
							int color = Color.argb(255, lampColor.getRed(), lampColor.getGreen(), lampColor.getBlue());
							lampButton.setBackgroundColor(color);
							lampButton.setTextColor(Color.argb(255, 255, 255, 255));
						} else {
							lampButton.setBackgroundColor(Color.WHITE);
							lampButton.setTextColor(Color.argb(255, 39, 72, 118));
						}
					}
					
					((GridLayout) findViewById(R.id.bedRoomGrid)).addView(lampButton);
				}
			} else {
				// do nothing
			}
		}
	}
	
	// checks if lamp is already active
	private boolean checkIfActiveLamp(Lamp lamp) {
		if(currentProfile != null) {
			if(currentProfile.getActiveLamps().contains(lamp)) return true;
		}
		return false;
	}
	
	public void doneButtonClick(View v) {
		// TODO
		// save changed name
		EditText profileName = (EditText) findViewById(R.id.profileName); 
		currentProfile.setName( profileName.getText().toString() );
		
		// add profile to all rooms which contains an active lamp
		for(Room room : currentProfile.getRooms()) {
			room.removeProfile(currentProfile);
		}
		
		for(Lamp lamp : currentProfile.getActiveLamps()) {
				lamp.getRoom().addProfile(currentProfile);
		}
		
		// close activity and return to profilefragment
		finish();
	}
	
	public void deleteButtonClick(View v) {
		myHouse.removeProfile(currentProfile);
		finish();
	}
	
	// back button behavior overwritten for new profile activity
	public void onBackPressed() {
		myHouse.removeProfile(currentProfile);
		finish();
	};
	
	OnClickListener lampClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			// add empty profile to room
		
			Lamp lamp = myHouse.getLampByName(((Button) v).getText().toString());
			lamp.getRoom().addProfile(currentProfile);
			
			if(lamp.getName() == "Lampe1") {
				Intent profileColorsActivity = new Intent(NewProfileActivity.this, ProfileColorsActivity.class);
				profileColorsActivity.putExtra("lampName", ((Button) v).getText().toString());
				profileColorsActivity.putExtra("profileName", currentProfile.getName());
				startActivity(profileColorsActivity);
			} else {
				Lamp clickedLamp = myHouse.getLampByName(((Button) v).getText().toString());
				if(currentProfile.getActiveLamps().contains(clickedLamp)) {
					currentProfile.removeColorOfLamp( (RGBLamp) clickedLamp); 
					showRoomsWithLamps();
				} else {
					currentProfile.addColorForLamp( (RGBLamp) clickedLamp , new LampColor(255, 0, 0));
					showRoomsWithLamps();
				}
			} 
		}
	};
	
 }
