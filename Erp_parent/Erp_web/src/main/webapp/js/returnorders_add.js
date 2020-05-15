//订单索引
var existEditIndex = -1;
var id = "";
$(function() {
 	$('#ordersgrid').datagrid({
 		fitColumns:true,
    	columns:[[
    	  		{field:'goodsuuid',title:'商品编号',width:100,editor:{type:'numberbox',options:{
    	  			disabled:true
    	  		}}},
    	  		{field:'goodsname',title:'商品名称',width:100,editor:{type:'combobox',options:{
    	  			editable:false,
    	  			url:'goods_list',
    	  			valueField:'name',
		        	  textField:'name', //可能要写name
    	    	//下面onSelect这里传的参数是record,其实就是所在行为商品属性,所以record为商品goods
    	  			onSelect:function(goods) {
    	  				
    	  			  //获取商品编辑编辑器
		        		  var goodsuuidEditor = getEditor('goodsuuid');
		        		  //target，指向真正使用element
		        		  //给商品编号赋值
		        		  $(goodsuuidEditor.target).val(goods.goodsuuid);
		        		  //获取仓库数量编辑器
		        		  var storenumEditor = getEditor('storenum');
		        		  $.ajax({
		        			  url:'storeDetail_getStoreNum?goodsuuid='+goods.goodsuuid,
		        			  type:'post',
		        			  dataType:'json',
		        			  success:function(rtn) {
		        				 //alert(JSON.stringify(rtn));
		        				  $(storenumEditor.target).val(rtn);
		        			  }
		        		  });
		        		  //获取价格编辑器
		        		  var priceEditor = getEditor('price');
		        		  if(Request['type'] * 1 == 1){//采购订单
		        			  //设置为进货价格		        		  
		        			  $(priceEditor.target).val(goods.inprice);
		        		  }
		        		  if(Request['type'] * 1 == 2){//销售订单
		        			  //设置为销售价格		        		  
		        			  $(priceEditor.target).val(goods.outprice);
		        		  }		        		  
		        		  //获取数量编辑器
		        		  var numEditor = getEditor('num');
		        		  //选中数量输入框
		        		  $(numEditor.target).select();
		        		  //绑定事件
		        		  bindGridEditor();
		        		  //计算金额
		        		  cal();
		        		  //计算合计金额
		        		  sum();
    	  			}
    	  		}}},
    	  		{field:'price',title:'价格',width:100,editor:{type:'numberbox',options:{
    	  			disabled:true,
    	  			precision:2	
    	  		}}},
    	  		{field:'num',title:'数量',width:100,editor:'numberbox'},
    	  		{field:'storenum',title:'仓库数量',width:100,editor:{type:'numberbox',options:{
    	  			disabled:true
    	  		}}},
    	  		{field:'money',title:'金额',width:100,editor:{type:'numberbox',options:{
    	  			disabled:true,
    	  			precision:2	
    	  		}}},
    	  		{field:'-',title:'操作',width:100,formatter:function(value,row,rowindex){
	    	  	   if( row.num != '合计'){
	    	  		  return '<a href="javascript:void(0)" onclick="deleteRow('+ rowindex +')">删除<a/>';
	    	  	   }}}  
    	  		]],
    	  		singleSelect:true,
    			//显示编辑
    			rownumbers: true,
    			//显示行脚
    			showFooter: true,
    	  		toolbar:[{
    	  			text:'新增',
    	  			iconCls:'icon-add',
    	  			handler:function(){
    	  				//把下拉列表菜单清空
    	  				
    	  				if(existEditIndex > -1) {
    	  					$('#ordersgrid').datagrid('endEdit',existEditIndex);
    	  				}
    	  				$('#ordersgrid').datagrid('appendRow',{price:0,num:0,money:0,storenum:0});
    	  				//得到所有行,数组
    	  				var rows = $('#ordersgrid').datagrid('getRows');
    	  				existEditIndex = rows.length-1;
    	  				//开启编辑
    	  				$('#ordersgrid').datagrid('beginEdit',rows.length-1);
    	  				}
    	  		},'-',{
    				text: '提交',
    				iconCls: 'icon-save',
    				handler: function(){
    					//1. 存在编辑状态的行
    					if(existEditIndex > -1){
    						$('#ordersgrid').datagrid('endEdit',existEditIndex);
    					}
    					//获取所有的明细
    					var rows = $('#ordersgrid').datagrid('getRows');
    					if(rows.length == 0){
    						return;
    					}
    					var formdata = $('#orderForm').serializeJSON();
    					//转换成json字符串
    					//给formdata加了一个json属性，key。同时再给它赋值
    					formdata.json = JSON.stringify(rows);
    					//formdata['json']= JSON.stringify(rows);
    					$.ajax({
    						url: 'returnOrders_add?type=' + Request['type'],
    						data: formdata,
    						dataType: 'json',
    						type: 'post',
    						success:function(rtn){
    							$.messager.alert('提示',rtn.message,'info',function(){
    								if(rtn.success){
    									//清空供应商
    									$('#supplier').combogrid('clear');
    									//清空表格
    									$('#ordersgrid').datagrid('loadData',{total:0, rows:[],footer:[{num: '合计', money: 0}]});
    									
    									//关闭增加订单的窗口
    									$('#addOrdersDlg').dialog('close');
    									//刷新订单列表
    									$('#grid').datagrid('reload');
    								}
    							});
    						}
    					});
    				}
    			
    			}
    		],
    	  		onClickRow:function(rowIndex, rowData){
    	  			//rowIndex：点击的行的索引值，该索引值从0开始。
    	  			//rowData：对应于点击行的记录。			
    	  			//关闭当前可以编辑的行
    	  			$('#ordersgrid').datagrid('endEdit',existEditIndex);
    	  			//设置当前可编辑的索引行
    	  			existEditIndex = rowIndex;
    	  			$('#ordersgrid').datagrid('beginEdit',existEditIndex);
    	  			//绑定自动计算
    	  			bindGridEditor();
    	  		}
 	});
	
	//加行脚
	$('#ordersgrid').datagrid('reloadFooter',[{num: '合计', money: 0}]);
	
	
	//下拉列表
	$('#supplier').combogrid({    
	    panelWidth:450,    
	    value:'',
	    idField:'uuid',    
	    textField:'name',    
	    url:'supplier_list?type=' + Request['type'],
	    columns:[[    
	        {field:'uuid',title:'编号',width:100},   
	        {field:'name',title:'名称',width:100},   
	        {field:'contact',title:'联系人',width:60},  
	        {field:'address',title:'联系地址',width:120},    
	        {field:'tele',title:'电话',width:100},
	        {field:'email',title:'邮箱',width:100}    
	    ]],
	    mode:'remote',
	    onChange:function(goods){
	    	//点击时候获取ID,
	    	id = $('#supplier').combogrid('getValue'); 
	    	//alert(id);
	    	//获取列属性,重新加载ID,
	    	var a =$('#ordersgrid').datagrid('getColumnOption','goodsname');
	    	//alert(JSON.stringify(a.editor.options.url));
	    	a.editor.options.url='goods_getSupplierList?supplieruuid='+id;
	    	$('#ordersgrid').datagrid('reload',{
	    		goodsname:{}
	    	});
	    	$('#ordersgrid').datagrid('reload',{
	    		goodsname:a
	    	});
	    	
	    	if(existEditIndex != -1) {
	    	var goodsnameEditor = getEditor('goodsname');
	    	$(goodsnameEditor.target).combobox('reload','goods_getSupplierList?supplieruuid='+id);
	    	}
	    }
	});  
	
});


