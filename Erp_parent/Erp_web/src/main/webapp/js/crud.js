//提交的方法
var method = "";
//查询条件
var listParam = "";
$(function(){
	if(Request['type'] == 1){
		listParam = "?type=1";
	}
	if(Request['type'] == 2){
		listParam = "?type=2";
	}
});
$(function(){
	$('#grid').datagrid({    
	    url:name+'_listByPage' + listParam,    
	    columns:columns,
	    singleSelect: true,
	    pagination: true,
	    toolbar: [{
	    	text: '新增',
			iconCls: 'icon-add',
			handler: function(){
				method = "add";
				$('#editForm').form('clear');
				if(name == 'emp'){
					document.getElementById('username').removeAttribute("readonly");
				}
				
				$('#editDlg').dialog('open');
			}
		},'-',{
			text: '导出',
			iconCls: 'icon-excel',
			handler: function(){
				var formData = $('#searchForm').serializeJSON();
				//下载文件
				$.download(name + "_export" + listParam,formData);
			}
		},'-',{
			text: '导入',
			iconCls: 'icon-save',
			handler: function(){
				$('#importDlg').dialog('open');
			}
		}
		]
	});
	
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
	
	$('#editDlg').dialog({    
	    title: '编辑',    
	    width: 300,
	    height: 300,
	    closed: true,//窗口是是否为关闭状态, true：表示关闭    
	    modal: true//模式窗口
	});   
	
	$('#btnSave').bind('click',function(){

		var isValid = $('#editForm').form('validate');
		if(isValid == false){
			return;
		}
		var formData = $('#editForm').serializeJSON();
		$.ajax({
			url: name+'_' + method + listParam,
			data: formData,
			dataType: 'json',
			type: 'post',
			success:function(rtn){
				$.messager.alert("提示",rtn.message,'info',function(){
					//成功的话，我们要关闭窗口
					$('#editDlg').dialog('close');
					//刷新表格数据
					$('#grid').datagrid('reload');
				});
			}
		});
	});
	//判断是否有导入的功能
	var importForrm = document.getElementById('importForm');
	if(importForm){
		//导入数据的窗口
		$('#importDlg').dialog({
			title:'导入',
			width:330,
			height:100,
			modal:true,
			closed:true,
			buttons:[
					    {
					    	text: '导入',
					    	handler:function(){
					    		$.ajax({
					    			url: name + '_doImport',
					    			data:new FormData($('#importForm')[0]),
					    			type:'post',
					    			processData:false,
					    			contentType:false,
					    			dataType:'json',
					    			success:function(rtn){
					    				$.messager.alert('提示',rtn.message,'info',function(){
					    					if(rtn.success){
					    						$('#importDlg').dialog('close');
					    						$('#importForm').form('clear');
					    						$('#grid').datagrid('reload');
					    					}
					    				});
					    			}
					    		});
					    	}
					    }
					]
		});
	}
});


/**
 * 删除部门
 */
function del(uuid){
	$.messager.confirm("确认","确认要删除吗？",function(yes){
		if(yes){
			$.ajax({
				url: name+'_delete?uuid=' + uuid,
				dataType: 'json',
				type: 'post',
				success:function(rtn){
					$.messager.alert("提示",rtn.message,'info',function(){
						//刷新表格数据
						$('#grid').datagrid('reload');
					});
				}
			});
		}
	});
}

/**
 * 修改部门
 */
function edit(uuid){
	//弹出窗口
	$('#editDlg').dialog('open');
	if(name == 'emp'){
		document.getElementById('username').setAttribute('readonly', 'readonly')
	}
	//  为指定的element添加指定的属性，使用原生JS方法
	//jQuery('username').attr('disabled', 'disabled');
	//清空表单内容
	$('#editForm').form('clear');
	method = "update";
	//加载数据
	$('#editForm').form('load',name+'_get?uuid=' + uuid);
}
