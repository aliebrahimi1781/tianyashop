#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>操作信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
  </script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="functionController.do?saveop">
	<input name="id" type="hidden" value="${symbol_dollar}{operation.id}">
	<fieldset class="step">
	<div class="form"><label class="Validform_label"> 操作名称: </label> <input name="operationname" class="inputxt" value="${symbol_dollar}{operation.operationname}" datatype="s2-20"> <span
		class="Validform_checktip"> 操作名称范围2~20位字符</span></div>
	<div class="form"><label class="Validform_label"> 操作代码: </label> <input name="operationcode" class="inputxt" value="${symbol_dollar}{operation.operationcode}"></div>
	<div class="form"><label class="Validform_label"> 图标名称: </label> <select name="TSIcon.id">
		<c:forEach items="${symbol_dollar}{iconlist}" var="icon">
			<option value="${symbol_dollar}{icon.id}" <c:if test="${symbol_dollar}{icon.id==function.TSIcon.id }">selected="selected"</c:if>>${symbol_dollar}{icon.iconName}</option>
		</c:forEach>
	</select></div>
	<input name="TSFunction.id" value="${symbol_dollar}{functionId}" type="hidden">
	<div class="form"><label class="Validform_label"> 状态: </label> <input name="status" class="inputxt" value="${symbol_dollar}{operation.status}" datatype="n" ignore="ignore"> <span
		class="Validform_checktip">必须为数字</span></div>
	</fieldset>
</t:formvalid>
</body>
</html>
