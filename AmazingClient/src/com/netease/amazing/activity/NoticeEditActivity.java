package com.netease.amazing.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.sdk.client.AccountRestClient;
import com.netease.amazing.sdk.client.ContactRestClient;
import com.netease.amazing.sdk.client.NoticeRestClient;
import com.netease.amazing.sdk.dto.ChildDTO;
import com.netease.amazing.sdk.dto.ContactDTO;
import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.sdk.dto.TeacherDTO;
import com.netease.amazing.sdk.dto.UserDTO.Role;
import com.netease.amazing.util.UserInfoStore;

public class NoticeEditActivity extends Activity {
	
	private ImageButton editNoticeBack;
	private EditText editNoticeContent;
	private Spinner editNoticeReceiver;
	private ImageButton editNoticeSendButton;
	private TextView editNoticeReceiverText;
	private  ProgressDialog pDialog;
	
	private List<TeacherDTO> teachers;
	private List<ChildDTO> friends;
	
	private ContactDTO contactDTO = null;
	private NoticeDTO noticeDTO = new NoticeDTO();
	private NoticeRestClient noticeRestClient = new NoticeRestClient(UserInfoStore.url,UserInfoStore.loginName,UserInfoStore.password);
	private ContactRestClient contactRestClient = new ContactRestClient(UserInfoStore.url,UserInfoStore.loginName,UserInfoStore.password);	
	private boolean isNetError =false;
	private Handler contactProgressHandler = new ContactProgressHandler();
	private boolean firstPageIn = true;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.notice_edit);
		getView();
		pDialog = ProgressDialog.show(this, "连接中..","连接中..请稍后....", true, true);
		new GetInitDataTask().execute("no");
		setListener();
	}

	

	private void getView() {
		editNoticeBack = (ImageButton)findViewById(R.id.notice_edit_titlebar_back_btn);
		editNoticeContent = (EditText)findViewById(R.id.notice_edit_content);
		editNoticeReceiver = (Spinner)findViewById(R.id.notice_edit_receiver);
		editNoticeSendButton = (ImageButton)findViewById(R.id.notice_edit_titlebar_save_btn);
		editNoticeReceiverText = (TextView)findViewById(R.id.notice_edit_receiver_text);
	}
	
	public void setView() {
		ArrayList<String> contactNames = new ArrayList<String>();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.contentTextView);
		if(contactDTO == null) {Log.i("contactDTO", "test ");}
		teachers = contactDTO.getTeachers();
		friends = contactDTO.getFriends();
		int i=0;
		
		//家长
		if(UserInfoStore.userRole == Role.PARENT) {
			String defaultItemString = "";
			if(teachers !=null) {
				while(i++ <teachers.size()) {
					String tempName = teachers.get(i-1).getName();
					contactNames.add(tempName);
					defaultItemString = defaultItemString +tempName +";";
				}
			}
			i=0;
			if(friends !=null) {
				while(i++ <friends.size()) {
					contactNames.add(friends.get(i-1).getName());
				}
			}
			adapter.add(defaultItemString.substring(0,defaultItemString.length()-1));
			adapter.addAll(contactNames);
			
		}
		
		else {
			if(teachers !=null) {
				while(i++ <teachers.size()) {
					String tempName = teachers.get(i-1).getName();
					contactNames.add(tempName);
					
				}
			}
			i=0;
			if(friends !=null) {
				while(i++ <friends.size()) {
					contactNames.add(friends.get(i-1).getName());
				}
			}
			adapter.add("所有家长");
			adapter.addAll(contactNames);
		}
		
		editNoticeReceiver.setAdapter(adapter);
	}
	
	private void setListener() {
		editNoticeBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				NoticeEditActivity.this.finish();
			}
		});
		editNoticeReceiver.setOnItemSelectedListener( new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {
				String itemContent = (String) adapterView.getItemAtPosition(position);
				if(firstPageIn) {
					editNoticeReceiverText.setText(itemContent +";");
					firstPageIn = !firstPageIn;
				}
				String defaultReceiver = (String) adapterView.getItemAtPosition(0);
				if(UserInfoStore.userRole == Role.TEACHER) {
					if(editNoticeReceiverText.getText().equals(defaultReceiver +";")) {
						editNoticeReceiverText.setText(itemContent +";");
					}
					else {
						if(!editNoticeReceiverText.getText().equals(defaultReceiver +";") && itemContent.equals("所有家长")) {
							editNoticeReceiverText.setText(itemContent +";");
						}else {
							if(!editNoticeReceiverText.getText().toString().contains(itemContent +";"))
								editNoticeReceiverText.setText(editNoticeReceiverText.getText() +itemContent +";");
						}
					}
				}else {
					if(position ==0) {
						editNoticeReceiverText.setText(defaultReceiver +";");
					}
					else if(defaultReceiver.contains(itemContent +";")) {
						editNoticeReceiverText.setText(itemContent +";");
						}
					else if(!editNoticeReceiverText.getText().toString().contains(itemContent +";") )
						editNoticeReceiverText.setText(editNoticeReceiverText.getText() +itemContent +";");
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
			});
		
		editNoticeSendButton.setOnClickListener(new MyOnClickListener());
	}
	
	
	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String[] receiverNames = editNoticeReceiverText.getText().toString().split(";");
			String content = editNoticeContent.getText().toString();
			if(content ==null || content.equals("")) {
				new AlertDialog.Builder(NoticeEditActivity.this)    
				                .setTitle("标题")  
				                .setMessage("请输入内容")  
				                .setPositiveButton("确定", null).show();
				return;
			}
			noticeDTO.setContent(content);
			noticeDTO.setTittle("hehe");
			noticeDTO.setNeedFeedBack(false);
			noticeDTO.setId(-1);
			noticeDTO.setNoticeDate(new Date());
			noticeDTO.setSendToAllClassMates(false);
			
			//如果userRole为老师并且在编辑框中为所有家长，那么就发送消息给所有人
			if(UserInfoStore.userRole == Role.TEACHER && editNoticeReceiverText.getText().toString().equals("所有家长;")) {
				noticeDTO.setSendToAllClassMates(true);
				
			}
			List<Long> receiverIds = new ArrayList<Long>();
			int i =0;
			for(i=0;i<receiverNames.length;i++) {
				//获得名单上所有好友的id，添加到list中
				if(friends != null) {
				for(int j =0;j<friends.size();j++) {
					if(friends.get(j).getName().equals(receiverNames[i]))
						receiverIds.add(friends.get(j).getUserID());
				}
				}
				//获得名单上所有老师的id，添加到list中
				if(teachers != null) {
				for(int k=0;k<teachers.size();k++) {
					Log.i("teachersize",teachers.size()+"");
					if(teachers.get(k).getName().equals(receiverNames[i])) {
						receiverIds.add(teachers.get(k).getId());
					}
				}
				}
			}
			noticeDTO.setRecieveObjsIDs(receiverIds);
			new SendNoitceTask().execute("no");
			pDialog = ProgressDialog.show(NoticeEditActivity.this, "","消息发送中", true, true);
		}
		
	}
	
	class SendNoitceTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			try {
				noticeRestClient.sendNewNotice(noticeDTO);
			} catch (ClientProtocolException e) {
				isNetError = true;
			} catch (IOException e) {
				isNetError = true;
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			//update the ui
			if(pDialog !=null) {
				pDialog.dismiss();
			}
			if(!isNetError) {
			Toast.makeText(NoticeEditActivity.this, "通知已发送", Toast.LENGTH_SHORT).show();
			editNoticeContent.setText("");
			editNoticeReceiverText.setText("");
			}else {
			Toast.makeText(NoticeEditActivity.this, "网络连接出错", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	class GetInitDataTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			try {
				contactDTO = contactRestClient.getAllContacts();
			} catch (ClientProtocolException e) {
				isNetError = true;
				return isNetError;
			} catch (IOException e) {
				isNetError = true;
				return isNetError;
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
				contactProgressHandler.sendEmptyMessage(0);
				setView();
			}
		}

	
	
	class ContactProgressHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(pDialog !=null) {
				Log.i("ccc", "ccc");
				pDialog.dismiss();
			}
			if(isNetError) {
				Toast.makeText(NoticeEditActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
