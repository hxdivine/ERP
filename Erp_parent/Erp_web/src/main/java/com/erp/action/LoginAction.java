package com.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.erp.biz.IEmpBiz;
import com.erp.entity.Emp;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction {
	private IEmpBiz empBiz;
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	private String username;
	private String pwd;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * 登录校验
	 */
	public void checkUser(){
		try {
/*			Emp loginUser = empBiz.findByUsernameAndPwd(username, pwd);
			if(loginUser != null){
				//记录当前登录的用户
				ActionContext.getContext().getSession().put("loginUser", loginUser);
				ajaxReturn(true,"");
			}else{
				ajaxReturn(false,"用户名或密码错误");
			}*/
			//创建令牌
			UsernamePasswordToken upt = new UsernamePasswordToken(username, pwd);
			//获取subject 封装了当前用户的一些操作
			Subject subject = SecurityUtils.getSubject();
			//执行登录
			subject.login(upt);
			ajaxReturn(true,"");
			
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false,"登录错误");
		}
	}
	/**
	 * 显示用户名
	 */
	public void showName(){
		//获取当前登录用户
		//Emp emp = (Emp)ActionContext.getContext().getSession().get("loginUser");
		//session是否超时，用户名是否登陆过
		Subject subject = SecurityUtils.getSubject();
		Emp emp = (Emp)subject.getPrincipal();
		if(null != emp){
			ajaxReturn(true,emp.getName());
		}else{
			ajaxReturn(false,"请重新登录");
		}
	}
	/**
	 * 退出登录
	 */
	public void loginOut(){
		//ActionContext.getContext().getSession().remove("loginUser");
		SecurityUtils.getSubject().logout();;
	}
	/**
	 * 写入json
	 * @param listString
	 */
	public void write(String listString ){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(listString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ajaxReturn(boolean success, String message){
		//返回前端的JSON数据
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("success",success);
		rtn.put("message",message);
		write(JSON.toJSONString(rtn));
	}
}
