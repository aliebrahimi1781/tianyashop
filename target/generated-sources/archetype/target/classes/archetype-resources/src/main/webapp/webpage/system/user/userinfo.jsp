#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true">
	<fieldset class="step"><legend> 用户信息 </legend>
	<div class="form"><label class="form"> 用户名: </label> ${symbol_dollar}{user.userName }</div>
	<div class="form"><label class="form"> 姓名: </label> ${symbol_dollar}{user.realName}</div>
	<div class="form"><label class="form"> 手机号码: </label> ${symbol_dollar}{user.mobilePhone}</div>
	<div class="form"><label class="form"> 办公室电话: </label> ${symbol_dollar}{user.officePhone}</div>
	<div class="form"><label class="form"> 邮箱: </label> ${symbol_dollar}{user.email}</div>
	</fieldset>
	</form>
</t:formvalid>
</body>
</html>

