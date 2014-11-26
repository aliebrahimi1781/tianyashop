#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单信息(无标签)</title>
<link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/default/easyui.css" type="text/css" />
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
<script type="text/javascript" src="plug-in/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug-in/tools/curdtools.js"></script>
<script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype.js"></script>
<script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
</head>
<script type="text/javascript">
	function resetTrNum(tableId) {
		${symbol_dollar}tbody = ${symbol_dollar}("${symbol_pound}" + tableId + "");
		${symbol_dollar}tbody.find('>tr').each(function(i){
			${symbol_dollar}(':input, select', this).each(function() {
				var ${symbol_dollar}this = ${symbol_dollar}(this), name = ${symbol_dollar}this.attr('name'), val = ${symbol_dollar}this.val();
				if (name != null) {
					if (name.indexOf("${symbol_pound}index${symbol_pound}") >= 0) {
						${symbol_dollar}this.attr("name",name.replace('${symbol_pound}index${symbol_pound}',i));
					} else {
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s + 1,e);
						${symbol_dollar}this.attr("name",name.replace(new_name,i));
					}
				}
			});
		});
	}
	
	${symbol_dollar}(function() {
		${symbol_dollar}("${symbol_pound}formobj").Validform({
			tiptype : 1,
			btnSubmit : "${symbol_pound}btn_sub",
			btnReset : "${symbol_pound}btn_reset",
			ajaxPost : true,
			usePlugin : {
				passwordstrength : {
					minLen : 6,
					maxLen : 18,
					trigger : function(obj, error) {
						if (error) {
							obj.parent().next().find(".Validform_checktip").show();
							obj.find(".passwordStrength").hide();
						} else {
							${symbol_dollar}(".passwordStrength").show();
							obj.parent().next().find(".Validform_checktip").hide();
						}
					}
				}
			},
			callback : function(data) {
				var win = frameElement.api.opener;
				if (data.success == true) {
					frameElement.api.close();
					win.tip(data.msg);
					
				} else {
					if (data.responseText == ''|| data.responseText == undefined){
						${symbol_dollar}("${symbol_pound}formobj").html(data.msg);
					}else{
						${symbol_dollar}("${symbol_pound}formobj").html(data.responseText);
					}
					return false;
				}
				win.reloadTable();
			}
		});
		
		${symbol_dollar}('${symbol_pound}addBtn').linkbutton({   
		    iconCls: 'icon-add'  
		});  
		${symbol_dollar}('${symbol_pound}delBtn').linkbutton({   
		    iconCls: 'icon-remove'  
		}); 
		${symbol_dollar}('${symbol_pound}addBtn').bind('click', function(){   
	 		 var tr =  ${symbol_dollar}("${symbol_pound}add_jeecgOrderProduct_table_template tr").clone();
		 	 ${symbol_dollar}("${symbol_pound}add_jeecgOrderProduct_table").append(tr);
		 	 resetTrNum('add_jeecgOrderProduct_table');
	    });  
		${symbol_dollar}('${symbol_pound}delBtn').bind('click', function(){   
	       ${symbol_dollar}("${symbol_pound}add_jeecgOrderProduct_table").find("input:checked").parent().parent().remove();   
	        resetTrNum('add_jeecgOrderProduct_table');
	    });
		
		${symbol_dollar}('${symbol_pound}addCustomBtn').linkbutton({   
		    iconCls: 'icon-add'  
		});  
		${symbol_dollar}('${symbol_pound}delCustomBtn').linkbutton({   
		    iconCls: 'icon-remove'  
		}); 
		${symbol_dollar}('${symbol_pound}addCustomBtn').bind('click', function(){   
	 		 var tr =  ${symbol_dollar}("${symbol_pound}add_jeecgOrderCustom_table_template tr").clone();
		 	 ${symbol_dollar}("${symbol_pound}add_jeecgOrderCustom_table").append(tr);
		 	 resetTrNum('add_jeecgOrderCustom_table');
	    });  
		${symbol_dollar}('${symbol_pound}delCustomBtn').bind('click', function(){   
	      	${symbol_dollar}("${symbol_pound}add_jeecgOrderCustom_table").find("input:checked").parent().parent().remove();   
	        resetTrNum('add_jeecgOrderCustom_table'); 
	    }); 
	});
