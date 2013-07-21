package com.netease.amazing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
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
		
		final long newsId = Long.parseLong(m.get(NewsDataSource.NEWS_ID).toString());
		final int itemPosition = position;
		ImageView newsSenderImageView = (ImageView)view.findViewById(R.id.news_item_sender_image);
		newsSenderImageView.setImageResource((Integer)m.get(NewsDataSource.NEWS_PUBLISHER_IMAGE));
		
		TextView newsSenderNameView = (TextView)view.findViewById(R.id.news_item_sender);
		newsSenderNameView.setText(m.get(NewsDataSource.NEWS_PUBLISHER_NAME).toString());
		
		TextView newsContentView =(TextView)view.findViewById(R.id.news_item_content);
		newsContentView.setText(m.get(NewsDataSource.NEWS_CONTENT).toString());
		
		ImageView newsContentImageView = (ImageView)view.findViewById(R.id.news_item_image);
		if(m.get(NewsDataSource.NEWS_WITH_IMAGE)!=null){
			newsContentImageView.setImageResource((Integer)m.get(NewsDataSource.NEWS_WITH_IMAGE));
		}else{
			newsContentImageView.setVisibility(View.GONE);
		}
		
		TextView newsSendDateView = (TextView)view.findViewById(R.id.news_item_publish_date);
		newsSendDateView.setText(m.get(NewsDataSource.NEWS_PUBLISH_DATE).toString());
		
		TextView newsSenderFromView = (TextView)view.findViewById(R.id.news_item_from);
		newsSenderFromView.setText(m.get(NewsDataSource.NEWS_PUBLISHER_FROM).toString());
		
		List<CommentTextView> commentTextViewList = getCommentView(view);
		
		Button buttonLike = (Button)view.findViewById(R.id.news_item_like);
		if((Boolean)m.get(NewsDataSource.NEWS_CURRENT_USER_LIKE)){
			buttonLike.setClickable(false);
		}else{
			buttonLike.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view){
					/**登录用户喜欢该动态，写入数据库中动态评论表中，并且返回最新的前8条评论
					 * 
					 * */
					new LikeNewsCommentExecuteTask(getCommentView(view),
							view.getContext().getApplicationContext(), 
							(Button)view.findViewById(R.id.news_item_like)).execute(newsId);
				}
				
			});
		}
		
		
		Button buttonTakeIt = (Button)view.findViewById(R.id.news_item_take_it);
		if((Boolean)m.get(NewsDataSource.NEWS_CURRENT_USER_TAKE_DOWN)){
			buttonTakeIt.setClickable(false);
		}else{
			buttonTakeIt.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view){
					/**登录用户收录该动态，写入数据库中动态评论表中，并且返回最新的前8条评论
					 * 
					 * */
					new TakeDownNewsCommentExecuteTask(getCommentView(view),
							view.getContext().getApplicationContext(), 
							(Button)view.findViewById(R.id.news_item_like)).execute(newsId);		
				}
				
			});
		}
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
		buttonShow.setOnClickListener(new ShowButtonsListener(newsId,buttonLike, buttonTakeIt, buttonComment,commentTextViewList));
		
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
		private long newsId;
		public ShowButtonsListener(long newsId,Button buttonLike, Button buttonTakeIt, Button buttonComment, List<CommentTextView> commentTextViewList){
			this.newsId = newsId;
			this.buttonLike = buttonLike;
			this.buttonTakeIt = buttonTakeIt;
			this.buttonComment = buttonComment;
			this.commentTextViewList = commentTextViewList;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(IsButtonShow == 0){
				new GetInitNewsCommentDataTask(commentTextViewList).execute(newsId);
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
		
	}
	
	private void setCommentTextView(CommentTextView viewTempStructure, NewsComment newsComment){
		viewTempStructure.getCommentPublisherNameView().setVisibility(View.VISIBLE);
		viewTempStructure.getCommentPublisherNameView().setText(newsComment.getNewsCommmentPublisherName());

		//viewTempStructure.getCommentReplyLabelView().setVisibility(View.VISIBLE);
		//viewTempStructure.getCommentReplyToNameView().setVisibility(View.VISIBLE);
		viewTempStructure.getCommentColonView().setVisibility(View.VISIBLE);
		viewTempStructure.getCommentContentView().setVisibility(View.VISIBLE);
		if(newsComment.getNewsCommentType() == NewsComment.NEWS_COMMENT_TYPE_LIKE){
			viewTempStructure.getCommentContentView().setText("喜欢这个动态！");	
			viewTempStructure.getCommentContentView().setTextColor(R.color.light_blue);
		}else if(newsComment.getNewsCommentType() == NewsComment.NEWS_COMMENT_TYPE_TAKE_DOWN){
			viewTempStructure.getCommentContentView().setText("收录了这个动态");	
			viewTempStructure.getCommentContentView().setTextColor(R.color.light_green);
		}else{
			viewTempStructure.getCommentContentView().setText(newsComment.getNewsComment());	
		}
	}
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    获取评论内容的线程
	 */
	class GetInitNewsCommentDataTask extends AsyncTask<Long,String,List<NewsComment>> {
		private List<CommentTextView> commentTextViewList;
		GetInitNewsCommentDataTask(List<CommentTextView> commentTextViewList){
			this.commentTextViewList = commentTextViewList;
		}

		@Override
		protected List<NewsComment> doInBackground(Long... arg0) {
			List<NewsComment> newsCommentList = NewsDataHandler.getNewsCommentToNewsIndexByNewsId(
					arg0[0].longValue(), NewsComment.NEWS_COMMENT_COUNT_FOR_INDEX);				
			return newsCommentList;
		}
		
		@Override
		protected void onPostExecute(List<NewsComment> result) {
			super.onPostExecute(result);
			CommentTextView viewTempStructure ;
			NewsComment newsComment;
			for(int i=0; i<result.size(); ++i){
				viewTempStructure = commentTextViewList.get(i);
				newsComment = result.get(i);
				setCommentTextView(viewTempStructure, newsComment);
			}
		}
		
	}
	
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    添加喜欢这个动态的操作
	 */
	class LikeNewsCommentExecuteTask extends AsyncTask<Long,String,List<NewsComment>> {
		private List<CommentTextView> commentTextViewList;
		private boolean isLikeNewsExecuteSuccess = false;
		private Context context;
		private Button button;
		LikeNewsCommentExecuteTask(List<CommentTextView> commentTextViewList ,Context context, Button button){
			this.commentTextViewList = commentTextViewList;
			this.context = context;
			this.button = button;
		}

		@Override
		protected List<NewsComment> doInBackground(Long... arg0) {
			isLikeNewsExecuteSuccess = NewsDataHandler.setLikeNews(arg0[0].longValue());
			List<NewsComment> newsCommentList = NewsDataHandler.getNewsCommentToNewsIndexByNewsId(
					arg0[0], NewsComment.NEWS_COMMENT_COUNT_FOR_INDEX);				
			return newsCommentList;
		}
		
		@Override
		protected void onPostExecute(List<NewsComment> result) {
			super.onPostExecute(result);
			CommentTextView viewTempStructure ;
			NewsComment newsComment;
			for(int i=0; i<result.size(); ++i){
				viewTempStructure = commentTextViewList.get(i);
				newsComment = result.get(i);
				setCommentTextView(viewTempStructure, newsComment);
			}
			if(isLikeNewsExecuteSuccess){
				Toast.makeText(context, "已添加至喜歡",Toast.LENGTH_SHORT).show();
				button.setClickable(false);
			}else{
				Toast.makeText(context, "操作失败",Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    添加收录这个动态的操作
	 */
	class TakeDownNewsCommentExecuteTask extends AsyncTask<Long,String,List<NewsComment>> {
		private List<CommentTextView> commentTextViewList;
		private boolean isLikeNewsExecuteSuccess = false;
		private Context context;
		private Button button;
		TakeDownNewsCommentExecuteTask(List<CommentTextView> commentTextViewList ,Context context, Button button){
			this.commentTextViewList = commentTextViewList;
			this.context = context;
			this.button = button;
		}

		@Override
		protected List<NewsComment> doInBackground(Long... arg0) {
			isLikeNewsExecuteSuccess = NewsDataHandler.setTakeDownNews(arg0[0].longValue());
			List<NewsComment> newsCommentList = NewsDataHandler.getNewsCommentToNewsIndexByNewsId(
					arg0[0], NewsComment.NEWS_COMMENT_COUNT_FOR_INDEX);				
			return newsCommentList;
		}
		
		@Override
		protected void onPostExecute(List<NewsComment> result) {
			super.onPostExecute(result);
			CommentTextView viewTempStructure ;
			NewsComment newsComment;
			for(int i=0; i<result.size(); ++i){
				viewTempStructure = commentTextViewList.get(i);
				newsComment = result.get(i);
				setCommentTextView(viewTempStructure, newsComment);
			}
			if(isLikeNewsExecuteSuccess){
				Toast.makeText(context, "已收录该动态",Toast.LENGTH_SHORT).show();
				button.setClickable(false);
			}else{
				Toast.makeText(context, "操作失败",Toast.LENGTH_SHORT).show();
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


		List<TextView> commentReplyLabelViewList = new ArrayList<TextView>();
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_1));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_2));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_3));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_4));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_5));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_6));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_7));
		commentReplyLabelViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_label_8));

		
		List<TextView> commentReplyToNameViewList = new ArrayList<TextView>();
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_1));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_2));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_3));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_4));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_5));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_6));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_7));
		commentReplyToNameViewList.add((TextView)view.findViewById(R.id.news_item_comment_reply_to_name_8));


		List<TextView> commentColonViewList = new ArrayList<TextView>();
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_1));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_2));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_3));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_4));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_5));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_6));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_7));
		commentColonViewList.add((TextView)view.findViewById(R.id.news_item_comment_colon_8));

		List<TextView> commentContentViewList = new ArrayList<TextView>();
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_1));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_2));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_3));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_4));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_5));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_6));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_7));
		commentContentViewList.add((TextView)view.findViewById(R.id.news_item_comment_content_8));
		
		List<CommentTextView> commentTextViewlist = new ArrayList<CommentTextView>();
		for(int i=0; i<commentContentViewList.size();++i){
			commentTextViewlist.add(new CommentTextView(commentPublisherNameViewList.get(i),
					commentReplyLabelViewList.get(i),commentReplyToNameViewList.get(i),
					commentColonViewList.get(i),commentContentViewList.get(i)));
		}
		return commentTextViewlist;
	}
}
