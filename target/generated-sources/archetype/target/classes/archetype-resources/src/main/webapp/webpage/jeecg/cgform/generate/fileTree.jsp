#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="${package}.web.cgform.common.CgAutoListConstant"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文件目录树</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript" src="plug-in/cgform/js/cgformField.js"></script>
<script type="text/javascript">
var selectedDir = '';
${symbol_dollar}(function(){
     ${symbol_dollar}('${symbol_pound}fileTree').tree({   
         checkbox: false,   
         url: 'generateController.do?doExpandFileTree',   
        onBeforeExpand:function(node,param){
        	var newurl = "generateController.do?doExpandFileTree&parentNode=" + node.id;
        	newurl =  encodeURI(newurl);
        	 ${symbol_dollar}('${symbol_pound}fileTree').tree('options').url = newurl;
         },               
        onClick:function(node){   
             selectedDir = node.id;     
        }   
     });   
 }); 
function fillPath(id){
	W.selectCallback(id,selectedDir);
}
</script>
<style type="text/css">
</style>
</head>
<div id="fileTreeDiv">
<ul id="fileTree">
</ul>
</div>