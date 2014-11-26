#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>微站点模板</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link type="text/css" rel="stylesheet" href="plug-in/weixin/css/appmsg_edit.css" />
  <link type="text/css" rel="stylesheet" href="plug-in/weixin/css/jquery.fileupload.css" />
  <link type="text/css" rel="stylesheet" href="plug-in/bootstrap/css/bootstrap.min.css" />
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  
  <!--fileupload-->
  <script type="text/javascript" src="plug-in/weixin/js/vendor/jquery.ui.widget.js"></script>
  <script type="text/javascript" src="plug-in/weixin/js/load-image.min.js"></script>
  <script type="text/javascript" src="plug-in/weixin/js/jquery.fileupload.js"></script>
  <script type="text/javascript" src="plug-in/weixin/js/jquery.fileupload-process.js"></script>
  <script type="text/javascript" src="plug-in/weixin/js/jquery.fileupload-image.js"></script>
  <script type="text/javascript" src="plug-in/weixin/js/jquery.iframe-transport.js"></script>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
   ${symbol_dollar}(function () {
      'use strict';
      // Change this to the location of your server-side upload handler:
      var url = 'weixinCmsSiteController.do?upload',
          uploadButton = ${symbol_dollar}('<button/>')
              .addClass('btn btn-primary')
              .prop('disabled', true)
              .text('上传中...')
              .on('click', function () {
                  var ${symbol_dollar}this = ${symbol_dollar}(this), data = ${symbol_dollar}this.data();
                  ${symbol_dollar}this.off('click').text('正在上传...').on('click', function () {
                          ${symbol_dollar}this.remove();
                          data.abort();
                  });
                  data.submit().always(function () {
                      ${symbol_dollar}this.remove();
                  });
              });
      ${symbol_dollar}('${symbol_pound}fileupload').fileupload({
          url: url,
          dataType: 'json',
          autoUpload: false,
          acceptFileTypes: /(${symbol_escape}.|${symbol_escape}/)(gif|jpe?g|png)${symbol_dollar}/i,
          maxFileSize: 2000000, // 2 MB
          disableImageResize: /Android(?!.*Chrome)|Opera/
              .test(window.navigator.userAgent),
          previewMaxWidth: 290,
          previewMaxHeight: 160,
          previewCrop: true
      }).on('fileuploadadd', function (e, data) {
          ${symbol_dollar}("${symbol_pound}files").text("");
          data.context = ${symbol_dollar}('<div/>').appendTo('${symbol_pound}files');
          ${symbol_dollar}.each(data.files, function (index, file) {
              //var node = ${symbol_dollar}('<p/>').append(${symbol_dollar}('<span/>').text(file.name));
              //fileupload
              var node = ${symbol_dollar}('<p/>');
              if (!index) {
                  node.append('<br>').append(uploadButton.clone(true).data(data));
              }
              node.appendTo(data.context);
          });
      }).on('fileuploadprocessalways', function (e, data) {
          var index = data.index,
              file = data.files[index],
              node = ${symbol_dollar}(data.context.children()[index]);
          if (file.preview) {
              node.prepend('<br>').prepend(file.preview);
          }
          if (file.error) {
              node
                  .append('<br>')
                  .append(${symbol_dollar}('<span class="text-danger"/>').text(file.error));
          }
          if (index + 1 === data.files.length) {
              data.context.find('button')
                  .text('上传')
                  .prop('disabled', !!data.files.error);
          }
      }).on('fileuploadprogressall', function (e, data) {
          var progress = parseInt(data.loaded / data.total * 100, 10);
          ${symbol_dollar}('${symbol_pound}progress .progress-bar').css(
              'width',
              progress + '%'
          );
      }).on('fileuploaddone', function (e, data) {
          console.info(data);
          var  file = data.files[0];
          //var delUrl = "<a class=${symbol_escape}"js_removeCover${symbol_escape}" onclick=${symbol_escape}"return false;${symbol_escape}" href=${symbol_escape}"javascript:void(0);${symbol_escape}">删除</a>";
          ${symbol_dollar}("${symbol_pound}imgName").text("").append(file.name);
          ${symbol_dollar}("${symbol_pound}imageName").val(file.name);
          ${symbol_dollar}("${symbol_pound}progress").hide();
  		var d =data.result;
  		if (d.success) {
  			var link = ${symbol_dollar}('<a>').attr('target', '_blank').prop('href', d.attributes.viewhref);
          	${symbol_dollar}(data.context.children()[0]).wrap(link);
          	console.info(d.attributes.viewhref);
          	${symbol_dollar}("${symbol_pound}reviewImgUrl").val(d.attributes.url);
  		}else{
  			var error = ${symbol_dollar}('<span class="text-danger"/>').text(d.msg);
              ${symbol_dollar}(data.context.children()[0]).append('<br>').append(error);
  		}
      }).on('fileuploadfail', function (e, data) {
          ${symbol_dollar}.each(data.files, function (index, file) {
              var error = ${symbol_dollar}('<span class="text-danger"/>').text('File upload failed.');
              ${symbol_dollar}(data.context.children()[index])
                  .append('<br>')
                  .append(error);
          });
      }).prop('disabled', !${symbol_dollar}.support.fileInput)
          .parent().addClass(${symbol_dollar}.support.fileInput ? undefined : 'disabled');
      
      //编辑时初始化图片预览
      var name = "${symbol_dollar}{weixinCmsStylePage.templateName}", imageHref = "${symbol_dollar}{weixinCmsStylePage.reviewImgUrl}";
      if(name != ""){
          ${symbol_dollar}("${symbol_pound}imageTitle").html(name);
      }
      if(imageHref != ""){
          ${symbol_dollar}("${symbol_pound}imageShow").html('<img src="${symbol_dollar}{weixinCmsStylePage.reviewImgUrl}" width="100%" height="160" />');
      }
  });
  function setimageTitle(obj){
	  ${symbol_dollar}("${symbol_pound}imageTitle").html(${symbol_dollar}(obj).val());
  }
  </script>
  <script type="text/javascript">
  ${symbol_dollar}(function(){
    //查看模式情况下,删除和上传附件功能禁止使用
	if(location.href.indexOf("load=detail")!=-1){
		${symbol_dollar}(".jeecgDetail").hide();
	}
   });

  	function uploadFile(data){
  		${symbol_dollar}("${symbol_pound}id").val(data.obj.id);
  			upload();
  	}
  	
  	function close(){
  		frameElement.api.close();
  	}
  </script>
 </head>
 <body>
 <div class="media_preview_area">
 <div class="appmsg editing">
				<div class="appmsg_content" id="js_appmsg_preview">
						<h4 class="appmsg_title">
							<a target="_blank" href="javascript:void(0);" onclick="return false;" id="imageTitle">标题</a>
						</h4>
						<div class="appmsg_info">
							<em class="appmsg_date"></em>
						</div>
						<div id="files" class="files">
							<i class="appmsg_thumb default" id="imageShow">预览图</i>
						</div>
						 <div id="progress" class="progress">
					        <div class="progress-bar progress-bar-success"></div>
					    </div>
						<p class="appmsg_desc"></p>
				</div>
		</div>
