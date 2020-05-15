package com.erp.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.entity.StoreAlert;
import com.erp.entity.StoreDetail;

public interface IStoreDetailDao extends IBaseDao<StoreDetail>{
	/**
	 * 构建查询条件
	 * @return
	 */
	DetachedCriteria getDetachedCriteria(StoreDetail storeDetail);
	/**
	 * 条件查询
	 * @param storeDetail
	 * @return
	 */
	List<StoreDetail> getStoreDetailList(StoreDetail storeDetail);
	/**
	 * 获取库存预警列表
	 * @return
	 */
	List<StoreAlert> getStoreAlertList();
	/**
	 * 根据商品ID查询商品库存
	 * @param goodsuuid
	 * @return
	 */
	List<StoreDetail> getStoreNum(Long goodsuuid);
}
