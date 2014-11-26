#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	 function goForm() {
		 ${symbol_dollar}('${symbol_pound}myCkeditorForm').form('submit', {
				url : "cgformFtlController.do?saveFormEkeditor",
				dataType:"json",
				success : function(data) {
					var mydata = eval("("+data+")");
					//关闭窗口
					frameElement.api.close();
				}
		});
     }
	</script>
</head>
<form action="cgformFtlControllerr.do?saveCkeditor" method="post" id="myCkeditorForm"><input type="hidden" value="${symbol_dollar}{cgformFtlEntity.id}" name="id" /> <textarea cols="80" id="editor1"
	name="contents" rows="100">
			${symbol_dollar}{contents}
		</textarea> <script>
		CKEDITOR.replace('editor1',{
			height:350,
			customConfig:'${symbol_dollar}{pageContext.request.contextPath}/plug-in/ckeditor/ftlConfig.js'
		});
		</script></form>
</html>