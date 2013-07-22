package com.netease.amazing.activity;



import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.amazing.R;
import com.netease.amazing.component.ContactFragment;
import com.netease.amazing.component.NewsFragment;
import com.netease.amazing.component.NoticeFragment;
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
	
	private Menu menu;
	private int currentFragment = 0;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        setUpActionBar();
        setUpViewPager();
        setUpTabs();
    }

    
    private void setUpActionBar() {
    	final ActionBar actionBar = getActionBar();
    	actionBar.setIcon(R.drawable.titlebar_setting_bg);
//    	actionBar.setHomeButtonEnabled(false);
    	actionBar.setDisplayUseLogoEnabled(true);
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
    	actionBar.setDisplayShowTitleEnabled(true);  
    	//设置actionBar的title bar ， 自定义样式
    	actionBar.setDisplayShowCustomEnabled(true);
    	actionBar.setCustomView(R.layout.apk_actionbar_title);
    	
    	actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.titlebar_bk32x74)); //modify by zhuxiaohua
    }
    
    private void setUpViewPager() {
    	mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    	
    	//设置加载fragment的数量，防止预加载
    	
    	
    	mViewPager = (ViewPager)findViewById(R.id.pager);
    	mViewPager.setAdapter(mViewPagerAdapter);
    	mViewPager.setOffscreenPageLimit(0);
    	mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
    		@Override
    		public void onPageSelected(int position) {
    			final ActionBar actionBar = getActionBar();
    			actionBar.setSelectedNavigationItem(position);
    			
    			switch(position) {
    			case 0:
    				currentFragment = 0;
    				menu.getItem(0).setTitle("");
    				break;
    			case 1:
    				currentFragment = 1;
    				menu.getItem(0).setTitle("").setIcon(R.drawable.titlebar_addnews_bg);
    				break;
    			case 2:
    				currentFragment = 2;
    				menu.getItem(0).setTitle("").setIcon(R.drawable.titlebar_addfriend_bg);
    				break;
    			}
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
    					break;
    			}
    		}
    	});
    }

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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
    	switch(item.getItemId()) {
    	case android.R.id.home:
    		intent = new Intent(this,SettingActivity.class);
			startActivity(intent);
    		break;
    	case R.id.home_menu_right:
    		if(currentFragment ==0) {
    			intent = new Intent(this,NoticeEditActivity.class);
    			startActivity(intent);
    		}
    		if(currentFragment == 1) {
    			intent = new Intent(this,NewsAddActivity1.class);
    			startActivity(intent);
    		}
    		if(currentFragment ==2) {
    			intent = new Intent(this,ContactAddFriendActivity.class);
    			startActivity(intent);
    		}
    		break;
    	}
    	return super.onOptionsItemSelected(item);
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
					tabLabel = getString(R.string.notice_tab_title);
					break;
				case TAB_INDEX_TWO:
					tabLabel = getString(R.string.news_tab_title);
					break;
				case TAB_INDEX_THREE:
					tabLabel = getString(R.string.contact_tab_title);
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
    @Override
    /**
     * 点击menu按钮时
     */
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = menu;
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}