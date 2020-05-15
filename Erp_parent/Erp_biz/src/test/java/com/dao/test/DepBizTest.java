package com.dao.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.erp.biz.impl.DepBiz;
import com.erp.dao.impl.DepDao;

public class DepBizTest {
	@Test
	public void testDep(){
		//ApplicationContext aat = new ClassPathXmlApplicationContext("classpath:spring/applicationContext_tx.xml");
		ApplicationContext at = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-biz.xml");
		DepBiz depBiz = (DepBiz)at.getBean("depBiz");
		System.out.println(depBiz.getList().size());
	}
	public static void main(String[] args){
		ApplicationContext aat = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-tx.xml");
		ApplicationContext at = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-biz.xml");
		DepBiz depBiz = (DepBiz)at.getBean("depBiz");
		System.out.println(depBiz.getList().size());
	}
}
