package inf.uie.Limux.activity;

import inf.uie.Limux.R;
import inf.uie.Limux.R.id;
import inf.uie.Limux.R.layout;
import inf.uie.Limux.R.menu;
import inf.uie.Limux.model.House;
import inf.uie.Limux.model.Lamp;
import inf.uie.Limux.model.Profile;
import inf.uie.Limux.model.Room;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewProfileActivity extends Activity {
	
	private House myHouse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_profile);
		setTitle("New Profile");
		
		myHouse = House.getInstance();
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
					((GridLayout) findViewById(R.id.bedRoomGrid)).addView(lampButton);
				}
			} else {
				// do nothing
			}
		}
	}
	
	public void doneButtonClick(View v) {
		// TODO
	}
	
	OnClickListener lampClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent profileColorsActivity = new Intent(NewProfileActivity.this, ProfileColorsActivity.class);
			profileColorsActivity.putExtra("lampName", ((Button) v).getText().toString());
			startActivity(profileColorsActivity);
		}
	};
	
 }
