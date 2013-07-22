package com.netease.amazing.adapter;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.datasource.DataSource;
import com.netease.amazing.datasource.NewsDataSource;
import com.netease.amazing.dbhandler.NewsDataHandler;
import com.netease.amazing.pojo.NewsComment;
import com.netease.amazing.util.InitImageView;

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
		
		TextView newsSenderNameView = (TextView)view.findViewById(R.id.news_item_sender);
		newsSenderNameView.setText(m.get(NewsDataSource.NEWS_PUBLISHER_NAME).toString());
		
		TextView newsContentView =(TextView)view.findViewById(R.id.news_item_content);
		newsContentView.setText(m.get(NewsDataSource.NEWS_CONTENT).toString());
		
//		ImageView newsContentImageView = (ImageView)view.findViewById(R.id.news_item_image);
//		if(m.get(NewsDataSource.NEWS_WITH_IMAGE)!=null){
//			newsContentImageView.setImageResource((Integer)m.get(NewsDataSource.NEWS_WITH_IMAGE));
//		}else{
//			newsContentImageView.setVisibility(View.GONE);
//		}
		ImageView newsPublisherImgView = (ImageView) view.findViewById(R.id.news_item_sender_image);
		new InitImageView(newsPublisherImgView).execute(m.get(NewsDataSource.NEWS_PUBLISHER_IMAGE).toString());
		
		TextView newsSendDateView = (TextView)view.findViewById(R.id.news_item_publish_date);
		newsSendDateView.setText(m.get(NewsDataSource.NEWS_PUBLISH_DATE).toString());
		
		TextView newsSenderFromView = (TextView)view.findViewById(R.id.news_item_from);
		newsSenderFromView.setText(m.get(NewsDataSource.NEWS_PUBLISHER_FROM).toString());
		
		boolean isNewsCurrentUserLike = (Boolean)m.get(NewsDataSource.NEWS_CURRENT_USER_LIKE);
		boolean isNewsCurrentUserTakeDown = (Boolean)m.get(NewsDataSource.NEWS_CURRENT_USER_TAKE_DOWN);
				
		final List<CommentTextView> commentTextViewList = getCommentView(view);
		
		final Button buttonLike = (Button)view.findViewById(R.id.news_item_like);
		final Button buttonTakeIt = (Button)view.findViewById(R.id.news_item_take_it);
		Button buttonShow = (Button)view.findViewById(R.id.news_item_show_other_buttons);
		Button buttonComment = (Button)view.findViewById(R.id.news_item_comment);
		
		final OnClickListener listener = new ShowButtonsListener(isNewsCurrentUserLike,isNewsCurrentUserTakeDown,
				newsId,buttonLike, buttonTakeIt, buttonComment,buttonShow,commentTextViewList);
		

		buttonLike.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				/**��¼�û�ϲ���ö�̬��д�����ݿ��ж�̬���۱��У����ҷ������µ�ǰ8������
				 * 
				 * */
				new LikeNewsCommentExecuteTask(listener,commentTextViewList,
						view.getContext().getApplicationContext(), 
						buttonLike).execute(newsId);
			}
			
		});
		
		

		buttonTakeIt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				/**��¼�û���¼�ö�̬��д�����ݿ��ж�̬���۱��У����ҷ������µ�ǰ8������
				 * 
				 * */
				new TakeDownNewsCommentExecuteTask(listener,commentTextViewList,
						view.getContext().getApplicationContext(), 
						buttonTakeIt).execute(newsId);		
			}
			
		});

		buttonComment.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(view.getContext().getApplicationContext(), "�uՓ" + itemPosition,
					     Toast.LENGTH_SHORT).show();
				/**��¼�û�������ۺ󵯳��Ի���������룬����������д�����ݿ����۱��У�������ǰ15����������
				 * 
				 * */
			}
			
		});
		
		buttonShow.setOnClickListener(listener);
		return view;
	}
	
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 * 		�����Ƿ���ʾϲ������¼�����۵ȹ��ܰ�����button�㿪�󣬻�ȡ���ݿ����۱������µ�ǰ15��������ʾ
	 *
	 */
	private class ShowButtonsListener implements OnClickListener{
		private int IsButtonShow=0;
		private Button buttonLike;
		private Button buttonTakeIt;
		private Button buttonComment;
		private Button buttonShow;
		private boolean isCurrentUserLike;
		private boolean isCurrentUserTakeDown;
		private List<CommentTextView> commentTextViewList;
		private long newsId;
		public ShowButtonsListener(boolean isCurrentUserLike, boolean isCurrentUserTakeDown, 
				long newsId,Button buttonLike, Button buttonTakeIt,
				Button buttonComment, Button buttonShow, List<CommentTextView> commentTextViewList){
			this.newsId = newsId;
			this.buttonLike = buttonLike;
			this.buttonTakeIt = buttonTakeIt;
			this.buttonComment = buttonComment;
			this.commentTextViewList = commentTextViewList;
			this.isCurrentUserLike = isCurrentUserLike;
			this.isCurrentUserTakeDown = isCurrentUserTakeDown;
			this.buttonShow = buttonShow;
		}
		public void setIsCurrentUserLike(boolean isCurrentUserLike){
			this.isCurrentUserLike = isCurrentUserLike;
		}
		public void setIsCurrentUserTakeDown(boolean isCurrentUserTakeDown){
			this.isCurrentUserTakeDown = isCurrentUserTakeDown;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(IsButtonShow == 0){
				new GetInitNewsCommentDataTask(commentTextViewList).execute(newsId);
				this.buttonLike.setVisibility(View.VISIBLE);
				this.buttonTakeIt.setVisibility(View.VISIBLE);
				if(this.isCurrentUserLike){
					this.buttonLike.setClickable(false);
				}
				if(this.isCurrentUserTakeDown){
					this.buttonTakeIt.setClickable(false);
				}
				this.buttonComment.setVisibility(View.VISIBLE);
				this.buttonShow.setBackgroundResource(R.drawable.location_btn_down46x46);
				IsButtonShow = 1;
			}else{
				this.buttonLike.setVisibility(View.GONE);
				this.buttonTakeIt.setVisibility(View.GONE);
				this.buttonComment.setVisibility(View.GONE);
				for(CommentTextView commentTextView:commentTextViewList){
					commentTextView.getCommentLinearLayoutView().setVisibility(View.GONE);
					commentTextView.getCommentPublisherNameView().setVisibility(View.GONE);
					commentTextView.getCommentReplyLabelView().setVisibility(View.GONE);
					commentTextView.getCommentReplyToNameView().setVisibility(View.GONE);
					commentTextView.getCommentColonView().setVisibility(View.GONE);
					commentTextView.getCommentContentView().setVisibility(View.GONE);
				}
				this.buttonShow.setBackgroundResource(R.drawable.location_btn_left46x46);
				IsButtonShow = 0;
			}
		}
		
	}
	
	private void setCommentTextView(CommentTextView viewTempStructure, NewsComment newsComment){
		viewTempStructure.getCommentLinearLayoutView().setVisibility(View.VISIBLE);
		viewTempStructure.getCommentPublisherNameView().setVisibility(View.VISIBLE);
		viewTempStructure.getCommentPublisherNameView().setText(newsComment.getNewsCommmentPublisherName());

		//viewTempStructure.getCommentReplyLabelView().setVisibility(View.VISIBLE);
		//viewTempStructure.getCommentReplyToNameView().setVisibility(View.VISIBLE);
		viewTempStructure.getCommentColonView().setVisibility(View.VISIBLE);
		viewTempStructure.getCommentContentView().setVisibility(View.VISIBLE);
		if(newsComment.getNewsCommentType() == NewsComment.NEWS_COMMENT_TYPE_LIKE){
			viewTempStructure.getCommentContentView().setText("ϲ�������̬��");	
			viewTempStructure.getCommentContentView().setTextColor(R.color.light_blue);
		}else if(newsComment.getNewsCommentType() == NewsComment.NEWS_COMMENT_TYPE_TAKE_DOWN){
			viewTempStructure.getCommentContentView().setText("��¼�������̬");	
			viewTempStructure.getCommentContentView().setTextColor(R.color.light_green);
		}else{
			viewTempStructure.getCommentContentView().setText(newsComment.getNewsComment());	
		}
	}
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    ��ȡ�������ݵ��߳�
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
	 *    ���ϲ�������̬�Ĳ���
	 */
	class LikeNewsCommentExecuteTask extends AsyncTask<Long,String,List<NewsComment>> {
		private List<CommentTextView> commentTextViewList;
		private boolean isLikeNewsExecuteSuccess = false;
		private Context context;
		private Button button;
		private OnClickListener clickListener;
		LikeNewsCommentExecuteTask(OnClickListener clickListener, List<CommentTextView> commentTextViewList ,Context context, Button button){
			this.commentTextViewList = commentTextViewList;
			this.context = context;
			this.button = button;
			this.clickListener = clickListener;
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
				Toast.makeText(context, "�������ϲ�g",Toast.LENGTH_SHORT).show();
				button.setClickable(false);
				((ShowButtonsListener)clickListener).setIsCurrentUserLike(true);
			}else{
				Toast.makeText(context, "����ʧ��",Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    �����¼�����̬�Ĳ���
	 */
	class TakeDownNewsCommentExecuteTask extends AsyncTask<Long,String,List<NewsComment>> {
		private List<CommentTextView> commentTextViewList;
		private boolean isLikeNewsExecuteSuccess = false;
		private Context context;
		private Button button;
		private OnClickListener clickListener;
		TakeDownNewsCommentExecuteTask(OnClickListener clickListener,List<CommentTextView> commentTextViewList ,Context context, Button button){
			this.commentTextViewList = commentTextViewList;
			this.context = context;
			this.button = button;
			this.clickListener = clickListener;
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
				Toast.makeText(context, "����¼�ö�̬",Toast.LENGTH_SHORT).show();
				button.setClickable(false);
				((ShowButtonsListener)clickListener).setIsCurrentUserTakeDown(true);
			}else{
				Toast.makeText(context, "����ʧ��",Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	/**
	 * 
	 * @author Huang Xiao Jun
	 * Class Description:
	 *    ���ڱ���������ص�view�ؼ�
	 */
	class CommentTextView{
		private TextView commentPublisherNameView;
		private TextView commentReplyLabelView;
		private TextView commentReplyToNameView;
		private TextView commentColonView;
		private TextView commentContentView;
		private LinearLayout commentLinearLayoutView;
		
		public CommentTextView(TextView commentPublisherNameView,
				TextView commentReplyLabelView,
				TextView commentReplyToNameView, TextView commentColonView,
				TextView commentContentView, LinearLayout commentLinearLayoutView) {
			super();
			this.commentPublisherNameView = commentPublisherNameView;
			this.commentReplyLabelView = commentReplyLabelView;
			this.commentReplyToNameView = commentReplyToNameView;
			this.commentColonView = commentColonView;
			this.commentContentView = commentContentView;
			this.commentLinearLayoutView = commentLinearLayoutView;
		}
		
		public LinearLayout getCommentLinearLayoutView() {
			return commentLinearLayoutView;
		}

		public void setCommentLinearLayoutView(LinearLayout commentLinearLayoutView) {
			this.commentLinearLayoutView = commentLinearLayoutView;
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

		List<LinearLayout> linearLayoutViewList = new ArrayList<LinearLayout>();
		linearLayoutViewList.add((LinearLayout)view.findViewById(R.id.news_item_comment_linearlayout_1));
		linearLayoutViewList.add((LinearLayout)view.findViewById(R.id.news_item_comment_linearlayout_2));
		linearLayoutViewList.add((LinearLayout)view.findViewById(R.id.news_item_comment_linearlayout_3));
		linearLayoutViewList.add((LinearLayout)view.findViewById(R.id.news_item_comment_linearlayout_4));
		linearLayoutViewList.add((LinearLayout)view.findViewById(R.id.news_item_comment_linearlayout_5));
		linearLayoutViewList.add((LinearLayout)view.findViewById(R.id.news_item_comment_linearlayout_6));
		linearLayoutViewList.add((LinearLayout)view.findViewById(R.id.news_item_comment_linearlayout_7));
		linearLayoutViewList.add((LinearLayout)view.findViewById(R.id.news_item_comment_linearlayout_8));

		List<CommentTextView> commentTextViewlist = new ArrayList<CommentTextView>();
		for(int i=0; i<commentContentViewList.size();++i){
			commentTextViewlist.add(new CommentTextView(commentPublisherNameViewList.get(i),
					commentReplyLabelViewList.get(i),commentReplyToNameViewList.get(i),
					commentColonViewList.get(i),commentContentViewList.get(i),linearLayoutViewList.get(i)));
		}
		return commentTextViewlist;
	}
}
