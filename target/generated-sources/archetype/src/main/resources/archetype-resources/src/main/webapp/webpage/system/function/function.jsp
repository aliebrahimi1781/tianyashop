#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
		
	${symbol_dollar}(function() {
		${symbol_dollar}('${symbol_pound}cc').combotree({
			url : 'functionController.do?setPFunction&selfId=${symbol_dollar}{function.id}',
			panelHeight:'auto',
			onClick: function(node){
				${symbol_dollar}("${symbol_pound}functionId").val(node.id);
			}
		});
		
		if(${symbol_dollar}('${symbol_pound}functionLevel').val()=='1'){
			${symbol_dollar}('${symbol_pound}pfun').show();
		}else{
			${symbol_dollar}('${symbol_pound}pfun').hide();
		}
		
		
		${symbol_dollar}('${symbol_pound}functionLevel').change(function(){
			if(${symbol_dollar}(this).val()=='1'){
				${symbol_dollar}('${symbol_pound}pfun').show();
				var t = ${symbol_dollar}('${symbol_pound}cc').combotree('tree');
				var nodes = t.tree('getRoots');
				if(nodes.length>0){
					${symbol_dollar}('${symbol_pound}cc').combotree('setValue', nodes[0].id);
					${symbol_dollar}("${symbol_pound}functionId").val(nodes[0].id);
				}
			}else{
				var t = ${symbol_dollar}('${symbol_pound}cc').combotree('tree');
				var node = t.tree('getSelected');
				if(node){
					${symbol_dollar}('${symbol_pound}cc').combotree('setValue', null);
				}
                ${symbol_dollar}("${symbol_pound}functionId").val(null);
				${symbol_dollar}('${symbol_pound}pfun').hide();
			}
		});
	});
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" refresh="true" action="functionController.do?saveFunction">
	<input name="id" type="hidden" value="${symbol_dollar}{function.id}">
	<fieldset class="step">
	<div class="form"><label class="Validform_label"> 菜单名称: </label> <input name="functionName" class="inputxt" value="${symbol_dollar}{function.functionName}" datatype="s1-15"> <span
		class="Validform_checktip">菜单名称范围1~15位字符,且不为空</span></div>
	<div class="form"><label class="Validform_label"> 菜单等级: </label> <select name="functionLevel" id="functionLevel" datatype="*">
		<option value="0" <c:if test="${symbol_dollar}{function.functionLevel eq 0}">selected="selected"</c:if>>一级菜单</option>
		<option value="1" <c:if test="${symbol_dollar}{function.functionLevel>0}"> selected="selected"</c:if>>下级菜单</option>
	</select> <span class="Validform_checktip"></span></div>
	<div class="form" id="pfun"><label class="Validform_label"> 父菜单: </label> <input id="cc" <c:if test="${symbol_dollar}{function.TSFunction.functionLevel eq 0}">
					value="${symbol_dollar}{function.TSFunction.id}"</c:if>
		<c:if test="${symbol_dollar}{function.TSFunction.functionLevel > 0}">
					value="${symbol_dollar}{function.TSFunction.functionName}"</c:if>> <input id="functionId" name="TSFunction.id" style="display: none;"
		value="${symbol_dollar}{function.TSFunction.id}"></div>
	<div class="form" id="funurl"><label class="Validform_label"> 菜单地址: </label> <input name="functionUrl" class="inputxt" value="${symbol_dollar}{function.functionUrl}" style="width:300px"></div>
	<div class="form"><label class="Validform_label"> 图标名称: </label> <select name="TSIcon.id">
		<c:forEach items="${symbol_dollar}{iconlist}" var="icon">
			<option value="${symbol_dollar}{icon.id}" <c:if test="${symbol_dollar}{icon.id==function.TSIcon.id || (function.id eq null && icon.iconClas eq 'pictures') }">selected="selected"</c:if>>${symbol_dollar}{icon.iconName}</option>
		</c:forEach>
	</select></div>
    <div class="form">
        <label class="Validform_label"> 桌面图标名称: </label>
        <select name="TSIconDesk.id">
            <c:forEach items="${symbol_dollar}{iconDeskList}" var="icon">
                <option value="${symbol_dollar}{icon.id}" <c:if test="${symbol_dollar}{icon.id==function.TSIconDesk.id || (function.id eq null && icon.iconClas eq 'System Folder') }">selected="selected"</c:if>>
                    ${symbol_dollar}{icon.iconName}
                </option>
            </c:forEach>
        </select>
    </div>
    <%--update-end--Author:zhangguoming  Date:20140509 for：云桌面图标管理--%>
	<div class="form" id="funorder"><label class="Validform_label"> 菜单顺序: </label> <input name="functionOrder" class="inputxt" value="${symbol_dollar}{function.functionOrder}" datatype="n1-3"></div>
	</fieldset>
</t:formvalid>
</body>
</html>
