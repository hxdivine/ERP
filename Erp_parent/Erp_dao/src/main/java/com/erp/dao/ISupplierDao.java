package com.erp.dao;

import java.util.List;

import com.erp.entity.Supplier;

public interface ISupplierDao extends IBaseDao<Supplier> {
	/**
	 * 通过名称查找供应商或客户
	 */
	List<Supplier> findByName(String name);
}
