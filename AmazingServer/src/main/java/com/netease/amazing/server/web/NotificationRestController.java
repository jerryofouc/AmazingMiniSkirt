package com.netease.amazing.server.web;

import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.server.repository.TeacherDao;
import com.netease.amazing.server.repository.TeacherTipDao;
import com.netease.amazing.server.service.NotificationTipService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping({"/api/notification"})
public class NotificationRestController extends BaseController
{

  @Autowired
  private NotificationTipService notificationTipService;

  @Autowired
  private TeacherTipDao teacherTipDao;

  @Autowired
  private TeacherDao teacherDao;

  @RequestMapping(value={"/latest"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  @ResponseBody
  public List<NoticeDTO> getlatestNotificatoin(@RequestParam(value="count", defaultValue="5", required=false) int count)
  {
    long userId = getCurrentUserId().longValue();
    return this.notificationTipService.getLatestNotification(userId, count);
  }

  @RequestMapping(value={"/rangeDown"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  @ResponseBody
  List<NoticeDTO> getRangeDownNotification(@RequestParam("beginId") long beginId, @RequestParam(value="count", defaultValue="5", required=false) int count)
  {
    long userId = getCurrentUserId().longValue();
    return this.notificationTipService.getRangeDownNotification(beginId, userId, count);
  }

  @RequestMapping(value={"/rangeUp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
  @ResponseBody
  List<NoticeDTO> getAllRangeUpNotification(@RequestParam("beginId") long beginId)
  {
    long userId = getCurrentUserId().longValue();
    return this.notificationTipService.getAllRangeUpNotification(beginId, userId);
  }
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, consumes={"application/json"})
  @ResponseBody
  public ResponseEntity<?> create(@RequestBody NoticeDTO noticeDTO, UriComponentsBuilder uriBuilder) {
    long userId = getCurrentUserId().longValue();
    this.notificationTipService.createNewNotification(userId, noticeDTO);

    HttpHeaders headers = new HttpHeaders();
    return new ResponseEntity(headers, HttpStatus.CREATED);
  }
  
  @RequestMapping(value={"/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) {
    long userId = getCurrentUserId().longValue();
    this.notificationTipService.deleteNotice(userId, id.longValue());
  }
  
 
  
}