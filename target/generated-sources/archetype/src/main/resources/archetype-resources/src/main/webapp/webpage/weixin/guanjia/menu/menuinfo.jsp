#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>


<!DOCTYPE html>
<html>
 <head>
  <title>菜单信息录入</title>
  <t:base type="jquery,easyui,tools"></t:base>
 <script type="text/javascript">
 	${symbol_dollar}(document).ready(function(){ 
 	   var msgType = "${symbol_dollar}{msgType}";
 	   var templateId = "${symbol_dollar}{templateId}";
 	   var supMenuId="${symbol_dollar}{fatherId}";
 	   if(templateId){
 	   		var templateObj = ${symbol_dollar}("${symbol_pound}templateId");
	 		templateObj.html("");
	 		${symbol_dollar}.ajax({
	 			url:"menuManagerController.do?gettemplate",
	 			data:{"msgType":msgType},
	 			dataType:"JSON",
	 			type:"POST",
	 			success:function(data){
	 				var msg="";
	 				for(var i=0;i<data.length;i++){
	 				   
	 				    if(templateId == data[i].id){
	 				    	if(msgType=="expand"){
	 				    		msg += "<option value='"+data[i].id+"' selected='selected'>"+data[i].name+"</option>";
	 				    	}else{
	 				    		msg += "<option value='"+data[i].id+"' selected='selected'>"+data[i].templateName+"</option>";
	 				    	}
	 				    }else{
		 				    if(msgType=="expand"){
		 				    	msg += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
		 				    }else{
		 				    	msg += "<option value='"+data[i].id+"'>"+data[i].templateName+"</option>";
		 				    }
	 				    }
	 				}
	 				templateObj.html(msg);
	 			}
	 		});
 	   }
 	
 	
 	   
 	   if(true){
 	   		var supMenuIdObj = ${symbol_dollar}("${symbol_pound}fatherName");
	 		supMenuIdObj.html("");
	 		${symbol_dollar}.ajax({
	 			url:"menuManagerController.do?getSubMenu",
	 			data:{"msgType":msgType},
	 			dataType:"JSON",
	 			type:"POST",
	 			success:function(data){
	 				var msg="<option>一级菜单</option>";
	 				for(var i=0;i<data.length;i++){
	 							//判断是否为修改之前选择的上级,更改为选中状态
								if(data[i].id==supMenuId){
									msg += "<option value='"+data[i].id+"' selected='selected'>"+data[i].name+"</option>";
								}else{
									msg += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
								}	 				   			
	 				    		
	 				   
	 				}
	 				supMenuIdObj.html(msg);
	 			}
	 		});
 	   }
 		${symbol_dollar}("${symbol_pound}type").change(function(){
 			var selectValue = ${symbol_dollar}(this).children('option:selected').val();
 			
 			if("click"==selectValue){
 				${symbol_dollar}("${symbol_pound}url").attr("disabled","disabled");
 				${symbol_dollar}("${symbol_pound}trurl").attr("style","display:none");
 				
 				${symbol_dollar}("${symbol_pound}xxtr").removeAttr("style");
 				var inputAttr = ${symbol_dollar}("input[name='msgType']");
 				for(var i=0;i<inputAttr.length;i++){
 					${symbol_dollar}(inputAttr[i]).removeAttr("disabled");
 				}
 				${symbol_dollar}("${symbol_pound}templateTr").removeAttr("style");
 				${symbol_dollar}("${symbol_pound}templateId").removeAttr("disabled");
 				//设置消息类型和验证
 				${symbol_dollar}("${symbol_pound}msgType").attr("datatype","*");
 				${symbol_dollar}("${symbol_pound}templateId").attr("datatype","*");
 			}else{
 				${symbol_dollar}("${symbol_pound}url").removeAttr("disabled");
 				${symbol_dollar}("${symbol_pound}trurl").removeAttr("style");
 				
 				${symbol_dollar}("${symbol_pound}xxtr").attr("style","display:none");
 				var inputAttr = ${symbol_dollar}("input[name='msgType']");
 				for(var i=0;i<inputAttr.length;i++){
 					${symbol_dollar}(inputAttr[i]).attr("disabled","disabled");
 				}
 				
 				${symbol_dollar}("${symbol_pound}templateTr").attr("style","display:none");
 				${symbol_dollar}("${symbol_pound}templateId").attr("disabled","disabled");
 				//取消验证。防止无法提交
 				${symbol_dollar}("${symbol_pound}msgType").removeAttr("datatype");
 				${symbol_dollar}("${symbol_pound}templateId").removeAttr("datatype");
 			}
 		});
 		
 		var inputAttr = ${symbol_dollar}("input[name='msgType']");
		for(var i=0;i<inputAttr.length;i++){
			${symbol_dollar}(inputAttr[i]).click(function(){
			   var textVal = ${symbol_dollar}(this).val();
			   if("text"==textVal){
			   		getTemplates("text");
			   }else if("expand"==textVal){
			   		getTemplates("expand");
			   }else if("news"==textVal){
			   		getTemplates("news");
			   }
			});
		}
	
		//获取动作设置选中的项,用于初始化依赖dom元素
		var typeVal = ${symbol_dollar}("${symbol_pound}type").val(); // 动作设置选中项的值
		if("click" == typeVal){
			${symbol_dollar}("${symbol_pound}xxtr").show();
			${symbol_dollar}("${symbol_pound}trurl").hide();
		}else if("view" == typeVal){
			${symbol_dollar}("${symbol_pound}xxtr").hide();
			${symbol_dollar}("${symbol_pound}trurl").show();
		}
			
			
 	});
 	
 	function getTemplates(msgType){
 		var templateObj = ${symbol_dollar}("${symbol_pound}templateId");
 		templateObj.html("");
 		${symbol_dollar}.ajax({
 			url:"menuManagerController.do?gettemplate",
 			data:{"msgType":msgType},
 			dataType:"JSON",
 			type:"POST",
 			success:function(data){
 				var msg="";
 				msg += "<option value=''>------</option>";
 				for(var i=0;i<data.length;i++){
 					if(msgType=="expand"){
 					 	msg += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
 					}else{
 					 	msg += "<option value='"+data[i].id+"'>"+data[i].templateName+"</option>";
 					}
 				}
 				templateObj.html(msg);
 			}
 		});
 	}
 </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="menuManagerController.do?su">

  <c:if test="${symbol_dollar}{menuEntity.id!=null}">
  	<input id="id" name="id" type="hidden" value="${symbol_dollar}{menuEntity.id}">
  </c:if>
  
   <c:if test="${symbol_dollar}{fatherId!=null}">
  	<input id="fatherId" name="fatherId" type="hidden" value="${symbol_dollar}{fatherId}">
  </c:if>
  <c:if test="${symbol_dollar}{accountid!=null}">
  	<input id="accountid" name="accountid" type="hidden" value="${symbol_dollar}{accountid}">
  </c:if>
   <table style="width:100%" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
     <td align="right" style="width:65px">
      <label class="Validform_label">
     	  菜单名称:
      </label>
     </td>
     <td colspan="3" class="value">
      <input id="name" class="inputxt" name="name"  value="${symbol_dollar}{name}" datatype="*" nullmsg="菜单名称不能为空！">
      <span class="Validform_checktip">请输入 菜单名称！</span>
     </td>
    </tr>
    
     <tr>
     <td align="right" style="width:65px">
      <label class="Validform_label">
     	  上级菜单:
      </label>
     </td>
     <td colspan="3" class="value">
      <select name="fatherName" id="fatherName">
      	
      </select>
     <!--   <input id="c" class="inputxt"  name="fatherName"  value="${symbol_dollar}{fatherName}">-->
      <span class="Validform_checktip">请输入上级菜单！</span>
     </td>
    </tr>
    
    <tr>
     <td align="right" style="width:65px">
      <label class="Validform_label">
      动作设置:
      </label>
     </td>
     <td colspan="3" class="value">
      <select name="type" id="type">
     
      	<option value="click"  <c:if test="${symbol_dollar}{type=='click'}">selected="selected"</c:if>>消息触发类</option>
      	<option value="view"  <c:if test="${symbol_dollar}{type=='view'}">selected="selected"</c:if>>网页链接类</option>
      </select>
      <span class="Validform_checktip">请设置动作</span>
     </td>
    </tr>
   
    <tr id="trurl">
     <td align="right" style="width:65px">
      <label class="Validform_label">
       URL:
      </label>
     </td>
     <td colspan="3" class="value">
      <input id="url" class="inputxt" name="url" value="${symbol_dollar}{url}"  style="width: 300px"><!-- disabled="disabled"  地址不能编辑？？ 业务不了解。所以先注释掉 --> 
      <span class="Validform_checktip">填写url</span>
     </td>
    </tr>
    
    <tr id="xxtr" style="width:65px">
     <td align="right">
      <label class="Validform_label">
       消息类型:
      </label>
     </td>
     <td class="value" colspan="3">
        <input type="radio" value="text" name="msgType" id="msgType" datatype="*"  <c:if test="${symbol_dollar}{msgType=='text'}">checked="checked"</c:if>/>文本
        <input type="radio" value="news" name="msgType"  <c:if test="${symbol_dollar}{msgType=='news'}">checked="checked"</c:if>/>图文
        <input type="radio" value="expand" name="msgType"  <c:if test="${symbol_dollar}{msgType=='expand'}">checked="checked"</c:if>/>扩展
      <span class="Validform_checktip">选择消息类型</span>
     </td>
    </tr>
    
    <tr id="templateTr" style="width:65px">
     <td align="right">
      <label class="Validform_label">
       选择模板:
      </label>
     </td>
     <td class="value" colspan="3">
      <select name="templateId" id="templateId" datatype="*">
      	
      </select>
      <span class="Validform_checktip">选择模板</span>
     </td>
    </tr>
    
    <tr>
     <td align="right" style="width:65px">
      <label class="Validform_label">
        菜单标识:
      </label>
     </td>
     <td class="value" colspan="3">
      <input id="url" class="inputxt" name="menuKey" value="${symbol_dollar}{menuKey}" datatype="*" nullmsg="菜单标示不能为空！">
      <span class="Validform_checktip">填写菜单标识</span>
     </td>
    </tr>
     
    <tr>
     <td align="right" style="width:65px">
      <label class="Validform_label">
        顺序:
      </label>
     </td>
     <td class="value" colspan="3">
      <input id="orders" class="inputxt" name="orders" value="${symbol_dollar}{orders}" >
      <span class="Validform_checktip">填写显示顺序</span>
     </td>
    </tr> 
 
   </table>
  </t:formvalid>
 
 </body>