package com.netease.amazing.server.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.amazing.sdk.dto.NewsDTO;
import com.netease.amazing.server.service.TweetService;

@Controller
@RequestMapping(value = "/api/tweet")
public class TweetRestController extends BaseController{
	@Autowired
	private TweetService tweetService;
	
	@RequestMapping(value="/latest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<NewsDTO> getLatestTweet(@RequestParam(value="count",defaultValue="5",required=false) int count){
		long userId = getCurrentUserId();
		return tweetService.getLatestTweets(userId, count);
	}
}
