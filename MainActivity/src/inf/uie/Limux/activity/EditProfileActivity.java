package inf.uie.Limux.activity;

import inf.uie.Limux.R;
import inf.uie.Limux.R.id;
import inf.uie.Limux.R.layout;
import inf.uie.Limux.R.menu;
import inf.uie.Limux.model.LampColor;
import inf.uie.Limux.model.House;
import inf.uie.Limux.model.Lamp;
import inf.uie.Limux.model.Profile;
import inf.uie.Limux.model.RGBLamp;
import inf.uie.Limux.model.Room;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditProfileActivity extends Activity {
	
	private House myHouse = House.getInstance();
	private Profile currentProfile = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		// set profile name in edit field
		Intent lastIntent = getIntent();
		String profileName = lastIntent.getStringExtra("profileName");
		EditText profileNameEdit = (EditText) findViewById(R.id.profileName);
		profileNameEdit.setText(profileName);
		
		currentProfile = myHouse.getProfileByName(profileName);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// refresh current profile, really needed? not sure
		EditText profileNameEdit = (EditText) findViewById(R.id.profileName);
		currentProfile = myHouse.getProfileByName(profileNameEdit.getText().toString());
		
		showRoomsWithLamps();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
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
	
	// ------- CUSTOM METHODS --------
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
					
					// set backgroundcolor of button according to lamp color when lamp is active
					if(checkIfActiveLamp(lamp)) {
						if(lamp instanceof RGBLamp) {
							LampColor lampColor = currentProfile.getLampWithColorMap().get(lamp);
							int color = Color.argb(255, lampColor.getRed(), lampColor.getGreen(), lampColor.getBlue());
							lampButton.setBackgroundColor(color);
						} else {
							lampButton.setBackgroundColor(Color.WHITE);
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
					
					// set backgroundcolor of button according to lamp color when lamp is active
					if(checkIfActiveLamp(lamp)) {
						if(lamp instanceof RGBLamp) {
							LampColor lampColor = currentProfile.getLampWithColorMap().get(lamp);
							int color = Color.argb(255, lampColor.getRed(), lampColor.getGreen(), lampColor.getBlue());
							lampButton.setBackgroundColor(color);
						} else {
							lampButton.setBackgroundColor(Color.WHITE);
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
		
		// save changed name
		EditText profileName = (EditText) findViewById(R.id.profileName); 
		currentProfile.setName( profileName.getText().toString() );
		
		// add profile to all rooms which contains an active lamp
		for(Lamp lamp : currentProfile.getActiveLamps()) {
				lamp.getRoom().addProfile(currentProfile);
		}
		
		// close activity and return to profilefragment
		finish();
		// TODO
	}
	
	OnClickListener lampClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent profileColorsActivity = new Intent(EditProfileActivity.this, ProfileColorsActivity.class);
			profileColorsActivity.putExtra("lampName", ((Button) v).getText().toString());
			profileColorsActivity.putExtra("profileName", currentProfile.getName());
			startActivity(profileColorsActivity);
		}
	};
}
