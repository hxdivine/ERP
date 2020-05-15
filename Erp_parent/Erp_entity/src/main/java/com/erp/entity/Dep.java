package com.erp.entity;

/**
 * 部门
 * @author 骄傲的大树
 *
 */
public class Dep {
	
	/**
	 *部门编号 
	 */
	private long uuid;
	
	/**
	 * 部门名称
	 */
	private String name;
	
	/**
	 * 联系电话
	 */
	private String tele;

	public long getUuid() {
		return uuid;
	}

	public void setUuid(long uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}
	
	
}
