package com.netease.amazing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.netease.amazing.pojo.Notice;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   ListView”µ“şÔ´»ùî
 */
public abstract class DataSource1 {
	public final static int FETCH_SIZE = 10;
	public abstract boolean updateValue(int type);	
	public abstract List<Map<String, Object>> toMapList();
}


