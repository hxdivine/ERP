package com.erp.biz;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.dao.IEmpDao;
import com.erp.dao.IGoodsDao;
import com.erp.dao.IStoreDao;
import com.erp.dao.ISupplierDao;


/**
 * 业务层通用泛型
 * @author 骄傲的大树
 *
 */
public interface IBaseBiz<T> {
	/**
	 * 获取所有部门
	 * @return
	 */
	List<T> getList();
	
	/**
	 * 获取所有部门
	 * @return
	 */
	List<T> getList(DetachedCriteria detachedCriteria);
	
	/**
	 * 新增
	 * @param t
	 */
	void add(T t);
	/**
	 * 修改
	 * @param t
	 */
	void update(T t);
	/**
	 * 根据id获取部门
	 * @param t
	 */
	T getById(long id);
	/**
	 * 根据id获取
	 * 
	 * @param t
	 */
	T getById(String id);
	/**
	 * 删除
	 * @param t
	 */
	void delete(long id);
	/**
	 * 分页
	 * @param detachedCriteria
	 * @param page
	 * @param rows
	 * @return
	 */
	List<T> findByPage(DetachedCriteria detachedCriteria,int firstResult,int maxResult);
	/**
	 * 获取总数
	 * @param detachedCriteria
	 * @return
	 */
	long findCount(DetachedCriteria detachedCriteria);
	
	/**
	 * 获取商品名称
	 * @param uuid
	 * @param empNameMap
	 * @return
	 */
	String getGoodsName(Long uuid,Map<Long,String> NameMap,IGoodsDao goodsDao);
	/**
	 * 获取仓库名称
	 * @param uuid
	 * @param empNameMap
	 * @return
	 */
	String getStoreName(Long uuid,Map<Long,String> NameMap,IStoreDao storeDao);
	/**
	 * 获取员工名称
	 * @param uuid
	 * @param empNameMap
	 * @return
	 */
	String getEmpName(Long uuid,Map<Long,String> empNameMap,IEmpDao empDao);
	
	/**
	 * 获取供应商名称
	 * @param uuid
	 * @param empNameMap
	 * @return
	 */
	String getSupplierName(Long uuid,Map<Long,String> supplierNameMap,ISupplierDao supplierDao);
}
