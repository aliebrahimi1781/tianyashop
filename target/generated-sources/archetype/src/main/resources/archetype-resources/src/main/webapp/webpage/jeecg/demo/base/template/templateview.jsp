#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<style type="text/css">
.ke-icon-example1 {
	background-image: url(../themes/default/print.png);
	background-position: 0px -33px;
	width: 16px;
	height: 16px;
}
</style>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript" src="plug-in/kindeditor/kindeditor.js"></script>
<script type="text/javascript" src="plug-in/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="attachmentcontent"]', {
			filterMode : false,
			readonlyMode : true,
			toolbarLineHeight : 0,
			items : [],
			afterCreate : function() {
				var version = ${symbol_dollar}.browser.version;
				if (${symbol_dollar}.browser.msie) {
					if (version == '9.0') {
						
						this.edit.setHeight((window.screen.height-400)); //设置高度

					}
					if (version == '6.0') {
						
						this.edit.setHeight((window.screen.height-500)); //设置高度
					}
					if (version == '7.0') {
						this.edit.setHeight((window.screen.height-330)); //设置高度
					} else {
						this.edit.setHeight((window.screen.height) * 67 / 100); //设置高度
					}

				}
				if (${symbol_dollar}.browser.webkit) {
					this.edit.setHeight((window.screen.height-220)); //设置高度
				}
				if (${symbol_dollar}.browser.mozilla) {
					this.edit.setHeight((window.screen.height-280)); //设置高度
				}
			}
		});
	});
<%--// 自定义插件 ${symbol_pound}1
	KindEditor.lang({
		example1 : '打印'
	});
	KindEditor.plugin('example1', function(K) {
		var self = this, name = 'example1';
		self.clickToolbar(name, function() {
			prn1_preview(self.html());
			if ('${symbol_dollar}{attachmenttitle}' == '备案申请表') {
				var state = ${symbol_dollar}{state};
				var printflag = ${symbol_dollar}{printflag};
				if (printflag == 1 && state == 1) {
					document.location.href = "projectFilingController.do?appSucces";
				}

			}
		});
	});--%>
	function print() {
		prn1_preview(${symbol_dollar}('${symbol_pound}attachmentcontent').val());
	}
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<div class="datagrid-toolbar"><a href="${symbol_pound}" class="easyui-linkbutton" plain="true" iconCls="icon-print" onclick="print();">打印</a></div>
<input type="hidden" name="id" value="${symbol_dollar}{id}">
<input type="hidden" id="attachmenttitle" name="attachmenttitle" value="${symbol_dollar}{attachmenttitle}">
<input type="hidden" id="infocode" name="infocode" value="${symbol_dollar}{code}">
<textarea id="attachmentcontent" name="attachmentcontent" style="visibility: hidden; width: 100%; height: 100% px">
	    ${symbol_dollar}{attachmentcontent}
	</textarea>
</body>
<script language="javascript" src="plug-in/lodop/LodopFuncs.js"></script>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0
		pluginspage="plug-in/lodop/install_lodop32.exe"></embed> </object>
<script type="text/javascript">
	var LODOP; //声明为全局变量 
	function prn1_preview(content) {
		CreateOneFormPage(content);
		LODOP.PREVIEW();
	};
	function CreateOneFormPage(content) {
		LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
		LODOP.ADD_PRINT_HTM("10mm", 34, "RightMargin:0.9cm", "BottomMargin:10mm", content);
	};
</script>
</html>
