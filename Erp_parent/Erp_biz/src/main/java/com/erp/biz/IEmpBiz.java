package com.erp.biz;

import java.io.OutputStream;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.entity.Emp;
import com.erp.entity.Menu;
import com.erp.entity.Tree;
/**
*员工业务层接口
*
*/

public interface IEmpBiz extends IBaseBiz<Emp> {
	public final int hashIterations = 2; 
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
	 * @param oldPwd
	 * @param newPwd
	 */
	void updatePwd(Long uuid,String oldPwd,String newPwd);
	/**
	 * 重置密码
	 * 
	 */
	void updatePwd_resert(Long uuid,String newPwd);
	/**
	 * 导出数据
	 * @param os
	 * @param dc
	 */
	void export(OutputStream os, DetachedCriteria dc);
	 
	/**
	 * 获取用户角色
	 * @param uuid
	 * @return
	 */
	List<Tree> readEmpRoles(Long uuid);
	/**
	 * 更新用户角色
	 * @param uuid
	 * @param checkedStr
	 */
	void updateEmpRoles(Long uuid,String checkedStr);
	/**
	 * 查询用户下的菜单权限
	 * @param uuid
	 * @return
	 */
	List<Menu> getMenuByEmpuuid(Long uuid);
	/**
	 * 获取用户的菜单
	 * @param uuid
	 * @return
	 */
	Menu readMenuByEmpuuid(Long uuid);
}