</div>
<div class="media_edit_area" id="js_appmsg_editor">	
			<div class="appmsg_editor" style="margin-top: 0px;">
		 		<div class="inner" style="width:90%">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" callback="@Override uploadFile" layout="table" action="weixinCmsStyleController.do?doAdd" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${symbol_dollar}{weixinCmsStylePage.id }">
					<input id="createName" name="createName" type="hidden" value="${symbol_dollar}{weixinCmsStylePage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${symbol_dollar}{weixinCmsStylePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${symbol_dollar}{weixinCmsStylePage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${symbol_dollar}{weixinCmsStylePage.updateDate }">
					<input id="accountid" name="accountid" type="hidden" value="${symbol_dollar}{weixinCmsStylePage.accountid }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							模板名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="templateName" name="templateName" type="text" style="width: 300px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							预览图:
						</label>
					</td>
					<td class="value">
							<span class="btn btn-success fileinput-button">
							        <i class="glyphicon glyphicon-plus"></i>
							        <span>浏览</span>
							        <!-- The file input field used as target for the file upload widget -->
							        <input id="fileupload" type="file" name="files[]" multiple>
							</span>
					     	<input id="reviewImgUrl" name="reviewImgUrl" type="hidden" style="width: 150px" class="inputxt">
							<span id="imgName"></span> 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">预览图</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							模板路径:
						</label>
					</td>
					<td class="value">
					     	 <input id="templateUrl" name="templateUrl" type="text" value="${symbol_dollar}{weixinCmsStylePage.templateUrl}"  style="width: 300px" class="inputxt" >
					     	 <br>参考风格：car/baoma/
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板路径</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							模板压缩文件:
						</label>
					</td>
					<td class="value">
					     	<t:upload name="fiels" buttonText="上传文件" uploader="weixinCmsStyleController.do?upload" extend="*.zip" id="file_upload" formData="id"></t:upload>
						<div class="form" id="filediv" style="height: 50px"></div>
					
					</td>
				</tr>
			</table>
		</t:formvalid>
			</div>
		</div>
	</div>
 </body>
  <script src = "webpage/weixin/cms/style/weixinCmsStyle.js"></script>		