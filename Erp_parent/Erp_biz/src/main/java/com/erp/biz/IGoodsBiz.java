package com.erp.biz;


import java.io.OutputStream;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.entity.Goods;

/**
 * 部门业务层接口
 * @author 骄傲的大树
 *
 */
public interface IGoodsBiz extends IBaseBiz<Goods>{
	/**
	 * 导出数据
	 * @param os
	 * @param dc
	 */
	public void export(OutputStream os,DetachedCriteria dc);
}
