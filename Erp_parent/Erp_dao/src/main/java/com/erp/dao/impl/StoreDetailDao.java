package com.erp.dao.impl;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.erp.dao.IStoreDetailDao;
import com.erp.entity.StoreAlert;
import com.erp.entity.StoreDetail;

public class StoreDetailDao extends BaseDao<StoreDetail> implements IStoreDetailDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(StoreDetail storeDetail ){
		DetachedCriteria dc=DetachedCriteria.forClass(StoreDetail.class);
		if(storeDetail != null){
			//根据商品编号查询
			if(null != storeDetail .getGoodsuuid()){
				dc.add(Restrictions.eq("goodsuuid", storeDetail .getGoodsuuid()));
			}
			//根据仓库编号查询
			if(null != storeDetail .getStoreuuid()){
				dc.add(Restrictions.eq("storeuuid", storeDetail .getStoreuuid()));
			}
		}
		return dc;
	}
	public List<StoreDetail> getStoreDetailList(StoreDetail storeDetail) {
		DetachedCriteria dc = getDetachedCriteria(storeDetail );
		return (List<StoreDetail>)this.getHibernateTemplate().findByCriteria(dc);
	}
	@Override
	public List<StoreAlert> getStoreAlertList() {
		return (List<StoreAlert>)this.getHibernateTemplate().find("from StoreAlert where storenum < outnum");
	}
	@Override
	public List<StoreDetail> getStoreNum(Long goodsuuid) {
		String hql = "select count(1) from StoreDetail where goodsuuid = ?";
		return (List<StoreDetail>) this.getHibernateTemplate().find(hql, goodsuuid);
	}
	
	
}
