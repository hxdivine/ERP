package com.erp.biz.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.biz.IBaseBiz;
import com.erp.dao.IBaseDao;
import com.erp.dao.IEmpDao;
import com.erp.dao.IGoodsDao;
import com.erp.dao.IStoreDao;
import com.erp.dao.ISupplierDao;
/**
 * 通用业务层
 * @author 骄傲的大树
 *
 * @param <T>
 */
public class BaseBiz<T> implements IBaseBiz<T> {
	private IBaseDao<T> baseDao;

	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	public IBaseDao<T> getBaseDao() {
		return baseDao;
	}

	@Override
	public List<T> getList() {
		return baseDao.getList();
	}

	@Override
	public List<T> getList(DetachedCriteria detachedCriteria) {
		return baseDao.getList(detachedCriteria);
	}
	@Override
	public void add(T t) {
		baseDao.add(t);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public T getById(long id) {
		return baseDao.getById(id);
	}
	
	@Override
	public T getById(String id) {
		return baseDao.getById(id);
	}
	@Override
	public void delete(long id) {
		baseDao.delete(id);
	}
	
	/**
	 * 部门表的分页查询
	 */
	public List<T> findByPage(DetachedCriteria detachedCriteria,int firstResult,int maxResult) {
	
		List<T> list = baseDao.findByPage(detachedCriteria,firstResult,maxResult);	
		return list;
	}


	@Override
	public long findCount(DetachedCriteria detachedCriteria) {
		return baseDao.findCount(detachedCriteria);
	}
	@Override
	public String getGoodsName(Long uuid,Map<Long,String> NameMap,IGoodsDao goodsDao){
		if(null == uuid){
			return null;
		}
		String name = NameMap.get(uuid);
		if(null == name){
			name = goodsDao.getById(uuid).getName();
			NameMap.put(uuid, name);
		}
		return name;
	}
	@Override
	public String getStoreName(Long uuid,Map<Long,String> NameMap,IStoreDao storeDao){
		if(null == uuid){
			return null;
		}
		String name = NameMap.get(uuid);
		if(null == name){
			name = storeDao.getById(uuid).getName();
			NameMap.put(uuid, name);
		}
		return name;
	}
	
	/**
	 * 获取员工名称
	 * @param uuid
	 * @param empNameMap
	 * @return
	 */
	public String getEmpName(Long uuid,Map<Long,String> empNameMap,IEmpDao empDao){
		if(uuid == null){
			return null;
		}
		String empName = empNameMap.get(uuid);
		//缓存中是否存在
		if(null == empName){
			empName = empDao.getById(uuid).getName();
			//存入缓存
			empNameMap.put(uuid, empName);
		}
		return empName;
	}
	
	/**
	 * 获取供应商名称
	 * @param uuid
	 * @param empNameMap
	 * @return
	 */
	public String getSupplierName(Long uuid,Map<Long,String> supplierNameMap,ISupplierDao supplierDao){
		if(uuid == null){
			return null;
		} 
		String supplierName = supplierNameMap.get(uuid);
		//缓存中是否存在
		if(null == supplierName){
			supplierName = supplierDao.getById(uuid).getName();
			//存入缓存
			supplierNameMap.put(uuid, supplierName);
		}
		return supplierName;
	}
}
