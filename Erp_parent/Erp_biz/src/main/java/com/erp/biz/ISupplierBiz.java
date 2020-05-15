package com.erp.biz;


import java.io.InputStream;
import java.io.OutputStream;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.entity.Supplier;

/**
 * 供应商业务层接口
 * @author 骄傲的大树
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{
	/**
	 * 带出数据
	 * @param os
	 */
	void export(OutputStream os,DetachedCriteria dc);
	/**
	 * 数据导入
	 * @param is
	 */
	void doImport(InputStream is);
	
}
