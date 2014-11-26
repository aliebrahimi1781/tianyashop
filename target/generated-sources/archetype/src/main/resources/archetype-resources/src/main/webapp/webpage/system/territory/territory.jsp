#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>地域信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
		
	${symbol_dollar}(function() {
		${symbol_dollar}('${symbol_pound}cc').combotree({
			url : 'territoryController.do?setPTerritory',
			panelHeight:'auto',
			onClick: function(node){
				${symbol_dollar}("${symbol_pound}territoryId").val(node.id);
			}
		});
		
		if(${symbol_dollar}('${symbol_pound}territoryLevel').val()=='1'){
			${symbol_dollar}('${symbol_pound}pfun').show();
		}else{
			${symbol_dollar}('${symbol_pound}pfun').hide();
		}
		
		
		${symbol_dollar}('${symbol_pound}territoryLevel').change(function(){
			if(${symbol_dollar}(this).val()=='1'){
				${symbol_dollar}('${symbol_pound}pfun').show();
				var t = ${symbol_dollar}('${symbol_pound}cc').combotree('tree');
				var nodes = t.tree('getRoots');
				if(nodes.length>0){
					${symbol_dollar}('${symbol_pound}cc').combotree('setValue', nodes[0].id);
					${symbol_dollar}("${symbol_pound}territoryId").val(nodes[0].id);
				}
			}else{
				var t = ${symbol_dollar}('${symbol_pound}cc').combotree('tree');
				var node = t.tree('getSelected');
				if(node){
					${symbol_dollar}('${symbol_pound}cc').combotree('setValue', null);
				}
				${symbol_dollar}('${symbol_pound}pfun').hide();
			}
		});
	});
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" refresh="true" action="territoryController.do?saveTerritory">
	<input name="id" type="hidden" value="${symbol_dollar}{territory.id}">
	<fieldset class="step">
	<div class="form"><label class="Validform_label"> 地域名称: </label> <input name="territoryName" class="inputxt" value="${symbol_dollar}{territory.territoryName}" datatype="s2-15"> <span
		class="Validform_checktip">地域名称范围2~15位字符,且不为空</span></div>
	<div class="form"><label class="Validform_label"> 地域等级: </label> <select name="territoryLevel" id="territoryLevel" datatype="*">
		<option value="0" <c:if test="${symbol_dollar}{territory.territoryLevel eq 0}">selected="selected"</c:if>>一级地域</option>
		<option value="1" <c:if test="${symbol_dollar}{territory.territoryLevel>0}"> selected="selected"</c:if>>下级地域</option>
	</select> <span class="Validform_checktip"></span></div>
	<div class="form" id="pfun"><label class="Validform_label"> 父地域: </label> <input id="cc"
		<c:if test="${symbol_dollar}{territory.TSTerritory.territoryLevel eq 0}">
					value="${symbol_dollar}{territory.TSTerritory.id}"</c:if>
		<c:if test="${symbol_dollar}{territory.TSTerritory.territoryLevel > 0}">
					value="${symbol_dollar}{territory.TSTerritory.territoryName}"</c:if>> <input id="territoryId" name="TSTerritory.id" style="display: none;"
		value="${symbol_dollar}{territory.TSTerritory.id}"></div>
	<div class="form" id="funorder"><label class="Validform_label"> 区域码: </label> <input name="territoryCode" class="inputxt" value="${symbol_dollar}{territory.territoryCode}" datatype="*6-16"></div>
	<div class="form" id="funorder"><label class="Validform_label"> 显示顺序: </label> <input name="territorySort" class="inputxt" value="${symbol_dollar}{territory.territorySort}" datatype="n1-3"></div>
	</fieldset>
</t:formvalid>
</body>
</html>
