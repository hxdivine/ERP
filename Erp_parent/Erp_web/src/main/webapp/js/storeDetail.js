$(function(){
	//列表
	$('#grid').datagrid({
		url:'storeDetail_listByPage',
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'storeName',title:'仓库',width:100},
		  		    {field:'goodsName',title:'商品',width:100},
		  		    {field:'num',title:'数量',width:100}
					]],
		singleSelect: true,
		pagination: true,
		fitColumns:true,
		onDblClickRow:function(rowIndex, rowData){
			//rowIndex， 行的索引
			//rowData， 行里的数据
			//alert(JSON.stringify(rowData));
			//显示详情
			$('#uuid').html(rowData.uuid);
			$('#storeName').html(rowData.storeName);
			$('#goodsName').html(rowData.goodsName);
			$('#num').html(rowData.numName);
			//打开窗口
			$('#storeOperDlg').dialog('open');
			
		}
	});
	//点击查询按钮
	$('#btnOperSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
	

	//增加操作详情的窗口
	$('#storeOperDlg').dialog({
		title:'库存变动详情',
		width:850,
		height:480,
		modal:true,
		closed:true
	});
})
	