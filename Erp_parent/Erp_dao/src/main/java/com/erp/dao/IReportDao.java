package com.erp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报表数据访问层接口
 * @author 骄傲的大树
 *
 */
public interface IReportDao {
	/**
	 * 销售统计报表
	 * @return
	 */
	List OrdersReport(Date startDate, Date endDate);
	/**
	 * 销售趋势图
	 * @param year
	 * @return
	 */
	List<Map<String, Object>> trendReport(Integer year);
}
