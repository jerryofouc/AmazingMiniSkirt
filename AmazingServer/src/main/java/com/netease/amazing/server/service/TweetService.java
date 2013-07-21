package com.netease.amazing.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.netease.amazing.server.entity.Comment;
import com.netease.amazing.server.entity.Tweet;
import com.netease.amazing.server.entity.User;
import com.netease.amazing.server.entity.User.Role;
import com.netease.amazing.server.repository.CommentDao;
import com.netease.amazing.server.repository.TweetDao;
import com.netease.amazing.server.repository.UserDao;


@Component
@Transactional(readOnly = true)
public class TweetService {
	private static final long A_DAY_IN_MINISED = 24*60*60*60*1000;
	private static final long A_YEAR_IN_MINISED = 365*A_DAY_IN_MINISED;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	private CommentDao commentDao;
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
			Date curDate = Calendar.getInstance().getTime();
			SimpleDateFormat simpleDateFormat;
			if(curDate.getTime() - newsDate.getTime() < A_DAY_IN_MINISED ){
				simpleDateFormat = new SimpleDateFormat("今天  HH:mm");
			}else if(curDate.getTime() - newsDate.getTime() < A_YEAR_IN_MINISED){
				simpleDateFormat = new SimpleDateFormat("MM月dd日  HH:mm");
			}else{
				simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
			}
			newsDTO.setNewsPublishDate(simpleDateFormat.format(newsDate));
			
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
		Tweet tweet = tweetDao.findOne(newsId);
		List<Comment> comments = tweet.getComments();
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

}
