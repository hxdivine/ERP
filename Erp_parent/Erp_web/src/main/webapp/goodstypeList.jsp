<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>商品类型</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!-- 引入easyUI  css和js -->
    <link rel="stylesheet" type="text/css" href="ui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui/themes/icon.css">
	<script type="text/javascript" src="ui/jquery.min.js"></script>
	<script type="text/javascript" src="ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui/jquery.serializejson.min.js"></script>
	<script type="text/javascript" src="ui/download.js"></script>
	<script type="text/javascript" src="js/crud.js"></script>
    
    <script type="text/javascript">
         var name="goodsType";
         var columns=[ [ 
			{field : 'uuid', title : '商品类型编号',width:80},
			{field : 'name', title : '商品类型名称',width:80},

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
			<td>商品类型名称</td><td><input name="name"></td>

		</tr>
	</table>
	<button id="btnSearch" type="button">查询</button>
</form>
<div style="height:2px;"></div>
</div>

<table id="grid"></table>

<div id="editDlg">
<form id="editForm">
		<input type="hidden" name="uuid" />

		<table>
			<tr>
    		  <td>商品类型名称</td>
   			   <td><input name="name"></td>
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
