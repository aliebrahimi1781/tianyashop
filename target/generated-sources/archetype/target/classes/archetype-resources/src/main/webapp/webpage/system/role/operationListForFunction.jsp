#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:forEach items="${symbol_dollar}{operationList}" var="operation">
	<c:if test="${symbol_dollar}{fn:contains(operationcodes, operation.operationcode)}">
		<span class="icon ${symbol_dollar}{operation.TSIcon.iconClas}">&nbsp;</span>
		<input style="width: 20px;" type="checkbox" name="operationCheckbox" value="${symbol_dollar}{operation.operationcode}" checked="checked" />${symbol_dollar}{operation.operationname}
	 </c:if>
	<c:if test="${symbol_dollar}{!fn:contains(operationcodes, operation.operationcode)}">
		<span class="icon group_add">&nbsp;</span>
		<input style="width: 20px;" type="checkbox" name="operationCheckbox" value="${symbol_dollar}{operation.operationcode}" />${symbol_dollar}{operation.operationname}
	 </c:if>
	<br>
</c:forEach>
<script type="text/javascript">
function submitOperation() {
	var functionId = "${symbol_dollar}{functionId}";
	var roleId = ${symbol_dollar}("${symbol_pound}rid").val();
	var operationcodes = "";
	${symbol_dollar}("input[name='operationCheckbox']").each(function(i){
		   if(this.checked){
			   operationcodes+=this.value+",";
		   }
	 });
	operationcodes=escape(operationcodes); 
	doSubmit("roleController.do?updateOperation&functionId=" + functionId + "&roleId=" + roleId+"&operationcodes="+operationcodes);
}
</script>
