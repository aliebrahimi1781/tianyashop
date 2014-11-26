#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>动态报表配置抬头</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
  ${symbol_dollar}(document).ready(function(){
	${symbol_dollar}('${symbol_pound}tt').tabs({
	   onSelect:function(title){
	       ${symbol_dollar}('${symbol_pound}tt .panel-body').css('width','auto');
		}
	});
  });
 //初始化下标
	function resetTrNum(tableId) {
		${symbol_dollar}tbody = ${symbol_dollar}("${symbol_pound}"+tableId+"");
		${symbol_dollar}tbody.find('>tr').each(function(i){
			${symbol_dollar}(':input, select,button,a', this).each(function(){
				var ${symbol_dollar}this = ${symbol_dollar}(this), name = ${symbol_dollar}this.attr('name'),id=${symbol_dollar}this.attr('id'),onclick_str=${symbol_dollar}this.attr('onclick'), val = ${symbol_dollar}this.val();
				if(name!=null){
					if (name.indexOf("${symbol_pound}index${symbol_pound}") >= 0){
						${symbol_dollar}this.attr("name",name.replace('${symbol_pound}index${symbol_pound}',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						${symbol_dollar}this.attr("name",name.replace(new_name,i));
					}
				}
				if(id!=null){
					if (id.indexOf("${symbol_pound}index${symbol_pound}") >= 0){
						${symbol_dollar}this.attr("id",id.replace('${symbol_pound}index${symbol_pound}',i));
					}else{
						var s = id.indexOf("[");
						var e = id.indexOf("]");
						var new_id = id.substring(s+1,e);
						${symbol_dollar}this.attr("id",id.replace(new_id,i));
					}
				}
				if(onclick_str!=null){
					if (onclick_str.indexOf("${symbol_pound}index${symbol_pound}") >= 0){
						${symbol_dollar}this.attr("onclick",onclick_str.replace(/${symbol_pound}index${symbol_pound}/g,i));
					}else{
					}
				}
			});
		});
	}
	function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			${symbol_dollar}("${symbol_pound}" + Img).attr("src", fileUrl);
			${symbol_dollar}("${symbol_pound}" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
	function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			${symbol_dollar}("${symbol_pound}" + file).attr("href", fileUrl);
			${symbol_dollar}("${symbol_pound}" + inputId).attr("value", fileUrl);
			decode(fileUrl, file);
		};
		finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
	function decode(value, id) {//value传入值,id接受值
		var last = value.lastIndexOf("/");
		var filename = value.substring(last + 1, value.length);
		${symbol_dollar}("${symbol_pound}" + id).text(decodeURIComponent(filename));
	}
 </script>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="cgreportConfigHeadController.do?doUpdate">
	<input id="id" name="id" type="hidden" value="${symbol_dollar}{cgreportConfigHeadPage.id }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label">编码:</label></td>
			<td class="value"><input id="code" name="code" type="text" style="width: 150px" class="inputxt" datatype="*" value='${symbol_dollar}{cgreportConfigHeadPage.code}'> <span class="Validform_checktip"></span></td>
			<td align="right"><label class="Validform_label">名称:</label></td>
			<td class="value"><input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*" value='${symbol_dollar}{cgreportConfigHeadPage.name}'> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">查询数据SQL:</label></td>
			<td class="value" colspan="3"><textarea rows="5" cols="90" id="cgrSql" name="cgrSql" datatype="*">${symbol_dollar}{cgreportConfigHeadPage.cgrSql}</textarea> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">描述:</label></td>
			<td class="value" colspan="3"><textarea rows="3" cols="90" id="content" name="content" datatype="*">${symbol_dollar}{cgreportConfigHeadPage.content}</textarea> <span class="Validform_checktip"></span></td>
		</tr>
	</table>
	<div style="width: auto; height: 200px;"><%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
	<div style="width: 800px; height: 1px;"></div>
	<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
		<t:tab href="cgreportConfigHeadController.do?cgreportConfigItemList&id=${symbol_dollar}{cgreportConfigHeadPage.id}" icon="icon-search" title="动态报表配置明细" id="cgreportConfigItem"></t:tab>
	</t:tabs></div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display: none">
	<tbody id="add_cgreportConfigItem_table_template">
		<tr>
			<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
			<td align="left"><input name="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].fieldName" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
			<td align="left"><input name="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].orderNum" maxlength="10" type="text" class="inputxt" style="width: 30px;"></td>
			<td align="left"><input name="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].fieldTxt" maxlength="1000" type="text" class="inputxt" style="width: 120px;"></td>
			<td align="left"><t:dictSelect field="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].fieldType" type="list" typeGroupCode="fieldtype" defaultVal="String" hasLabel="false" title="字段类型"></t:dictSelect></td>
			<td align="left"><select id="isShow" name="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].isShow"  style="width: 60px;">
				<option value="Y">显示</option>
				<option value="N">隐藏</option>
			</select></td>
			<td align="left"><input name="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].fieldHref" maxlength="1000" type="text" class="inputxt" style="width: 120px;">
			<td align="left"><t:dictSelect field="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].SMode" type="list" typeGroupCode="searchmode" defaultVal="" hasLabel="false" title="查询模式"></t:dictSelect></td>
			<td align="left"><input name="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].replaceVa" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
			<td align="left"><input name="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].dictCode" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
			<td align="left"><t:dictSelect field="cgreportConfigItemList[${symbol_pound}index${symbol_pound}].SFlag" type="list" typeGroupCode="yesorno" defaultVal="" hasLabel="false" title="是否查询"></t:dictSelect></td>
		</tr>
	</tbody>
</table>
</body>
<script src="webpage/jeecg/cgreport/core/cgreportConfigHead.js"></script>