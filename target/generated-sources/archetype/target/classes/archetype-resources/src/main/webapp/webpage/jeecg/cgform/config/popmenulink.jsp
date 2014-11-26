#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
    <script type=text/javascript src="plug-in/clipboard/ZeroClipboard.js"></script>
    <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
    <script>
        ${symbol_dollar}(function(){
            var clip = new ZeroClipboard.Client();
            clip.setHandCursor( true );
            clip.addEventListener('complete', function(client, text){
                alert("复制成功");
                frameElement.api.close();
            });
            var url = "${symbol_dollar}{url}&id=${symbol_dollar}{title}";
            clip.setText(url);
            ${symbol_dollar}("${symbol_pound}menuLink").val(url);
            clip.glue('copyBtn');
            ${symbol_dollar}("${symbol_pound}closeBtn").click(function(){
                frameElement.api.close();
            });
        });
    </script>
</head>
<body>
    <div>
        <input id='menuLink'  type='text' style="width:380px;" disabled="disabled"/>
    </div>
    <div class="ui_buttons" style="padding-left: 240px;">
        <input type="button" id="copyBtn" value="复制" class="ui_state_highlight">
        <input type="button" id="closeBtn" value="关闭" onclick='close();'>
    </div>
</body>
</html>