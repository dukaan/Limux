package inf.uie.Limux.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Profile;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import inf.uie.Limux.R;
import inf.uie.Limux.beacon.ScanActivity;
import inf.uie.Limux.model.House;

public class ShowProfileFragment extends Fragment {

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
		
		for(inf.uie.Limux.model.Profile profile : myHouse.getAllProfiles()) {
			Button profileButton = new Button(getActivity());
			RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			profileButton.setLayoutParams(rl);
			profileButton.setText(profile.getName());
			((GridLayout) getView().findViewById(R.id.buttonGrid)).addView(profileButton);
		}
		
	}

}
