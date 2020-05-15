package com.erp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.erp.dao.IReportDao;

public class ReportDao extends HibernateDaoSupport implements IReportDao{

	@Override
	public List OrdersReport(Date startDate, Date endDate) {
		String hql = "select new Map(gt.name as name,sum(o1.money) as y) " +
				"from GoodsType gt, Goods gs, OrderDetail o1, Orders o " +
				"where " + 
				"gs.goodsType = gt and o1.orders = o " + 
				"and gs.uuid = o1.goodsuuid and o.type = '2' ";
				
		List<Date> dateList = new ArrayList<Date>();
		if(null != startDate){
			hql += "and o.createtime >= ? ";
			dateList.add(startDate);
		}
		if(null != endDate){
			hql += "and o.createtime <= ? ";
			dateList.add(endDate);
		}
		
		hql += "group by gt.name";
		//转换指定类型
		Date[] param = new Date[dateList.size()];
		//转换新的数组
		Date[] params = dateList.toArray(param);
		return this.getHibernateTemplate().find(hql,params);
	}

	@Override
	public List<Map<String, Object>> trendReport(Integer year) {
		String hql = "select new Map(month(o.createtime) as month,sum(o1.money) as money) " + 
				"from OrderDetail o1, Orders o " + 
				"where o1.orders = o " +
				"and o.type = '2' and year(o.createtime) = ? " + 
				"group by month(o.createtime)";
		return (List<Map<String,Object>>)this.getHibernateTemplate().find(hql, year);
	}

}
