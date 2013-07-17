package com.netease.amazing.server.utils;

import com.netease.amazing.sdk.dto.UserDTO;
import com.netease.amazing.server.entity.User;

public class ToDTOUtils {
	public static UserDTO toUserDTO(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setLoginName(user.getLoginName());
		userDTO.setName(user.getName());
		return userDTO;
	}
}
