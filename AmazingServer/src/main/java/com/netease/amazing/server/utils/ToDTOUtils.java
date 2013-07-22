package com.netease.amazing.server.utils;

import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.sdk.dto.UserDTO;
import com.netease.amazing.server.entity.Notification;
import com.netease.amazing.server.entity.User;

public class ToDTOUtils {
	public static UserDTO toUserDTO(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setLoginName(user.getLoginName());
		userDTO.setName(user.getName());
		userDTO.setSignature(user.getSignature());
		userDTO.setRole(user.getRole());
		if(user.getChild() != null){
			userDTO.setHeadPic(user.getChild().getHeadPicPath());
		}
		userDTO.setFrontCover(user.getCoverPicPath());
		return userDTO;
	}
	public static NoticeDTO toNoticeDTO(Notification n){
		NoticeDTO noticeDTO = new NoticeDTO();
		noticeDTO.setTittle(n.getTittle());
		noticeDTO.setId(n.getId());
		noticeDTO.setContent(n.getContents());
		noticeDTO.setNeedFeedBack(n.isNeedFeedBack());
		noticeDTO.setNoticeDate(n.getCreateTime());
		return noticeDTO;
	}
}
