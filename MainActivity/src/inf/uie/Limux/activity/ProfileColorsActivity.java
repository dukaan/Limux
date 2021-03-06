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
import android.R.bool;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileColorsActivity extends Activity {
	
	private House myHouse = House.getInstance();
	private Profile currentProfile = null;
	private Lamp currentLamp = null;
	private LampColor chosenColor = null;

	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_colors);
		setTitle("Profile Colors");
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(0xFF275B7A));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(0xFF275B7A));
		
		// set title with name of clicked lamp
		Intent lastIntent = getIntent();
		
		String lampName = lastIntent.getStringExtra("lampName");
		TextView titleTextView = (TextView) findViewById(R.id.title);
		titleTextView.append("\n" + lampName);
		currentLamp = myHouse.getLampByName(lampName);
		
		String profileName = lastIntent.getStringExtra("profileName");
		currentProfile = myHouse.getProfileByName(profileName);
		
		if(currentProfile != null) {
			if (true) {
				for (LampColor lampColor : LampColor.colorPresets) {
					Button colorButton = new Button(this);
					LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(175, 175);
					colorButton.setLayoutParams(rl);
					colorButton.setText(lampColor.getName());
					colorButton.setTextSize(10.f);
					colorButton.setTextColor(Color.WHITE);
					currentProfile.getUsedColors().add(lampColor);
					
					int color = Color.argb(255, lampColor.getRed(), lampColor.getGreen(), lampColor.getBlue());
					colorButton.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
					colorButton.getBackground().setAlpha(128);
					GradientDrawable sd = (GradientDrawable) colorButton.getBackground();
					sd.setColor(color);
					
					//colorButton.getBackground().setColorFilter(color, Mode.MULTIPLY);
					colorButton.setOnClickListener(colorButtonOnClickListener);
					((GridLayout) findViewById(R.id.colorGrid)).addView(colorButton);
				}
			} else {
				TextView infoText = new TextView(this);
				LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				infoText.setLayoutParams(rl);
				infoText.setText("No Colors used yet.");
				((GridLayout) findViewById(R.id.colorGrid)).addView(infoText);
			}
		} else {
			TextView infoText = new TextView(this);
			LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			infoText.setLayoutParams(rl);
			infoText.setText("No Colors used yet.");
			((GridLayout) findViewById(R.id.colorGrid)).addView(infoText);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_colors, menu);
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
		if(chosenColor != null) {
			if(currentLamp instanceof RGBLamp) {
				currentProfile.addColorForLamp( (RGBLamp) currentLamp, chosenColor);
			}
		} else {
			if(currentLamp instanceof RGBLamp) {
				currentProfile.removeColorOfLamp( (RGBLamp) currentLamp);
			}
		}
			
		finish();
	}
	
	public void addNewColorClick(View v) {
		Intent colorPickerActivity = new Intent(this, ColorPickerActivity.class);
		colorPickerActivity.putExtra("currentProfile", currentProfile.getName());
		colorPickerActivity.putExtra("currentLamp", currentLamp.getName());
		startActivity(colorPickerActivity);
		finish();
	}
	
	OnClickListener colorButtonOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			// make non selected buttons more transparent
			GridLayout colorGrid = ((GridLayout) findViewById(R.id.colorGrid));
			for(int i=0; i < colorGrid.getChildCount(); i++) {
				Button cButton = (Button) colorGrid.getChildAt(i);
				cButton.getBackground().setAlpha(128);
			}
			
			chosenColor = currentProfile.getColorByName( ((Button) v).getText());
			((Button) v).getBackground().setAlpha(255);
		}
	};
}
