package com.netease.amazing.pojo;

import android.graphics.Bitmap;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   News用于存储每一条动态所包含的信息
 */
public class News {
	public News(boolean b){
		
	}
	private int newsId;
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int id) {
		this.newsId = id;
	}
	public static final int NEWS_WITH_IMAGE = 1;//带图像的动态
	public static final int NEWS_WITH_VOICE = 2;//带声音的动态
	public static final int NEWS_WITH_NOTHING = 0;//单纯文字的动态
	private String newsPublisherName; //动态发布人姓名， 当前登录用户的通讯录中所有联系人的动态都可以展示出来
	private byte[] newsPublisherImg; //动态发布人头像,存储为数据流
	private String newsPublisherRelationship; //动态发布人与当前登录用户的关系
	private String newsContent;//发布的动态内容;
	private int newsType;//该条动态信息的类型，参考上面常量
	/**
	 * 动态发布时间，精确到分钟，在服务器端进行时间判断，以服务器当前时间为准，客户端容易修改时间，存储格式如下:
	 * case 1:如果动态是当天发布的，取发布时间格式为：今天(这里注意空两格)14:28
	 * case 2:如果动态不是当天发布但是当年发布的，取发布格式为：7月23日(这里注意空两格)14:28
	 * case 3:如果动态不是当年发布的，取发布时间格式为：2012年5月6日(这里注意空两格)16:55
	 */
	private String newsPublishDate; 
	private String newPublisherFrom; //动态发布人来自的幼儿园
	private byte[] newsWithImage; //动态中的图片，不能与录音共存，注意动态的类型
	private byte[] newsWithVoice; //动态中的录音，不能与图片共存，注意动态的类型
}
