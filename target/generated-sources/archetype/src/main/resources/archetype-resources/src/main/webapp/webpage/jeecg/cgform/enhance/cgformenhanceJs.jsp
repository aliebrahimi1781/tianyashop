#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>JS增强</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="cgformEnhanceJsController.do?save">
	<input id="id" name="id" type="hidden" value="${symbol_dollar}{cgformenhanceJsPage.id }">
	<input id="formId" name="formId" type="hidden" value="${symbol_dollar}{cgformenhanceJsPage.formId }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 增强类型: </label></td>
			<td class="value"><select name="cgJsType" id="cgJsType">
				<option value="form" <c:if test="${symbol_dollar}{cgformenhanceJsPage.cgJsType=='form'}">selected="selected"</c:if>>form</option>
				<option value="list" <c:if test="${symbol_dollar}{cgformenhanceJsPage.cgJsType=='list'}">selected="selected"</c:if>>list</option>
			</select> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 增强js: </label></td>
			<td class="value"><textarea id="cgJsStr" name="cgJsStr" cols="150" rows="30">${symbol_dollar}{cgformenhanceJsPage.cgJsStr}</textarea> <span class="Validform_checktip"></span></td>
		</tr>
	</table>
</t:formvalid>
</body>
<script type="text/javascript">
 ${symbol_dollar}('${symbol_pound}cgJsType').change(function() {
	 var cgJsType =${symbol_dollar}('${symbol_pound}cgJsType').val();
	 var formId =${symbol_dollar}('${symbol_pound}formId').val();
	 ${symbol_dollar}.ajax({
			async : false,
			cache : false,
			type : 'POST',
			contentType : 'application/json', 
			dataType:"json",
			url : "cgformEnhanceJsController.do?doCgformEnhanceJs&cgJsType="+cgJsType+"&formId="+formId,// 请求的action路径
			error : function() {// 请求失败处理函数
				alert("出错了");
				frameElement.api.close();
			},
			success : function(data) {
				var d = data;
				if (d.success) {
					var cgformenhanceJsPage = d.obj;
					${symbol_dollar}('${symbol_pound}id').val(cgformenhanceJsPage.id);
					${symbol_dollar}('${symbol_pound}cgJsStr').val(cgformenhanceJsPage.cgJsStr);
				}else{
					${symbol_dollar}('${symbol_pound}id').val("");
					${symbol_dollar}('${symbol_pound}cgJsStr').val("");
				}
			}
		});
	 
 });
</script>
</html>