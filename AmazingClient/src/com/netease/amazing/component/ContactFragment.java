package com.netease.amazing.component;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.amazing.R;
import com.netease.amazing.activity.ContactInfoActivity;
import com.netease.amazing.dbhandler.ContactDataHandler;
import com.netease.amazing.pojo.Contact;
import com.netease.amazing.util.InitImageView;
/**
 * 
 * @author Huang Xiao Jun
 * Description:
 *   显示当前登录用户的通讯录列表
 *
 */
public class ContactFragment extends Fragment {
	private List<Contact> contactList = new ArrayList<Contact>();
	private ProgressDialog proDialog;
	ScrollView view;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			LinearLayout teacherLayout = (LinearLayout)view.findViewById(R.id.contact_teacher);
			LinearLayout classmateLayout = (LinearLayout)view.findViewById(R.id.contact_classmate);	
			LinearLayout friendLayout = (LinearLayout)view.findViewById(R.id.contact_friend);
			setContactInfoByCategory(contactList,teacherLayout,classmateLayout,friendLayout);
			// 关闭进度窗口  
            if (proDialog != null) {  
            	proDialog.dismiss();  
            }
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ScrollView)inflateAndSetupView(inflater, container, savedInstanceState, R.layout.contact_info_list);
		new GetContactListThread(getActivity()).start();	
		proDialog = ProgressDialog.show(getActivity(), "连接中..",
				"连接中..请稍后....", true, true);
		return 	view;
	}
	
	
	private View inflateAndSetupView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState, int layoutResourceId) {
		View layout = inflater.inflate(layoutResourceId, container, false);
		return layout;
	}
	
	/**
	 * 设置相应联系人的信息
	 * @param i 设置当前联系人在contactList中的位置，便于activity跳转时传递数据
	 * @param contact
	 * @param contactLayout
	 * @param blackLineLayout
	 * @param textViewLayout
	 */
	private void setContactLayout(int i, 
			Contact contact, 
			LinearLayout contactLayout,
			LinearLayout.LayoutParams linearLayout,
			LinearLayout.LayoutParams blackLineLayout,
			LinearLayout.LayoutParams textViewLayout){
		LinearLayout l = new LinearLayout(getActivity());
		l.setOrientation(0);
		l.setLayoutParams(linearLayout);
		
		Resources res = getResources();
		float value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, res.getDisplayMetrics());
		l.setPadding((int)value, (int)value, (int)value, (int)value);
		
		ImageView img =new ImageView(getActivity());
		value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, res.getDisplayMetrics());
		img.setLayoutParams(new LayoutParams((int)value,(int)value));
		new InitImageView(img).execute(contact.getImgDir());
		l.addView(img);
		
		TextView nameView = new TextView(getActivity());
		nameView.setText(contact.getName());
		value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, res.getDisplayMetrics());
		textViewLayout.setMargins((int) value, 0, 0, 0);
		nameView.setLayoutParams(textViewLayout);
		nameView.setGravity(Gravity.CENTER_VERTICAL);
		nameView.setTextSize(20);
		nameView.setOnClickListener(new ContactViewItemListener(i));
		l.addView(nameView);
		
		View blackLine = new View(getActivity());
		blackLine.setBackgroundColor(getResources().
				getColor(R.color.black_line));
		blackLine.setLayoutParams(blackLineLayout);
		
		contactLayout.addView(l);
		contactLayout.addView(blackLine);
	}
	
	/**
	 * 根据联系人与当前登录用户的关系，设置相应数据布局
	 * @param contactList
	 * @param teacherLayout
	 * @param classmateLayout
	 * @param friendLayout
	 */
	private void setContactInfoByCategory(final List<Contact> contactList,final LinearLayout teacherLayout,
			final LinearLayout classmateLayout, final LinearLayout friendLayout){
		
		LinearLayout.LayoutParams blackLineLayout = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 
                1);
		LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 
                LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 
                LayoutParams.WRAP_CONTENT);
		Contact contactTemp;
		for(int i=0; i<contactList.size(); ++i){
			contactTemp = contactList.get(i);
			if(contactTemp.getRelationship() == Contact.RELATIONSHIP_TEACHER){
				setContactLayout(i,contactList.get(i),teacherLayout,linearLayout,blackLineLayout,textViewLayout);
			}else if(contactTemp.getRelationship() == Contact.RELATIONSHIP_CLASSMATE){
				setContactLayout(i,contactList.get(i),classmateLayout,linearLayout,blackLineLayout,textViewLayout);
			}else if(contactTemp.getRelationship() == Contact.RELATIONSHIP_FRIEND){
				setContactLayout(i,contactList.get(i),friendLayout,linearLayout,blackLineLayout,textViewLayout);
			}
		}
	}
	
	
	class ContactViewItemListener implements OnClickListener{
		private int contactItem;
		
		public ContactViewItemListener(int contactItem){
			this.contactItem = contactItem;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//startActivity();
			Intent intent = new Intent();
            //Intent传递参数
            intent.putExtra(ContactInfoActivity.CONTACT_TYPE, contactList.get(contactItem));
            intent.setClass(getActivity(), ContactInfoActivity.class);
            startActivity(intent);
		}
		
	}
	
	class GetContactListThread extends Thread {
		public GetContactListThread(Context context){
			proDialog = new ProgressDialog(context);
			proDialog.show();
		}
				
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				contactList = ContactDataHandler.getContactList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            handler.sendEmptyMessage(1);
		}
	}
	
}
