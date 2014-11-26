#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	${symbol_dollar}(function() {
		${symbol_dollar}('${symbol_pound}functionid').tree({
			checkbox : true,
			url : 'roleController.do?setAuthority&roleId=${symbol_dollar}{roleId}',
			onLoadSuccess : function(node) {
				expandAll();
			},
			onClick: function(node){
				var isRoot =  ${symbol_dollar}('${symbol_pound}functionid').tree('getChildren', node.target);
				if(isRoot==''){
					var roleId = ${symbol_dollar}("${symbol_pound}rid").val();
					${symbol_dollar}('${symbol_pound}operationListpanel').panel("refresh", "roleController.do?operationListForFunction&functionId="+node.id+"&roleId="+roleId);
				}else {
				}
			}
		});
		${symbol_dollar}("${symbol_pound}functionListPanel").panel(
				{
					title :"菜单列表",
					tools:[{iconCls:'icon-save',handler:function(){mysubmit();}}]
				}
		);
		${symbol_dollar}("${symbol_pound}operationListpanel").panel(
				{
					title :"操作按钮列表",
					tools:[{iconCls:'icon-save',handler:function(){submitOperation();}}]
				}
		);
	});
	function mysubmit() {
		var roleId = ${symbol_dollar}("${symbol_pound}rid").val();
		var s = GetNode();
		doSubmit("roleController.do?updateAuthority&rolefunctions=" + s + "&roleId=" + roleId);
	}
	function GetNode() {
		var node = ${symbol_dollar}('${symbol_pound}functionid').tree('getChecked');
		var cnodes = '';
		var pnodes = '';
		var pnode = null; //保存上一步所选父节点
		for ( var i = 0; i < node.length; i++) {
			if (${symbol_dollar}('${symbol_pound}functionid').tree('isLeaf', node[i].target)) {
				cnodes += node[i].id + ',';
				pnode = ${symbol_dollar}('${symbol_pound}functionid').tree('getParent', node[i].target); //获取当前节点的父节点
				while (pnode!=null) {//添加全部父节点
					pnodes += pnode.id + ',';
					pnode = ${symbol_dollar}('${symbol_pound}functionid').tree('getParent', pnode.target); 
				}
			}
		}
		cnodes = cnodes.substring(0, cnodes.length - 1);
		pnodes = pnodes.substring(0, pnodes.length - 1);
		return cnodes + "," + pnodes;
	};
	
	function expandAll() {
		var node = ${symbol_dollar}('${symbol_pound}functionid').tree('getSelected');
		if (node) {
			${symbol_dollar}('${symbol_pound}functionid').tree('expandAll', node.target);
		} else {
			${symbol_dollar}('${symbol_pound}functionid').tree('expandAll');
		}
	}
	function selecrAll() {
		var node = ${symbol_dollar}('${symbol_pound}functionid').tree('getRoots');
		for ( var i = 0; i < node.length; i++) {
			var childrenNode =  ${symbol_dollar}('${symbol_pound}functionid').tree('getChildren',node[i].target);
			for ( var j = 0; j < childrenNode.length; j++) {
				${symbol_dollar}('${symbol_pound}functionid').tree("check",childrenNode[j].target);
			}
	    }
	}
	function reset() {
		${symbol_dollar}('${symbol_pound}functionid').tree('reload');
	}

	${symbol_dollar}('${symbol_pound}selecrAllBtn').linkbutton({   
	}); 
	${symbol_dollar}('${symbol_pound}resetBtn').linkbutton({   
	});   
</script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="functionListPanel"><input type="hidden" name="roleId" value="${symbol_dollar}{roleId}" id="rid"> <a id="selecrAllBtn"
	onclick="selecrAll();">全选</a> <a id="resetBtn" onclick="reset();">重置</a>
<ul id="functionid"></ul>
</div>
</div>
<div region="east" style="width: 150px; overflow: hidden;" split="true">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="operationListpanel"></div>
</div>
</div>
