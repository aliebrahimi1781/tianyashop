#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML>
<HEAD>
<TITLE>weboffice演示页面</TITLE>
<!-- --------------------=== 调用Weboffice初始化方法 ===--------------------- -->
<SCRIPT language=javascript event=NotifyCtrlReady for=WebOffice1>
/****************************************************
*
*	在装载完Weboffice(执行<object>...</object>)
*	控件后执行 "WebOffice1_NotifyCtrlReady"方法
*
****************************************************/
	WebOffice1_NotifyCtrlReady();
</SCRIPT>


<SCRIPT language=javascript>
/****************************************************
*
*		控件初始化WebOffice方法
*
****************************************************/
function WebOffice1_NotifyCtrlReady() {
	document.all.WebOffice1.ShowToolBar(0);
	//document.all.WebOffice1.SetWindowText("WebOffice", 0);
	document.all.WebOffice1.OptionFlag |= 128;
	var sFileType = "${symbol_dollar}{fileType}";//doc,xls,ppt,wps
	if ("${symbol_dollar}{docId}" != "") {
		document.all.WebOffice1.LoadOriginalFile("./webOfficeController.do?getDoc&fileId=${symbol_dollar}{docId}", sFileType);
	} else {
		// 新建文档
		document.all.WebOffice1.LoadOriginalFile("", sFileType);
	}
}
function window_onunload() {
	document.all.WebOffice1.Close();
}
function newDoc() {
	document.all.WebOffice1.ShowToolBar(0);
	var doctype=document.all.doctype.value;
	document.all.WebOffice1.LoadOriginalFile("", doctype);
}

// 打开本地文件
function docOpen() {
	document.all.WebOffice1.LoadOriginalFile("open", "doc");
}
function newSave() {
	document.all.WebOffice1.Save();
}
function SaveAsTo() {
	document.all.WebOffice1.ShowDialog(84);
}
function SaveDoc() {
	var returnValue;
	 if(myform.DocTitle.value ==""){
		alert("标题不可为空")
		myform.DocTitle.focus();
		return false;
	}
	if(myform.DocID.value ==""){
		alert("文号不可为空")
		myform.DocID.focus();
		return false;
	}
	
	document.all.WebOffice1.HttpInit();			//初始化Http引擎
	// 添加相应的Post元素 
	document.all.WebOffice1.HttpAddPostString("id", "${symbol_dollar}{docId}");
	document.all.WebOffice1.HttpAddPostString("doctitle", myform.DocTitle.value);
	document.all.WebOffice1.HttpAddPostString("docid", myform.DocID.value);
	document.all.WebOffice1.HttpAddPostString("doctype",myform.doctype.value);
	document.all.WebOffice1.HttpAddPostCurrFile("DocContent","");		// 上传文件

	returnValue = document.all.WebOffice1.HttpPost("./webOfficeController.do?saveDoc&fileId=${symbol_dollar}{docId}");	// 判断上传是否成功
	if("succeed" == returnValue){
		alert("文件上传成功");	
	}else if("failed" == returnValue)
		alert("文件上传失败")
	return_onclick(); 
}
</SCRIPT>
</HEAD>
<BODY style="BACKGROUND: ${symbol_pound}ccc" onunload="return window_onunload()">
<form name="myform">
<table width="1123" border="0" cellpadding="1" cellspacing="3">
	<tr bgcolor="${symbol_pound}8CBFE9">
		<td height="15" valign="top" class="downSide"><font size="-1">&nbsp;&nbsp; 文档编号:</font> <input name="DocID" value="${symbol_dollar}{docData.docid}" size="14"> <span class="STYLE1"> | </span> <font
			size="-1">&nbsp;&nbsp; 标 题:</font> <input name="DocTitle" value="${symbol_dollar}{docData.doctitle}" size="14"> <span class="STYLE1"> | </span> <font size="-1">&nbsp;&nbsp;&nbsp;文件格式: </font> <select
			name="doctype" size="1" id="doctype" ${symbol_dollar}{docId!='' ?"disabled='true' ":""} onchange="newDoc()">
			<option value="doc" ${symbol_dollar}{docData.doctype== 'doc'?"selected='selected'":""}>Word</option>
			<option value="xls" ${symbol_dollar}{docData.doctype== 'xls'?"selected='selected'":""}>Excel</option>
			<option value="ppt" ${symbol_dollar}{docData.doctype== 'ppt'?"selected='selected'":""}>Powerpoint</option>
			<option value="wps" ${symbol_dollar}{docData.doctype== 'wps'?"selected='selected'":""}>wps</option>
		</select> <span class="STYLE1">|</span> <input name="button9" type="button" onClick="return SaveDoc()" value="上传到服务器" classs="rollout"> <span class="STYLE1">|</span> <input name="button"
			type="button" onClick="return docOpen()" value="打开本地文件" /></td>
		<td></td>
	</tr>
	<tr>
		<td colspan="2" rowspan="12" valign="top"><!-- -----------------------------== 装载weboffice控件 ==--------------------------------- --> <SCRIPT>
					var s = ""
						s += "<object id=WebOffice1 height=500 width='100%' style='LEFT: 0px; TOP: 0px' classid='clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5' codebase='${symbol_dollar}{webRoot}/plug-in/webOffice/weboffice_v6.0.5.0.cab${symbol_pound}Version=6,0,5,0'>"
						s +="<param name='_ExtentX' value='6350'><param name='_ExtentY' value='6350'>"
						s +="</OBJECT>"			
						document.write(s)
				</SCRIPT> <!-- --------------------------------== 结束装载控件 ==----------------------------------- --></td>
	</tr>
</table>
</form>
</BODY>
</HTML>
