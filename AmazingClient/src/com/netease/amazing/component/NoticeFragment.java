package com.netease.amazing.component;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.example.amazing.R;
import com.netease.amazing.util.DataSource1;
import com.netease.amazing.util.ListViewBasedAdapter;
import com.netease.amazing.util.ListViewBasedAdapter1;
import com.netease.amazing.util.NoticeDataSource;
import com.netease.amazing.util.NoticeListAdapter;
import com.netease.amazing.util.RefreshableListView;
import com.netease.amazing.util.RefreshableListView.OnRefreshListener;

/**
 * 
 * @author Huang Xiao Jun
 * Class Desciption:
 *   ListViewFragment����б��@ʾ�����Ұ�������������ˢ�¹��ܣ�푑�item�c���¼�
 *
 */
public class NoticeFragment extends Fragment implements OnRefreshListener {
	
	private RefreshableListView mRefreshListView;
	private DataSource1 mDataSource = new NoticeDataSource();
	private ListViewBasedAdapter1 listAdapter;
	private final static int LIST_VIEW_PAGE_SIZE = 10;
	
	private int fragmentLayout = R.layout.fragment2;   //fragment�Ĳ���
	private int viewListLayout = R.id.mineList;   //viewList�Ĳ���
	
	private OnItemClickListener itemClickListener; //itemClick��Ӧ�¼�
	private MyListViewFragmentHandler fragmentHandler = new MyListViewFragmentHandler();
	private ProgressDialog proDialog;
	/**
	 * 
	 * @param fragmentLayout fragment����
	 * @param viewListLayout viewList����
	 * @param dataSource ����Դ
	 * @param adapter �m�������
	 * @param itemClickListener 푑���item�¼�
	 */
	public void set(int fragmentLayout, 
			int viewListLayout, 
			DataSource1 dataSource,
			OnItemClickListener itemClickListener){
		this.fragmentLayout = fragmentLayout;
		this.viewListLayout = viewListLayout;
		this.mDataSource = dataSource;
		this.itemClickListener = itemClickListener;
	}
	
	public void set(ListViewBasedAdapter1 listAdapter,OnItemClickListener itemClickLister) {
		this.listAdapter = listAdapter;
		this.itemClickListener = itemClickListener;
	}
	
	/**/
	class GetInitDataThread extends Thread {
		
		MyListViewFragmentHandler handler;
		
		GetInitDataThread(MyListViewFragmentHandler handler) {
			this.handler = handler;
		}
		
		@Override 
		public void run() {
			mDataSource.updateValue(0);
			//mDataSource.toMapList();
			listAdapter = new NoticeListAdapter(getActivity(), mDataSource);
			set(listAdapter,itemClickListener);
			mRefreshListView.setAdapter(listAdapter);
			handler.sendEmptyMessage(1);
		}
	}
	
	class MyListViewFragmentHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//��������С����Ի����� dismiss��
			if(proDialog != null) {
				proDialog.dismiss();
			}
		}
		
	}
	/**/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		if (container == null) {
			return null;
		}
		LinearLayout view = (LinearLayout) inflateAndSetupView(inflater,
				container, savedInstanceState, fragmentLayout);
		mRefreshListView = (RefreshableListView) view
				.findViewById(viewListLayout);
		
		new GetInitDataThread(fragmentHandler).start();
//		proDialog = ProgressDialog.show(getActivity(), "������..",
//				"������..���Ժ�....", true, true);
		
		
		//����ItemClick��Ӧ�¼�
		mRefreshListView.setOnItemClickListener(itemClickListener);
		mRefreshListView.setonRefreshListener(this);
		return view;
	}

	protected void changeListView(int type){
		mDataSource.updateValue(type);
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			listAdapter.setCount();
			listAdapter.notifyDataSetChanged();
			mRefreshListView.onRefreshComplete();
			super.handleMessage(msg);
		}
	};

	@Override
	public void onPullUpRefresh() {
		new Thread() {
			public void run() {
				try {
					//ģ����������ʱ��
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				changeListView(1);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	@Override
	public void onPullDownRefresh() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				changeListView(2);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}


	private View inflateAndSetupView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState, int layoutResourceId) {
		return inflater.inflate(layoutResourceId, container, false);
	}
}