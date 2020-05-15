package com.erp.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.erp.dao.IBaseDao;
/**
 * 通用持久层
 * @author 骄傲的大树
 *
 * @param <T>
 */
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {
	
	private Class<T> entityClass;
	
	public BaseDao(){
		
		Class clazz = this.getClass();  //当前使用这个类的对象  CustomerDaoImpl
		Type type = clazz.getGenericSuperclass(); //参数化类型 BaseDaoImpl<Customer>
		ParameterizedType pType = (ParameterizedType)type;
		Type[] types = pType.getActualTypeArguments();
		
		this.entityClass = (Class) types[0];
	}
	
	@Override
	public List<T> getList() {
		// TODO Auto-generated method stub
		return (List<T>)this.getHibernateTemplate().find("from " + entityClass.getSimpleName());
	}

	@Override
	public List<T> getList(DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return (List<T>)this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
	
	@Override
	public void add(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public T getById(String id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}
	
	@Override
	public T getById(long id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}
	@Override
	public void delete(long id) {
		T t = this.getHibernateTemplate().get(entityClass, id);
		this.getHibernateTemplate().delete(t);
	}
	
	@Override
	public long findCount(DetachedCriteria detachedCriteria) {
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>)this.getHibernateTemplate().findByCriteria(detachedCriteria);
		if(list.size() > 0){
			return list.get(0);
		}
		return 0;
	
	}
	@Override
	public List<T> findByPage(DetachedCriteria detachedCriteria,int firstResult,int maxResult) {
		//先清除之前查询总记录数
		detachedCriteria.setProjection(null);
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria,firstResult,maxResult);
		
	}
	
	
}
