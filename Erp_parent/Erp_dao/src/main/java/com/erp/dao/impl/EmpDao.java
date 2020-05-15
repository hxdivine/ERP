package com.erp.dao.impl;

import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.erp.dao.IEmpDao;
import com.erp.entity.Emp;
import com.erp.entity.Menu;

public class EmpDao extends BaseDao<Emp> implements IEmpDao {

	@Override
	public Emp findByUsernameAndPwd(String username, String pwd) {
		String hql = "from Emp where username = ? and pwd = ?";
		List<Emp> list = (List<Emp>)this.getHibernateTemplate().find(hql, username,pwd);
		if(list.size() > 0){
			//找到用户并返回
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updatePwd(Long uuid, String newPwd) {
		String hql = "update Emp set pwd = ? where uuid = ?";
		this.getHibernateTemplate().bulkUpdate(hql, newPwd,uuid);
	}

	@Override
	public List<Menu> getMenuByEmpuuid(Long uuid) {
		String hql = "select m from Emp e join e.roles r join r.menus m where e.uuid = ?";
		return (List<Menu> )this.getHibernateTemplate().find(hql,uuid);
	}
}
