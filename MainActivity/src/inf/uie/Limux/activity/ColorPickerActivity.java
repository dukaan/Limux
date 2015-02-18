package inf.uie.Limux.activity;

import inf.uie.Limux.activity.ColorPickerView;
import inf.uie.Limux.activity.MainActivity;
import inf.uie.Limux.activity.ColorPickerView.onColorChangedListener;
import inf.uie.Limux.beacon.ScanActivity;
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
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ColorPickerActivity extends Activity {
	
	private House myHouse = House.getInstance();
	private Profile currentProfile = null;
	private Lamp currentLamp = null;
	private LampColor chosenColor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_picker);
		setTitle("Color Picker");
		
		Intent lastIntent = getIntent();
		currentProfile = myHouse.getProfileByName(lastIntent.getStringExtra("currentProfile"));
		currentLamp = myHouse.getLampByName(lastIntent.getStringExtra("currentLamp"));
		EditText colorNameEdit = (EditText) findViewById(R.id.colorNameEditText);
		// change edit text underline color
		colorNameEdit.getBackground().setColorFilter(Color.argb(255, 89, 145, 180), Mode.SRC_ATOP);
		
		
        ColorPickerView colorPickerView = (ColorPickerView) findViewById(R.id.cpv);
        colorPickerView.setColorChangedListener(new onColorChangedListener() {
			
			@Override
			public void colorChanged(int red, int blue, int green) {
				setNewBackgroundColor(Color.argb(255, red, green, blue));
				chosenColor = new LampColor(red, green, blue);
			}
		});
		
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
		EditText colorName = (EditText) findViewById(R.id.colorNameEditText);
		
		LampColor color = new LampColor(colorName.getText().toString(), chosenColor.getRed(), chosenColor.getGreen(), chosenColor.getBlue());
		
		if(currentLamp instanceof RGBLamp) {
			currentProfile.addColorForLamp( (RGBLamp) currentLamp, color);
		} else {
			//TODO DimLamp and BinLamp
		}

		finish();
	}
	
    private void setNewBackgroundColor(final int color) {
    	runOnUiThread(new Runnable() {
    	    public void run() {  
    	    	//getWindow().getDecorView().setBackgroundColor(color);
    	    	//((TextView) findViewById(R.id.title)).setTextColor(color);
    	    	TextView hexText = (TextView) findViewById(R.id.hexValue);
    	    	hexText.setTextColor(color);
    	    	if(chosenColor != null)
    	    	hexText.setText("#" + Integer.toHexString(chosenColor.getRed()) + Integer.toHexString(chosenColor.getGreen()) + Integer.toHexString(chosenColor.getBlue()));
    	    }
    	});
    }
}
