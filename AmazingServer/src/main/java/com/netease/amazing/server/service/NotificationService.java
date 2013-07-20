package com.netease.amazing.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.server.entity.Child;
import com.netease.amazing.server.entity.Notification;
import com.netease.amazing.server.entity.Parent;
import com.netease.amazing.server.entity.Teacher;
import com.netease.amazing.server.entity.User;
import com.netease.amazing.server.repository.NotificationDao;
import com.netease.amazing.server.repository.UserDao;
import com.netease.amazing.server.utils.ToDTOUtils;


@Component
@Transactional(readOnly = true)
public class NotificationService {
	private static final String SORT_BY = "createTime";
	@Autowired
	private UserDao userDao;
	@Autowired
	private NotificationDao notificationDao;
	/**
	 * 得到从fromID之后所有的提醒
	 * @param fromId
	 * @param count
	 */
	public List<NoticeDTO> getNotifications(long fromId, int count){
		return null;
	}
	
	public List<NoticeDTO> getLatestNotification(long userId, int count) {
		User user = userDao.findOne(userId);
		Sort sort = new Sort(Direction.DESC,"createTime");
		Pageable pageable = new PageRequest(0,count,sort);
		List<Notification> notifications = notificationDao.findLatestNotification(user.getId(), pageable);
		List<NoticeDTO> retList = new ArrayList<NoticeDTO>();
		for(Notification n : notifications){
			NoticeDTO noticeDTO = ToDTOUtils.toNoticeDTO(n);
			retList.add(noticeDTO);
		}
		return retList;
	}
	/**
	 * 得到某一范围之内所有的通知
	 * @param beginId  开始通知id
	 * @param userId   用户id
	 * @param count	        所有id的数目
	 * @return
	 */
	public List<NoticeDTO> getRangeNotification(long beginId, long userId,int count) {
		User user = userDao.findOne(userId);
		Sort sort = new Sort(Direction.DESC,"createTime");
		Pageable pageable = new PageRequest(0,count,sort);
		List<Notification> notifications = notificationDao.findRangeNotification(user.getId(),beginId, pageable);
		List<NoticeDTO> retList = new ArrayList<NoticeDTO>();
		for(Notification n : notifications){
			NoticeDTO noticeDTO = ToDTOUtils.toNoticeDTO(n);
			retList.add(noticeDTO);
		}
		return retList;
	}
}