/**
 * 获取当前编辑行的指定编辑器
 * @param _field
 * @returns
 */
function getEditor(_field){
	return $('#ordersgrid').datagrid('getEditor',{index:existEditIndex,field:_field});
}


/**
 * 绑定键盘的输入事件
 */
function bindGridEditor(){
	//获取数量编辑器
	var numEditor = getEditor('num');
	$(numEditor.target).bind('keyup',function(){
		//计算金额
		cal();
		//计算合计金额
		sum();
		
		//比较数量
		compareNum();
	});
	
	//绑定价格编辑器
	var priceEditor = getEditor('price');
	$(priceEditor.target).bind('keyup',function(){
		//计算金额
		cal()
		//计算合计金额
		sum();
	
	});
}




/**
 * 计算金额
 */
function cal(){
	//获取数量编辑器
	var numEditor = getEditor('num');
	//取得商品的数量
	var num = $(numEditor.target).val();
	
	//获取价格编辑器
    var priceEditor = getEditor('price');
    //取出进货价格
    var price = $(priceEditor.target).val();
    
    //计算金额
    var money = num * price;
    //保留2位小数
    money = money.toFixed(2);

    //获取金额编辑器
    var moneyEditor = getEditor('money');
    //设置金额
    $(moneyEditor.target).val(money);
    
    //更新表格中的数据,设置row json对象里的key对应的值
    $('#ordersgrid').datagrid('getRows')[existEditIndex].money = money;
}




