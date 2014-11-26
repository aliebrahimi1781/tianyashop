#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	${symbol_dollar}('${symbol_pound}addCgreportConfigItemBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	${symbol_dollar}('${symbol_pound}delCgreportConfigItemBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	${symbol_dollar}('${symbol_pound}addCgreportConfigItemBtn').bind('click', function(){   
 		 var tr =  ${symbol_dollar}("${symbol_pound}add_cgreportConfigItem_table_template tr").clone();
	 	 ${symbol_dollar}("${symbol_pound}add_cgreportConfigItem_table").append(tr);
	 	 resetTrNum('add_cgreportConfigItem_table');
    });  
	${symbol_dollar}('${symbol_pound}delCgreportConfigItemBtn').bind('click', function(){   
      	${symbol_dollar}("${symbol_pound}add_cgreportConfigItem_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_cgreportConfigItem_table'); 
    }); 
    ${symbol_dollar}(document).ready(function(){
    	${symbol_dollar}(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			${symbol_dollar}(":input").attr("disabled","true");
			${symbol_dollar}(".datagrid-toolbar").hide();
		}
    });
</script>
<div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar"><a id="addCgreportConfigItemBtn" href="${symbol_pound}">添加</a> <a id="delCgreportConfigItemBtn" href="${symbol_pound}">删除</a></div>
<div style="width: auto; height: 300px; overflow-y: auto; overflow-x: scroll;">
<table border="0" cellpadding="2" cellspacing="0" id="cgreportConfigItem_table">
	<tr bgcolor="${symbol_pound}E6E6E6">
		<td align="center" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label">序号</label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 字段名 </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 排序 </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 字段文本 </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 字段类型 </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 是否显示 </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 字段href </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 查询模式 </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 取值表达式 </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 字典Code </label></td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE"><label class="Validform_label"> 是否查询 </label></td>
	</tr>
	<tbody id="add_cgreportConfigItem_table">
		<c:if test="${symbol_dollar}{fn:length(cgreportConfigItemList)  <= 0 }">
			<tr>
				<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
				<input name="cgreportConfigItemList[0].id" type="hidden" />
				<input name="cgreportConfigItemList[0].cgrheadId" type="hidden" />
				<td align="left"><input name="cgreportConfigItemList[0].fieldName" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
				<td align="left"><input name="cgreportConfigItemList[0].orderNum" maxlength="3" type="text" class="inputxt" style="width: 30px;"></td>
				<td align="left"><input name="cgreportConfigItemList[0].fieldTxt" maxlength="1000" type="text" class="inputxt" style="width: 120px;"></td>
				<td align="left"><t:dictSelect field="cgreportConfigItemList[0].fieldType"  extendJson="{style:'width:80px'}" type="list" typeGroupCode="fieldtype" defaultVal="${symbol_dollar}{cgreportConfigItemPage.fieldType}" hasLabel="false" title="字段类型"></t:dictSelect></td>
				<td align="left"><select name="cgreportConfigItemList[0].isShow" style="width: 60px;">
					<option value="Y">显示</option>
					<option value="N">隐藏</option>
				</select></td>
				<td align="left"><input name="cgreportConfigItemList[0].fieldHref" maxlength="100" type="text" class="inputxt" style="width: 120px;"></td>
				<td align="left"><t:dictSelect field="cgreportConfigItemList[0].SMode" type="list"  extendJson="{style:'width:90px'}" typeGroupCode="searchmode" defaultVal="${symbol_dollar}{cgreportConfigItemPage.sMode}" hasLabel="false" title="查询模式"></t:dictSelect></td>
				<td align="left"><input name="cgreportConfigItemList[0].replaceVa" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
				<td align="left"><input name="cgreportConfigItemList[0].dictCode" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
				<td align="left"><t:dictSelect field="cgreportConfigItemList[0].SFlag"  extendJson="{style:'width:60px'}" divClass="STYLE_LEG" type="list" typeGroupCode="yesorno" defaultVal="${symbol_dollar}{cgreportConfigItemPage.sFlag}" hasLabel="false" title="是否查询"></t:dictSelect></td>
			</tr>
		</c:if>
		<c:if test="${symbol_dollar}{fn:length(cgreportConfigItemList)  > 0 }">
			<c:forEach items="${symbol_dollar}{cgreportConfigItemList}" var="poVal" varStatus="stuts">
				<tr>
					<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
					<input name="cgreportConfigItemList[${symbol_dollar}{stuts.index }].id" type="hidden" value="${symbol_dollar}{poVal.id }" />
					<input name="cgreportConfigItemList[${symbol_dollar}{stuts.index }].cgrheadId" type="hidden" value="${symbol_dollar}{poVal.cgrheadId }" />
					<td align="left"><input name="cgreportConfigItemList[${symbol_dollar}{stuts.index }].fieldName" maxlength="36" type="text" class="inputxt" style="width: 120px;" value="${symbol_dollar}{poVal.fieldName }"></td>
					<td align="left"><input name="cgreportConfigItemList[${symbol_dollar}{stuts.index }].orderNum" maxlength="10" type="text" class="inputxt" style="width: 30px;" value="${symbol_dollar}{poVal.orderNum }"></td>
					<td align="left"><input name="cgreportConfigItemList[${symbol_dollar}{stuts.index }].fieldTxt" maxlength="1000" type="text" class="inputxt" style="width: 120px;" value="${symbol_dollar}{poVal.fieldTxt }"></td>
					<td align="left"><t:dictSelect field="cgreportConfigItemList[${symbol_dollar}{stuts.index }].fieldType" type="list" extendJson="{style:'width:80px'}" typeGroupCode="fieldtype" defaultVal="${symbol_dollar}{poVal.fieldType}" hasLabel="false" title="字段类型"></t:dictSelect>
					</td>
					<td align="left"><select id="isShow" name="cgreportConfigItemList[${symbol_dollar}{stuts.index}].isShow"  style="width: 60px;">
						<option value="N" <c:if test="${symbol_dollar}{poVal.isShow eq 'N'}"> selected="selected"</c:if>>隐藏</option>
						<option value="Y" <c:if test="${symbol_dollar}{poVal.isShow eq 'Y'}"> selected="selected"</c:if>>显示</option>
					</select></td>
					<td align="left"><input name="cgreportConfigItemList[${symbol_dollar}{stuts.index }].fieldHref" maxlength="120" type="text" class="inputxt" style="width: 120px;" value="${symbol_dollar}{poVal.fieldHref}"></td>
					<td align="left"><t:dictSelect field="cgreportConfigItemList[${symbol_dollar}{stuts.index }].SMode"  extendJson="{style:'width:90px'}" type="list" typeGroupCode="searchmode" defaultVal="${symbol_dollar}{poVal.SMode}" hasLabel="false" title="查询模式"></t:dictSelect></td>
					<td align="left"><input name="cgreportConfigItemList[${symbol_dollar}{stuts.index }].replaceVa" maxlength="36" type="text" class="inputxt" style="width: 120px;" value="${symbol_dollar}{poVal.replaceVa }"></td>
					<td align="left"><input name="cgreportConfigItemList[${symbol_dollar}{stuts.index }].dictCode" maxlength="36" type="text" class="inputxt" style="width: 120px;" value="${symbol_dollar}{poVal.dictCode }"></td>
					<td align="left"><t:dictSelect field="cgreportConfigItemList[${symbol_dollar}{stuts.index }].SFlag" extendJson="{style:'width:60px'}" type="list" typeGroupCode="yesorno" defaultVal="${symbol_dollar}{poVal.SFlag}" hasLabel="false" title="是否查询"></t:dictSelect></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
</div>