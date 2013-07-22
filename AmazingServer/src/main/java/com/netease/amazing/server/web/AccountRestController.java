package com.netease.amazing.server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.amazing.sdk.dto.UserDTO;
import com.netease.amazing.server.entity.User;
import com.netease.amazing.server.service.AccountService;
import com.netease.amazing.server.utils.ToDTOUtils;

@Controller
@RequestMapping(value = "/api/user")
public class AccountRestController extends BaseController{
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserDTO login(){
		long id = getCurrentUserId();
		User user = accountService.login(id);
		return ToDTOUtils.toUserDTO(user);
	}
	
	@RequestMapping(value={"/hasLogin"},method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Boolean getHasLogin(){
		long id = getCurrentUserId();
		User user = accountService.findUserById(id);
		if(user.isHasLogin() == true){
			return true;
		}else{
			return false;
		}
	}
}
