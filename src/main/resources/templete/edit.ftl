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
</head>
<body>
	<div class="mini-fit">
	<div id="form_edit_${springName}" style="padding-left: 50px; padding-bottom: 5px;">
		<input class="mini-hidden" id="_state" name="_state" value="added" />
		<table>

			<#list properties as prop>
					<#if prop.pri == false>
						<#if prop.type =="Date">
			<tr>
				<td class="form_label">${prop.comment!""}</td>
				<td colspan="3"><input class="mini-datepicker" id="${prop.name}" style="width: 80%;" name="${prop.name}" vtype="date:yyyy-MM-dd" format="yyyy-MM-dd" showTime="false"/></td>
			</tr>
						<#else>
			<tr>
				<td class="form_label">${prop.comment!""} :</td>
				<td colspan="3"><input class="mini-textbox" id="${prop.name}" style="width: 80%;" name="${prop.name}" /></td>
			</tr>
						</#if> 
					<#else>
			<tr>
				<td colspan="6">
					<input class="mini-hidden" id="${prop.name}" style="width: 80%;" name="${prop.name}" />
				</td>
			</tr>
					</#if> 
			</#list>
		
		</table>
	</div>
	</div>
	<div class="mini-toolbar" style="text-align:center;padding-top:3px;padding-bottom:3px;" 
        borderStyle="border-left:0;border-bottom:0;border-right:0;">
    	<a class="mini-button" iconCls="icon-save" onclick="save">保存</a>
    	<a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var form_edit_${springName} = new mini.Form("form_edit_${springName}");
		
		function save() {
			var data = form_edit_${springName}.getData();
			form_edit_${springName}.validate();
			if (form_edit_${springName}.isValid() == false)return;
			$.ajax({
						url : "${contextPath}/${moduleName}/${springName}/save/${moduleName}/${springName}",
						type : "post",
						data : mini.encode(data),
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
						success : function(data) {
							if(data =="success"){
								CloseWindow(data);
							}else{
								mini.alert("保存失败！");
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							mini.alert(jqXHR.responseText);
						}
					});
		}

		function SetData(data) {
			form_edit_${springName}.setData(data);
			mini.get("_state").setValue("modified");
		}

		function CloseWindow(action) {
			if (action == "close" && form.isChanged()) {
				if (confirm("数据被修改了，是否先保存？")) {
					return false;
				}
			}
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
		function onCancel() {
			CloseWindow("cancel");
		}
	</script>
</body>
</html>