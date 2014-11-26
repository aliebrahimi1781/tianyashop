#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
	<head>
<t:base type="jquery-webos,easyui,tools,DatePicker,autocomplete"></t:base>
		<link href="plug-in/sliding/css/main.css" rel="stylesheet" type="text/css" />

        <script>
            ${symbol_dollar}(function(){
            	${symbol_dollar}(".menuSearch_Info").live("click",function(){
            		//${symbol_dollar}(this).blur();
            			var url=${symbol_dollar}(this).attr("url");
            			var icon=${symbol_dollar}(this).attr("icon");
            			var id=${symbol_dollar}(this).attr("id");
            			var title=${symbol_dollar}(this).attr("title");
            			//window.parent.close();
            			createwindow(title,url,1000,500);
            		})
            });
        </script>
	</head>
	<body>
	${symbol_dollar}{menuListMap }
	</body>
</html>