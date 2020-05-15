$(function(){
	$('#Opergrid').datagrid({
		url:'storeOper_listByPage',
		
		columns:[[
		  		    {field:'uuid',title:'编号',width:50},
		  		    {field:'empName',title:'操作员工编号',width:50},
		  		    {field:'opertime',title:'操作日期',width:80,formatter:formatDate},
		  		    {field:'storeName',title:'仓库编号',width:80},
		  		    {field:'goodsName',title:'商品编号',width:80},
		  		    {field:'num',title:'数量',width:80},
		  		    {field:'type',title:'类型',width:80,formatter:function(value){
		  		    	//1：入库 2：出库
		  		    	if(value * 1 == 1){
		  		    		return "入库";
		  		    	}
		  		    	if(value * 1 == 2){
		  		    		return "出库";
		  		    	}
		  		    }}
			]],
		pagination : true,
		singleSelect : true,
		paginatioin : true,
		fitColumns : true
	});
	//点击查询按钮
	$('#btnOperSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchOperForm').serializeJSON();
		$('#Opergrid').datagrid('load',formData);
	});
})

function formatDate(value){
	return new Date(value).Format('yyyy-MM-dd hh:mm:ss');
}