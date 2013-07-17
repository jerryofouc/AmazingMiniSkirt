package com.netease.amazing.util;
import java.util.Date;

import com.example.amazing.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *    实现上拉，下拉刷新的ListView， 代码来自网上
 */
public class RefreshableListView extends ListView implements OnScrollListener {


	/** 上拉刷新，下拉刷新控制变量 */
	private final static int RELEASE_TO_REFRESH = 0;
	private final static int PULL_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;

	/** 标识上拉下拉刷新的变量 */
	private boolean mIsPullUp = false;
	private boolean mIsPullDown = false;

	/** 实际的padding的距离与界面上偏移距离的比例 */
	private final static int RATIO = 2;

	private LayoutInflater mInflater;
	private Context mContext;

	/** 下拉刷新，上拉刷新组件父布局 */
	private LinearLayout headView;
	private LinearLayout footView;

	/** 下拉刷新组件 */
	private TextView mHeadTipsTextview;
	private TextView mHeadLastUpdatedTextView;
	private ImageView mHeadArrowImageView;
	private ProgressBar mHeadProgressBar;

	/** 上拉刷新组件 */
	private TextView mFootTipsTextview;
	private TextView mFootLastUpdatedTextView;
	private ImageView mFootArrowImageView;
	private ProgressBar mFootProgressBar;

	private RotateAnimation mAnimation;
	private RotateAnimation mReverseAnimation;

	/**用于保证一个完整的touch事件的完成*/
	private boolean mIsMarked;

	/** 头布局和底布局高度 */
	private int headContentHeight;
	private int footContentHeight;

	/**
	 * touch事件开始时Y坐标
	 */
	private int mStartY;
	private int firstItemIndex;

	private int mRefreshState = DONE;

	private boolean isBack;

	private OnRefreshListener refreshListener;

	/**
	 * 是否可下拉上拉刷新
	 */
	private boolean mIsRefreshable;
	private boolean isLastIndex = false;

	public RefreshableListView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public RefreshableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setCacheColorHint(mContext.getResources().getColor(R.color.transparent));
		mInflater = LayoutInflater.from(mContext);
		headView = (LinearLayout) mInflater.inflate(R.layout.list_view_head, null);
		footView = (LinearLayout) mInflater.inflate(R.layout.list_view_bottom, null);

		initHeadView();
		initFootView();
		initAnimation();

