package com.netease.amazing.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.netease.amazing.sdk.dto.NewsCommentsDTO;
import com.netease.amazing.sdk.dto.NewsCommentsDTO.CommentType;
import com.netease.amazing.sdk.dto.NewsDTO;
import com.netease.amazing.sdk.dto.UserDTO.Role;
import com.netease.amazing.server.entity.Child;
import com.netease.amazing.server.entity.ChildRelationship;
import com.netease.amazing.server.entity.Comment;
import com.netease.amazing.server.entity.Tweet;
import com.netease.amazing.server.entity.User;
import com.netease.amazing.server.entity.UserTweet;
import com.netease.amazing.server.entity.UserTweet.PublicRelation;
import com.netease.amazing.server.repository.CommentDao;
import com.netease.amazing.server.repository.TweetDao;
import com.netease.amazing.server.repository.UserDao;
import com.netease.amazing.server.repository.UserTweetDao;
import com.netease.amazing.server.utils.DateUtils;


@Component
@Transactional(readOnly = true)
public class TweetService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserTweetDao userTweetDao;
	public List<NewsDTO> getLatestTweets(long userId, int count){
		List<NewsDTO> allNews = new ArrayList<NewsDTO>();
		Sort sort = new Sort( Direction.DESC,"createTime");
		Pageable pageable = new PageRequest(0,count,sort);
		List<Tweet> tweets = tweetDao.findLatestTweets(userId, pageable);
		tweetToNews(userId, allNews, tweets);
		return allNews;
	}

	
	
	public List<NewsDTO> getRangeDownTweets(long userId, long bottomId, int count){
		List<NewsDTO> allNews = new ArrayList<NewsDTO>();
		Sort sort = new Sort( Direction.DESC,"createTime");
		Pageable pageable = new PageRequest(0,count,sort);
		List<Tweet> tweets = tweetDao.findRangeDownTweets(userId, bottomId, pageable);
		tweetToNews(userId, allNews, tweets);
		return allNews;
	}
	
	
	public List<NewsDTO> getRangeAllUpTweets(long userId, long bottomId,int count) {
		List<NewsDTO> allNews = new ArrayList<NewsDTO>();
		Sort sort = new Sort( Direction.DESC,"createTime");
		Pageable pageable = new PageRequest(0,count,sort);
		List<Tweet> tweets = tweetDao.findRangeAllTweets(userId, bottomId,pageable);
		tweetToNews(userId, allNews, tweets);
		return allNews;
	}
	
	private void tweetToNews(long userId, List<NewsDTO> allNews,
			List<Tweet> tweets) {
		for(Tweet t : tweets){
			NewsDTO newsDTO = new NewsDTO();
			
			User fromUser = t.getUser();
			newsDTO.setHeadPicPath(fromUser.getChild().getHeadPicPath());
			newsDTO.setNewPublisherFrom(fromUser.getChild().getKlass().getKindergarden().getName());
			/*if(fromUser.getRole() == Role.PARENT){
				newsDTO.setNewPublisherFrom(fromUser.getChild().getName());
			}else if(fromUser.getRole() == Role.TEACHER) {
				newsDTO.setNewPublisherFrom(fromUser.getTeacher().getName());
			}*/
			
			newsDTO.setNewsContent(t.getContents());
			
			List<Comment> selfLikeComments = tweetDao.findComment(userId, CommentType.LIKE, t.getId());
			if(selfLikeComments == null || selfLikeComments.isEmpty()){
				newsDTO.setNewsCurrentUserLike(false);
			}else{
				newsDTO.setNewsCurrentUserLike(true);
			}
			
			List<Comment> selfIncludeComments = tweetDao.findComment(userId, CommentType.INCLUDE, t.getId());
			if(selfIncludeComments == null || selfIncludeComments.isEmpty()){
				newsDTO.setNewsCurrentUserTakeDown(false);
			}else{
				newsDTO.setNewsCurrentUserTakeDown(true);
			}
			
			newsDTO.setNewsId(t.getId());
			
			Date newsDate = t.getCreateTime();
			SimpleDateFormat simpleDateFormat = DateUtils.getDateFormat(newsDate);
			newsDTO.setNewsPublishDate(simpleDateFormat.format(newsDate));
			newsDTO.setNewPublisherName(t.getUser().getName());
			newsDTO.setNewsPublisherId(t.getUser().getId());
			User curUser = userDao.findOne(userId);
			if(t.getUser().getRole() == Role.TEACHER){
				newsDTO.setNewsPublisherRelationship(NewsDTO.RELATIONSHIP_TEACHER);
			}else if(t.getUser().getChild().getKlass().getId().equals(curUser.getChild().getKlass().getId())){
				newsDTO.setNewsPublisherRelationship(NewsDTO.RELATIONSHIP_CLASSMATE);
			}else{
				newsDTO.setNewsPublisherRelationship(NewsDTO.RELATIONSHIP_FRIEND);
			}
			
			newsDTO.setNewsType(t.getType());
			
			allNews.add(newsDTO);
		}
	}

	
	public static void main(String args[]){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		System.out.println(simpleDateFormat.format(Calendar.getInstance().getTime()));
	}



	public List<NewsCommentsDTO> getAllNewsComments(Long userId, Long newsId, int count){
		Sort sort = new Sort( Direction.DESC,"id");
		Pageable pageable = new PageRequest(0,count,sort);
		List<Comment> comments = tweetDao.findCommentByNewId(newsId,pageable);
		List<NewsCommentsDTO> newComments = new ArrayList<NewsCommentsDTO>();
		for(Comment c : comments){
			NewsCommentsDTO commentDTO = new NewsCommentsDTO();
			commentDTO.setNewsComment(c.getContent());
			commentDTO.setNewsCommentId(c.getId());
			commentDTO.setNewsCommentPublisherId(c.getUser().getId());
			//commentDTO.setNewsCommentTo(newsCommentTo);
			commentDTO.setNewsCommentType(c.getType());
			commentDTO.setNewsCommmentPublisherName(c.getUser().getName());
			commentDTO.setNewsId(c.getTweet().getId());
			newComments.add(commentDTO);
		}
		Collections.sort(newComments, new Comparator<NewsCommentsDTO>(){
			@Override
			public int compare(NewsCommentsDTO o1, NewsCommentsDTO o2) {
				return (int)(o2.getNewsId()-o1.getNewsId());
			}
		});
		return newComments;
	}


	@Transactional(readOnly = false)
	public void tweetOP(long userId, Long tweetId,CommentType type) {
		User user = userDao.findOne(userId);
		Tweet tweet = tweetDao.findOne(tweetId);
		Comment comment = new Comment();
		comment.setTweet(tweet);
		comment.setType(type);
		comment.setUser(user);
		commentDao.save(comment);
	}


	@Transactional(readOnly = false)
	public void includeTweet(long userId, Long tweetId) {
		User user = userDao.findOne(userId);
		Tweet tweet = tweetDao.findOne(tweetId);
		Comment comment = new Comment();
		comment.setTweet(tweet);
		comment.setType(CommentType.INCLUDE);
		comment.setUser(user);
		commentDao.save(comment);
		UserTweet userTweet = new UserTweet();
		userTweet.setInclude(true);
		userTweet.setTweet(tweet);
		userTweet.setUser(user);
		userTweetDao.save(userTweet);
	}


	@Transactional(readOnly = false)
	public void createNewTweet(long userId, NewsDTO newsDTO) {
		User curUser = userDao.findOne(userId);
		Tweet tweet =  new Tweet();
		tweet.setContents(newsDTO.getNewsContent());
		tweet.setCreateTime(Calendar.getInstance().getTime());
		tweet.setType(newsDTO.getNewsType());
		tweet.setUser(curUser);
		tweet = tweetDao.save(tweet);
		if(curUser.getRole() == Role.TEACHER){
			List<Child> children = curUser.getTeacher().getKlass().getChildren();
			for(Child c : children){
				User u = c.getUser();
				if(u == null){
					continue;
				}
				UserTweet ut = new UserTweet();
				ut.setInclude(false);
				ut.setPubRelation(PublicRelation.FROM_TEACHER);
				ut.setTweet(tweet);
				ut.setUser(u);
				userTweetDao.save(ut);
			}
		}else{
			List<Child> children = new ArrayList<Child>();
			List<ChildRelationship> frends = curUser.getChild().getFriends();
			for(ChildRelationship cr : frends){
				children.add(cr.getChildTo());
			}
			List<Child> classmates = curUser.getChild().getKlass().getChildren();
			children.addAll(classmates);
			Set<Child> childSet = new HashSet<Child>();
			childSet.addAll(children);
			for(Child c : childSet){
				User u = c.getUser();
				if(u == null){
					continue;
				}
				UserTweet ut = new UserTweet();
				ut.setInclude(false);
				ut.setPubRelation(PublicRelation.FROM_PARENT);
				ut.setTweet(tweet);
				ut.setUser(u);
				userTweetDao.save(ut);
			}
		}
	}

}
