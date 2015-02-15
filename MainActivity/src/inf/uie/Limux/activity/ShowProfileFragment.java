package inf.uie.Limux.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Profile;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import inf.uie.Limux.R;
import inf.uie.Limux.beacon.ScanActivity;
import inf.uie.Limux.model.House;

public class ShowProfileFragment extends Fragment {

	// house variable (singleton)
	private House myHouse;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_show_profils, container, false);
		
		myHouse = House.getInstance();
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		// add a button for each existing profile in the house
		for(inf.uie.Limux.model.Profile profile : myHouse.getAllProfiles()) {
			Button profileButton = new Button(getActivity());
			LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(350, 150);
			profileButton.setLayoutParams(rl);
			profileButton.setText(profile.getName());
			profileButton.setTextSize(10.f);
			profileButton.setOnClickListener(profileButtonClickListener);
			((GridLayout) getView().findViewById(R.id.buttonGrid)).addView(profileButton);
		}
		
	}
	
	// onClickListener for every profile button which opens up "EditProfileActivity"
	OnClickListener profileButtonClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent editProfileActivity = new Intent(getActivity(), EditProfileActivity.class);
			editProfileActivity.putExtra("profileName", ((Button) v).getText().toString());
			startActivity(editProfileActivity);
		}
	};

}
