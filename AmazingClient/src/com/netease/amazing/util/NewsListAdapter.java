package com.netease.amazing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.os.AsyncTask;
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
import com.netease.amazing.dbhandler.NewsDataHandler;
import com.netease.amazing.pojo.NewsComment;

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
		
		List<CommentTextView> commentTextViewList = getCommentView(view);
		
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
		buttonShow.setOnClickListener(new ShowButtonsListener(buttonLike, buttonTakeIt, buttonComment,commentTextViewList));
		
		return view;
	}
	
	class LikeNewsExecute extends AsyncTask<Long,Integer,Boolean> {

		@Override
		protected Boolean doInBackground(Long... params) {
			// TODO Auto-generated method stub
			return NewsDataHandler.setLikeNews(params[0]);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(result){
				/**
				 * 修改commentListView
				 */
			}	
		}
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
		private List<CommentTextView> commentTextViewList;
		private Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String[] commentArray = msg.getData().getStringArray("comment");
				TextView viewTemp;
				for(int i=0; i<commentArray.length; ++i){
					commentTextViewList.get(i).getCommentPublisherNameView().setVisibility(View.VISIBLE);
					commentTextViewList.get(i).getCommentReplyLabelView().setVisibility(View.VISIBLE);
					commentTextViewList.get(i).getCommentReplyToNameView().setVisibility(View.VISIBLE);
					commentTextViewList.get(i).getCommentColonView().setVisibility(View.VISIBLE);
					viewTemp = commentTextViewList.get(i).getCommentContentView(); 
					viewTemp.setText(commentArray[i]);
					viewTemp.setVisibility(View.VISIBLE);
				}
			}
		};
		public ShowButtonsListener(Button buttonLike, Button buttonTakeIt, Button buttonComment, List<CommentTextView> commentTextViewList){
			this.buttonLike = buttonLike;
			this.buttonTakeIt = buttonTakeIt;
			this.buttonComment = buttonComment;
			this.commentTextViewList = commentTextViewList;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(IsButtonShow == 0){
				new GetCommentsListThread().start();
				this.buttonLike.setVisibility(View.VISIBLE);
				this.buttonTakeIt.setVisibility(View.VISIBLE);
				this.buttonComment.setVisibility(View.VISIBLE);
				IsButtonShow = 1;
			}else{
				this.buttonLike.setVisibility(View.GONE);
				this.buttonTakeIt.setVisibility(View.GONE);
				this.buttonComment.setVisibility(View.GONE);
				for(CommentTextView commentTextView:commentTextViewList){
					commentTextView.getCommentPublisherNameView().setVisibility(View.GONE);
					commentTextView.getCommentReplyLabelView().setVisibility(View.GONE);
					commentTextView.getCommentReplyToNameView().setVisibility(View.GONE);
					commentTextView.getCommentColonView().setVisibility(View.GONE);
					commentTextView.getCommentContentView().setVisibility(View.GONE);
				}
				IsButtonShow = 0;
			}
		}
		/**
		 * 
		 * @author Huang Xiao Jun
		 * Class Description:
		 *    获取评论内容的线程 替换下面thread操作
		 */		
		class GetCommentsListExecute extends AsyncTask<Long,Integer,List<NewsComment>> {

			@Override
			protected List<NewsComment> doInBackground(Long... params) {
				// TODO Auto-generated method stub
				return NewsDataHandler.getNewsCommentToNewsIndexByNewsId(params[0],
						NewsComment.NEWS_COMMENT_COUNT_FOR_INDEX);
			}
			
			@Override
			protected void onPostExecute(List<NewsComment> result) {
				super.onPostExecute(result);
				/**
				 * 更新commentsListView
				 */
			}
		}
		/**
		 * 
		 * @author Huang Xiao Jun
		 * Class Description:
		 *    获取评论内容的线程
		 */
		class GetCommentsListThread extends Thread {
			
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
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    用于保存评论相关的view控件
	 */
	class CommentTextView{
		private TextView commentPublisherNameView;
		private TextView commentReplyLabelView;
		private TextView commentReplyToNameView;
		private TextView commentColonView;
		private TextView commentContentView;
		
		public CommentTextView(TextView commentPublisherNameView,
				TextView commentReplyLabelView,
				TextView commentReplyToNameView, TextView commentColonView,
				TextView commentContentView) {
			super();
			this.commentPublisherNameView = commentPublisherNameView;
			this.commentReplyLabelView = commentReplyLabelView;
			this.commentReplyToNameView = commentReplyToNameView;
			this.commentColonView = commentColonView;
			this.commentContentView = commentContentView;
		}
		public TextView getCommentPublisherNameView() {
			return commentPublisherNameView;
		}
		public void setCommentPublisherNameView(TextView commentPublisherNameView) {
			this.commentPublisherNameView = commentPublisherNameView;
		}
		public TextView getCommentReplyLabelView() {
			return commentReplyLabelView;
		}
		public void setCommentReplyLabelView(TextView commentReplyLabelView) {
			this.commentReplyLabelView = commentReplyLabelView;
		}
		public TextView getCommentReplyToNameView() {
			return commentReplyToNameView;
		}
		public void setCommentReplyToNameView(TextView commentReplyToNameView) {
			this.commentReplyToNameView = commentReplyToNameView;
		}
		public TextView getCommentColonView() {
			return commentColonView;
		}
		public void setCommentColonView(TextView commentColonView) {
			this.commentColonView = commentColonView;
		}
		public TextView getCommentContentView() {
			return commentContentView;
		}
		public void setCommentContentView(TextView commentContentView) {
			this.commentContentView = commentContentView;
		}
		
	}
	
	private List<CommentTextView> getCommentView(View view){
		List<TextView> commentPublisherNameViewList = new ArrayList<TextView>();
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_1));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_2));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_3));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_4));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_5));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_6));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_7));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_8));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_9));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_10));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_11));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_12));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_13));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_14));
		commentPublisherNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_publisher_name_15));

		List<TextView> commentReplyLabelViewList = new ArrayList<TextView>();
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_1));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_2));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_3));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_4));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_5));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_6));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_7));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_8));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_9));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_10));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_11));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_12));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_13));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_14));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_15));
		
		List<TextView> commentReplyToNameViewList = new ArrayList<TextView>();
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_1));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_2));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_3));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_4));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_5));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_6));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_7));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_8));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_9));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_10));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_11));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_12));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_13));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_14));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_15));

		List<TextView> commentColonViewList = new ArrayList<TextView>();
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_1));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_2));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_3));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_4));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_5));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_6));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_7));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_8));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_9));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_10));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_11));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_12));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_13));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_14));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_15));

		List<TextView> commentContentViewList = new ArrayList<TextView>();
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_1));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_2));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_3));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_4));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_5));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_6));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_7));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_8));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_9));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_10));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_11));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_12));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_13));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_14));	
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_15));
		
		List<CommentTextView> commentTextViewlist = new ArrayList<CommentTextView>();
		for(int i=0; i<commentContentViewList.size();++i){
			commentTextViewlist.add(new CommentTextView(commentPublisherNameViewList.get(i),
					commentReplyLabelViewList.get(i),commentReplyToNameViewList.get(i),
					commentColonViewList.get(i),commentContentViewList.get(i)));
		}
		return commentTextViewlist;
	}
}
