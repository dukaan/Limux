package inf.uie.Limux.activity;

import inf.uie.Limux.R;
import inf.uie.Limux.R.id;
import inf.uie.Limux.R.layout;
import inf.uie.Limux.R.menu;
import inf.uie.Limux.model.House;
import inf.uie.Limux.model.Lamp;
import inf.uie.Limux.model.LampColor;
import inf.uie.Limux.model.Profile;
import inf.uie.Limux.model.RGBLamp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ColorPickerActivity extends Activity {
	
	private House myHouse = House.getInstance();
	private Profile currentProfile = null;
	private Lamp currentLamp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_picker);
		
		Intent lastIntent = getIntent();
		currentProfile = myHouse.getProfileByName(lastIntent.getStringExtra("currentProfile"));
		currentLamp = myHouse.getLampByName(lastIntent.getStringExtra("currentLamp"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color_picker, menu);
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
	
	public void doneButtonClick(View v) {
		EditText red = (EditText) findViewById(R.id.redEditText);
		EditText green = (EditText) findViewById(R.id.greenEditText);
		EditText blue = (EditText) findViewById(R.id.blueEditText);
		EditText colorName = (EditText) findViewById(R.id.colorNameEditText);
		
		// create new color depending on picked color (rgb values)
		LampColor color = new LampColor(colorName.getText().toString(), Integer.parseInt(red.getText().toString()), Integer.parseInt(green.getText().toString()), Integer.parseInt(blue.getText().toString()));
		
		// set color of lamp
		currentProfile.addColorForLamp( (RGBLamp) currentLamp, color);
		finish();
	}
}
