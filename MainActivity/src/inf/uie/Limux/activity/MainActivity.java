package inf.uie.Limux.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import inf.uie.Limux.beacon.ScanActivity;
import inf.uie.Limux.bluetooth.Bluetooth;
import inf.uie.Limux.R;
import inf.uie.Limux.adapter.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Rooms", "Profiles", "Notifications" };

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF326380));
        actionBar.setTitle("  " + tabs[0]);
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(0xFF326380));
		actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
                //actionBar.setTitle("  " + actionBar.getSelectedTab().getText());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		actionBar.setTitle("Options");
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                // open Bluetooth Einstellungen
                Intent i = new Intent(MainActivity.this, Bluetooth.class);
                startActivity(i);
                return true;
            case R.id.beacon_settings:
                // open beacon Einstellungen
                Intent beas = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(beas);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	// ---------- CUSTOM CLICKHANDLERS -----------
	
	/**
	 * click handler when new profile button in profile fragment is clicked
	 */
	public void newProfileButtonClick(View view) {
		Intent newProfileActivity = new Intent(this, NewProfileActivity.class);
		startActivity(newProfileActivity);
	}
}
