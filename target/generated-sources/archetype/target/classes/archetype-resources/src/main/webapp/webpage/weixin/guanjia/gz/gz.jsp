#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title></title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  	var textList = "${symbol_dollar}{textList}";
  	var newsList = "${symbol_dollar}{newsList}";
  	${symbol_dollar}(document).ready(function(){
  		
  		var newsHtml = ${symbol_dollar}("${symbol_pound}newsId").html();
  	    var textHtml = ${symbol_dollar}("${symbol_pound}textId").html();
  	    
  		${symbol_dollar}("${symbol_pound}msgType").change(function(){
  			var value = ${symbol_dollar}(this).val();
  			
  			if("text"==value){
  				${symbol_dollar}("${symbol_pound}textId").removeAttr("style");
  				${symbol_dollar}("${symbol_pound}textContent").removeAttr("disabled");
  				${symbol_dollar}("${symbol_pound}newsId").attr("style","display:none");
  				${symbol_dollar}("${symbol_pound}newsContent").attr("disabled","disabled");
  			}else{
  			  
  				${symbol_dollar}("${symbol_pound}textId").attr("style","display:none");
  				${symbol_dollar}("${symbol_pound}textContent").attr("disabled","disabled");
  				${symbol_dollar}("${symbol_pound}newsId").removeAttr("style");	
  				${symbol_dollar}("${symbol_pound}newsContent").removeAttr("disabled");	
  				
  			}
  			
  		});
  	  
  	  var lx = "${symbol_dollar}{lx}";
  	  if(lx=='text'){
  	  	${symbol_dollar}("${symbol_pound}textId").removeAttr("style");
		${symbol_dollar}("${symbol_pound}textContent").removeAttr("disabled");
		${symbol_dollar}("${symbol_pound}newsId").attr("style","display:none");
		${symbol_dollar}("${symbol_pound}newsContent").attr("disabled","disabled");
  	  }else if(lx=='news'){
  	  	${symbol_dollar}("${symbol_pound}textId").attr("style","display:none");
		${symbol_dollar}("${symbol_pound}textContent").attr("disabled","disabled");
		${symbol_dollar}("${symbol_pound}newsId").removeAttr("style");	
		${symbol_dollar}("${symbol_pound}newsContent").removeAttr("disabled");	
  	  }
  	  
  	});
  </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="gzfrom" dialog="true" usePlugin="password" layout="table" action="subscribeController.do?su">
   <input id="id" name="id" type="hidden" value="${symbol_dollar}{id}">
   <input id="accountid" name="accountid" type="hidden"  value="${symbol_dollar}{subscribe.accountid}">
   <input id="templateName" name="templateName"  type="hidden" value="${symbol_dollar}{subscribe.templateName}"/>
   <table style="width:600px;" cellpadding="0" cellspacing="1" class="formtable">
    
    <tr>
     <td align="right">
      <label class="Validform_label">
     消息类型:
      </label>
     </td>
     <td class="value">
          <select name="msgType" id="msgType">
           
          	<option value="text"  <c:if test="${symbol_dollar}{subscribe.msgType=='text'}">selected="selected"</c:if>>文本消息</option>
          	<option value="news"  <c:if test="${symbol_dollar}{subscribe.msgType=='news'}">selected="selected"</c:if>>图文消息</option>
          </select>
     </td>
    </tr>
    
      <tr>
     <td align="right">
      <label class="Validform_label">
    	 消息模板:
      </label>
     </td>
     <td class="value" id="textId">
          <select name="templateId" id="textContent">
          		<c:forEach items="${symbol_dollar}{textList}" var="textVal">
          			<option value="${symbol_dollar}{textVal.id}" name="textMsg" <c:if test="${symbol_dollar}{templateId==textVal.id}">selected="selected"</c:if>>${symbol_dollar}{textVal.templateName}</option>
          		</c:forEach>
          </select>
     </td>
     <td class="value" style="display:none" id="newsId">
          <select name="templateId" id="newsContent" disabled="disabled">
          		<c:forEach items="${symbol_dollar}{newsList}" var="newsVal">
          			<option value="${symbol_dollar}{newsVal.id}" <c:if test="${symbol_dollar}{templateId==newsVal.id}">selected="selected"</c:if>>${symbol_dollar}{newsVal.templateName}</option>
          		</c:forEach>
          </select>
     </td>
    </tr>
    
   </table>
  </t:formvalid>
 </body>