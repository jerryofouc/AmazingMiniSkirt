package com.netease.amazing.server.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.server.service.NotificationService;

@Controller
@RequestMapping(value = "/api/notification/")
public class NotificationRestController extends BaseController{
	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(value="latest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<NoticeDTO> getlatestNotificatoin(@RequestParam(value="count",defaultValue="5",required=false) int count){
		long userId = getCurrentUserId();
		return notificationService.getLatestNotification(userId, count);
	}
	
	@RequestMapping(value="range", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<NoticeDTO> getRangeNotification(@RequestParam(value="beginId") long beginId, @RequestParam(value="count",defaultValue="5",required=false) int count){
		long userId = getCurrentUserId();
		return notificationService.getRangeNotification(beginId,userId, count);
	} 
	
}
