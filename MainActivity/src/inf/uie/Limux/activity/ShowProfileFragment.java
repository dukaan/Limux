package inf.uie.Limux.activity;

import java.util.ArrayList;

import android.R.integer;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.provider.ContactsContract.Profile;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import inf.uie.Limux.model.LampColor;

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
	}
	
	public void onResume() {
		super.onResume();
		
		// remove old buttons before refreshing
		((GridLayout) getView().findViewById(R.id.buttonGrid)).removeAllViews();
		
		// add a button for each existing profile in the house
		for(inf.uie.Limux.model.Profile profile : myHouse.getAllProfiles()) {
			Button profileButton = new Button(getActivity());
			LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(250, 250);
			profileButton.setLayoutParams(rl);
			profileButton.setText(profile.getName());
			profileButton.setTextSize(10.f);
			profileButton.setTextColor(Color.WHITE);
			
			profileButton.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
			//GradientDrawable gd = (GradientDrawable) profileButton.getBackground();
			
			ArrayList<Integer> colorList = new ArrayList<Integer>();
			for(LampColor color : profile.getUsedColors()) {
				colorList.add(Color.argb(255, color.getRed(), color.getGreen(), color.getBlue()));
			}
			
			int[] colorsInt = new int[colorList.size()];
			

			for(int i = 0; i<colorList.size(); i++) {
					colorsInt[i] = colorList.get(i);
					Log.i("Colors: ", profile.getName() + ": " + colorsInt[i]);
			}
			
			

			if(colorsInt.length > 1) {
				GradientDrawable gd = new GradientDrawable(Orientation.LEFT_RIGHT, colorsInt);
				gd.setStroke(6, Color.WHITE);
				gd.setShape(GradientDrawable.OVAL);
				profileButton.setBackground(gd);
			} else {
				((GradientDrawable) profileButton.getBackground()).setColor(colorsInt[0]);
			}
			
			//gd.setColor(colorsInt[0]);
			
			profileButton.setOnClickListener(profileButtonClickListener);
			((GridLayout) getView().findViewById(R.id.buttonGrid)).addView(profileButton);
		}
	};
	
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
