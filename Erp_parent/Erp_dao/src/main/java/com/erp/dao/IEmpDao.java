package com.erp.dao;

import java.util.List;

import com.erp.entity.Emp;
import com.erp.entity.Menu;

public interface IEmpDao extends IBaseDao<Emp> {
	/**
	 * 根据用户名 密码查询是否登录
	 * @param username
	 * @param pwd
	 * @return
	 */
	Emp findByUsernameAndPwd(String username,String pwd);
	/**
	 * 修改密码
	 * @param uuid
	 * @param newPwd
	 */
	void updatePwd(Long uuid, String newPwd);
	/**
	 * 查询用户下的菜单权限
	 * @param uuid
	 * @return
	 */
	List<Menu> getMenuByEmpuuid(Long uuid);
}
