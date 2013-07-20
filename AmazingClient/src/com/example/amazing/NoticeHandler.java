package com.example.amazing;

import java.util.ArrayList;

import com.netease.amazing.pojo.Notice;

public interface NoticeHandler {
	
	/**
	 * @param bottomNoticeId 当前
	 * @param noticeCount 通知条数
	 * @return 返回比id为bottomNoticeId晚发布的通知，通知条数为noticecount
	 */
	public ArrayList<Notice> getNotice(int bottomNoticeId,int noticecount);
	
	
	/**
	 * @param topNoticeId 
	 * @return 返回比topNoticeId早发布的所有通知
	 */
	public ArrayList<Notice> getTopNotice(int topNoticeId);
	
	
	/**
	 * @param noticeCount 通知条数
	 * @return 获得最新的noticeCount条通知
	 */
	public ArrayList<Notice> getInitNotice(int noticeCount);
	
	/**
	 * 发送提醒
	 * @param notice  封装好的提醒	
	 * @param receiver 提醒的接受者
	 * @return 是否发送成功
	 */
	public boolean sendNotice(Notice notice,Object... receiver);
	
	/**
	 *@param notice 删除发送给自己的一条提醒
	 *@return 是否删除成功
	 */ 
	public boolean deleteNotice(Notice notice);
}
