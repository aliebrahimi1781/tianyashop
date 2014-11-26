#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	${symbol_dollar}(document).ready(function() {
	 var browserversion = "";
	 //IE8+浏览器
	 if (${symbol_dollar}.browser.msie) {
		 browserversion = "IE" + ${symbol_dollar}.browser.version;
	 }
	 //谷歌浏览器
	 if (${symbol_dollar}.browser.webkit) {
		 browserversion = "Chrome" + ${symbol_dollar}.browser.version; 
	 }
	 //火狐浏览器
	 if (${symbol_dollar}.browser.mozilla) {
		 browserversion = "Mozilla Firefox" + ${symbol_dollar}.browser.version;
	 }
	 //欧朋浏览器
	 if (${symbol_dollar}.browser.opera) {
		 browserversion = "Opera" + ${symbol_dollar}.browser.version;
	 }
	 
	 window.location.href = "loginController.do?login";
	 
 });
</script>
</head>
<body>
</body>
</html>