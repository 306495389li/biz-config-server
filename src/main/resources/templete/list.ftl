<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String context = request.getContextPath();
	pageContext.setAttribute("context_", context);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${entityNameCN}管理</title>
<jsp:include page="../../auth/common/common.jsp"></jsp:include>
<%--  下拉框的典型代码
<div type="comboboxcolumn" name="xxx" field="xxx"
	headerAlign="center" align="left" allowSort="true" width="80">
	xxx
	<input property="editor" class="mini-combobox"  textField="xxx"
		valueField="xxx" allowInput="false" style="width: 100%;"
		url="${contextPath}/xxx/xxx/findAll/xxx/xxx" 
	/>
</div>
		
<div type="comboboxcolumn" name="xxx" field="xxx" align="left" width="50" headerAlign="center" allowSort="true">
	xxx
	<input property="editor"  class="mini-combobox" name="xxx" textField="text" valueField="id"
		url="${contextPath}/sys/adRefrence/getDropDownList/xxx" />
</div>	

<div type="comboboxcolumn" name="adShopId" field="adShopId"	headerAlign="center" align="left" allowSort="true" width="50">
	店铺
	<input property="editor" class="mini-combobox"  textField="shopName"  valueField="adShopId" allowInput="false" style="width: 100%;"
		url="${contextPath}/auth/adShop/findAll/auth/adShop"
	/>
