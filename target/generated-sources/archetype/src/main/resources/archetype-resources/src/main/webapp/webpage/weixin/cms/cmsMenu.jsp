#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>栏目管理</title>
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
  <!--UEditor-->
  <script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
  <script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.all.min.js"></script>
    
  <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
  <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
  <script type="text/javascript" charset="utf-8" src="plug-in/ueditor/lang/zh-cn/zh-cn.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  /*jslint unparam: true, regexp: true */
  /*global window, ${symbol_dollar} */
  ${symbol_dollar}(function () {
      'use strict';
      // Change this to the location of your server-side upload handler:
      var url = 'weixinArticleController.do?upload',
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
          	${symbol_dollar}("${symbol_pound}imageHref").val(d.attributes.url);
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
      var name = "${symbol_dollar}{columnPage.name}", imageHref = "${symbol_dollar}{columnPage.imageHref}";
      if(name != ""){
          ${symbol_dollar}("${symbol_pound}imageTitle").html(name);
      }
      if(imageHref != ""){
          ${symbol_dollar}("${symbol_pound}imageShow").html('<img src="${symbol_dollar}{columnPage.imageHref}" width="100%" height="160" />');
      }
  });
  function setimageTitle(obj){
	  ${symbol_dollar}("${symbol_pound}imageTitle").html(${symbol_dollar}(obj).val());
  }
  </script>
 </head>
 <body>
 	<div class="main_bd">
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
							<i class="appmsg_thumb default" id="imageShow">栏目图片</i>
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
		 		<div class="inner">
					<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="cmsMenuController.do?save">
					<input id="id" name="id" type="hidden" value="${symbol_dollar}{columnPage.id }">
					<input type="hidden" name="accountid" value="${symbol_dollar}{columnPage.accountid}">
					<input type="hidden" name="imageName" id="imageName" value="${symbol_dollar}{columnPage.imageName}">
					<table cellpadding="0" cellspacing="1" class="formtable">
						<tr>
							<td align="right">
								<label class="Validform_label">
									栏目名称:
								</label>
							</td>
							<td class="value">
								<input class="inputxt" id="name" name="name" style="width: 300px" value="${symbol_dollar}{columnPage.name}" datatype="*" onblur="setimageTitle(this)">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">栏目名称</label>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="Validform_label">
									栏目类型:
								</label>
							</td>
							<td class="value">
								<t:dictSelect field="type" typeGroupCode="cms_menu" hasLabel="false" defaultVal="${symbol_dollar}{columnPage.type}"></t:dictSelect>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">栏目类型</label>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="Validform_label">
									上传图片:
								</label>
							</td>
							<td class="value">
								<span class="btn btn-success fileinput-button">
							        <i class="glyphicon glyphicon-plus"></i>
							        <span>浏览</span>
							        <!-- The file input field used as target for the file upload widget -->
							        <input id="fileupload" type="file" name="files[]" multiple>
							    </span>
							    <input id="imageHref" name="imageHref" type="hidden"  nullmsg="请添加图片" value="${symbol_dollar}{columnPage.imageHref}">
							    <span id="imgName"></span> 
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">图片链接</label>
							</td>
						</tr>
					</table>
					</t:formvalid>
				</div>
				<i class="arrow arrow_out" style="margin-top: 0px;"></i>
				<i class="arrow arrow_in" style="margin-top: 0px;"></i>
			</div>
		</div>
	</div>
 </body>