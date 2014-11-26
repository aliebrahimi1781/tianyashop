#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>DEMO示例</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/jquery.jqprint.js"></script>
<script language="javascript">
function printall(){
	${symbol_dollar}(".printdiv").jqprint(); 
}
function printview(){
	document.all.WebBrowser1.ExecWB(7,1);
}
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<div class="printdiv"><t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="jeecgDemoController.do?save">
	<input id="id" name="id" type="hidden" value="${symbol_dollar}{jgDemo.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="15%" nowrap><label class="Validform_label"> 用户名: </label></td>
			<td class="value" width="85%"><c:if test="${symbol_dollar}{jgDemo.id!=null }">
					     ${symbol_dollar}{jgDemo.userName }
					     </c:if> <c:if test="${symbol_dollar}{jgDemo.id==null }">
				<input id="userName" class="inputxt" name="userName" value="${symbol_dollar}{jgDemo.userName }" datatype="*">
				<span class="Validform_checktip">用户名范围在2~10位字符</span>
			</c:if></td>
		</tr>
		<tr>
			<td align="right" nowrap><label class="Validform_label"> 手机号码: </label></td>
			<td class="value">${symbol_dollar}{jgDemo.mobilePhone}</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 办公电话: </label></td>
			<td class="value">${symbol_dollar}{jgDemo.officePhone}</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 常用邮箱: </label></td>
			<td class="value">${symbol_dollar}{jgDemo.email}</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 年龄: </label></td>
			<td class="value">${symbol_dollar}{jgDemo.age}</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 工资: </label></td>
			<td class="value">${symbol_dollar}{jgDemo.salary}</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 生日: </label></td>
			<td class="value">${symbol_dollar}{jgDemo.birthday }</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 创建日期: </label></td>
			<td class="value">${symbol_dollar}{jgDemo.createDate }</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 性别: </label></td>
			<td class="value">${symbol_dollar}{sex}</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 部门: </label></td>
			<td class="value"><c:forEach items="${symbol_dollar}{departList}" var="depart">
				<c:if test="${symbol_dollar}{depart.id==jgDemo.depId}">
			         ${symbol_dollar}{depart.departname}</c:if>
			</c:forEach></td>
		</tr>
	</table>
</t:formvalid></div>
<a class="easyui-linkbutton" href="javascript:printall()">打印</a>
</body>