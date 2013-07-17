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
 *    ʵ������������ˢ�µ�ListView�� ������������
 */
public class RefreshableListView extends ListView implements OnScrollListener {


	/** ����ˢ�£�����ˢ�¿��Ʊ��� */
	private final static int RELEASE_TO_REFRESH = 0;
	private final static int PULL_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;

	/** ��ʶ��������ˢ�µı��� */
	private boolean mIsPullUp = false;
	private boolean mIsPullDown = false;

	/** ʵ�ʵ�padding�ľ����������ƫ�ƾ���ı��� */
	private final static int RATIO = 2;

	private LayoutInflater mInflater;
	private Context mContext;

	/** ����ˢ�£�����ˢ����������� */
	private LinearLayout headView;
	private LinearLayout footView;

	/** ����ˢ����� */
	private TextView mHeadTipsTextview;
	private TextView mHeadLastUpdatedTextView;
	private ImageView mHeadArrowImageView;
	private ProgressBar mHeadProgressBar;

	/** ����ˢ����� */
	private TextView mFootTipsTextview;
	private TextView mFootLastUpdatedTextView;
	private ImageView mFootArrowImageView;
	private ProgressBar mFootProgressBar;

	private RotateAnimation mAnimation;
	private RotateAnimation mReverseAnimation;

	/**���ڱ�֤һ��������touch�¼������*/
	private boolean mIsMarked;

	/** ͷ���ֺ͵ײ��ָ߶� */
	private int headContentHeight;
	private int footContentHeight;

	/**
	 * touch�¼���ʼʱY����
	 */
	private int mStartY;
	private int firstItemIndex;

	private int mRefreshState = DONE;

	private boolean isBack;

	private OnRefreshListener refreshListener;

