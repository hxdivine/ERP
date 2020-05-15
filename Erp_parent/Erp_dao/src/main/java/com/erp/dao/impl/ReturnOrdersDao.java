package com.erp.dao.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.erp.dao.IReturnOrdersDao;
import com.erp.entity.ReturnOrders;

public class ReturnOrdersDao extends BaseDao<ReturnOrders> implements IReturnOrdersDao {
	public List returnOrdersReport(Date startDate, Date endDate) {
		//基本HQL语句，销售退货需要关联四张表，退货订单表，退货明细表，商品表，商品类型表，利用外键关联，且类型为2，若为1是采购退货
		//注意：若表关系为多对一，则将多的一方写在前面，一的一方写在后面，格式为多的一方.一的外键=一的一方
		//HQL语句，无法使用离线查询条件
		String hql = "select new Map(gt.name as name, sum(rdd.money) as y) "
				+ "from ReturnOrders ro, ReturnOrderDetail rdd, Goods g, GoodsType gt "
				+ "where rdd.returnOrders = ro "
				+ "and g.uuid = rdd.goodsuuid "
				+ "and g.goodsType = gt "
				+ "and ro.type = '2' ";
		
		List<Date> dateList = new ArrayList<Date>();
		if(null != startDate) {
			
			hql += "and ro.createtime >= ? ";
			dateList.add(startDate);
		}
		if(null != endDate) {
			
			hql += "and ro.createtime <= ? ";
			dateList.add(endDate);
		}
		//按照商品的种类来分组统计
		hql += "group by gt.name";
		Date[] param = new Date[0];
		Date[] params = dateList.toArray(param);
		
		return this.getHibernateTemplate().find(hql, params);
	}
	

}
