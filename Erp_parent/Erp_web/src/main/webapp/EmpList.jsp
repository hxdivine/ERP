<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工管理</title>
 <!-- 引入easyUI  css和js -->
	<link rel="stylesheet" type="text/css" href="ui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui/themes/icon.css">
	<script type="text/javascript" src="ui/jquery.min.js"></script>
	<script type="text/javascript" src="ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui/jquery.serializejson.min.js"></script>
	<script type="text/javascript" src="ui/date.js"></script>
	<script type="text/javascript" src="ui/download.js"></script>
	<script type="text/javascript" src="js/crud.js"></script>
	
<script type="text/javascript">
         var name = "emp";
         var height =300;
         var columns = [ [ 
			{field : 'uuid', title : '编号',width:30},
			{field : 'username', title : '登陆名',width:80},
			{field : 'name', title : '真实姓名',width:80},
			{field : 'gender', title : '性别',width:30,formatter:function(value,row,index){
				if(1 == value * 1){
					return '男';
				}
				if(0 == value * 0){
					return '女';
				}
			}},
			{field : 'email', title : '邮件地址',width:80},
			{field : 'tele', title : '联系电话',width:80},
			{field : 'address', title : '联系地址',width:100},
			{field : 'birthday', title : '出生年月日',width:80,formatter:function(value){
				return new Date(value).Format("yyyy-mm-dd");
			}},
			{field : 'dep', title : '部门名称',width:80,formatter:function(value){
				return value.name;
			}},
			{field:'-',title:'操作',formatter: function(value,row,index){
	        	var oper = "<a href=\"javascript:void(0)\" onclick=\"edit(" + row.uuid + ')">修改</a>';
	        	oper += ' <a href="javascript:void(0)" onclick="del(' + row.uuid + ')">删除</a>';
	        	return oper;
			}}
	    ]];
    
    </script>
  </head>
 <body>
<div class="easyui-panel" style="padding-left:4px;border-bottom:0px;">
<div style="height:2px;"></div>
<form id="searchForm" method="post">
	<table>
		<tr>
			<td>登陆名</td><td><input name="username"></td>
			<td>真实姓名</td><td><input name="name"></td>	
			<td>性别</td>
			<td>
				<select name="gender">
					<option>--请选择--</option>
					<option value="0">女</option>
					<option value="1">男</option>
				</select>
			</td>	
			<td>邮件地址</td><td><input name="email"></td>
		</tr>
		<tr>
			<td>联系电话</td><td><input name="tele"></td>
			<td>联系地址</td><td><input name="address"></td>
			<td>出生年月日</td><td><input name="birthday"></td>
			<td>部门</td>
			<td>
				<input name="dep.uuid" class="easyui-combobox" 
			      	data-options="url:'dep_list', textField:'name', valueField:'uuid' ">
			</td>
		</tr>
	</table>
	<button id="btnSearch" type="button">查询</button>
</form>
<div style="height:2px;"></div>

<table id="grid"></table>
<div id="editDlg">
  	  <form id="editForm" method="post">
			<input type="hidden" name="uuid" />
	
			<table hight="500px">
				<tr>
    				<td>登录名</td>
   				    <td><input name="username" class="easyui-validatebox" id="username"
   				    data-options=" required:'true',missingMessage:'登录名不能为空' "></td>
				</tr>
				<tr>
				      <td>真实姓名</td>
				      <td><input name="name"></td>
				</tr>
			<tr>
			      <td>性别</td>
			      <td>
			      	<input name="gender" type="radio" value="0">女
			      	<input name="gender" type="radio" value="1">男
			      </td>
			</tr>
			<tr>
			      <td>邮件地址</td>
			      <td><input name="email" class="easyui-validatebox"
			       data-options="required:'true',validType:'email',invalidMessage:'Email格式错误' ">
			       </td>
			</tr>
			<tr>
			      <td>联系电话</td>
			      <td><input name="tele"></td>
			</tr>
			<tr>
			      <td>联系地址</td>
			      <td><input name="address"></td>
			</tr>
			<tr>
			      <td>出生年月日</td>
			      <td><input name="birthday" class="easyui-datebox" editable="false"></td>
			</tr>
			<tr>
			      <td>部门</td>
			      <td>
			      	<input name="dep.uuid" class="easyui-combobox" 
			      	data-options="url:'dep_list', textField:'name', valueField:'uuid',required:'true' ">
			      </td>
			</tr>
			
						
			<tr>
				<td colspan="2" align="center">
					<button id="btnSave" type="button">保存</button>
					
				</td>
			</tr>
		</table>
					
					
	</form>
</div>
</body>
</html>