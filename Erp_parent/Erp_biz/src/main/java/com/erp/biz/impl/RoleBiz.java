package com.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import com.erp.biz.IRoleBiz;
import com.erp.dao.IMenuDao;
import com.erp.dao.IRoleDao;
import com.erp.entity.Menu;
import com.erp.entity.Role;
import com.erp.entity.Tree;

public class RoleBiz extends BaseBiz<Role> implements IRoleBiz  {
	  
	private IRoleDao roleDao;

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(this.roleDao);
	}
	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<Tree> readRoleMenus(Long uuid) {
		List<Tree> treeList = new ArrayList<Tree>();
		//获取角色信息
		Role role = roleDao.getById(uuid);
		//获取角色菜单
		List<Menu> roleMenus = role.getMenus();
		
		Menu root = menuDao.getById("0");
		Tree t1 = null;
		Tree t2 = null;
		for(Menu m : root.getMenus()){
			t1 = new Tree();
			t1.setId(m.getMenuid());
			t1.setText(m.getMenuname());
			
			//二级菜单
			for(Menu m2 : m.getMenus()){
				t2 = new Tree();
				t2.setId(m2.getMenuid());
				t2.setText(m2.getMenuname());
				//如果角色下包含权限菜单，让它选中
				if(roleMenus.contains(m2)){
					t2.setChecked(true);
				}
				
				t1.getChildren().add(t2);
			}
			treeList.add(t1);
		}
		return treeList;
	}

	@Override
	public void updateRoleMenus(Long uuid, String checkedStr) {
		//获取角色
		Role role = roleDao.getById(uuid);
		//清楚角色下的菜单权限
		role.setMenus(new ArrayList<Menu>());
		
		String[] ids = checkedStr.split(",");
		Menu menu = null;
		for(String id : ids){
			menu = menuDao.getById(id);
			//保存角色下的菜单权限
			role.getMenus().add(menu);
		}
	}

}
