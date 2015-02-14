package inf.uie.Limux.adapter;

import inf.uie.Limux.activity.ShowProfileFragment;
import inf.uie.Limux.activity.ShowNotificationsFragment;
import inf.uie.Limux.activity.ShowRoomsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new ShowRoomsFragment();
		case 1:
			// Games fragment activity
			return new ShowProfileFragment();
		case 2:
			// Movies fragment activity
			return new ShowNotificationsFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
