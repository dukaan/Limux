package inf.uie.Limux.beacon;

import inf.uie.Limux.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class BeaconPoCPreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	addPreferencesFromResource(R.xml.preferences);
    }

}
