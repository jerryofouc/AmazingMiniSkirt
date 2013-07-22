package com.netease.amazing.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.netease.amazing.sdk.dto.NewsGrowthLogDTO;
import com.netease.amazing.sdk.dto.NewsDTO.TweetType;
import com.netease.amazing.sdk.dto.UserDTO.Role;
import com.netease.amazing.server.entity.Tweet;
import com.netease.amazing.server.entity.User;
import com.netease.amazing.server.repository.TweetDao;
import com.netease.amazing.server.repository.UserDao;
import com.netease.amazing.server.utils.DateUtils;


@Component
@Transactional(readOnly = true)
public class GrowthLogService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TweetDao tweetDao;
	public List<NewsGrowthLogDTO> getGrowthLog(Long userId, int count){
		User curUser = userDao.findOne(userId);
		List<Tweet> mytweets = curUser.getTweets();
		List<Tweet> includeTweets = tweetDao.getAllIncludeTweets(userId);
		mytweets.addAll(includeTweets);
		Set<Tweet> tweetSet = new HashSet<Tweet>();
		tweetSet.addAll(mytweets);
		mytweets.clear();
		mytweets.addAll(tweetSet);
		Collections.sort(mytweets,new Comparator<Tweet>(){
			@Override
			public int compare(Tweet o1, Tweet o2) {
				return (int)(o2.getId()-o1.getId());
			}
		});
		List<NewsGrowthLogDTO> ret = new ArrayList<NewsGrowthLogDTO>();
		for(int i=0;i<count&&i<mytweets.size();i++){
			Tweet t = mytweets.get(i);
			NewsGrowthLogDTO newsGrowthLogDTO = new NewsGrowthLogDTO();
			if(t.getUser().getRole() == Role.PARENT){
				newsGrowthLogDTO.setNewPublisherFrom(t.getUser().getChild().getKlass().getKindergarden().getName());
				newsGrowthLogDTO.setUserClass(t.getUser().getChild().getKlass().getName());
				newsGrowthLogDTO.setHeadPicPath(t.getUser().getChild().getHeadPicPath());
			}else if(t.getUser().getRole() == Role.TEACHER){
				newsGrowthLogDTO.setNewPublisherFrom(t.getUser().getTeacher().getKlass().getKindergarden().getName());
				newsGrowthLogDTO.setUserClass(t.getUser().getTeacher().getKlass().getName());
				newsGrowthLogDTO.setHeadPicPath(t.getUser().getTeacher().getHeadPicPath());
			}
			
			
			newsGrowthLogDTO.setNewsContent(t.getContents());
			newsGrowthLogDTO.setNewsGrowthLogOwnerUserName(curUser.getChild().getName());
			if(t.getUser().getId().longValue() == userId.longValue()){
				newsGrowthLogDTO.setNewsGrowthLogType(NewsGrowthLogDTO.NEWS_GROWTH_TYPE_ORIGINAL);
			}else{
				newsGrowthLogDTO.setNewsGrowthLogType(NewsGrowthLogDTO.NEWS_GROWTH_TYPE_TAKE_DOWN);
				newsGrowthLogDTO.setNewsTakeDownUserName(curUser.getName());
			}
			newsGrowthLogDTO.setNewsId(t.getId());
			SimpleDateFormat simpleDateFormat = DateUtils.getDateFormat(t.getCreateTime());
			newsGrowthLogDTO.setNewsPublishDate(simpleDateFormat.format(t.getCreateTime()));
			newsGrowthLogDTO.setNewsPublisherName(t.getUser().getName());
			newsGrowthLogDTO.setNewsType(t.getType());
			newsGrowthLogDTO.setUserJoinInClassDays((int)((Calendar.getInstance().getTime().getTime() - curUser.getChild().getJoinClassDate().getTime())/DateUtils.A_DAY_IN_MINISED));
			ret.add(newsGrowthLogDTO);
		}
		return ret;
	}
	public List<NewsGrowthLogDTO> getPreGrowthLog(Long userId,
			long bottomNewsId, int count) {
		User curUser = userDao.findOne(userId);
		List<Tweet> mytweets = curUser.getTweets();
		List<Tweet> includeTweets = tweetDao.getAllIncludeTweets(userId);
		mytweets.addAll(includeTweets);
		Set<Tweet> tweetSet = new HashSet<Tweet>();
		tweetSet.addAll(mytweets);
		mytweets.clear();
		mytweets.addAll(tweetSet);
		Collections.sort(mytweets,new Comparator<Tweet>(){
			@Override
			public int compare(Tweet o1, Tweet o2) {
				return (int)(o2.getId()-o1.getId());
			}
		});
		List<NewsGrowthLogDTO> ret = new ArrayList<NewsGrowthLogDTO>();
		for(int i=0;count>0&&i<mytweets.size();i++){
			Tweet t = mytweets.get(i);
			if(t.getId()>=bottomNewsId){
				continue;
			}else{
				--count;
			}
			NewsGrowthLogDTO newsGrowthLogDTO = new NewsGrowthLogDTO();
			if(t.getUser().getRole() == Role.PARENT){
				newsGrowthLogDTO.setNewPublisherFrom(t.getUser().getChild().getKlass().getKindergarden().getName());
				newsGrowthLogDTO.setUserClass(t.getUser().getChild().getKlass().getName());
				newsGrowthLogDTO.setHeadPicPath(t.getUser().getChild().getHeadPicPath());
			}else if(t.getUser().getRole() == Role.TEACHER){
				newsGrowthLogDTO.setNewPublisherFrom(t.getUser().getTeacher().getKlass().getKindergarden().getName());
				newsGrowthLogDTO.setUserClass(t.getUser().getTeacher().getKlass().getName());
				newsGrowthLogDTO.setHeadPicPath(t.getUser().getTeacher().getHeadPicPath());
			}
			
			newsGrowthLogDTO.setNewsContent(t.getContents());
			newsGrowthLogDTO.setNewsGrowthLogOwnerUserName(curUser.getChild().getName());
			if(t.getUser().getId().longValue() == userId.longValue()){
				newsGrowthLogDTO.setNewsGrowthLogType(NewsGrowthLogDTO.NEWS_GROWTH_TYPE_ORIGINAL);
			}else{
				newsGrowthLogDTO.setNewsGrowthLogType(NewsGrowthLogDTO.NEWS_GROWTH_TYPE_TAKE_DOWN);
				newsGrowthLogDTO.setNewsTakeDownUserName(curUser.getName());
			}
			newsGrowthLogDTO.setNewsId(t.getId());
			SimpleDateFormat simpleDateFormat = DateUtils.getDateFormat(t.getCreateTime());
			newsGrowthLogDTO.setNewsPublishDate(simpleDateFormat.format(t.getCreateTime()));
			newsGrowthLogDTO.setNewsPublisherName(t.getUser().getName());
			newsGrowthLogDTO.setNewsType(t.getType());
			newsGrowthLogDTO.setUserJoinInClassDays((int)((Calendar.getInstance().getTime().getTime() - curUser.getChild().getJoinClassDate().getTime())/DateUtils.A_DAY_IN_MINISED));
			ret.add(newsGrowthLogDTO);
		}
		return ret;
	}
	public List<NewsGrowthLogDTO> allNewGrowlog(Long userId, long topNewsId) {
		User curUser = userDao.findOne(userId);
		List<Tweet> mytweets = curUser.getTweets();
		List<Tweet> includeTweets = tweetDao.getAllIncludeTweets(userId);
		mytweets.addAll(includeTweets);
		Set<Tweet> tweetSet = new HashSet<Tweet>();
		tweetSet.addAll(mytweets);
		mytweets.clear();
		mytweets.addAll(tweetSet);
		Collections.sort(mytweets,new Comparator<Tweet>(){
			@Override
			public int compare(Tweet o1, Tweet o2) {
				return (int)(o2.getId()-o1.getId());
			}
		});
		List<NewsGrowthLogDTO> ret = new ArrayList<NewsGrowthLogDTO>();
		for(int i=0;i<mytweets.size();i++){
			Tweet t = mytweets.get(i);
			if(t.getId()<=topNewsId){
				continue;
			}
			NewsGrowthLogDTO newsGrowthLogDTO = new NewsGrowthLogDTO();
			if(t.getUser().getRole() == Role.PARENT){
				newsGrowthLogDTO.setNewPublisherFrom(t.getUser().getChild().getKlass().getKindergarden().getName());
				newsGrowthLogDTO.setUserClass(t.getUser().getChild().getKlass().getName());
				newsGrowthLogDTO.setHeadPicPath(t.getUser().getChild().getHeadPicPath());
			}else if(t.getUser().getRole() == Role.TEACHER){
				newsGrowthLogDTO.setNewPublisherFrom(t.getUser().getTeacher().getKlass().getKindergarden().getName());
				newsGrowthLogDTO.setUserClass(t.getUser().getTeacher().getKlass().getName());
				newsGrowthLogDTO.setHeadPicPath(t.getUser().getTeacher().getHeadPicPath());
			}
			
			newsGrowthLogDTO.setNewsContent(t.getContents());
			newsGrowthLogDTO.setNewsGrowthLogOwnerUserName(curUser.getChild().getName());
			if(t.getUser().getId().longValue() == userId.longValue()){
				newsGrowthLogDTO.setNewsGrowthLogType(NewsGrowthLogDTO.NEWS_GROWTH_TYPE_ORIGINAL);
			}else{
				newsGrowthLogDTO.setNewsGrowthLogType(NewsGrowthLogDTO.NEWS_GROWTH_TYPE_TAKE_DOWN);
				newsGrowthLogDTO.setNewsTakeDownUserName(curUser.getName());
			}
			newsGrowthLogDTO.setNewsId(t.getId());
			SimpleDateFormat simpleDateFormat = DateUtils.getDateFormat(t.getCreateTime());
			newsGrowthLogDTO.setNewsPublishDate(simpleDateFormat.format(t.getCreateTime()));
			newsGrowthLogDTO.setNewsPublisherName(t.getUser().getName());
			newsGrowthLogDTO.setNewsType(t.getType());
			newsGrowthLogDTO.setUserJoinInClassDays((int)((Calendar.getInstance().getTime().getTime() - curUser.getChild().getJoinClassDate().getTime())/DateUtils.A_DAY_IN_MINISED));
			ret.add(newsGrowthLogDTO);
		}
		return ret;
	}
	public List<NewsGrowthLogDTO> getNewsGrowthLogByDownRefresh(Long userId,
			long topNewsId, int count) {
		User curUser = userDao.findOne(userId);
		List<Tweet> mytweets = curUser.getTweets();
		List<Tweet> includeTweets = tweetDao.getAllIncludeTweets(userId);
		mytweets.addAll(includeTweets);
		Set<Tweet> tweetSet = new HashSet<Tweet>();
		tweetSet.addAll(mytweets);
		mytweets.clear();
		mytweets.addAll(tweetSet);
		Collections.sort(mytweets,new Comparator<Tweet>(){
			@Override
			public int compare(Tweet o1, Tweet o2) {
				return (int)(o2.getId()-o1.getId());
			}
		});
		List<NewsGrowthLogDTO> ret = new ArrayList<NewsGrowthLogDTO>();
		for(int i=0;count>0&&i<mytweets.size();i++){
			Tweet t = mytweets.get(i);
			if(t.getId()<=topNewsId){
				break;
			}
			--count;
			NewsGrowthLogDTO newsGrowthLogDTO = new NewsGrowthLogDTO();
			if(t.getUser().getRole() == Role.PARENT){
				newsGrowthLogDTO.setNewPublisherFrom(t.getUser().getChild().getKlass().getKindergarden().getName());
				newsGrowthLogDTO.setUserClass(t.getUser().getChild().getKlass().getName());
				newsGrowthLogDTO.setHeadPicPath(t.getUser().getChild().getHeadPicPath());
			}else if(t.getUser().getRole() == Role.TEACHER){
				newsGrowthLogDTO.setNewPublisherFrom(t.getUser().getTeacher().getKlass().getKindergarden().getName());
				newsGrowthLogDTO.setUserClass(t.getUser().getTeacher().getKlass().getName());
				newsGrowthLogDTO.setHeadPicPath(t.getUser().getTeacher().getHeadPicPath());
			}
			
			newsGrowthLogDTO.setNewsContent(t.getContents());
			newsGrowthLogDTO.setNewsGrowthLogOwnerUserName(curUser.getChild().getName());
			if(t.getUser().getId().longValue() == userId.longValue()){
				newsGrowthLogDTO.setNewsGrowthLogType(NewsGrowthLogDTO.NEWS_GROWTH_TYPE_ORIGINAL);
			}else{
				newsGrowthLogDTO.setNewsGrowthLogType(NewsGrowthLogDTO.NEWS_GROWTH_TYPE_TAKE_DOWN);
				newsGrowthLogDTO.setNewsTakeDownUserName(curUser.getName());
			}
			newsGrowthLogDTO.setNewsId(t.getId());
			SimpleDateFormat simpleDateFormat = DateUtils.getDateFormat(t.getCreateTime());
			newsGrowthLogDTO.setNewsPublishDate(simpleDateFormat.format(t.getCreateTime()));
			newsGrowthLogDTO.setNewsPublisherName(t.getUser().getName());
			newsGrowthLogDTO.setNewsType(t.getType());
			newsGrowthLogDTO.setUserJoinInClassDays((int)((Calendar.getInstance().getTime().getTime() - curUser.getChild().getJoinClassDate().getTime())/DateUtils.A_DAY_IN_MINISED));
			ret.add(newsGrowthLogDTO);
		}
		return ret;
	}

}