</script>
<body>
<form id="formobj" action="jeecgOrderMainNoTagController.do?save" name="formobj" method="post"><input type="hidden" id="btn_sub" class="btn_sub" /> <input id="id" name="id" type="hidden"
	value="${symbol_dollar}{jeecgOrderMainPage.id }">
<table cellpadding="0" cellspacing="1" class="formtable">
	<tr>
		<td align="right"><label class="Validform_label"> 订单号:</label></td>
		<td class="value"><input class="inputxt" id="goOrderCode" name="goOrderCode" datatype="*" value="${symbol_dollar}{jeecgOrderMainPage.goOrderCode}" /></td>
		<td align="right"><label class="Validform_label"> 订单类型:</label></td>
		<td class="value"><t:dictSelect field="goderType" typeGroupCode="order" hasLabel="false" defaultVal="${symbol_dollar}{jeecgOrderMainPage.goderType}"></t:dictSelect></td>
	</tr>
	<tr>
		<td align="right"><label class="Validform_label">顾客类型 :</label></td>
		<td class="value"><t:dictSelect field="usertype" typeGroupCode="custom" hasLabel="false" defaultVal="${symbol_dollar}{jeecgOrderMainPage.usertype}"></t:dictSelect></td>
		<td align="right"><label class="Validform_label">联系人:</label></td>
		<td class="value"><input nullmsg="联系人不能为空" errormsg="联系人格式不对" class="inputxt" id="goContactName" name="goContactName" value="${symbol_dollar}{jeecgOrderMainPage.goContactName}" datatype="*"></td>
	</tr>
	<tr>
		<td align="right"><label class="Validform_label">手机:</label></td>
		<td class="value"><input class="inputxt" id="goTelphone" name="goTelphone" value="${symbol_dollar}{jeecgOrderMainPage.goTelphone}" datatype="m" errormsg="手机号码不正确!" ignore="ignore"></td>
		<td align="right"><label class="Validform_label">订单人数:</label></td>
		<td class="value"><input nullmsg="订单人数不能为空" errormsg="订单人数必须为数字" class="inputxt" id="goOrderCount" name="goOrderCount" value="${symbol_dollar}{jeecgOrderMainPage.goOrderCount}" datatype="n"></td>
	</tr>
	<tr>
		<td align="right"><label class="Validform_label">总价(不含返款):</label></td>
		<td class="value"><input class="inputxt" id="goAllPrice" name="goAllPrice" value="${symbol_dollar}{jeecgOrderMainPage.goAllPrice}" datatype="d"></td>
		<td align="right"><label class="Validform_label">返款:</label></td>
		<td class="value"><input nullmsg="返款不能为空" errormsg="返款格式不对" class="inputxt" id="goReturnPrice" name="goReturnPrice" value="${symbol_dollar}{jeecgOrderMainPage.goReturnPrice}" datatype="d"></td>
	</tr>
	<tr>
		<td align="right"><label class="Validform_label">备注:</label></td>
		<td class="value" colspan="3"><input class="inputxt" id="goContent" name="goContent" value="${symbol_dollar}{jeecgOrderMainPage.goContent}"></td>
	</tr>
