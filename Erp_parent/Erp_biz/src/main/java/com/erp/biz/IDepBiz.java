package com.erp.biz;


import java.io.OutputStream;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.entity.Dep;

/**
 * 部门业务层接口
 * @author 骄傲的大树
 *
 */
public interface IDepBiz extends IBaseBiz<Dep>{
	/**
	 * 导出数据
	 * @param os
	 * @param dc
	 */
	void export(OutputStream os, DetachedCriteria dc);

}
