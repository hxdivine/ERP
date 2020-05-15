package com.erp.biz.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.erp.biz.IReturnOrderDetailBiz;
import com.erp.dao.IReturnOrderDetailDao;
import com.erp.dao.IStoreDetailDao;
import com.erp.dao.IStoreOperDao;
import com.erp.dao.ISupplierDao;
import com.erp.entity.ReturnOrderDetail;
import com.erp.entity.ReturnOrders;
import com.erp.entity.StoreDetail;
import com.erp.entity.StoreOper;
import com.erp.entity.Supplier;
import com.erp.exception.ErpException;

public class ReturnOrderDetailBiz extends BaseBiz<ReturnOrderDetail> implements IReturnOrderDetailBiz  {
	  //
	private IReturnOrderDetailDao returnOrderDetailDao;
	private IStoreDetailDao storeDetailDao;
	private IStoreOperDao storeOperDao;
	
	public void setReturnOrderDetailDao(IReturnOrderDetailDao returnOrderDetailDao) {
		this.returnOrderDetailDao = returnOrderDetailDao;
		super.setBaseDao(this.returnOrderDetailDao);
	}

	public void setStoreDetailDao(IStoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
	}

	public void setStoreOperDao(IStoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
	}
	
	private ISupplierDao supplierDao;

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	/**
	 * 入库
	 * @param uuid		订单明细id
	 * @param storeuuid	仓库id
	 * @param empuuid	用户id
	 */
	@Override
	public void doInStore(Long uuid, Long storeuuid, Long empuuid) {
		/** 更新订单明细表 */
		ReturnOrderDetail returnOrderDetail = returnOrderDetailDao.getById(uuid);
		if(!ReturnOrderDetail.STATE_NOT_IN.equals(returnOrderDetail.getState())) {
			throw new ErpException("该订单明细已经入库！不能重复入库");
		}
		//设置订单明细的状态为已入库
		returnOrderDetail.setState(ReturnOrderDetail.STATE_IN);
		//入到哪个仓库
		returnOrderDetail.setStoreuuid(storeuuid);
		//入库的时间
		returnOrderDetail.setEndtime(new Date());
		//库管员
		returnOrderDetail.setEnder(empuuid);
		
		/** 库存表 */
		//创建库存表对象，然后封装查询条件
		StoreDetail storeDetail = new StoreDetail();
		storeDetail.setStoreuuid(storeuuid);
		storeDetail.setGoodsuuid(returnOrderDetail.getGoodsuuid());
		List<StoreDetail> storeList = storeDetailDao.getStoreDetailList(storeDetail);
		if(null != storeList && storeList.size() > 0) {
			//存在库存信息，数量累加
			long num = 0;
			if(null != storeList.get(0).getNum()) {
				num = storeList.get(0).getNum();
			}
			storeDetail.setNum(num + returnOrderDetail.getNum());
		} else {
			//不存在库存信息，插入记录
			storeDetail.setNum(returnOrderDetail.getNum());
			storeDetailDao.add(storeDetail);
		}
		
		/** 库存变更记录 */
		StoreOper storeOper = new StoreOper();
		storeOper.setEmpuuid(empuuid);
		storeOper.setOpertime(returnOrderDetail.getEndtime());
		storeOper.setStoreuuid(storeuuid);
		storeOper.setGoodsuuid(returnOrderDetail.getGoodsuuid());
		storeOper.setNum(returnOrderDetail.getNum());
		storeOper.setType(StoreOper.TYPE_IN);
		storeOperDao.add(storeOper);
		
		/** 订单表 */
		//构建查询条件，查询出该订单下订单明细状态为 未入库 的数量
		ReturnOrders returnOrders = returnOrderDetail.getReturnOrders();
		ReturnOrderDetail returnOrderDetail2 = new ReturnOrderDetail();
		returnOrderDetail2.setState(ReturnOrderDetail.STATE_NOT_IN);
		returnOrderDetail2.setReturnOrders(returnOrders);
		long count = returnOrderDetailDao.findCount(returnOrderDetailDao.getDetachedCriteria(returnOrderDetail2));
		//如果为0，更新订单状态为已入库，设置库管员和入库时间
		if(count == 0) {
			returnOrders.setState(ReturnOrders.STATE_END);
			returnOrders.setEnder(empuuid);
			returnOrders.setEndtime(returnOrderDetail.getEndtime());
		}
	}
	
	/**
	 * 出库
	 */
	//@RequiresPermissions("采购退货订单出库")
	public void doOutStore(Long uuid,Long storeuuid, Long empuuid){
		//******** 第1步 更新商品明细**********
		//1. 获取明细信息
		ReturnOrderDetail detail = returnOrderDetailDao.getById(uuid);
		//2. 判断明细的状态，一定是未入库的
		if(!ReturnOrderDetail.STATE_NOT_OUT.equals(detail.getState())){
			throw new ErpException("亲！该明细已经出库了，不能重复出库");
		}
		//3. 修改状态为已出库
		detail.setState(ReturnOrderDetail.STATE_IN);
		//4. 出库时间
		detail.setEndtime(new Date());
		//5. 库管员
		detail.setEnder(empuuid);
		//6. 从哪个仓库出
		detail.setStoreuuid(storeuuid);
		
		//*******第2 步 更新商品仓库库存*********
		//1. 构建查询的条件
		StoreDetail storeDetail = new StoreDetail();
		storeDetail.setGoodsuuid(detail.getGoodsuuid());
		storeDetail.setStoreuuid(storeuuid);
		//2. 通过查询 检查是否存在库存信息
		List<StoreDetail> storeList = storeDetailDao.getStoreDetailList(storeDetail);
		if(storeList.size()>0){
			//存在的话，则应该累加它的数量
			StoreDetail sd = storeList.get(0);
			sd.setNum(sd.getNum() - detail.getNum());
			if(sd.getNum() < 0){
				throw new ErpException("库存不足");
			}
		}else{
			throw new ErpException("库存不足");
		}
		
		//****** 第3步 添加操作记录*****
		//1. 构建操作记录
		StoreOper log = new StoreOper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType(StoreOper.TYPE_OUT);
		//2. 保存到数据库中
		storeOperDao.add(log);
		
		//**** 第4步 是否需要更新订单的状态********
	
		//1. 构建查询条件
		ReturnOrderDetail queryParam = new ReturnOrderDetail();
		ReturnOrders orders = detail.getReturnOrders();
		queryParam.setReturnOrders(orders);
		//2. 查询订单下是否还存在状态为0的明细
		queryParam.setState(ReturnOrderDetail.STATE_NOT_OUT);
		//3. 调用 getCount方法，来计算是否存在状态为0的明细
		long count = returnOrderDetailDao.findCount(returnOrderDetailDao.getDetachedCriteria(queryParam));
		if(count == 0){
			//4. 所有的明细都已经出库了，则要更新订单的状态，关闭订单
			orders.setState(ReturnOrders.STATE_END);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
			//客户
			Supplier supplier = supplierDao.getById(orders.getSupplieruuid());
		}
	}
}
