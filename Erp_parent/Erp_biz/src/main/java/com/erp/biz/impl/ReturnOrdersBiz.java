package com.erp.biz.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;

import com.erp.biz.IReturnOrdersBiz;
import com.erp.dao.IEmpDao;
import com.erp.dao.IReturnOrdersDao;
import com.erp.dao.ISupplierDao;
import com.erp.entity.ReturnOrderDetail;
import com.erp.entity.ReturnOrders;
import com.erp.exception.ErpException;

public class ReturnOrdersBiz extends BaseBiz<ReturnOrders> implements IReturnOrdersBiz  {
	
	private IReturnOrdersDao returnOrdersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;

	public void setReturnOrdersDao(IReturnOrdersDao returnOrdersDao) {
		this.returnOrdersDao = returnOrdersDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	@Override
	//@RequiresPermissions("采购退货订单审核")
	public void doCheck(Long uuid, Long empuuid) {
		// 获取订单，进入持久化状态
		ReturnOrders returnOrders = returnOrdersDao.getById(uuid);
		// 订单的状态
		if(!ReturnOrders.STATE_CREATE.equals(returnOrders.getState())) {
			throw new ErpException("该订单已经审核过，不能重复审核！");
		}
		// 1. 修改订单的状态
		returnOrders.setState(ReturnOrders.STATE_CHECK);
		// 2. 审核的时间
		returnOrders.setChecktime(new Date());
		// 3. 审核人
		returnOrders.setChecker(empuuid);
		
		
		
	}

	@Override
	public List returnOrdersReport(Date startDate, Date endDate) {
		
		return returnOrdersDao.returnOrdersReport(startDate, endDate);
	}

	@Override
	public void add1(ReturnOrders returnOrders) {
		// 1. 设置订单的状态
		returnOrders.setState(ReturnOrders.STATE_CREATE);
		// 2. 订单的类型
		returnOrders.setType(ReturnOrders.TYPE_OUT);
		// 3. 下单时间
		returnOrders.setCreatetime(new Date());

		// 合计金额
		double totalmoney = 0;

		for (ReturnOrderDetail detail : returnOrders.getReturnOrderDetails()) {
			detail.setEnder(null);
			detail.setEndtime(null);
			detail.setStoreuuid(null);
			detail.setUuid(null);
			// 累计金额
			totalmoney += detail.getMoney();
			// 明细的状态
			detail.setState(ReturnOrderDetail.STATE_NOT_IN);
			// 跟订单的关系
			detail.setReturnOrders(returnOrders);
		}
		// 设置订单总金额
		returnOrders.setTotalmoney(totalmoney);

		// 保存到DB
		returnOrdersDao.add(returnOrders);

	}
	
	/*
	 * 采购退货申请
	 * */
	@Override
	public void add(ReturnOrders returnOrders) {
/*		// 获取主题
		Subject subject = SecurityUtils.getSubject();
		// 采购订单申请
		if (ReturnOrders.TYPE_IN.equals(returnOrders.getType())) {
			// 代码级别的权限控制
			// 判断当前登陆的用户是否有 采购订单申请 的权限
			if (!subject.isPermitted("我的采购退货订单")) {
				throw new ErpException("权限不足");
			}
		} else if (ReturnOrders.TYPE_OUT.equals(returnOrders.getType())) {
			
			 * if(!subject.isPermitted("销售退货登记")){ throw new
			 * ErpException("权限不足"); }
			 
		} else {
			throw new ErpException("非法参数");
		}*/

		// 有权限,开始操作
		// 1.生成日期
		returnOrders.setCreatetime(new Date());

		// 2.退货订单状态 ,设置为 未审核
		returnOrders.setState(ReturnOrders.STATE_CREATE);

		// 合计金额
		double total = 0;
		for (ReturnOrderDetail detail : returnOrders.getReturnOrderDetails()) {
			// 累计金额
			total += detail.getMoney();
			// 明细的状态
			detail.setState(ReturnOrderDetail.STATE_NOT_IN);
			// 跟订单的关系
			detail.setReturnOrders(returnOrders);
		}
		// 设置订单总金额
		returnOrders.setTotalmoney(total);

		returnOrdersDao.add(returnOrders);
		
	}
	
	//重写list方法,获取所有姓名
	@Override
	public List<ReturnOrders> findByPage(DetachedCriteria detachedCriteria, int firstResult, int maxResult) {
		//获取分页后的订单列表
		List<ReturnOrders> returnOrdersList = super.findByPage(detachedCriteria, firstResult, maxResult);
		//缓存员工编号与员工的名称, key=员工的编号，value=员工的名称
		Map<Long, String> empNameMap = new HashMap<Long, String>();
		//缓存供应商编号与员工的名称, key=供应商的编号，value=供应商的名称
		Map<Long, String> supplierNameMap = new HashMap<Long, String>();
		//循环，获取员工的名称
		for(ReturnOrders r : returnOrdersList){
			//从缓存中取出员工名称
			r.setCreaterName(getEmpName(r.getCreater(),empNameMap,empDao));
			r.setCheckerName(getEmpName(r.getChecker(),empNameMap,empDao));
			r.setEnderName(getEmpName(r.getEnder(),empNameMap,empDao));
			
			//供应商
			r.setSupplierName(getSupplierName(r.getSupplieruuid(),supplierNameMap,supplierDao));
		}
		return returnOrdersList;
	}
	
}
