package com.example.amazing;

import com.android.example.uis.R;
import com.example.util.StatusTracker;
import com.example.util.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment2 extends Fragment {
    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mActivityName = getString(R.string.activity_a);
        mStatusView = (TextView)this.getActivity().findViewById(R.id.status_view_a);
        mStatusAllView = (TextView)this.getActivity().findViewById(R.id.status_view_all_a);
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_create));
        Utils.printStatus(mStatusView, mStatusAllView);
		return inflateAndSetupView(inflater, container, savedInstanceState, R.layout.fragment2);	
	}
	
	private View inflateAndSetupView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState, int layoutResourceId) {
		View layout = inflater.inflate(layoutResourceId, container, false);
		
		return layout;
	}
    @Override
	public void onStart() {
        super.onStart();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_start));
        Utils.printStatus(mStatusView, mStatusAllView);
    }


    @Override
    public void onResume() {
        super.onResume();
        
        SharedPreferences sharedPref = this.getActivity().getPreferences(Activity.MODE_PRIVATE);
        int defaultValue = 0;
        
        long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
        
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_resume));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    public void onPause() {
        super.onPause();
        
        SharedPreferences sharedPref = this.getActivity().getPreferences(Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        
        editor.putInt(getString(R.string.saved_high_score), 100);
        editor.commit();
        
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_pause));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    public void onStop() {
        super.onStop();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_stop));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_destroy));
        mStatusTracker.clear();
    }
	
}
