#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<SCRIPT type=text/javascript src="plug-in/clipboard/ZeroClipboard.js"></SCRIPT>
<t:base type="jquery,easyui,jqueryui-sortable,tools"></t:base>
<div>
<div><input type='radio' name='synMethod' value='normal' checked>普通同步(保留表数据)</div>
<div><input type='radio' name='synMethod' value='force'>强制同步(删除表,重新生成)</div>
</div>
<SCRIPT type="text/javascript">
 function getSynChoice(){
	var synchoice;
	${symbol_dollar}("[name='synMethod']").each(function(){
		if(${symbol_dollar}(this).attr("checked")){
			synchoice = ${symbol_dollar}(this).val();
		}
	});
	return synchoice;
 }
 </SCRIPT>