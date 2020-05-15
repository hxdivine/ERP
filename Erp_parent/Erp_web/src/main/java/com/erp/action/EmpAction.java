package com.erp.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSON;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.IEmpBiz;
import com.erp.entity.Emp;
import com.erp.entity.Menu;
import com.erp.entity.Tree;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 员工表
 * @author 骄傲的大树
 *
 */
public class EmpAction extends BaseAction<Emp> implements ModelDriven<Emp>,IBaseAction {

	private Emp emp=new Emp();
	
	public Emp getModel() {
		return emp;
	}
	private IEmpBiz empBiz;

	
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(empBiz);
	}
	private String oldPwd;
	private String newPwd;
	
	
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	/**
	 * 重置密码
	 */
	public void updatePwd_resert(){
		try {
			empBiz.updatePwd_resert(emp.getUuid(), newPwd);
			ajaxReturn(true,"重置密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false,"重置密码失败");
		}
	}
	/**
	 * 修改密码
	 */
	public void updatePwd(){
		Emp loginUser = getLoginUser();
		if(null == loginUser){
			ajaxReturn(false,"亲，您没有登录用户");
			return;
		}
		//try-catch 防止数据库出错
		try {
			empBiz.updatePwd(loginUser.getUuid(), oldPwd, newPwd);
			ajaxReturn(true,"修改密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false,"修改密码失败");
		}
		
	}
	/**
	 * 条件查询所有部门信息
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	/**
	 * 点击修改时回显数据
	 */
	public void get(){
		super.Toget(emp.getUuid());
	}
	/**
	 * 修改方法
	 */
	public void update(){
		super.Toupdate(emp);
	}
	/**
	 * 删除方法
	 */
	public void delete(){
		super.Todelete(emp.getUuid());
	}
	
	public void add(){
		super.Toadd(emp);
	}
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Emp.class);
		if(emp != null){
			if (emp.getUsername() != null && emp.getUsername().trim().length() > 0) {
				dc.add(Restrictions.like("username", emp.getUsername(), MatchMode.ANYWHERE));
			}
			if (emp.getName() != null && emp.getName().trim().length() > 0) {
				dc.add(Restrictions.like("name", emp.getName(), MatchMode.ANYWHERE));
			}
			if (emp.getGender() != null) {
				dc.add(Restrictions.eq("gender", emp.getGender()));
			}
			if (emp.getEmail() != null && emp.getEmail().trim().length() > 0) {
				dc.add(Restrictions.like("email", emp.getEmail(), MatchMode.ANYWHERE));
			}
			if (emp.getTele() != null && emp.getTele().trim().length() > 0) {
				dc.add(Restrictions.like("tele", emp.getTele(), MatchMode.ANYWHERE));
			}
			if (emp.getAddress() != null && emp.getAddress().trim().length() > 0) {
				dc.add(Restrictions.like("address", emp.getAddress(), MatchMode.ANYWHERE));
			}

		}
		return dc;
	}
	/**
	 * 导出数据
	 */
	public void export(){
		String filename = "员工";
		filename += ".xls";
		//响应对象
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			//设置输出流格式
			response.setHeader("Content-Disposition", "attachment;filename=" + 
			new String(filename.getBytes(),"ISO-8859-1"));
			empBiz.export(response.getOutputStream(), getDetachedCriteria());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户角色
	 */
	public void readEmpRoles(){
		List<Tree> roleList = empBiz.readEmpRoles(emp.getUuid());
		write(JSON.toJSONString(roleList));
	}
	private String checkedStr;
	
	public String getCheckedStr() {
		return checkedStr;
	}
	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}
	public void updateEmpRoles(){
		try {
			empBiz.updateEmpRoles(emp.getUuid(), checkedStr);
			ajaxReturn(true,"更新用户角色菜单成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false,"更新用户角色菜单失败");
		}
	}
	
	/**
	 * 查询用户下的菜单权限
	 */
	public void getMensByEmpuuid(){
		Emp emp1 = getLoginUser();
		if(emp1 != null){
			List<Menu> menuList = empBiz.getMenuByEmpuuid(emp1.getUuid());
			write(JSON.toJSONString(menuList));
		}
	}
}
