package com.erp.biz.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.erp.biz.IEmpBiz;
import com.erp.dao.IEmpDao;
import com.erp.dao.IMenuDao;
import com.erp.dao.IRoleDao;
import com.erp.entity.Emp;
import com.erp.entity.Menu;
import com.erp.entity.Role;
import com.erp.entity.Tree;
import com.erp.exception.ErpException;

@Transactional
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz{
	
    private IEmpDao empDao;

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);//调用通用的类时需要将DAO传过去
	}
	private IRoleDao roleDao;
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public Emp findByUsernameAndPwd(String username, String pwd) {
		String newPwd = encrypt(pwd,username);
		return empDao.findByUsernameAndPwd(username, newPwd);
	}

	@Override
	public void updatePwd(Long uuid, String oldPwd, String newPwd) {
		//取出员工信息
		Emp emp = empDao.getById(uuid);
		//加密旧密码
		String encrypted = encrypt(oldPwd, emp.getUsername());
		if( !encrypted.equals(emp.getPwd())){
			throw new ErpException("旧密码不正确");
		}
		newPwd = encrypt(newPwd, emp.getUsername());
		empDao.updatePwd(uuid, newPwd);
	}

	/**
	 * 密码加密
	 */
	public void add(Emp emp){
		//设置初始密码
		String pwd = encrypt(emp.getUsername(),emp.getUsername());
		emp.setPwd(pwd);
		super.add(emp);
	}
	/**
	 * 加密
	 * hashIterations 散列次数  》 加密次数
	 * @param source 原密码
	 * @param salt  扰乱吗
	 * @return  newPwd
	 */
	private String encrypt(String source,String salt){
		Md5Hash md5 = new Md5Hash(source,salt,hashIterations);
		String newPwd = md5.toString();
		return newPwd;
	}

	@Override
	public void updatePwd_resert(Long uuid, String newPwd) {
		Emp emp = empDao.getById(uuid);
		newPwd = encrypt(newPwd, emp.getUsername());
		
		empDao.updatePwd(uuid, newPwd);
	}
	
	@Override
	public void export(OutputStream os, DetachedCriteria dc) {
		// 获取供应商 客户信息
		List<Emp> list = empDao.getList(dc);
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = "员工";

		// 创建工作表
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 创建一行
		HSSFRow row = sheet.createRow(0);
		String[] header = { "编号", "登录名", "真实姓名", "性别","联系地址","联系电话", "出生年月","Email","部门名称" };
		int[] width = { 2000,5000, 5000,2000, 8000,4000, 4000, 10000,5000 };
		// 创建第一行表头信息
		HSSFCell cell = null;
		for (int i = 0; i < header.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header[i]);
			sheet.setColumnWidth(i, width[i]);
		}
		// 导出内容
		int rowCount = 1;
		String sex = "";
		for (Emp emp : list) {
			row = sheet.createRow(rowCount);
			row.createCell(0).setCellValue(emp.getUuid()); // 名称
			row.createCell(1).setCellValue(emp.getUsername()); // 名称
			row.createCell(2).setCellValue(emp.getName()); // 电话
			if(Emp.GENDER_MAN == emp.getGender().longValue()){
				sex = "男";
			}else if(Emp.GENDER_WOMEN == emp.getGender().longValue()){
				sex = "女";
			}
			row.createCell(3).setCellValue(sex); // Email
			row.createCell(4).setCellValue(emp.getAddress()); // 地址
			row.createCell(5).setCellValue(emp.getTele()); // 地址
			row.createCell(6).setCellValue(emp.getBirthday()); // 地址
			row.createCell(7).setCellValue(emp.getEmail()); // 地址
			row.createCell(8).setCellValue(emp.getDep().getName()); // 地址
			rowCount++;
		}
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Tree> readEmpRoles(Long uuid) {
		List<Tree> treeList = new ArrayList<Tree>();
		//获取员工信息
		Emp emp  = empDao.getById(uuid);
		//获取用户下的角色列表
		List<Role> empRoles = emp.getRoles();
		//获取所有的角色列表
		List<Role> rolesList = roleDao.getList();
		Tree t1 = null;
		
		for(Role role : rolesList){
			t1 = new Tree();
			t1.setId(String.valueOf(role.getUuid()));
			t1.setText(role.getName());
			
			if(empRoles.contains(role)){
				t1.setChecked(true);
			}
			treeList.add(t1);
		}
		return treeList;
	}

	@Override
	public void updateEmpRoles(Long uuid, String checkedStr) {
		//获取用户信息
		Emp emp = empDao.getById(uuid);
		//清空用户下的所有角色
		emp.setRoles(new ArrayList<Role>());
		
		String[] ids = checkedStr.split(",");
		Role role = null;
		for(String id : ids){
			role = roleDao.getById(Long.valueOf(id));
			//设置用户的角色
			emp.getRoles().add(role);
		}
	}
	/**
	 * 查询用户下的菜单权限
	 * @param uuid
	 * @return
	 */
	public List<Menu> getMenuByEmpuuid(Long uuid){
		return empDao.getMenuByEmpuuid(uuid);
	}
	@Override
	public Menu readMenuByEmpuuid(Long uuid){
		//获取所有菜单
		Menu root = menuDao.getById("0");
		//用户菜单
		List<Menu> empMenus = empDao.getMenuByEmpuuid(uuid);
		//模菜单
		Menu menu = cloneMenu(root);
		//循环匹配
		//一级菜单
		Menu _m1 = null;
		Menu _m2 = null;
		for(Menu m1 : root.getMenus()){
			_m1 = cloneMenu(m1);
			//二级菜单循环
			for(Menu m2 : m1.getMenus()){
				//用户包含这个菜单
				if(empMenus.contains(m2)){
					_m2 = cloneMenu(m2);
					_m1.getMenus().add(_m2);
				}
			}
			//有二级菜单才能加到根菜单下
			if(_m1.getMenus().size() > 0){
				menu.getMenus().add(_m1);
			}
		}
		return menu;
	}
	/**
	 * 复制menu
	 * @param src
	 * @return
	 */
	private Menu cloneMenu(Menu src){
		Menu menu = new Menu();
		menu.setIcon(src.getIcon());
		menu.setMenuid(src.getMenuid());
		menu.setMenuname(src.getMenuname());
		menu.setUrl(src.getUrl());
		menu.setMenus(new ArrayList<Menu>());
		return menu;
	}
}
