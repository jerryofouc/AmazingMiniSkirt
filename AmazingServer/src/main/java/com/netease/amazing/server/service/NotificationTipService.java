package com.netease.amazing.server.service;

import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.sdk.dto.UserDTO.Role;
import com.netease.amazing.server.entity.Child;
import com.netease.amazing.server.entity.Notification;
import com.netease.amazing.server.entity.ParentNotification;
import com.netease.amazing.server.entity.Teacher;
import com.netease.amazing.server.entity.TeacherTip;
import com.netease.amazing.server.entity.Tip;
import com.netease.amazing.server.entity.User;
import com.netease.amazing.server.repository.NotificationDao;
import com.netease.amazing.server.repository.ParentNotificationDao;
import com.netease.amazing.server.repository.TeacherTipDao;
import com.netease.amazing.server.repository.TipDao;
import com.netease.amazing.server.repository.UserDao;
import com.netease.amazing.server.utils.ToDTOUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly=true)
public class NotificationTipService
{
  private static final String SORT_BY = "createTime";

  @Autowired
  private UserDao userDao;

  @Autowired
  private NotificationDao notificationDao;

  @Autowired
  private TipDao tipDao;

  @Autowired
  private TeacherTipDao teacherTipDao;

  @Autowired
  private ParentNotificationDao parentNotificationDao;

  public List<NoticeDTO> getNotifications(long fromId, int count)
  {
    return null;
  }

  public List<NoticeDTO> getLatestNotification(long userId, int count) {
    User user = (User)this.userDao.findOne(Long.valueOf(userId));
    Sort sort = new Sort(Sort.Direction.DESC, new String[] { SORT_BY });
    Pageable pageable = new PageRequest(0, count, sort);
    List<Notification> notifications = this.notificationDao.findLatestNotification(user.getId().longValue(), pageable);
    List<NoticeDTO> retList = new ArrayList<NoticeDTO>();
    for (Notification n : notifications) {
      NoticeDTO noticeDTO = ToDTOUtils.toNoticeDTO(n);
      retList.add(noticeDTO);
    }
    return retList;
  }

  public List<NoticeDTO> getRangeDownNotification(long beginId, long userId, int count)
  {
    User user = (User)this.userDao.findOne(Long.valueOf(userId));
    Sort sort = new Sort(Sort.Direction.DESC, new String[] { "createTime" });
    Pageable pageable = new PageRequest(0, count, sort);
    List<Notification> notifications = this.notificationDao.findRangeNotification(user.getId(), beginId, pageable);
    List<NoticeDTO> retList = new ArrayList<NoticeDTO>();
    for (Notification n : notifications) {
      NoticeDTO noticeDTO = ToDTOUtils.toNoticeDTO(n);
      retList.add(noticeDTO);
    }
    return retList;
  }

  public List<NoticeDTO> getAllRangeUpNotification(long beginId, long userId) {
    User user = (User)this.userDao.findOne(Long.valueOf(userId));
    Sort sort = new Sort(Sort.Direction.DESC, new String[] { "createTime" });
    List<Notification> notifications = this.notificationDao.findRangeUpNotification(user.getId(), beginId, sort);
    List<NoticeDTO> retList = new ArrayList<NoticeDTO>();
    for (Notification n : notifications) {
      NoticeDTO noticeDTO = ToDTOUtils.toNoticeDTO(n);
      retList.add(noticeDTO);
    }
    return retList;
  }

  @Transactional(readOnly=false)
  public void createNewNotification(long userId, NoticeDTO notice) {
    User user = (User)this.userDao.findOne(Long.valueOf(userId));
    if (user.getRole() == Role.PARENT)
      createNewTip(user, notice);
    else if (user.getRole() == Role.TEACHER){
    	createNewNotification(user, notice);
    }
  }

  private void createNewNotification(User user, NoticeDTO notice)
  {
    Teacher curTeacher = user.getTeacher();
    Notification notification = new Notification();
    notification.setContents(notice.getContent());
    notification.setTittle(notice.getTittle());
    notification.setCreateTime(Calendar.getInstance().getTime());
    notification.setNeedFeedBack(notice.isNeedFeedBack());
    notification.setFeedBack(notice.getFeedBack());
    notification.setTeacher(curTeacher);
    notification = (Notification)this.notificationDao.save(notification);

    List<Long> recievedObbjsIDs = notice.getRecieveObjsIDs(); 
    if(notice.isSendToAllClassMates()){
    	List<Child> children =  user.getTeacher().getKlass().getChildren();
    	for(Child c : children){
    		User u = c.getUser();
    		if (u != null) {
    		   ParentNotification pn = new ParentNotification();
    		   pn.setNotification(notification);
    		   pn.setParent(u);
    		   this.parentNotificationDao.save(pn);
    		   if(recievedObbjsIDs != null){
    			   if(recievedObbjsIDs.contains(u.getId()));
    			   recievedObbjsIDs.remove(u.getId());
    		   }
    		}
    	}
     }
    
    for (Long id : recievedObbjsIDs) {
      User parent = (User)this.userDao.findOne(id);
      if (parent != null) {
        ParentNotification pn = new ParentNotification();
        pn.setNotification(notification);
        pn.setParent(parent);
        this.parentNotificationDao.save(pn);
      }
    }
  }

  private void createNewTip(User user, NoticeDTO notice)
  {
    Tip tip = new Tip();
    tip.setTittle(notice.getTittle());
    tip.setContents(notice.getContent());
    tip.setCreateTime(Calendar.getInstance().getTime());
    tip.setParent(user);
    tip = (Tip)this.tipDao.save(tip);
    for (Long id : notice.getRecieveObjsIDs()) {
      User u = (User)this.userDao.findOne(id);
      if (u != null) {
        Teacher t = u.getTeacher();
        if (t != null) {
          TeacherTip teacherTip = new TeacherTip();
          teacherTip.setTeacher(t);
          teacherTip.setTip(tip);
          this.teacherTipDao.save(teacherTip);
        }
      }
    }
  }

  @Transactional(readOnly=false)
  public void deleteNotice(long userId, long noticeId) {
    User user = (User)this.userDao.findOne(Long.valueOf(userId));
    if (user.getRole() == Role.PARENT)
      this.notificationDao.deletePNByUserIdAndNotificationId(user.getId(), Long.valueOf(noticeId));
    else
      this.notificationDao.deleteByTPUserIdAndTipId(user.getId(), Long.valueOf(noticeId));
  }
  
  public static void main(String args[]){
	  List<Long> ll = new ArrayList<Long>();
	  ll.add(new Long(1));
	  System.out.println(ll.contains(new Long(1)));
  }
}