</div>
--%>
</head>
<body>
	<div class="mini-panel" title="${entityNameCN}查询" iconCls="icon-add"	style="width: 100%; height: 100;" showToolbar="false" showFooter="true">
		<div class="mini-fit">
			<div id="form_${springName}" class="mini-form" align="center" style="height: 100%">
				<table id="table1" class="table" style="height: 100%">
					<#list properties as prop>
					<tr>
							<#if prop.pri == false>
								<#if prop.type =="Date">
						<td class="form_label">${prop.comment!""} >=:</td>
						<td colspan="3"><input class="mini-datepicker" id="${prop.name}" style="width: 80%;" name="GTE_${prop.name}" onenter="onKeyEnter" vtype="date:yyyy-MM-dd" format="yyyy-MM-dd" showTime="false"/></td>
								<#else>
						<td class="form_label">${prop.comment!""} :</td>
						<td colspan="3"><input class="mini-textbox" id="${prop.name}" style="width: 80%;" name="EQ_${prop.name}" onenter="onKeyEnter" /></td>
								</#if> 
							</#if> 
					</tr>
					</#list>
				</table>
			</div>
		</div>
	</div>
	<div property="footer" align="center">
		<a class="mini-button" onclick="search()" iconCls="icon-search" plain="true"> 查询 </a> 
		<a class="mini-button" onclick="reset()" iconCls="icon-redo" plain="true"> 重置 </a>
	</div>
	<div class="mini-panel" title="${entityNameCN}列表" iconCls="icon-add" style="width: 100%; height: 83%" showToolbar="false" showFooter="false">
		<div style="width: 100%;">
			<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button"	iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
							<a class="mini-button" iconCls="icon-remove" onclick="remove()" plain="true">删除</a> 
							<a class="mini-button" iconCls="icon-save" onclick="saveData()"	plain="true">保存</a>
						</td>
					</tr>
						<%--
					<tr>
						<td style="width: 100%;">
							<a class="mini-button"	iconCls="icon-add" onclick="addRow_in_form()" plain="true">增加</a>
							<a class="mini-button" iconCls="icon-edit" onclick="edit_in_form()"	plain="true">编辑</a>
							<a class="mini-button" iconCls="icon-remove" onclick="remove()" plain="true">删除</a> 
							<a class="mini-button" iconCls="icon-save" onclick="saveData()"	plain="true">保存</a>
						</td>
					</tr>
						--%>
				</table>
			</div>
		</div>
		<div class="mini-fit" style="width: 100%;height:600px">
			<div id="datagrid_${springName}" class="mini-datagrid" style="width: 100%; height: 540px;" allowResize="false" showPager="true" dataField="rows"
				pageSize="20" allowCellEdit="true" allowCellSelect="true" multiSelect="true"
				url="${contextPath}/${moduleName}/${springName}/find/${moduleName}/${springName}"	>
				<div property="columns">
					<div type="indexcolumn" width="25"></div>
					<div type="checkcolumn" width="25"></div>
						<#list properties as prop>
							<#if prop.pri>
					<div name="${prop.name}" field="${prop.name}" headerAlign="center"	width="50" align="center" allowSort="true">ID</div>
							<#else>
								<#if prop.type =="Date">
					<div name="${prop.name}" field="${prop.name}" headerAlign="center" property="editor" align="center" allowSort="true" width="50" renderer="onDateRenderer">${prop.comment!""} <input property="editor" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" /></div>
								<#else>
					<div name="${prop.name}" field="${prop.name}" headerAlign="center" property="editor" align="left" allowSort="true" width="60">${prop.comment!""} <input property="editor" class="mini-textbox" /></div>
								</#if> 
							</#if> 
						</#list>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var datagrid_${springName} = mini.get("datagrid_${springName}");
		var form_${springName} = new mini.Form("form_${springName}");
		datagrid_${springName}.load();

		//查询
		function search() {
			var data = form_${springName}.getData();
			datagrid_${springName}.load(data);
		};

		//重置
		function reset() {
			form_${springName}.reset();
			datagrid_${springName}.load();
		}

		//刷新
		function reload() {
			search();
		}

		//回车事件，调用查询
		function onKeyEnter(e) {
			search();
		};

		//添加一行
		function addRow() {
			var newRow = {
				name : ""
			};
			datagrid_${springName}.addRow(newRow, 0);
		};
		
		//在表单中新增
		function addRow_in_form(){
		mini.open({
		  		url : "${contextPath}/${moduleName}/${springName}/edit",
				title : "新增",
				width : 500,
				height : 300,
				onload : function() {
				},
				ondestroy : function(action) {
					if(action =="success"){
						mini.alert("新增成功！");
						datagrid_${springName}.load();	
					}
				}
			});
		}
		//在表单中编辑
		function edit_in_form(){
			var row = datagrid_${springName}.getSelected();
			if (row) {
				mini.open({
					url : "${contextPath}/${moduleName}/${springName}/edit",
					title : "编辑",
					width : 500,
					height : 300,
					onload : function() {
						var iframe = this.getIFrameEl();
						iframe.contentWindow.SetData(row);
					},
					ondestroy : function(action) {
						if(action == "success"){
							mini.alert("修改成功！");
							datagrid_${springName}.load();
						}
					}
				});
			} else {
				mini.alert("请选中一条记录");
			}
		
		}
		
		//删除-页面批量删除
	    function remove() {
			var row = datagrid_${springName}.getSelecteds();
			if (row) {
				datagrid_${springName}.removeRows(row);
			} else {
				mini.alert("请选中一条记录");
			}
		};
		
		//保存-批量
		function saveData() {
			var data = datagrid_${springName}.getChanges();//获取页面新增/删除/修改的数据
			if (data.length > 0) {
			    var json = mini.encode(data,"yyyy-MM-dd HH:mm:ss");
                mini.mask({
                    el: document.body,
                    cls: 'mini-mask-loading',
                    html: '保存中，请稍后......'
                });
                $.ajax({
                    url : "${contextPath}/${moduleName}/${springName}/doBatchSaveOrUpdateOrDelete/${moduleName}/${springName}",
                    data : json,
                    type : "post",
                    contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
                    success : function(text) {
                        mini.unmask();
                        var jsonObj = eval("("+text+")");
                        mini.alert("成功"+jsonObj["successNumber"]+"个</br>失败"+jsonObj["errorNumber"]+"个","系统提示");
                        datagrid_${springName}.reload();
                    },
                    error : function(jqXHR, textStatus, errorThrown) {
                        alert(jqXHR.responseText);
                    }
                });
			} else {
                mini.alert("没有数据变动");
                datagrid_${springName}.reload();
            }
		}
	</script>
</body>
</html>