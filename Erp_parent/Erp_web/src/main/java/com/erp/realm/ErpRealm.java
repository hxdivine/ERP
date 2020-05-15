package com.erp.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.erp.biz.IEmpBiz;
import com.erp.entity.Emp;
import com.erp.entity.Menu;

public class ErpRealm extends AuthorizingRealm {
	
	private IEmpBiz empBiz;

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	/**
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取登录用户
		Emp emp = (Emp)arg0.getPrimaryPrincipal();
		//获取登录用户的菜单
		List<Menu> menuList = empBiz.getMenuByEmpuuid(emp.getUuid());
		//加入授权
		for(Menu m : menuList){
			info.addStringPermission(m.getMenuname());
		}
		return info;
	}

	/**
	 * 认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		//获取密码
		String pwd = new String(upt.getPassword());
		//获取登录用户
		Emp emp = empBiz.findByUsernameAndPwd(upt.getUsername(), pwd);
		
		if(emp != null){
			//参数1 主角
			//参数2 授权码 密码
			//参数3 realm的名称
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(emp,pwd,getName());
			return info;
		}
		return null;
	}

}
