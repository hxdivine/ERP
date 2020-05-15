package com.utils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * GBK
 */
public class charset implements Filter {
	private String encoding;
    public charset() {
        // TODO Auto-generated constructor stub
    }
	public void destroy() {
		encoding = null;
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(encoding!=null){                                                                          
			request.setCharacterEncoding(encoding);                                  
			response.setContentType("text/html;charset="+encoding);     
			HttpServletRequest req = (HttpServletRequest)request; 
			HttpServletResponse rsp = (HttpServletResponse)response;
			if(req.getMethod().equalsIgnoreCase("get")){                            
		
				req=new GetHttpServletRequestWrapper(req,encoding);
			}
			
			chain.doFilter(req, rsp);
			
		}
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.encoding = fConfig.getInitParameter("encoding");
	}

}
