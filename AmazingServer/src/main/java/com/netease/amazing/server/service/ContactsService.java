package com.netease.amazing.server.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.netease.amazing.sdk.dto.ChildDTO;
import com.netease.amazing.sdk.dto.ContactDTO;
import com.netease.amazing.sdk.dto.TeacherDTO;
import com.netease.amazing.server.entity.Child;
import com.netease.amazing.server.entity.ChildRelationship;
import com.netease.amazing.server.entity.Gender;
import com.netease.amazing.server.entity.Kindergarden;
import com.netease.amazing.server.entity.Parent;
import com.netease.amazing.server.entity.Teacher;
import com.netease.amazing.server.entity.User;
import com.netease.amazing.server.repository.UserDao;

@Component
@Transactional(readOnly = true)
public class ContactsService {
	@Autowired
	private UserDao userDao;
	public ContactDTO getAllContacts(long id){
		ContactDTO contactDTO = new ContactDTO();
		List<TeacherDTO> teacherDTOs = new ArrayList<TeacherDTO>();
		List<ChildDTO> classMates = new ArrayList<ChildDTO>();
		List<ChildDTO> friends = new ArrayList<ChildDTO>();

		User curUser = userDao.findOne(id);
		//注意这边应该判断是否是父母
		Parent curParent = curUser.getParent();
		Child curChild = curParent.getChild();
		
		if(curChild.getBirthday()!=null){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			contactDTO.setBirthday(dateFormat.format(curChild.getBirthday()));
		}
		
		com.netease.amazing.server.entity.Class klass = curChild.getKlass();
		Kindergarden kinderGarden = null;
		if(klass != null){
			contactDTO.setFromClass(klass.getName());
			kinderGarden = klass.getKindergarden();
			if(kinderGarden != null){
				contactDTO.setFromSchool(kinderGarden.getName());
			}
		}
		
		List<Teacher> teachers = klass.getTeachers();
		for(Teacher t : teachers){
			TeacherDTO teacherDto = new TeacherDTO();
			teacherDto.setName(t.getName());
			teacherDto.setFixLine(t.getFixLine());
			teacherDto.setMobilePhone(t.getTelephone());
			teacherDTOs.add(teacherDto);
		}
		contactDTO.setTeachers(teacherDTOs);
		
		
		List<Child> curClassMates = klass.getChildren();
		for(Child child : curClassMates){
			ChildDTO childDTO = new ChildDTO();
			if(child.getBirthday()!=null){
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				childDTO.setBirthday(dateFormat.format(child.getBirthday()));
			}
			childDTO.setName(child.getName());
			childDTO.setFromClass(child.getKlass().getName());
			childDTO.setFromSchool(child.getKlass().getKindergarden().getName());
			List<Parent> parents = child.getParents();
			for(Parent p : parents){
				if(p.getGender() == Gender.MALE){
					childDTO.setFatherName(p.getName());
					childDTO.setFatherTelephone(p.getTelephone());
				}else{
					childDTO.setMotherName(p.getName());
					childDTO.setMotherTelephone(p.getTelephone());
				}
			}
			classMates.add(childDTO);
		}
		contactDTO.setClassMates(classMates);
		
		List<ChildRelationship> friendships = curChild.getFriends();
		for(ChildRelationship friendship : friendships){
			Child childFriend = friendship.getChildTo();
			ChildDTO childDTO = new ChildDTO();
			childDTO.setFromClass(childFriend.getKlass().getName());
			childDTO.setFromSchool(childFriend.getKlass().getKindergarden().getName());
			if(childFriend.getBirthday()!=null){
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				childDTO.setBirthday(dateFormat.format(childFriend.getBirthday()));
			}
			childDTO.setName(childFriend.getName());
			List<Parent> parents = childFriend.getParents();
			for(Parent p : parents){
				if(p.getGender() == Gender.MALE){
					childDTO.setFatherName(p.getName());
					childDTO.setFatherTelephone(p.getTelephone());
				}else{
					childDTO.setMotherName(p.getName());
					childDTO.setMotherTelephone(p.getTelephone());
				}
			}
			friends.add(childDTO);
		}
		contactDTO.setFriends(friends);
		return contactDTO;
	}
}
