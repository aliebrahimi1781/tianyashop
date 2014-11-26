#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导出</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="jpPersonController.do?save">
	<input id="id" name="id" type="hidden" value="${symbol_dollar}{jpPersonPage.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 年龄: </label></td>
			<td class="value"><input class="inputxt" id="age" name="age" ignore="ignore" value="${symbol_dollar}{jpPersonPage.age}" datatype="n"> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 生日: </label></td>
			<td class="value"><input class="Wdate" onClick="WdatePicker()" style="width: 150px" id="birthday" name="birthday"
				value="<fmt:formatDate value='${symbol_dollar}{jpPersonPage.birthday}' type="date" pattern="yyyy-MM-dd"/>" datatype="*"> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 出生日期: </label></td>
			<td class="value"><input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="createdt" name="createdt" ignore="ignore"
				value="<fmt:formatDate value='${symbol_dollar}{jpPersonPage.createdt}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 用户名: </label></td>
			<td class="value"><input class="inputxt" id="name" name="name" value="${symbol_dollar}{jpPersonPage.name}" datatype="*"> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 工资: </label></td>
			<td class="value"><input class="inputxt" id="salary" name="salary" ignore="ignore" value="${symbol_dollar}{jpPersonPage.salary}" datatype="d"> <span class="Validform_checktip"></span></td>
		</tr>
	</table>
</t:formvalid>
</body>