	/**
	 * �Ƿ����������ˢ��
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
	 * ��ʼ��View
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
	 * ��ʼ������ˢ�²���
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
	 * ��ʼ������ˢ�²���
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
	 * ��ʼ������
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
	 * ��ʶ���ײ��ˣ���������ˢ��
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
					// ��ʶ����ˢ��
					mIsPullUp = true;

					if (mRefreshState != REFRESHING && mIsMarked
							&& mRefreshState != LOADING) {
						// ��֤������padding�Ĺ����У���ǰ��λ��һֱ����head������������б�����Ļ�Ļ����������Ƶ�ʱ���б��ͬʱ���й���
						// ��������ȥˢ����
						if (mRefreshState == RELEASE_TO_REFRESH) {

							// setSelection(15);
							// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
							if (((mStartY - tempY) / RATIO < footContentHeight)
									&& (mStartY - tempY) > 0) {
								mRefreshState = PULL_TO_REFRESH;
								changeFooterViewBymRefreshState();
							}
							// һ�����Ƶ�����
							else if (mStartY - tempY <= 0) {
								mRefreshState = DONE;
								changeFooterViewBymRefreshState();
							}
							// �������ˣ����߻�û�����Ƶ���Ļ�ײ����ڸ�foot�ĵز�
							else {
							}
						}
						// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,DONE������PULL_TO_REFRESH״̬
						if (mRefreshState == PULL_TO_REFRESH && isLastIndex) {
							// setSelection(0);

							// ���������Խ���RELEASE_TO_REFRESH��״̬
							if ((mStartY - tempY) / RATIO >= footContentHeight) {
								mRefreshState = RELEASE_TO_REFRESH;
								isBack = true;
								changeFooterViewBymRefreshState();
							}
							// ���Ƶ�����
							else if (mStartY - tempY <= 0) {
								mRefreshState = DONE;
								changeFooterViewBymRefreshState();
							}
						}

						// done״̬��
						if (mRefreshState == DONE) {
							if (mStartY - tempY > 0) {
								mRefreshState = PULL_TO_REFRESH;
								changeFooterViewBymRefreshState();
							}
						}

						if (isLastIndex) {
							// ����footView��size
							if (mRefreshState == PULL_TO_REFRESH) {
								footView.setPadding(0, 0, 0, -1
										* footContentHeight + (mStartY - tempY)
										/ RATIO);

							}

							// ����footView��paddingTop
							if (mRefreshState == RELEASE_TO_REFRESH) {
								footView.setPadding(0, 0, 0, (mStartY - tempY)
										/ RATIO - footContentHeight);
							}
						}

					}
				} else {
					// ��ʶ����ˢ��
					mIsPullDown = true;

					if (mRefreshState != REFRESHING && mIsMarked
							&& mRefreshState != LOADING) {

						// ��֤������padding�Ĺ����У���ǰ��λ��һֱ����head������������б�����Ļ�Ļ����������Ƶ�ʱ���б��ͬʱ���й���

						// ��������ȥˢ����
						if (mRefreshState == RELEASE_TO_REFRESH) {

							// setSelection(0);

							// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
							if (((tempY - mStartY) / RATIO < headContentHeight)
									&& (tempY - mStartY) > 0) {
								mRefreshState = PULL_TO_REFRESH;
								changeHeaderViewBymRefreshState();
							}
							// һ�����Ƶ�����
							else if (tempY - mStartY <= 0) {
								mRefreshState = DONE;
								changeHeaderViewBymRefreshState();
							}
							// �������ˣ����߻�û�����Ƶ���Ļ�����ڸ�head�ĵز�
							else {
								// ���ý����ر�Ĳ�����ֻ�ø���paddingTop��ֵ������
							}
						}
						// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,DONE������PULL_To_REFRESH״̬
						if (mRefreshState == PULL_TO_REFRESH) {
							// setSelection(0);
							// ���������Խ���RELEASE_TO_REFRESH��״̬
							if ((tempY - mStartY) / RATIO >= headContentHeight) {
								mRefreshState = RELEASE_TO_REFRESH;
								isBack = true;
								changeHeaderViewBymRefreshState();
							}
							// ���Ƶ�����
							else if (tempY - mStartY <= 0) {
								mRefreshState = DONE;
								changeHeaderViewBymRefreshState();
							}
						}

						// done״̬��
						if (mRefreshState == DONE) {
							if (tempY - mStartY > 0) {
								mRefreshState = PULL_TO_REFRESH;
								changeHeaderViewBymRefreshState();
							}
						}

						// ����headView��size
						if (mRefreshState == PULL_TO_REFRESH) {
							headView.setPadding(0, -1 * headContentHeight
									+ (tempY - mStartY) / RATIO, 0, 0);
						}

						// ����headView��paddingTop
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

	// ����ˢ��״̬�ı�ʱ�򣬵��ø÷������Ը��½���
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
			// ����RELEASE_TO_REFRESH״̬ת������
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

	// ����ˢ��״̬�ı�ʱ�򣬵��ø÷������Ը��½���
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
			// ����RELEASE_TO_REFRESH״̬ת������
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
	 * ˢ�»ص��ӿ�
	 * @author APPLE
	 *
	 */
	public interface OnRefreshListener {
		public void onPullUpRefresh();

		public void onPullDownRefresh();
	}

	/**
	 * ˢ�����
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

		// ˢ����ɺ�ԭ����������ʶλ�Լ��Ƿ���listview�ײ���ʶ
		mIsPullUp = false;
		mIsPullDown = false;
		isLastIndex = false;
	}

	/**
	 * �ص�����ˢ��
	 */
	private void onPullDownRefresh() {
		if (refreshListener != null) {
			refreshListener.onPullDownRefresh();
		}
	}
	/**
	 * �ص�����ˢ��
	 */
	private void onPullUpRefresh() {
		if (refreshListener != null) {
			refreshListener.onPullDownRefresh();
		}
	}

	/**�����ؼ��ߴ�*/
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
	 * ��������ԴAdapter
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
