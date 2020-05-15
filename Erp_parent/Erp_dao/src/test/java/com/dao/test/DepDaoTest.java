package com.dao.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.erp.dao.impl.DepDao;

public class DepDaoTest {
	@Test
	public void testDep(){
		ApplicationContext at = new ClassPathXmlApplicationContext("classpath:spring/applicationContext_dao.xml");
		DepDao depDao = (DepDao)at.getBean("depDao");
		System.out.println(depDao.getList().size());
	}
	public static void main(String[] args){
		ApplicationContext at = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
		DepDao depDao = (DepDao)at.getBean("depDao");
		System.out.println(depDao.getList().size());
	}
}
