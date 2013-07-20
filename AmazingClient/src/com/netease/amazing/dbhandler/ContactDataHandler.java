package com.netease.amazing.dbhandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.netease.amazing.pojo.Contact;
import com.netease.amazing.sdk.client.AccountRestClient;
import com.netease.amazing.sdk.client.ContactRestClient;
import com.netease.amazing.sdk.dto.ChildDTO;
import com.netease.amazing.sdk.dto.ContactDTO;
import com.netease.amazing.sdk.dto.TeacherDTO;

/**
 * 
 * @author Huang Xiao Jun
 * Description:
 *   ContactHandler，接口，用于描述与后台交互的相关方法
 *
 */
public class ContactDataHandler {
	private static final String BASE_URL = "http://10.240.34.42:8080/server";
	private static final String USER_NAME = "xukai";
	private static final String PASSWORD = "123456";
	/**
	 * @return 在当前用户通讯录列表中的,按照姓名首字的拼音顺序排列
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static List<Contact> getContactList() throws Exception{

		List<Contact> contactList = new ArrayList<Contact>();
		AccountRestClient.testLogin(BASE_URL,USER_NAME, PASSWORD);
		
		ContactRestClient contactRestClient = new ContactRestClient(BASE_URL,USER_NAME, PASSWORD);
		
		ContactDTO allContacts = contactRestClient.getAllContacts();
		
		Iterator<TeacherDTO> teacherIterator = allContacts.getTeachers().iterator();
		TeacherDTO teacher;
		while(teacherIterator.hasNext()){
			teacher = teacherIterator.next();
			Contact c = new Contact();
			c.setName(teacher.getName());
			c.setPhoneOfTeacher(teacher.getFixLine());
			c.setMobileOfTeacher(teacher.getMobilePhone());
			c.setRelationship(Contact.RELATIONSHIP_TEACHER);
			contactList.add(c);
			System.out.println(c);
		}
		
		
		Iterator<ChildDTO> classmateIterator = allContacts.getClassMates().iterator();
		ChildDTO classmate;
		while(classmateIterator.hasNext()){
			classmate = classmateIterator.next();
			Contact c = new Contact();
			c.setName(classmate.getName());
			c.setBirthday(classmate.getBirthday());
			c.setNameOfMum(classmate.getMotherName());
			c.setNameOfDad(classmate.getFatherName());
			c.setPhoneOfMum(classmate.getMotherTelephone());
			c.setPhoneOfDad(classmate.getFatherTelephone());
			c.setNickName(classmate.getNickName());
			c.setRelationship(Contact.RELATIONSHIP_CLASSMATE);
			contactList.add(c);
		}
		
		Iterator<ChildDTO> friendIterator = allContacts.getFriends().iterator();
		ChildDTO friend;
		while(friendIterator.hasNext()){
			friend = friendIterator.next();
			Contact c = new Contact();
			c.setName(friend.getName());
			c.setBirthday(friend.getBirthday());
			c.setNameOfMum(friend.getMotherName());
			c.setNameOfDad(friend.getFatherName());
			c.setPhoneOfMum(friend.getMotherTelephone());
			c.setPhoneOfDad(friend.getFatherTelephone());
			c.setFromSchool(friend.getFromSchool());
			c.setFromClass(friend.getFromClass());
			c.setNickName(friend.getNickName());
			c.setRelationship(Contact.RELATIONSHIP_FRIEND);
			contactList.add(c);
		}
		return contactList;
	}
}
