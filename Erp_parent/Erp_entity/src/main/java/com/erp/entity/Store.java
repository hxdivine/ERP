package com.erp.entity;
/**
 * 仓库
 * @author 骄傲的大树
 *
 */
public class Store {

	private Long uuid;//编号
	private String name;//名称
	//员工编号
	private Long empuuid;
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getEmpuuid() {
		return empuuid;
	}
	public void setEmpuuid(Long empuuid) {
		this.empuuid = empuuid;
	}

	
}
