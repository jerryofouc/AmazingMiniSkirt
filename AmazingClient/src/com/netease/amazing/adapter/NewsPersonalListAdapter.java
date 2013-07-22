package com.netease.amazing.adapter;

import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.datasource.DataSource;
import com.netease.amazing.datasource.NewsPersonalDataSource;
/**
 * 
 * @author Huang Xiao Jun
 * Class Description：
 *   用于展示个人主页(成长日志)的列表
 *
 */
public class NewsPersonalListAdapter extends ListViewBasedAdapter {


	public NewsPersonalListAdapter(Context context, DataSource dataSource) {
		super(context, dataSource);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = inflater.inflate(R.layout.news_personal_item,null);
		Map<String,Object> m = (Map<String,Object>)getItem(position);
		
		final int itemPosition = position;
		TextView newsPersonalItemJoinClassDaysView = (TextView)view.findViewById(R.id.news_personal_item_join_class_days);
		newsPersonalItemJoinClassDaysView.setText(m.get(NewsPersonalDataSource.NEWS_PERSONAL_ITEM_JOIN_CLASS_DAYS).toString());
		
		TextView newsPersonalItemSaverAndFromView = (TextView)view.findViewById(R.id.news_personal_item_saver_and_from);
		newsPersonalItemSaverAndFromView.setText(m.get(NewsPersonalDataSource.NEWS_PERSONAL_ITEM_SAVER).toString());
		
		ImageView newsPersonalItemImageView = (ImageView)view.findViewById(R.id.news_item_image);
		
//		if(Integer.parseInt(m.get(NewsPersonalDataSource.NEWS_GROWTH_TYPE).toString()) == NewsGrowthLog.NEWS_WITH_IMAGE){
//			//newsPersonalItemImageView.setImageResource((Integer)m.get(NewsPersonalDataSource.NEWS_PERSONAL_ITEM_IMAGE));
//		}else{
//			newsPersonalItemImageView.setVisibility(View.GONE);
//		}

		TextView newsPersonalItemContentView = (TextView)view.findViewById(R.id.news_personal_item_content);
		newsPersonalItemContentView.setText(m.get(NewsPersonalDataSource.NEWS_PERSONAL_ITEM_CONTENT).toString());
		
		TextView newsPersonalItemPublishDateView =(TextView)view.findViewById(R.id.news_personal_item_publish_date);
		newsPersonalItemPublishDateView.setText(m.get(NewsPersonalDataSource.NEWS_PERSONAL_ITEM_PUBLISH_DATE).toString());
				
		TextView newsPersonalItemFromView = (TextView)view.findViewById(R.id.news_personal_item_from);
		newsPersonalItemFromView.setText(m.get(NewsPersonalDataSource.NEWS_PERSONAL_ITEM_FROM).toString());
		
		Button buttonLike = (Button)view.findViewById(R.id.news_personal_item_like);
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
		
		Button buttonDeleteIt = (Button)view.findViewById(R.id.news_personal_item_delete_it);
		buttonDeleteIt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(view.getContext().getApplicationContext(), "已删除" + itemPosition,
					     Toast.LENGTH_SHORT).show();
				/**登录用户删除该动态，刷新列表
				 * 
				 * */
			}
			
		});
		
		Button buttonComment = (Button)view.findViewById(R.id.news_personal_item_comment);
		buttonComment.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(view.getContext().getApplicationContext(), "評論" + itemPosition,
					     Toast.LENGTH_SHORT).show();
				/**登录用户点击评论后弹出对话框接受输入，降评论内容写入数据库评论表中
				 * 
				 * */
			}
			
		});
		
		Button buttonShow = (Button)view.findViewById(R.id.news_personal_item_show_other_buttons);
		buttonShow.setOnClickListener(new ShowButtonsListener(buttonLike, buttonDeleteIt, buttonComment));
		
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

		public ShowButtonsListener(Button buttonLike, Button buttonTakeIt, Button buttonComment){
			this.buttonLike = buttonLike;
			this.buttonTakeIt = buttonTakeIt;
			this.buttonComment = buttonComment;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(IsButtonShow == 0){
				this.buttonLike.setVisibility(View.VISIBLE);
				this.buttonTakeIt.setVisibility(View.VISIBLE);
				this.buttonComment.setVisibility(View.VISIBLE);
				IsButtonShow = 1;
			}else{
				this.buttonLike.setVisibility(View.GONE);
				this.buttonTakeIt.setVisibility(View.GONE);
				this.buttonComment.setVisibility(View.GONE);
				IsButtonShow = 0;
			}
		}
	}
	

	
}
