package com.erp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 树形菜单数据实体
 * @author 骄傲的大树
 *
 */
public class Tree {
	
	private String id; //菜单id
	private String text; //菜单标题
	private boolean checked; //是否被选中
	private List<Tree> children; //子菜单
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Tree> getChildren() {
		//避免出现空引用
		if(children == null){
			children = new ArrayList<Tree>();
		}
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	
	
}
