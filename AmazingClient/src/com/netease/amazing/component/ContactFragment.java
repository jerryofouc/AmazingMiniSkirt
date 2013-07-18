package com.netease.amazing.component;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.amazing.R;

public class ContactFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ScrollView view = (ScrollView)inflateAndSetupView(inflater, container, savedInstanceState, R.layout.contact_info_list);
		
		
		LinearLayout teacher = (LinearLayout)view.findViewById(R.id.teacher);
		getUserPhoneNumByCategory(5,teacher);
			
		LinearLayout classmate = (LinearLayout)view.findViewById(R.id.classmate);
		getUserPhoneNumByCategory(5,classmate);
				
		LinearLayout friend = (LinearLayout)view.findViewById(R.id.friend);
		getUserPhoneNumByCategory(5,friend);
		
		
		return 	view;	
	}
	
	private View inflateAndSetupView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState, int layoutResourceId) {
		View layout = inflater.inflate(layoutResourceId, container, false);
		return layout;
	}
	
	private void getUserPhoneNumByCategory(final int size,final LinearLayout category){
		LayoutParams blackLineLayout = new LayoutParams(LayoutParams.FILL_PARENT, 
                1);
		LayoutParams textViewLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT);
		for(int i=0;i<size;++i){
			LinearLayout l = new LinearLayout(getActivity());
			//l.setId(0x8f200001+1);
			l.setOrientation(0);
			l.setLayoutParams(textViewLayout);
			category.addView(l);
			
			
			ImageView img =new ImageView(getActivity());
			//img.setId(0x8f000001+i);
			img.setImageResource(R.drawable.ic_launcher);
			img.setLayoutParams(textViewLayout);
			l.addView(img);
						
			
			TextView nameView = new TextView(getActivity());
			//nameView.setId(0x8f100001+i);
			nameView.setText("teacher name"+i);
			nameView.setLayoutParams(textViewLayout);
			l.addView(nameView);	
			
			
			View blackLine = new View(getActivity());
			blackLine.setBackgroundColor(getResources().
					getColor(R.color.dark_green));
			blackLine.setLayoutParams(blackLineLayout);
			category.addView(blackLine);
		}
	}
	
}
