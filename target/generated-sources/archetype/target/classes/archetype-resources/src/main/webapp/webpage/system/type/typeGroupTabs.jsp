#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:tabs id="typeGroupTabs" iframe="false" tabPosition="bottom">
	<c:forEach items="${symbol_dollar}{typegroupList}" var="typegroup">
		<t:tab iframe="systemController.do?typeList&typegroupid=${symbol_dollar}{typegroup.id}" icon="icon-add" title="${symbol_dollar}{typegroup.typegroupname}" id="${symbol_dollar}{typegroup.typegroupcode}"></t:tab>
	</c:forEach>
</t:tabs>
