package com.netease.amazing.pojo;
import java.io.Serializable;
import java.util.Date;


public class Notice implements Serializable{
	private int id;
	private String title;
	private boolean isUpload;
	private Date noticeDate;
	private String content;
	private boolean isNeedFeedBack;
	private Object[] attachments;
	
	
	
	public Notice() {
		
	}
	public Notice(String title, boolean isUpload, Date noticeDate,
			String content, boolean isNeedFeedBack, Object[] attachments) {
		super();
		this.title = title;
		this.isUpload = isUpload;
		this.noticeDate = noticeDate;
		this.content = content;
		this.isNeedFeedBack = isNeedFeedBack;
		this.attachments = attachments;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isUpload() {
		return isUpload;
	}
	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Object[] getAttachments() {
		return attachments;
	}
	public void setAttachments(Object[] attachments) {
		this.attachments = attachments;
	}


	public boolean isNeedFeedBack() {
		return isNeedFeedBack;
	}


	public void setNeedFeedBack(boolean isNeedFeedBack) {
		this.isNeedFeedBack = isNeedFeedBack;
	}
	
	public String cutContent(int length,String encoder) {
		return content.substring(0, 30) +"...";
	}
	
	public String cutTitle(int length,String encoder) {
		return title.substring(0, 10) +"...";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Notice(Boolean b) {
		setId(1000);
		setTitle("2013������¼ҳ��Ĺ��ڼ���ע�⺢�Ӱ�ȫ��֪ͨ");
		setNoticeDate(new Date(System.currentTimeMillis()));
		setContent(" ��һ�ڼ����ڼ���Ϊ��һ����ǿѧ����ȫ������ȷ�������ڼ�ѧ��������ͲƲ���ȫ��ά��ѧУ�ȶ����г���ֽ��й�Ҫ��֪ͨ����:" +
							"һ����УҪ�ڷż�֮ǰ����ȡ��ᡢ���ӵ���ʽ��������ѧ������һ��ȫ��İ�ȫ�������ص���з��𡢷���������ˮ����ʳ���ж��ͽ�ͨ��ȫ�ȷ���Ľ�������һ�����"+
							"ѧ�����������˺���ʶ�����ұ��������������ڼ䣬�ˡ���������Ҫ����ѧ��ע�������ȫ���ϸ����ؽ�ͨ���򣬲��ü�ʻ�����������ܾ��������ơ���֤�����ͳ��س�"+
							"����������ͨ�¹ʵķ��������������š����ᳫѧ�������ڡ���һ���ڼ���������档�Ͻ�ѧУ����֧����ѧ���ᡢ�༶��������֯ѧ���μӸ������͵Ĵ��λ��"+
							"������ʵ���÷ż�ǰ�İ�ȫ�����Ų飬���ü�¼������������ʽ��"+
							"�ġ���ǿ��У��ϵ��ͨ��ӡ������ҳ��顷���ҷõ���ʽ����֪�ҳ���໤�˳е����ӵİ�ȫ�໤���Σ��ڽ��ա���ĩ�ͷ�ѧ���ǿ�Ժ��ӵļ�ܡ�"+
							"�塢���á���һ�����ڵ�ֵ�հ��ţ���ʵֵ���쵼��ֵ���ʦ���Σ�����ֵ���¼��ȷ���ش�����������Ҫ��Ϣ��һʱ��׼ȷ���͡�ͬʱ���Ѽ���ֵ����ͽ��������䣺");
		
		setNeedFeedBack(false);
		setUpload(false);
		setAttachments(null);
	}
}
