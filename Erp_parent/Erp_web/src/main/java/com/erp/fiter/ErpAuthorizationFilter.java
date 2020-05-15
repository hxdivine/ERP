package com.erp.fiter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
/**
 * 自定义授权过滤器
 * @author 骄傲的大树
 *
 */
public class ErpAuthorizationFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		//获取主题
		Subject subject = getSubject(request,response);
		String[] perms = (String[]) mappedValue;
		boolean isPermitted = true;
		if(null == perms || perms.length == 0){
			return isPermitted;
		}
		if(perms != null && perms.length > 0){
			for(String perm : perms){
				//如果有一个相符返回true
				if(subject.isPermitted(perm)){
					return true;
				}
			}
		}
		return false;
	}

}
