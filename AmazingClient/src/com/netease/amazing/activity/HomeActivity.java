package com.netease.amazing.activity;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.amazing.R;
import com.netease.amazing.component.ContactFragment;
import com.netease.amazing.component.NewsFragment;
import com.netease.amazing.component.NoticeFragment;
import com.netease.amazing.component.OldNoticeFragment;
import com.netease.amazing.util.NewsDataSource;
//import android.support.v4.app.FragmentPagerAdapter;

public class HomeActivity extends FragmentActivity 
				implements ActionBar.TabListener{
    
	private NoticeFragment mFragment1 = new NoticeFragment();
	private NewsFragment mFragment2 = new NewsFragment();
	private ContactFragment mFragment3 = new ContactFragment();
	
	private static final int TAB_INDEX_COUNT = 3;
	
	private static final int TAB_INDEX_ONE = 0;
	private static final int TAB_INDEX_TWO = 1;
	private static final int TAB_INDEX_THREE = 2;
	
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
	/** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        //mFragment2.set(R.layout.fragment2, R.id.mineList, new NewsDataSource(), "com.netease.amazing.util.NewsListAdapter", myItemClickListener);
        
        setUpActionBar();
        setUpViewPager();
        setUpTabs();
    }
    
    /**/
    
    /**/
    class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
		}
    	
    }
    
    private void setUpActionBar() {
    	final ActionBar actionBar = getActionBar();
    	actionBar.setHomeButtonEnabled(false);
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	actionBar.setDisplayShowTitleEnabled(true);
    	actionBar.setDisplayShowHomeEnabled(false);   
    	
    	//设置actionBar的title bar ， 自定义样式
    	actionBar.setDisplayShowCustomEnabled(true); 
    	actionBar.setCustomView(R.layout.title_bar);
    }
    
    private void setUpViewPager() {
    	mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    	
    	//设置加载fragment的数量，防止预加载
    	//mViewPager.setOffscreenPageLimit(0);
    	
    	mViewPager = (ViewPager)findViewById(R.id.pager);
    	mViewPager.setAdapter(mViewPagerAdapter);
    	mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
    		@Override
    		public void onPageSelected(int position) {
    			final ActionBar actionBar = getActionBar();
    			actionBar.setSelectedNavigationItem(position);
    		}
    		
    		@Override
    		public void onPageScrollStateChanged(int state) {
    			switch(state) {
    				case ViewPager.SCROLL_STATE_IDLE:
    					break;
    				case ViewPager.SCROLL_STATE_DRAGGING:
    					break;
    				case ViewPager.SCROLL_STATE_SETTLING:
    					break;
    				default:
    					//TODO
    					break;
    			}
    		}
    	});
    }
    
    //设置tabs
    private void setUpTabs() {
    	final ActionBar actionBar = getActionBar();
    	for (int i = 0; i < mViewPagerAdapter.getCount(); ++i) {
    		actionBar.addTab(actionBar.newTab()
    				.setText(mViewPagerAdapter.getPageTitle(i))
    				.setTabListener(this));
    	}
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
    
    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			switch (position) {
				case TAB_INDEX_ONE:
					return mFragment1;
				case TAB_INDEX_TWO:
					return mFragment2;
				case TAB_INDEX_THREE:
					return mFragment3;
			}
			throw new IllegalStateException("No fragment at position " + position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return TAB_INDEX_COUNT;
		}
    	
		@Override
		public CharSequence getPageTitle(int position) {
			String tabLabel = null;
			switch (position) {
				case TAB_INDEX_ONE:
					tabLabel = getString(R.string.tab_1);
					break;
				case TAB_INDEX_TWO:
					tabLabel = getString(R.string.tab_2);
					break;
				case TAB_INDEX_THREE:
					tabLabel = getString(R.string.tab_3);
					break;
			}
			return tabLabel;
		}
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
//    @Override
//    /**
//     * 点击menu按钮时
//     */
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
}