		setOnScrollListener(this);
	}

	/**
	 * 初始化下拉刷新布局
	 */
	private void initHeadView() {
		mHeadArrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);

		mHeadArrowImageView.setMinimumWidth(70);
		mHeadArrowImageView.setMinimumHeight(50);

		mHeadProgressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);

		mHeadTipsTextview = (TextView) headView
				.findViewById(R.id.head_tipsTextView);

		mHeadLastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		addHeaderView(headView, null, false);
	}

	/**
	 * 初始化上拉刷新布局
	 */
	private void initFootView() {
		mFootArrowImageView = (ImageView) footView
				.findViewById(R.id.foot_arrowImageView);
		mFootArrowImageView.setMinimumWidth(70);
		mFootArrowImageView.setMinimumHeight(50);
		mFootProgressBar = (ProgressBar) footView
				.findViewById(R.id.foot_progressBar);
		mFootTipsTextview = (TextView) footView
				.findViewById(R.id.foot_tipsTextView);
		mFootLastUpdatedTextView = (TextView) footView
				.findViewById(R.id.foot_lastUpdatedTextView);

		measureView(footView);
		footContentHeight = footView.getMeasuredHeight();

		footView.setPadding(0, 0, 0, -1 * footContentHeight);
		footView.invalidate();

		addFooterView(footView, null, false);
	}

	/**
	 * 初始化动画
	 */
	private void initAnimation() {
		mAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mAnimation.setInterpolator(new LinearInterpolator());
		mAnimation.setDuration(250);
		mAnimation.setFillAfter(true);

		mReverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseAnimation.setInterpolator(new LinearInterpolator());
		mReverseAnimation.setDuration(200);
		mReverseAnimation.setFillAfter(true);
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
			int arg3) {
		firstItemIndex = firstVisiableItem;

		if (firstItemIndex == 0) {
			isLastIndex = false;
		}
	}

	/**
	 * 标识到底部了，可以上拉刷新
	 */
	public void onScrollStateChanged(AbsListView view, int arg1) {
		if (view.getLastVisiblePosition() == view.getCount() - 1) {
			isLastIndex = true;
		}
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (mIsRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!mIsMarked) {
					mIsMarked = true;
					mStartY = (int) event.getY();
				}
				break;

			case MotionEvent.ACTION_UP:
				if (mRefreshState != REFRESHING && mRefreshState != LOADING) {
					if (mRefreshState == DONE) {
						// do nothing
					}
					if (mRefreshState == PULL_TO_REFRESH) {
						mRefreshState = DONE;
						if (mIsPullUp) {
							changeFooterViewBymRefreshState();
						}

						if (mIsPullDown) {
							changeHeaderViewBymRefreshState();
						}
					}
					if (mRefreshState == RELEASE_TO_REFRESH) {
						mRefreshState = REFRESHING;

						if (mIsPullUp) {
							changeFooterViewBymRefreshState();
							onPullUpRefresh();
						}

						if (mIsPullDown) {
							changeHeaderViewBymRefreshState();
							onPullDownRefresh();
						}
					}
				}
				mIsMarked = false;
				isBack = false;
				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();
				if (tempY - mStartY < 0) {
					// 标识上拉刷新
					mIsPullUp = true;

					if (mRefreshState != REFRESHING && mIsMarked
							&& mRefreshState != LOADING) {
						// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在下推的时候，列表会同时进行滚动
						// 可以松手去刷新了
						if (mRefreshState == RELEASE_TO_REFRESH) {

							// setSelection(15);
							// 往下推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
							if (((mStartY - tempY) / RATIO < footContentHeight)
									&& (mStartY - tempY) > 0) {
								mRefreshState = PULL_TO_REFRESH;
								changeFooterViewBymRefreshState();
							}
							// 一下子推到底了
							else if (mStartY - tempY <= 0) {
								mRefreshState = DONE;
								changeFooterViewBymRefreshState();
							}
							// 往上拉了，或者还没有下推到屏幕底部部掩盖foot的地步
							else {
							}
						}
						// 还没有到达显示松开刷新的时候,DONE或者是PULL_TO_REFRESH状态
						if (mRefreshState == PULL_TO_REFRESH && isLastIndex) {
							// setSelection(0);

							// 上拉到可以进入RELEASE_TO_REFRESH的状态
							if ((mStartY - tempY) / RATIO >= footContentHeight) {
								mRefreshState = RELEASE_TO_REFRESH;
								isBack = true;
								changeFooterViewBymRefreshState();
							}
							// 上推到顶了
							else if (mStartY - tempY <= 0) {
								mRefreshState = DONE;
								changeFooterViewBymRefreshState();
							}
						}

						// done状态下
						if (mRefreshState == DONE) {
							if (mStartY - tempY > 0) {
								mRefreshState = PULL_TO_REFRESH;
								changeFooterViewBymRefreshState();
							}
						}

						if (isLastIndex) {
							// 更新footView的size
							if (mRefreshState == PULL_TO_REFRESH) {
								footView.setPadding(0, 0, 0, -1
										* footContentHeight + (mStartY - tempY)
										/ RATIO);

							}

							// 更新footView的paddingTop
							if (mRefreshState == RELEASE_TO_REFRESH) {
								footView.setPadding(0, 0, 0, (mStartY - tempY)
										/ RATIO - footContentHeight);
							}
						}

					}
				} else {
					// 标识下拉刷新
					mIsPullDown = true;

					if (mRefreshState != REFRESHING && mIsMarked
							&& mRefreshState != LOADING) {

						// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

						// 可以松手去刷新了
						if (mRefreshState == RELEASE_TO_REFRESH) {

							// setSelection(0);

							// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
							if (((tempY - mStartY) / RATIO < headContentHeight)
									&& (tempY - mStartY) > 0) {
								mRefreshState = PULL_TO_REFRESH;
								changeHeaderViewBymRefreshState();
							}
							// 一下子推到顶了
							else if (tempY - mStartY <= 0) {
								mRefreshState = DONE;
								changeHeaderViewBymRefreshState();
							}
							// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
							else {
								// 不用进行特别的操作，只用更新paddingTop的值就行了
							}
						}
						// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
						if (mRefreshState == PULL_TO_REFRESH) {
							// setSelection(0);
							// 下拉到可以进入RELEASE_TO_REFRESH的状态
							if ((tempY - mStartY) / RATIO >= headContentHeight) {
								mRefreshState = RELEASE_TO_REFRESH;
								isBack = true;
								changeHeaderViewBymRefreshState();
							}
							// 上推到顶了
							else if (tempY - mStartY <= 0) {
								mRefreshState = DONE;
								changeHeaderViewBymRefreshState();
							}
						}

						// done状态下
						if (mRefreshState == DONE) {
							if (tempY - mStartY > 0) {
								mRefreshState = PULL_TO_REFRESH;
								changeHeaderViewBymRefreshState();
							}
						}

						// 更新headView的size
						if (mRefreshState == PULL_TO_REFRESH) {
							headView.setPadding(0, -1 * headContentHeight
									+ (tempY - mStartY) / RATIO, 0, 0);
						}

						// 更新headView的paddingTop
						if (mRefreshState == RELEASE_TO_REFRESH) {
							headView.setPadding(0, (tempY - mStartY) / RATIO
									- headContentHeight, 0, 0);
						}
					}
				}
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	// 下拉刷新状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewBymRefreshState() {
		switch (mRefreshState) {
		
		case RELEASE_TO_REFRESH:
			mHeadArrowImageView.setVisibility(View.VISIBLE);
			mHeadProgressBar.setVisibility(View.GONE);
			mHeadTipsTextview.setVisibility(View.VISIBLE);
			mHeadLastUpdatedTextView.setVisibility(View.VISIBLE);

			mHeadArrowImageView.clearAnimation();
			mHeadArrowImageView.startAnimation(mAnimation);

			mHeadTipsTextview.setText(mContext
					.getString(R.string.release_refresh));
			break;
			
		case PULL_TO_REFRESH:
			mHeadProgressBar.setVisibility(View.GONE);
			mHeadTipsTextview.setVisibility(View.VISIBLE);
			mHeadLastUpdatedTextView.setVisibility(View.VISIBLE);
			mHeadArrowImageView.clearAnimation();
			mHeadArrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_TO_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				mHeadArrowImageView.clearAnimation();
				mHeadArrowImageView.startAnimation(mReverseAnimation);
				mHeadTipsTextview.setText(mContext
						.getString(R.string.pulldown_refresh));
			} else {
				mHeadTipsTextview.setText(mContext
						.getString(R.string.pulldown_refresh));
			}
			break;

		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);

			mHeadProgressBar.setVisibility(View.VISIBLE);
			mHeadArrowImageView.clearAnimation();
			mHeadArrowImageView.setVisibility(View.GONE);
			mHeadTipsTextview.setText(mContext.getString(R.string.refreshing));
			mHeadLastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
			
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			mHeadProgressBar.setVisibility(View.GONE);
			mHeadArrowImageView.clearAnimation();
			mHeadArrowImageView
					.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			mHeadTipsTextview.setText(mContext
					.getString(R.string.pulldown_refresh));
			mHeadLastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		}
	}

	// 上拉刷新状态改变时候，调用该方法，以更新界面
	private void changeFooterViewBymRefreshState() {
		switch (mRefreshState) {
		
		case RELEASE_TO_REFRESH:
			mFootArrowImageView.setVisibility(View.VISIBLE);
			mFootProgressBar.setVisibility(View.GONE);
			mFootTipsTextview.setVisibility(View.VISIBLE);
			mFootLastUpdatedTextView.setVisibility(View.VISIBLE);

			mFootArrowImageView.clearAnimation();
			mFootArrowImageView.startAnimation(mAnimation);

			mFootTipsTextview.setText(mContext
					.getString(R.string.release_refresh));
			break;
			
		case PULL_TO_REFRESH:
			mFootProgressBar.setVisibility(View.GONE);
			mFootTipsTextview.setVisibility(View.VISIBLE);
			mFootLastUpdatedTextView.setVisibility(View.VISIBLE);
			mFootArrowImageView.clearAnimation();
			mFootArrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_TO_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				mFootArrowImageView.clearAnimation();
				mFootArrowImageView.startAnimation(mReverseAnimation);
				mFootTipsTextview.setText(mContext
						.getString(R.string.pullup_refresh));
			} else {
				mFootTipsTextview.setText(mContext
						.getString(R.string.pullup_refresh));
			}
			break;

		case REFRESHING:
			footView.setPadding(0, 0, 0, 0);

			mFootProgressBar.setVisibility(View.VISIBLE);
			mFootArrowImageView.clearAnimation();
			mFootArrowImageView.setVisibility(View.GONE);
			mFootTipsTextview.setText(mContext.getString(R.string.refreshing));
			mFootLastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		case DONE:
			footView.setPadding(0, 0, 0, -1 * footContentHeight);

			mFootProgressBar.setVisibility(View.GONE);
			mFootArrowImageView.clearAnimation();
			mFootArrowImageView
					.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			mFootTipsTextview.setText(mContext
					.getString(R.string.pullup_refresh));
			mFootLastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		mIsRefreshable = true;
	}

	/**
	 * 刷新回调接口
	 * @author APPLE
	 *
	 */
	public interface OnRefreshListener {
		public void onPullUpRefresh();

		public void onPullDownRefresh();
	}

	/**
	 * 刷新完成
	 */
	public void onRefreshComplete() {
		mRefreshState = DONE;
		String localTime = new Date().toLocaleString();
		if (mIsPullDown) {
			mHeadLastUpdatedTextView.setText(mContext
					.getString(R.string.recently_modified) + localTime);
			changeHeaderViewBymRefreshState();
		}

		if (mIsPullUp) {
			mFootLastUpdatedTextView.setText(mContext
					.getString(R.string.recently_modified) + localTime);
			changeFooterViewBymRefreshState();
		}

		// 刷新完成后还原上拉下拉标识位以及是否是listview底部标识
		mIsPullUp = false;
		mIsPullDown = false;
		isLastIndex = false;
	}

	/**
	 * 回调下拉刷新
	 */
	private void onPullDownRefresh() {
		if (refreshListener != null) {
			refreshListener.onPullDownRefresh();
		}
	}
	/**
	 * 回调上拉刷新
	 */
	private void onPullUpRefresh() {
		if (refreshListener != null) {
			refreshListener.onPullDownRefresh();
		}
	}

	/**测量控件尺寸*/
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 设置数据源Adapter
	 * @param adapter
	 */
	public void setAdapter(BaseAdapter adapter) {
		String localTime = new Date().toLocaleString();

		mHeadLastUpdatedTextView.setText(mContext
				.getString(R.string.recently_modified) + localTime);

		mFootLastUpdatedTextView.setText(mContext
				.getString(R.string.recently_modified) + localTime);
		super.setAdapter(adapter);
	}

}
