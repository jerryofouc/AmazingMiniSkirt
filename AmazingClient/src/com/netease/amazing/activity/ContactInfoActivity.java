package com.netease.amazing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.amazing.R;
import com.netease.amazing.pojo.Contact;

public class ContactInfoActivity extends Activity {
	public static String CONTACT_TYPE = "contactType";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int layout = 0;
		Intent intent = getIntent();
		Contact contact = (Contact)intent.getSerializableExtra(CONTACT_TYPE);
		
		if(contact.getRelationship() == Contact.RELATIONSHIP_TEACHER){
			setContentView(R.layout.contact_info_teacher);
			TextView teacherName = (TextView)findViewById(R.id.contact_teacher_name);
			teacherName.setText(contact.getName());
			
			TextView teacherPhone = (TextView)findViewById(R.id.contact_no_teacher_content);
			teacherPhone.setText(contact.getPhoneOfTeacher());
			
			TextView teacherMobile = (TextView)findViewById(R.id.contact_mobile_teacher_content);
			teacherMobile.setText(contact.getMobileOfTeacher());
			
		}else if(contact.getRelationship() == Contact.RELATIONSHIP_CLASSMATE){
			setContentView(R.layout.contact_info_classmate);
			
			TextView childName = (TextView)findViewById(R.id.contact_classmate_name);
			childName.setText(contact.getName());
			
			TextView childBirth = (TextView)findViewById(R.id.contact_classmate_birthday);
			childBirth.setText(contact.getBirthday());
			
			TextView childFatherMobile = (TextView)findViewById(R.id.contact_classmate_father_content);
			childFatherMobile.setText(contact.getPhoneOfDad());
			
			TextView childFatherName = (TextView)findViewById(R.id.contact_classmate_father_name);
			childFatherName.setText(contact.getNameOfDad());

			TextView childMotherName = (TextView)findViewById(R.id.contact_classmate_mother_name);
			childMotherName.setText(contact.getNameOfMum());

			TextView childMotherMobile = (TextView)findViewById(R.id.contact_classmate_mother_content);
			childMotherMobile.setText(contact.getPhoneOfMum());
			
		}else if(contact.getRelationship() == Contact.RELATIONSHIP_FRIEND){
			setContentView(R.layout.contact_info_friend);
			
			TextView childName = (TextView)findViewById(R.id.contact_friend_name);
			childName.setText(contact.getName());
			
			TextView childSchool = (TextView)findViewById(R.id.contact_friend_from);
			childSchool.setText(contact.getFromSchool()+contact.getFromClass());
			
			TextView childBirth = (TextView)findViewById(R.id.contact_friend_birthday);
			childBirth.setText(contact.getBirthday());
			
			TextView childFatherMobile = (TextView)findViewById(R.id.contact_friend_father_content);
			childFatherMobile.setText(contact.getPhoneOfDad());
			
			TextView childFatherName = (TextView)findViewById(R.id.contact_friend_father_name);
			childFatherName.setText(contact.getNameOfDad());

			TextView childMotherName = (TextView)findViewById(R.id.contact_friend_mother_name);
			childMotherName.setText(contact.getNameOfMum());

			TextView childMotherMobile = (TextView)findViewById(R.id.contact_friend_mother_content);
			childMotherMobile.setText(contact.getPhoneOfMum());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_teacher_info, menu);
		return true;
	}

}
