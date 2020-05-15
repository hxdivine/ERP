<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门管理</title>
<link rel="stylesheet" type="text/css" href="ui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="ui/themes/icon.css">
<script type="text/javascript" src="ui/jquery.min.js"></script>
<script type="text/javascript" src="ui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui/jquery.serializejson.min.js"></script>
<script type="text/javascript" src="ui/download.js"></script>
<script type="text/javascript" src="js/crud.js"></script>

<script type="text/javascript">
var name = "dep";
var columns=[[    
  	        {field:'uuid',title:'部门编号',width:100},    
	        {field:'name',title:'部门名称',width:100},    
	        {field:'tele',title:'部门联系电话',width:100,align:'right'},
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
<form id="searchForm">
<table>
	<tr>
		<td>名称</td>
		<td><input name="name"></td>
		<td>电话</td>
		<td><input name="tele"></td>
		<td><button id="btnSearch" type="button">查询</button></td>
	</tr>
</table>

</form>
<div style="height:2px;"></div>
</div>

<table id="grid"></table>

<div id="editDlg">
<form id="editForm">
	<table>
		<tr>
			<td>部门名称</td>
			<td><input name="name"><input type="hidden" name="uuid" /></td>
		</tr>
		<tr>
			<td>部门电话</td>
			<td><input name="tele"></td>
		</tr>
	</table>
	<button id="btnSave" type="button">保存</button>
</form>
</div>

</body>
</html>