#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<html>
<head>
<t:base type="jquery,tools"></t:base>

</head>
<body>
<SCRIPT type="text/javascript">
   var _sun_selectedTab=  ${symbol_dollar}('${symbol_pound}maintabs').tabs('getSelected') ; 
   var _sun_selectedTab_title=_sun_selectedTab.panel('options').title;
   ${symbol_dollar}.dialog.alert("提醒：用户权限不足，请联系管理员!");
   ${symbol_dollar}('${symbol_pound}maintabs').tabs('close', _sun_selectedTab_title); 
 </SCRIPT>
</body>
</html>

