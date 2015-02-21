package inf.uie.Limux;

import inf.uie.Limux.model.DimLamp;
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

public class DimPickerActivity extends Activity {
	
	private House myHouse = House.getInstance();
	private Profile currentProfile = null;
	private Lamp currentLamp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dim_picker);
		
		Intent lastIntent = getIntent();
		currentProfile = myHouse.getProfileByName(lastIntent.getStringExtra("profileName"));
		currentLamp = myHouse.getLampByName(lastIntent.getStringExtra("lampName"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dim_picker, menu);
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
		//((DimLamp) currentLamp).setBrightness(Integer.parseInt(((EditText) findViewById(R.id.dimLevelEdit) ).getText().toString()));
		if(currentLamp != null) {
			EditText dimValue = (EditText) findViewById(R.id.dimLevelEdit);
			int dim = Integer.parseInt(dimValue.getText().toString());
			currentProfile.addColorForLamp( (RGBLamp) currentLamp, new LampColor(dim, 0, 0));
			finish();
		}
		
	}
}
