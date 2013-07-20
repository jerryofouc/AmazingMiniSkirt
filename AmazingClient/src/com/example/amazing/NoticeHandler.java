package com.example.amazing;

import java.util.ArrayList;

import com.netease.amazing.pojo.Notice;

public interface NoticeHandler {
	
	/**
	 * @param bottomNoticeId ��ǰ
	 * @param noticeCount ֪ͨ����
	 * @return ���ر�idΪbottomNoticeId������֪ͨ��֪ͨ����Ϊnoticecount
	 */
	public ArrayList<Notice> getNotice(int bottomNoticeId,int noticecount);
	
	
	/**
	 * @param topNoticeId 
	 * @return ���ر�topNoticeId�緢��������֪ͨ
	 */
	public ArrayList<Notice> getTopNotice(int topNoticeId);
	
	
	/**
	 * @param noticeCount ֪ͨ����
	 * @return ������µ�noticeCount��֪ͨ
	 */
	public ArrayList<Notice> getInitNotice(int noticeCount);
	
	/**
	 * ��������
	 * @param notice  ��װ�õ�����	
	 * @param receiver ���ѵĽ�����
	 * @return �Ƿ��ͳɹ�
	 */
	public boolean sendNotice(Notice notice,Object... receiver);
	
	/**
	 *@param notice ɾ�����͸��Լ���һ������
	 *@return �Ƿ�ɾ���ɹ�
	 */ 
	public boolean deleteNotice(Notice notice);
}
