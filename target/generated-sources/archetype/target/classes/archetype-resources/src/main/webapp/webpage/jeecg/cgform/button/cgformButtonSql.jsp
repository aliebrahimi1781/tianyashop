#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>按钮sql增强</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="cgformButtonSqlController.do?save">
	<input id="id" name="id" type="hidden" value="${symbol_dollar}{cgformButtonSqlPage.id }">
	<input id="formId" name="formId" type="hidden" value="${symbol_dollar}{cgformButtonSqlPage.formId }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 操作码: </label></td>
			<td class="value"><select id="buttonCode" name="buttonCode" datatype="*">
				<option value="add" <c:if test="${symbol_dollar}{cgformButtonSqlPage.buttonCode=='add'}">selected="selected"</c:if>>add</option>
				<option value="update" <c:if test="${symbol_dollar}{cgformButtonSqlPage.buttonCode=='update'}">selected="selected"</c:if>>update</option>
				<option value="delete" <c:if test="${symbol_dollar}{cgformButtonSqlPage.buttonCode=='delete'}">selected="selected"</c:if>>delete</option>
				<c:forEach items="${symbol_dollar}{buttonList}" var="button">
					<option value="${symbol_dollar}{button.buttonCode }" <c:if test="${symbol_dollar}{button.buttonCode==cgformButtonSqlPage.buttonCode}">selected="selected"</c:if>>${symbol_dollar}{button.buttonCode}</option>
				</c:forEach>
			</select> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 描述: </label></td>
			<td class="value"><textarea id="content" name="content" cols="100" rows="3">${symbol_dollar}{cgformButtonSqlPage.content}</textarea> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 增强sql: </label></td>
			<td class="value"><textarea id="cgbSqlStr" name="cgbSqlStr" rows="25" cols="150">${symbol_dollar}{cgformButtonSqlPage.cgbSqlStr }</textarea> <span class="Validform_checktip"></span></td>
		</tr>
	</table>
</t:formvalid>
</body>
<script type="text/javascript">
 ${symbol_dollar}('${symbol_pound}buttonCode').change(function() {
	 var buttonCode =${symbol_dollar}('${symbol_pound}buttonCode').val();
	 var formId =${symbol_dollar}('${symbol_pound}formId').val();
	 ${symbol_dollar}.ajax({
			async : false,
			cache : false,
			type : 'POST',
			contentType : 'application/json', 
			dataType:"json",
			url : "cgformButtonSqlController.do?doCgformButtonSql&buttonCode="+buttonCode+"&formId="+formId,// 请求的action路径
			error : function() {// 请求失败处理函数
				alert("出错了");
				frameElement.api.close();
			},
			success : function(data) {
				var d = data;
				if (d.success) {
					var cgformButtonSqlPage = d.obj;
					${symbol_dollar}('${symbol_pound}id').val(cgformButtonSqlPage.id);
					${symbol_dollar}('${symbol_pound}content').val(cgformButtonSqlPage.content);
					${symbol_dollar}('${symbol_pound}cgbSqlStr').val(cgformButtonSqlPage.cgbSqlStr);
				}else{
					${symbol_dollar}('${symbol_pound}id').val("");
					${symbol_dollar}('${symbol_pound}content').val("");
					${symbol_dollar}('${symbol_pound}cgbSqlStr').val("");
				}
			}
		});
	 
 });
</script>
</html>