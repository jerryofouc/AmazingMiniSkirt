package com.netease.amazing.datasource;

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
	public final static int FETCH_SIZE = 10;
	public abstract boolean updateValue(int type);	
	public abstract List<Map<String, Object>> toMapList();
}


