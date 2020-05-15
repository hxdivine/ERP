package com.erp.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erp.biz.IReportBiz;
import com.erp.dao.IReportDao;

public class ReportBiz implements IReportBiz{
	private IReportDao reportDao;

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	public List OrdersReport(Date startDate, Date endDate) {
		return reportDao.OrdersReport(startDate,endDate);
	}

	@Override
	public List<Map<String,Object>> trendReport(Integer year) {
		//对月份进行查缺补漏
		List<Map<String,Object>> list = reportDao.trendReport(year);
		List<Map<String,Object>> rtnData = new ArrayList<Map<String,Object>>();
		//零时封装数据库获取的所有map
		Map<String,Map<String,Object>> yearDataMap = new HashMap<String,Map<String,Object>>();
		
		for(Map<String,Object> month : list){
			yearDataMap.put(month.get("month") + "", month);
		}
		//对yearDataMap不存在的月份进行补漏
		Map<String,Object> monthData = null;
		for(int i = 1; i <= 12; i++){
			monthData = yearDataMap.get(i + "");
			if(monthData == null){
				monthData = new HashMap<String,Object>();
				monthData.put("month", i + "月");
				monthData.put("money", 0);
			}else{
				monthData.put("month", i + "月");
			}
			rtnData.add(monthData);
		}
		return rtnData;
	}
	
}
