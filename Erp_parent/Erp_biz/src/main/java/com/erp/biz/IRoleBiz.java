package com.erp.biz;


import java.util.List;

import com.erp.entity.Role;
import com.erp.entity.Tree;

/**
 * 角色业务层接口
 * @author 骄傲的大树
 *
 */
public interface IRoleBiz extends IBaseBiz<Role>{
	/**
	 * 获取角色菜单权限
	 * uuid 角色编号
	 * @return
	 */
	List<Tree> readRoleMenus(Long uuid);
	/**
	 *更新角色信息
	 *	 * @param uuids 角色编号
	 * @param checkedStr 选中的菜单权限
	 */
	void updateRoleMenus(Long uuid,String checkedStr);
}