/**
 * 计算合计金额
 */
function sum(){	
	//获取所有行
	var rows = $('#ordersgrid').datagrid('getRows');
	var total = 0;
	//循环累计
	$.each(rows, function(i, row){
		total += parseFloat(row.money);
	});
	total = total.toFixed(2);
	
	//设置合计金额到行脚里去
	$('#ordersgrid').datagrid('reloadFooter',[{num: '合计', money: total}]);
}

//比较数量
function compareNum() {
	//获取仓库数量编辑器
	var storenumEditor = getEditor('storenum');
	//获取仓库数量值,
	var storenum = $(storenumEditor.target).val();
	
	////获取金额编辑器
	var moenyEditor = getEditor('money');
	
	//获取数量编辑器
	var numEditor = getEditor('num');
	//取得商品的数量
	var num = $(numEditor.target).val();
	
	if(num * 1> storenum * 1) {
		$.messager.alert('提示','库存数量不足','info',function() {
			$(numEditor.target).numberbox('setValue', 0);

			$(moenyEditor.target).numberbox('setValue', 0);
			
			
			sum1();
		})
	}
	if(num * 1 < 0) {
		$.messager.alert('提示','不能输入负数','info',function() {
			$(numEditor.target).numberbox('setValue', 0);

			$(moenyEditor.target).numberbox('setValue', 0);
			
			
			sum1();
		})
	}
}

/**
 * 删除行
 * @param rowIndex
 */
function deleteRow(rowIndex){
	$.messager.confirm("确认","确认要删除吗？",function(yes){
	//alert(JSON.stringify(data));
	//关闭编辑
		if(yes){
	$('#ordersgrid').datagrid('endEdit',existEditIndex);
	//删除行
	$('#ordersgrid').datagrid('deleteRow',rowIndex);
	
	var data = $('#ordersgrid').datagrid('getData');
	//重新加载数据
	$('#ordersgrid').datagrid('loadData',data);
	//计算合计
	sum();
		}
	})
}


/**
 * 计算合计金额(不要本行的金额)
 */
function sum1(){	
	//获取所有行
	var rows = $('#ordersgrid').datagrid('getRows');
	var total = 0;
	//循环累计
	$.each(rows, function(i, row){
		if(i != existEditIndex){
			total += parseFloat(row.money);
		}
	});
	
	total = total.toFixed(2);
	
	//设置合计金额到行脚里去
	$('#ordersgrid').datagrid('reloadFooter',[{num: '合计', money: total}]);
}
	


