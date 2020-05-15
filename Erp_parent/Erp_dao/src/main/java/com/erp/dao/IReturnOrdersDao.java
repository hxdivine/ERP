package com.erp.dao;

import java.util.Date;
import java.util.List;

import com.erp.entity.ReturnOrders;

public interface IReturnOrdersDao extends IBaseDao<ReturnOrders>{

	List returnOrdersReport(Date startDate, Date endDate);
	
}