</table>
<div style="width: auto; height: 200px;">
<div style="width: 690px; height: 1px;"></div>
<div id="tt" class="easyui-tabs" data-options="onSelect:function(t){${symbol_dollar}('${symbol_pound}tt .panel-body').css('width','auto');}">
<div title="产品明细">
<div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar"><a id="addBtn" href="${symbol_pound}">添加</a> <a id="delBtn" href="${symbol_pound}">删除</a></div>
<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderCustom_table">
	<tr bgcolor="${symbol_pound}E6E6E6">
		<td align="center" bgcolor="${symbol_pound}EEEEEE">序号</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">产品名称</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">个数</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">服务项目类型</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">单价</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">小计</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">备注</td>
	</tr>
	<tbody id="add_jeecgOrderProduct_table">
		<c:if test="${symbol_dollar}{fn:length(jeecgOrderProductList)  <= 0 }">
			<tr>
				<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品名称！" datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位" name="jeecgOrderProductList[0].gopProductName" maxlength="100" type="text" value=""></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品个数！" datatype="n" errormsg="订单产品明细的产品个数必须为数字" name="jeecgOrderProductList[0].gopCount" maxlength="10" type="text" value=""></td>
				<td align="left"><t:dictSelect field="jeecgOrderProductList[0].gopProductType" typeGroupCode="service" hasLabel="false" defaultVal="${symbol_dollar}{poVal.gocSex}"></t:dictSelect></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品单价！" datatype="d" errormsg="订单产品明细的产品单价填写不正确" name="jeecgOrderProductList[0].gopOnePrice" maxlength="10" type="text" value=""></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品小计！" datatype="d" errormsg="订单产品明细的产品小计填写不正确" name="jeecgOrderProductList[0].gopSumPrice" maxlength="10" type="text" value=""></td>
				<td align="left"><input name="jeecgOrderProductList[0].gopContent" maxlength="200" type="text" value=""></td>
			</tr>
		</c:if>
		<c:if test="${symbol_dollar}{fn:length(jeecgOrderProductList)  > 0 }">
			<c:forEach items="${symbol_dollar}{jeecgOrderProductList}" var="poVal" varStatus="stuts">
				<tr>
					<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
					<td align="left"><input nullmsg="请输入订单产品明细的产品名称！" datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位" name="jeecgOrderProductList[${symbol_dollar}{stuts.index }].gopProductName" maxlength="100" type="text"
						value="${symbol_dollar}{poVal.gopProductName}"></td>
					<td align="left"><input nullmsg="请输入订单产品明细的产品个数！" datatype="n" errormsg="订单产品明细的产品个数必须为数字" name="jeecgOrderProductList[${symbol_dollar}{stuts.index }].gopCount" maxlength="10" type="text"
						value="${symbol_dollar}{poVal.gopCount }"></td>
					<td align="left"><t:dictSelect field="jeecgOrderProductList[${symbol_dollar}{stuts.index }].gopProductType" typeGroupCode="service" hasLabel="false" defaultVal="${symbol_dollar}{poVal.gopProductType}"></t:dictSelect></td>
					<td align="left"><input nullmsg="请输入订单产品明细的产品单价！" datatype="d" errormsg="订单产品明细的产品单价填写不正确" name="jeecgOrderProductList[${symbol_dollar}{stuts.index }].gopOnePrice" maxlength="10" type="text"
						value="${symbol_dollar}{poVal.gopOnePrice }"></td>
					<td align="left"><input nullmsg="请输入订单产品明细的产品小计！" datatype="d" errormsg="订单产品明细的产品小计填写不正确" name="jeecgOrderProductList[${symbol_dollar}{stuts.index }].gopSumPrice" maxlength="10" type="text"
						value="${symbol_dollar}{poVal.gopSumPrice }"></td>
					<td align="left"><input name="jeecgOrderProductList[${symbol_dollar}{stuts.index }].gopContent" maxlength="200" type="text" value="${symbol_dollar}{poVal.gopContent }"></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
</div>
<div title="客户明细 ">
<div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar"><a id="addCustomBtn" href="${symbol_pound}">添加</a> <a id="delCustomBtn" href="${symbol_pound}">删除</a></div>
<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderCustom_table">
	<tr bgcolor="${symbol_pound}E6E6E6">
		<td align="center" bgcolor="${symbol_pound}EEEEEE">序号</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">姓名</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">性别</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">身份证号</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">护照号</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">业务</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">备注</td>
	</tr>
	<tbody id="add_jeecgOrderCustom_table">
		<c:if test="${symbol_dollar}{fn:length(jeecgOrderCustomList)  <= 0 }">
			<tr>
				<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
				<td align="left"><input name="jeecgOrderCustomList[0].gocCusName" maxlength="50" type="text"></td>
				<td align="left"><t:dictSelect field="jeecgOrderCustomList[0].gocSex" typeGroupCode="sex" hasLabel="false" defaultVal="${symbol_dollar}{jgDemo.sex}"></t:dictSelect></td>
				<td align="left"><input name="jeecgOrderCustomList[0].gocIdcard" maxlength="32" type="text"></td>
				<td align="left"><input name="jeecgOrderCustomList[0].gocPassportCode" maxlength="32" type="text"></td>
				<td align="left"><input name="jeecgOrderCustomList[0].gocBussContent" maxlength="100" type="text"></td>
				<td align="left"><input name="jeecgOrderCustomList[0].gocContent" maxlength="200" type="text"></td>
			</tr>
		</c:if>
		<c:if test="${symbol_dollar}{fn:length(jeecgOrderCustomList)  > 0 }">
			<c:forEach items="${symbol_dollar}{jeecgOrderCustomList}" var="poVal" varStatus="stuts">
				<tr>
					<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
					<td align="left"><input name="jeecgOrderCustomList[${symbol_dollar}{stuts.index }].gocCusName" maxlength="50" type="text" value="${symbol_dollar}{poVal.gocCusName }"></td>
					<td align="left"><t:dictSelect field="jeecgOrderCustomList[${symbol_dollar}{stuts.index }].gocSex" typeGroupCode="sex" hasLabel="false" defaultVal="${symbol_dollar}{poVal.gocSex}"></t:dictSelect></td>
					<td align="left"><input name="jeecgOrderCustomList[${symbol_dollar}{stuts.index }].gocIdcard" maxlength="32" type="text" value="${symbol_dollar}{poVal.gocIdcard }"></td>
					<td align="left"><input name="jeecgOrderCustomList[${symbol_dollar}{stuts.index }].gocPassportCode" maxlength="32" type="text" value="${symbol_dollar}{poVal.gocPassportCode }"></td>
					<td align="left"><input name="jeecgOrderCustomList[${symbol_dollar}{stuts.index }].gocBussContent" maxlength="100" type="text" value="${symbol_dollar}{poVal.gocBussContent }"></td>
					<td align="left"><input name="jeecgOrderCustomList[${symbol_dollar}{stuts.index }].gocContent" maxlength="200" type="text" value="${symbol_dollar}{poVal.gocContent }"></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<input type="hidden" name="jeecgOrderCustomShow" value="true" /></div>
