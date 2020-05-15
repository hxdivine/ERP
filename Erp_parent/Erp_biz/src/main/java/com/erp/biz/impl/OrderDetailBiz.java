package com.erp.biz.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.erp.biz.IOrderDetailBiz;
import com.erp.dao.IOrderDetailDao;
import com.erp.dao.IStoreDetailDao;
import com.erp.dao.IStoreOperDao;
import com.erp.dao.ISupplierDao;
import com.erp.entity.OrderDetail;
import com.erp.entity.Orders;
import com.erp.entity.StoreDetail;
import com.erp.entity.StoreOper;
import com.erp.entity.Supplier;
import com.erp.exception.ErpException;
import com.redsun.bos.ws.impl.IWaybillWs;

public class OrderDetailBiz extends BaseBiz<OrderDetail> implements IOrderDetailBiz  {
	  //
	private IOrderDetailDao orderDetailDao;
	private IStoreDetailDao storeDetailDao;
	private IStoreOperDao storeOperDao;
	private IWaybillWs waybillWs;
	private ISupplierDao supplierDao;
	
	public void setOrderDetailDao(IOrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
		super.setBaseDao(this.orderDetailDao);
	}
	
	
	public void setWaybillWs(IWaybillWs waybillWs) {
		this.waybillWs = waybillWs;
	}



	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}



	public void setStoreDetailDao(IStoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
	}
	public void setStoreOperDao(IStoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
	}
	
	@Override
	@RequiresPermissions("采购订单入库")
	public void doInStore(Long uuid, Long storeuuid, Long empuuid) {
		//************第一步
		//获取订单明细信息
		OrderDetail detail = orderDetailDao.getById(uuid);
		//判断明细状态，一定要是未入库
		if(!OrderDetail.STATE_NOT_IN.equals(detail.getState())){
			throw new ErpException("不能重复入库哦！");
		}
		//修改状态为已入库
		detail.setState(OrderDetail.STATE_IN);
		//入库时间
		detail.setEndtime(new Date());
		
		//库管员
		detail.setEnder(empuuid);
		//入的仓库
		detail.setStoreuuid(storeuuid);
		
		//************第二步
		//构建查询条件
		StoreDetail storeDetail = new StoreDetail();
		storeDetail.setGoodsuuid(detail.getGoodsuuid());
		storeDetail.setStoreuuid(storeuuid);
		//2. 通过查询 检查是否存在库存信息
		List<StoreDetail> storeList = storeDetailDao.getStoreDetailList(storeDetail);
		if(storeList.size()>0){
			//存在的话，则应该累加它的数量
			long num = 0;
			if(null != storeList.get(0).getNum()){
				num = storeList.get(0).getNum().longValue();
			}
			storeList.get(0).setNum(num + detail.getNum());
		}else{
			//不存在，则应该插入库存的记录
			storeDetail.setNum(detail.getNum());
			storeDetailDao.add(storeDetail);
		}
		//****** 第3步 添加操作记录*****
		//1. 构建操作记录
		StoreOper log = new StoreOper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType(StoreOper.TYPE_IN);
		//2. 保存到数据库中
		storeOperDao.add(log);
		
		//**** 第4步 是否需要更新订单的状态********
	
		//1. 构建查询条件
		OrderDetail queryParam = new OrderDetail();
		Orders orders = detail.getOrders();
		queryParam.setOrders(orders);
		//2. 查询订单下是否还存在状态为0的明细
		queryParam.setState(OrderDetail.STATE_NOT_IN);
		//3. 调用 getCount方法，来计算是否存在状态为0的明细
		long count = orderDetailDao.findCount(orderDetailDao.getDetachedCriteria(queryParam));
		if(count == 0){
			//4. 所有的明细都已经入库了，则要更新订单的状态，关闭订单
			orders.setState(Orders.STATE_END);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
		}
	}
	@Override
	@RequiresPermissions("销售订单出库")
	public void doOutStore(Long uuid, Long storeuuid, Long empuuid) {
		// ************第一步
		// 获取订单明细信息
		OrderDetail detail = orderDetailDao.getById(uuid);
		// 判断明细状态，一定要是未入库
		if (!OrderDetail.STATE_NOT_OUT.equals(detail.getState())) {
			throw new ErpException("已经出库了的订单,不能重复出库哦！");
		}
		// 修改状态为已入库
		detail.setState(OrderDetail.STATE_OUT);
		// 出库时间
		detail.setEndtime(new Date());

		// 库管员
		detail.setEnder(empuuid);
		// 从哪个仓库出库
		detail.setStoreuuid(storeuuid);

		// ************第二步
		// 构建查询条件
		StoreDetail storeDetail = new StoreDetail();
		storeDetail.setGoodsuuid(detail.getGoodsuuid());
		storeDetail.setStoreuuid(storeuuid);
		// 2. 通过查询 检查是否存在库存信息
		List<StoreDetail> storeList = storeDetailDao.getStoreDetailList(storeDetail);
		if (storeList.size() > 0) {
			// 存在的话，则应该减去它的数量
			StoreDetail sd = storeList.get(0);
			System.out.println(sd.getNum().longValue());
			System.out.println(detail.getNum().longValue());
			long num = sd.getNum().longValue() - detail.getNum().longValue();
			System.out.println(num);
			if(num < 0){
				throw new ErpException("库存不足！");
			}
			storeList.get(0).setNum(num);
		} else {
			throw new ErpException("库存不足！");
		}
		// ****** 第3步 添加操作记录*****
		// 1. 构建操作记录
		StoreOper log = new StoreOper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType(StoreOper.TYPE_OUT);
		// 2. 保存到数据库中
		storeOperDao.add(log);

		// **** 第4步 是否需要更新订单的状态********

		// 1. 构建查询条件
		OrderDetail queryParam = new OrderDetail();
		Orders orders = detail.getOrders();
		queryParam.setOrders(orders);
		// 2. 查询订单下是否还存在状态为0的明细
		queryParam.setState(OrderDetail.STATE_NOT_OUT);
		// 3. 调用 getCount方法，来计算是否存在状态为0的明细
		long count = orderDetailDao.findCount(orderDetailDao.getDetachedCriteria(queryParam));
		if (count == 0) {
			// 4. 所有的明细都已经出库了，则要更新订单的状态，关闭订单
			orders.setState(Orders.STATE_OUT);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
			
			//客户
			Supplier supplier = supplierDao.getById(orders.getSupplieruuid());
			//在线预约下单,获取运单号
			Long waybillsn = waybillWs.addWaybill(1l, supplier.getAddress(), supplier.getContact(), supplier.getTele(), "--");
			//更新运单号
			orders.setWaybillsn(waybillsn);
		}
	}

}
