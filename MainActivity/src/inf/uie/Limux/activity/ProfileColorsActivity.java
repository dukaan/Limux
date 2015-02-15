package inf.uie.Limux.activity;

import inf.uie.Limux.R;
import inf.uie.Limux.R.id;
import inf.uie.Limux.R.layout;
import inf.uie.Limux.R.menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileColorsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_colors);
		setTitle("New Profile");
		
		// set title with name of clicked lamp
		Intent lastIntent = getIntent();
		String lampName = lastIntent.getStringExtra("lampName");
		TextView titleTextView = (TextView) findViewById(R.id.title);
		titleTextView.append("\n" + lampName.toUpperCase());
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
}
