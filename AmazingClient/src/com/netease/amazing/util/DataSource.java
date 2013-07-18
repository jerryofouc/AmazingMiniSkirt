package com.netease.amazing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   ListView”µ“şÔ´»ùî
 */
public abstract class DataSource {
	protected List<Map<String, Object>> mDataSource = new ArrayList<Map<String, Object>>();
	public final static int PAGE_START = 1;
	public final static int PAGE_END = 10;

	public abstract List<Map<String, Object>> updateValue(int pageStart, int pageSize);	
	
	public List<Map<String, Object>> getmDataSource() {
		return mDataSource;
	}
	public void setmDataSource(List<Map<String, Object>> mDataSource) {
		this.mDataSource = mDataSource;
	}
}