</div>
</div>
</form>

<table style="display: none">
	<tbody id="add_jeecgOrderProduct_table_template">
		<tr>
			<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
			<td align="left"><input nullmsg="请输入订单产品明细的产品名称！" datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位" name="jeecgOrderProductList[${symbol_pound}index${symbol_pound}].gopProductName" maxlength="100" type="text"></td>
			<td align="left"><input nullmsg="请输入订单产品明细的产品个数！" datatype="n" errormsg="订单产品明细的产品个数必须为数字" name="jeecgOrderProductList[${symbol_pound}index${symbol_pound}].gopCount" maxlength="10" type="text"></td>
			<td align="left"><select name="jeecgOrderProductList[${symbol_pound}index${symbol_pound}].gopProductType">
				<option value="1">特殊服务</option>
				<option value="2">普通服务</option>
			</select></td>
			<td align="left"><input nullmsg="请输入订单产品明细的产品单价！" datatype="d" errormsg="订单产品明细的产品单价填写不正确" name="jeecgOrderProductList[${symbol_pound}index${symbol_pound}].gopOnePrice" maxlength="10" type="text"></td>
			<td align="left"><input nullmsg="请输入订单产品明细的产品小计！" datatype="d" errormsg="订单产品明细的产品小计填写不正确" name="jeecgOrderProductList[${symbol_pound}index${symbol_pound}].gopSumPrice" maxlength="10" type="text"></td>
			<td align="left"><input name="jeecgOrderProductList[${symbol_pound}index${symbol_pound}].gopContent" maxlength="200" type="text"></td>
		</tr>
	</tbody>
	<tbody id="add_jeecgOrderCustom_table_template">
		<tr>
			<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
			<td align="left"><input name="jeecgOrderCustomList[${symbol_pound}index${symbol_pound}].gocCusName" maxlength="50" type="text"></td>
			<td align="left"><select name="jeecgOrderCustomList[${symbol_pound}index${symbol_pound}].gocSex">
				<option value="0">男性</option>
				<option value="1">女性</option>
			</select></td>
			<td align="left"><input name="jeecgOrderCustomList[${symbol_pound}index${symbol_pound}].gocIdcard" maxlength="32" type="text"></td>
			<td align="left"><input name="jeecgOrderCustomList[${symbol_pound}index${symbol_pound}].gocPassportCode" maxlength="32" type="text"></td>
			<td align="left"><input name="jeecgOrderCustomList[${symbol_pound}index${symbol_pound}].gocBussContent" maxlength="100" type="text"></td>
			<td align="left"><input name="jeecgOrderCustomList[${symbol_pound}index${symbol_pound}].gocContent" maxlength="200" type="text"></td>
		</tr>
	</tbody>
</table>
</body>
</html>