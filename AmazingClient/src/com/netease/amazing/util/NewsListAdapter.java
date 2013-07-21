package com.netease.amazing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;

public class NewsListAdapter extends ListViewBasedAdapter {
	public NewsListAdapter(Context context,DataSource dataSource) {
		super(context, dataSource);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = inflater.inflate(R.layout.news_item,null);
		Map<String,Object> m = (Map<String,Object>)getItem(position);
		
		final int itemPosition = position;
		ImageView newsSenderImageView = (ImageView)view.findViewById(R.id.news_item_sender_image);
		newsSenderImageView.setImageResource((Integer)m.get(NewsDataSource.NEWS_PUBLISHER_IMAGE));
		
		TextView newsSenderNameView = (TextView)view.findViewById(R.id.news_item_sender);
		newsSenderNameView.setText(m.get(NewsDataSource.NEWS_PUBLISHER_NAME).toString());
		
		TextView newsContentView =(TextView)view.findViewById(R.id.news_item_content);
		newsContentView.setText(m.get(NewsDataSource.NEWS_CONTENT).toString());
		
		ImageView newsContentImageView = (ImageView)view.findViewById(R.id.news_item_image);
		newsContentImageView.setImageResource((Integer)m.get(NewsDataSource.NEWS_WITH_IMAGE));
		
		TextView newsSendDateView = (TextView)view.findViewById(R.id.news_item_publish_date);
		newsSendDateView.setText(m.get(NewsDataSource.NEWS_PUBLISH_DATE).toString());
		
		TextView newsSenderFromView = (TextView)view.findViewById(R.id.news_item_from);
		newsSenderFromView.setText(m.get(NewsDataSource.NEWS_PUBLISHER_FROM).toString());
		
		//用于保存关于这个item的评论，以便于动态展示
		List<TextView> newsCommentTextViewList = new ArrayList<TextView>();
		TextView newsComment1 = (TextView)view.findViewById(R.id.news_item_comment_content_1);
		TextView newsComment2 = (TextView)view.findViewById(R.id.news_item_comment_content_2);
		TextView newsComment3 = (TextView)view.findViewById(R.id.news_item_comment_content_3);
		TextView newsComment4 = (TextView)view.findViewById(R.id.news_item_comment_content_4);
		TextView newsComment5 = (TextView)view.findViewById(R.id.news_item_comment_content_5);
		TextView newsComment6 = (TextView)view.findViewById(R.id.news_item_comment_content_6);
		TextView newsComment7 = (TextView)view.findViewById(R.id.news_item_comment_content_7);
		TextView newsComment8 = (TextView)view.findViewById(R.id.news_item_comment_content_8);
		TextView newsComment9 = (TextView)view.findViewById(R.id.news_item_comment_content_9);
		TextView newsComment10 = (TextView)view.findViewById(R.id.news_item_comment_content_10);
		TextView newsComment11 = (TextView)view.findViewById(R.id.news_item_comment_content_11);
		TextView newsComment12 = (TextView)view.findViewById(R.id.news_item_comment_content_12);
		TextView newsComment13 = (TextView)view.findViewById(R.id.news_item_comment_content_13);
		TextView newsComment14 = (TextView)view.findViewById(R.id.news_item_comment_content_14);
		TextView newsComment15 = (TextView)view.findViewById(R.id.news_item_comment_content_15);
		newsCommentTextViewList.add(newsComment1);
		newsCommentTextViewList.add(newsComment2);
		newsCommentTextViewList.add(newsComment3);
		newsCommentTextViewList.add(newsComment4);
		newsCommentTextViewList.add(newsComment5);
		newsCommentTextViewList.add(newsComment6);
		newsCommentTextViewList.add(newsComment7);
		newsCommentTextViewList.add(newsComment8);
		newsCommentTextViewList.add(newsComment9);
		newsCommentTextViewList.add(newsComment10);
		newsCommentTextViewList.add(newsComment11);
		newsCommentTextViewList.add(newsComment12);
		newsCommentTextViewList.add(newsComment13);
		newsCommentTextViewList.add(newsComment14);
		newsCommentTextViewList.add(newsComment15);
		
		Button buttonLike = (Button)view.findViewById(R.id.news_item_like);
		buttonLike.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(view.getContext().getApplicationContext(), "已添加至喜歡" + itemPosition,
					     Toast.LENGTH_SHORT).show();
				/**登录用户喜欢该动态，写入数据库中动态评论表中，并且返回最新的前15条评论
				 * 
				 * */
			}
			
		});
		
		Button buttonTakeIt = (Button)view.findViewById(R.id.news_item_take_it);
		buttonTakeIt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(view.getContext().getApplicationContext(), "已收錄" + itemPosition,
					     Toast.LENGTH_SHORT).show();
				/**登录用户收录该动态，写入数据库中动态评论表中，并且返回最新的前15条评论
				 * 
				 * */
			}
			
		});
		
		Button buttonComment = (Button)view.findViewById(R.id.news_item_comment);
		buttonComment.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(view.getContext().getApplicationContext(), "評論" + itemPosition,
					     Toast.LENGTH_SHORT).show();
				/**登录用户点击评论后弹出对话框接受输入，降评论内容写入数据库评论表中，并返回前15条最新评论
				 * 
				 * */
			}
			
		});
		
		Button buttonShow = (Button)view.findViewById(R.id.news_item_show_other_buttons);
		buttonShow.setOnClickListener(new ShowButtonsListener(buttonLike, buttonTakeIt, buttonComment,newsCommentTextViewList));
		
		return view;
	}
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 * 		设置是否显示喜欢、收录、评论等功能按键，button点开后，获取数据库评论表中最新的前15条数据显示
	 *
	 */
	private class ShowButtonsListener implements OnClickListener{
		private int IsButtonShow=0;
		private Button buttonLike;
		private Button buttonTakeIt;
		private Button buttonComment;
		private List<TextView> textViewList;
		private Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String[] commentArray = msg.getData().getStringArray("comment");
				TextView viewTemp;
				for(int i=0; i<commentArray.length; ++i){
					viewTemp = textViewList.get(i); 
					viewTemp.setText(commentArray[i]);
					viewTemp.setVisibility(View.VISIBLE);
				}
			}
		};
		public ShowButtonsListener(Button buttonLike, Button buttonTakeIt, Button buttonComment, List<TextView> textViewList){
			this.buttonLike = buttonLike;
			this.buttonTakeIt = buttonTakeIt;
			this.buttonComment = buttonComment;
			this.textViewList = textViewList;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(IsButtonShow == 0){
				new GetContactListThread().start();
				this.buttonLike.setVisibility(View.VISIBLE);
				this.buttonTakeIt.setVisibility(View.VISIBLE);
				this.buttonComment.setVisibility(View.VISIBLE);
				IsButtonShow = 1;
			}else{
				this.buttonLike.setVisibility(View.GONE);
				this.buttonTakeIt.setVisibility(View.GONE);
				this.buttonComment.setVisibility(View.GONE);
				for(View commentView:textViewList){
					commentView.setVisibility(View.GONE);
				}
				IsButtonShow = 0;
			}
		}
		/**
		 * 
		 * @author Huang Xiao Jun
		 * Class Description:
		 *    获取评论内容的线程
		 */
		class GetContactListThread extends Thread {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int n = new Random().nextInt(16);
				String[] stringArray = new String[n]; 
				for(int i=0; i<n ; ++i){
					stringArray[i] = new String("comment"+i);
				}

				Bundle b = new Bundle();
				b.putStringArray("comment",stringArray);
				Message msg = new Message();
				msg.setData(b);
	            handler.sendMessage(msg);
			}
		}
	}
	

	
}
