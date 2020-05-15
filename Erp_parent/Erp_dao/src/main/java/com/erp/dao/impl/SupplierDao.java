package com.erp.dao.impl;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.erp.dao.ISupplierDao;
import com.erp.entity.Supplier;

public class SupplierDao extends BaseDao<Supplier> implements ISupplierDao {

	@Override
	public List<Supplier> findByName(String name) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(Supplier.class);
		dc.add(Restrictions.eq("name", name));
		return (List<Supplier>)this.getHibernateTemplate().findByCriteria(dc);
	}

}
