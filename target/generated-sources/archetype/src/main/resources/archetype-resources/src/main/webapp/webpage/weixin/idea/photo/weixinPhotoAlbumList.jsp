#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/weixin/css/photo_album.css">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="weixinPhotoAlbumController.do?weixinPhotoAlbum">
    <input id="id" name="id" type="hidden" value="${symbol_dollar}{id}">
	<table cellpadding="0" cellspacing="1" class="formtable">
	  <tr>
	    <td>
	      <a href="${symbol_pound}" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('新建相册','weixinPhotoAlbumController.do?addorupdate',null,null,null)">新建相册</a>
		</td>
	  </tr>
	</table>
  </t:formvalid>
    <div class="photoList" id="func">
      <ul class="list">
        <c:forEach items="${symbol_dollar}{albums}" var="album">
	      <li>
	        <img src="${symbol_dollar}{album.photo.realpath}" width="104" height="107" onclick="uploadPhotoInit('${symbol_dollar}{album.id}')">
	          <h3>${symbol_dollar}{album.name}</h3>
	          <a href="${symbol_pound}" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="goEditPhotoAlbum('${symbol_dollar}{album.id}')" title="编辑相册"></a>
	          <a href="${symbol_pound}" class="easyui-linkbutton" plain="true" icon="icon-remove" onclick="delPhotoAlbum('${symbol_dollar}{album.id}','${symbol_dollar}{album.photo.id}')" title="删除相册"></a>
	         </img>
	      </li>
		</c:forEach>
	  </ul>
    </div>
  </div>
 </div>
<script lang="javascript">
  //以tab页打开上传照片页面
  function uploadPhotoInit(id){
    var url = 'weixinPhotoAlbumController.do?viewPhotos&type=table&isIframe&id='+id;
    formobj.action = url;
    formobj.submit();
 	//addOneTab("照片上传", url);
  }
  //新建相册回调
  function cc(c) {
    ${symbol_dollar}.dialog.confirm('上传照片去', function(){
		var url = 'weixinPhotoAlbumController.do?viewPhotos&type=table&isIframe&id='+c.attributes.albumId;
 		formobj.action = url;
    	formobj.submit();
 		//addOneTab("照片上传", url);
	}, function(){
		formobj.submit();
	}).zindex();
  }
  //弹出编辑相册
  function goEditPhotoAlbum(id){
   	var url = 'weixinPhotoAlbumController.do?addorupdate&id='+id;
 	add('相册编辑', url);
  }
  //删除相册
  function delPhotoAlbum(id,photoId){
  	if (photoId){
  		alertTip('请先解除相册封面');
  		return;
  	}
  	var url = 'weixinPhotoAlbumController.do?del&id='+id;
  	${symbol_dollar}.dialog.confirm("该操作会删除相册下的所有相片,确认删除", function(){
		doAjaxSubmit(url,handledel);
	}, function(){
	}).zindex();
  }
  function handledel(){
  	formobj.submit();
  }

</script>