#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	${symbol_dollar}('${symbol_pound}addCustomBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	${symbol_dollar}('${symbol_pound}delCustomBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	${symbol_dollar}('${symbol_pound}addCustomBtn').bind('click', function(){   
 		 var tr =  ${symbol_dollar}("${symbol_pound}add_jeecgStudent_table_template tr").clone();
	 	 ${symbol_dollar}("${symbol_pound}add_jeecgStudent_table").append(tr);
	 	 resetTrNum('add_jeecgStudent_table');
    });  
	${symbol_dollar}('${symbol_pound}delCustomBtn').bind('click', function(){   
      	${symbol_dollar}("${symbol_pound}add_jeecgStudent_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_jeecgStudent_table'); 
    }); 
    ${symbol_dollar}(document).ready(function(){
    	${symbol_dollar}(".datagrid-toolbar").parent().css("width","auto");
    });
    function resetTrNum(tableId) {
		${symbol_dollar}tbody = ${symbol_dollar}("${symbol_pound}"+tableId+"");
		${symbol_dollar}tbody.find('>tr').each(function(i){
			${symbol_dollar}(':input, select', this).each(function(){
				var ${symbol_dollar}this = ${symbol_dollar}(this), name = ${symbol_dollar}this.attr('name'), val = ${symbol_dollar}this.val();
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
			});
		});
	}
</script>
<div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar"><a id="addCustomBtn" href="${symbol_pound}">添加</a> <a id="delCustomBtn" href="${symbol_pound}">删除</a></div>
<div style="width: auto; height: 300px; overflow-y: auto; overflow-x: scroll;">
<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderCustom_table">
	<tr bgcolor="${symbol_pound}E6E6E6">
		<td align="center" bgcolor="${symbol_pound}EEEEEE">序号</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">姓名</td>
		<td align="left" bgcolor="${symbol_pound}EEEEEE">性别</td>
		<td  style="display: none;"></td>
	</tr>
	<tbody id="add_jeecgStudent_table">
		<c:if test="${symbol_dollar}{fn:length(studentsList)  <= 0 }">
			<tr>
				<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
				<td align="left"><input name="students[0].name" maxlength="50" type="text" style="width: 220px;"></td>
				<td align="left"><t:dictSelect field="students[0].sex" typeGroupCode="sex" hasLabel="false" defaultVal="${symbol_dollar}{jgDemo.sex}"></t:dictSelect></td>
            </tr>
		</c:if>
		<c:if test="${symbol_dollar}{fn:length(studentsList)  > 0 }">
			<c:forEach items="${symbol_dollar}{studentsList}" var="poVal" varStatus="stuts">
				<tr>
					<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
					<td align="left"><input name="students[${symbol_dollar}{stuts.index }].name" maxlength="50" type="text" value="${symbol_dollar}{poVal.name }" style="width: 220px;"></td>
					<td align="left"><t:dictSelect field="students[${symbol_dollar}{stuts.index }].sex" typeGroupCode="sex" hasLabel="false" defaultVal="${symbol_dollar}{poVal.sex}"></t:dictSelect></td>
                    <td align="left" style="display: none;"><input name="students[${symbol_dollar}{stuts.index }].id" type="text" value="${symbol_dollar}{poVal.id }" style="width: 0px;"></td>
                </tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
</div>