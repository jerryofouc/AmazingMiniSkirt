package com.netease.amazing.server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.amazing.sdk.dto.ContactDTO;
import com.netease.amazing.server.service.ContactsService;

/**
 * 得到所有的联系人
 * @author zhangxiaojie
 *
 */

@Controller
@RequestMapping(value = "/api/contacts")
public class ContactsRestController extends BaseController{
	@Autowired
	private ContactsService contactsService;
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ContactDTO getContacts(){
		long id = getCurrentUserId();
		return contactsService.getAllContacts(id);
	}
}	
