package com.netease.amazing.server.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.amazing.sdk.dto.NewsCommentsDTO;
import com.netease.amazing.sdk.dto.NewsDTO;
import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.sdk.dto.NewsCommentsDTO.CommentType;
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

	@RequestMapping(value={"/rangeDown"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public List<NewsDTO> getRangeDown(@RequestParam("bottomId") long bottomId, @RequestParam(value="count", defaultValue="5", required=false) int count){
		long userId = getCurrentUserId();
		return tweetService.getRangeDownTweets(userId, bottomId, count);
	}
	
	@RequestMapping(value={"/rangeUp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public List<NewsDTO> getRangeUp(@RequestParam("topId") long topId,@RequestParam(value="count", defaultValue="1000", required=false) int count){
		long userId = getCurrentUserId();
		return tweetService.getRangeAllUpTweets(userId, topId,count);
	}
	
	@RequestMapping(value={"/{id}/comments"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public List<NewsCommentsDTO> getAllComments(@PathVariable("id") Long id,@RequestParam(value="count", defaultValue="1000", required=false) int count){
		long userId = this.getCurrentUserId();
		return tweetService.getAllNewsComments(userId,id,count);
	}
	
	@RequestMapping(value={"/{id}/like"}, method={RequestMethod.POST}, produces={"application/json"})
	@ResponseBody
	public ResponseEntity<?> likeComment(@PathVariable("id") Long id){
		long userId = this.getCurrentUserId();
		tweetService.tweetOP(userId, id, CommentType.LIKE);
		HttpHeaders headers = new HttpHeaders();
	    return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value={"/{id}/include"}, method={RequestMethod.POST}, produces={"application/json"})
	@ResponseBody
	public ResponseEntity<?> includeComment(@PathVariable("id") Long id){
		long userId = this.getCurrentUserId();
		tweetService.includeTweet(userId, id);
		HttpHeaders headers = new HttpHeaders();
	    return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method={RequestMethod.POST}, consumes={"application/json"})
	@ResponseBody
	public ResponseEntity<?> createNewTweet(@RequestBody NewsDTO newsDTO){
		long userId = this.getCurrentUserId();
		tweetService.createNewTweet(userId,newsDTO);
		HttpHeaders headers = new HttpHeaders();
	    return new ResponseEntity(headers, HttpStatus.CREATED);
	}
}
