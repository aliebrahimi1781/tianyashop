#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 类描述：上传标签
 * 
 * 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class UploadTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String id;// ID
	protected String uploader;//url
	protected String name;//控件名称
	protected String formData;//参数名称
	protected String extend="";//上传文件的扩展名	
	protected String buttonText="浏览";//按钮文本
	protected boolean multi=true;//是否多文件
	protected String queueID="filediv";//文件容器ID
	protected boolean dialog=true;//是否是弹出窗口模式
	protected String callback;
	protected boolean auto=false;//是否自动上传
	protected String onUploadSuccess;//上传成功处理函数
	protected boolean view=false;//生成查看删除链接
	public void setView(boolean view) {
		this.view = view;
	}
	public void setOnUploadSuccess(String onUploadSuccess) {
		this.onUploadSuccess = onUploadSuccess;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}
	public void setQueueID(String queueID) {
		this.queueID = queueID;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public void setMulti(boolean multi) {
		this.multi = multi;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}
	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if("pic".equals(extend))
		{
			extend="*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif";
		}
		if(extend.equals("office"))
		{
			extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm";
		}
		sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/uploadify/css/uploadify.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
		sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/uploadify/jquery.uploadify-3.1.js${symbol_escape}"></script>");
		sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/Map.js${symbol_escape}"></script>");
		sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">"
				+"var flag = false;"
				+"var fileitem=${symbol_escape}"${symbol_escape}";"
				+"var fileKey=${symbol_escape}"${symbol_escape}";"
				+"var serverMsg=${symbol_escape}"${symbol_escape}";"
				+"var m = new Map();"
				+ "${symbol_dollar}(function(){"
				+"${symbol_dollar}(${symbol_escape}'${symbol_pound}"+id+"${symbol_escape}').uploadify({"
				+"buttonText:${symbol_escape}'"+buttonText+"${symbol_escape}',"
				+"auto:"+auto+","
				+"progressData:${symbol_escape}'speed${symbol_escape}'," 
				+"multi:"+multi+","
				+"height:25,"
				+"overrideEvents:[${symbol_escape}'onDialogClose${symbol_escape}']," 
				+"fileTypeDesc:${symbol_escape}'文件格式:${symbol_escape}'," 
				+"queueID:${symbol_escape}'"+queueID+"${symbol_escape}',"
				+"fileTypeExts:${symbol_escape}'"+extend+"${symbol_escape}',"
				+"fileSizeLimit:${symbol_escape}'15MB${symbol_escape}',"
				+"swf:${symbol_escape}'plug-in/uploadify/uploadify.swf${symbol_escape}',	"
				+"uploader:${symbol_escape}'"+getUploader()			
						+"onUploadStart : function(file) { ");	
				if (formData!=null) {
					String[] paramnames=formData.split(",");				
					for (int i = 0; i < paramnames.length; i++) {
						String paramname=paramnames[i];
						sb.append("var "+paramname+"=${symbol_dollar}(${symbol_escape}'${symbol_pound}"+paramname+"${symbol_escape}').val();");
					}				 
			        sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}"+id+"${symbol_escape}').uploadify(${symbol_escape}"settings${symbol_escape}", ${symbol_escape}"formData${symbol_escape}", {");
			        for (int i = 0; i < paramnames.length; i++) {
						String paramname=paramnames[i];
						if (i==paramnames.length-1) {
							sb.append("'"+paramname+"':"+paramname+"");	
						}else{
							sb.append("'"+paramname+"':"+paramname+",");
						}
					}
			        sb.append("});");
				}; 
		       sb.append("} ," 	          
				+"onQueueComplete : function(queueData) { ");
				if(dialog)
				{
				sb.append("var win = frameElement.api.opener;"  	  
	            +"win.reloadTable();"
	            +"win.tip(serverMsg);"
	            +"frameElement.api.close();");
				}
				else
				{
				  if(callback!=null)
				  sb.append(""+callback+"();");
				}
				if(view)
				{
				sb.append("${symbol_dollar}(${symbol_escape}"${symbol_pound}viewmsg${symbol_escape}").html(m.toString());");
				sb.append("${symbol_dollar}(${symbol_escape}"${symbol_pound}fileKey${symbol_escape}").val(fileKey);");
				}
				sb.append("},");
				//上传成功处理函数
				sb.append("onUploadSuccess : function(file, data, response) {");
				sb.append("var d=${symbol_dollar}.parseJSON(data);");
				if(view)
				{
				sb.append("var fileitem=${symbol_escape}"<span id=${symbol_escape}'${symbol_escape}"+d.attributes.id+${symbol_escape}"${symbol_escape}'><a href=${symbol_escape}'${symbol_pound}${symbol_escape}' onclick=openwindow(${symbol_escape}'文件查看${symbol_escape}',${symbol_escape}'${symbol_escape}"+d.attributes.viewhref+${symbol_escape}"${symbol_escape}',${symbol_escape}'70%${symbol_escape}',${symbol_escape}'80%${symbol_escape}') title=${symbol_escape}'查看${symbol_escape}'>${symbol_escape}"+d.attributes.name+${symbol_escape}"</a><img border=${symbol_escape}'0${symbol_escape}' onclick=confuploadify(${symbol_escape}'${symbol_escape}"+d.attributes.delurl+${symbol_escape}"${symbol_escape}',${symbol_escape}'${symbol_escape}"+d.attributes.id+${symbol_escape}"${symbol_escape}') title=${symbol_escape}'删除${symbol_escape}' src=${symbol_escape}'plug-in/uploadify/img/uploadify-cancel.png${symbol_escape}' widht=${symbol_escape}'15${symbol_escape}' height=${symbol_escape}'15${symbol_escape}'>&nbsp;&nbsp;</span>${symbol_escape}";");
				sb.append("m.put(d.attributes.id,fileitem);");
				sb.append("fileKey=d.attributes.fileKey;");
				}
				if(onUploadSuccess!=null)
				{
				sb.append(onUploadSuccess+"(d,file,response);");
				}
				sb.append("if(d.success){");
				sb.append("var win = frameElement.api.opener;");
//				sb.append("win.tip(d.msg);");
				sb.append("serverMsg = d.msg;");
				sb.append("}");
				sb.append("},");
				sb.append("onFallback : function(){tip(${symbol_escape}"您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试${symbol_escape}")},");
				sb.append("onSelectError : function(file, errorCode, errorMsg){");
				sb.append("switch(errorCode) {");
				sb.append("case -100:");
				sb.append("tip(${symbol_escape}"上传的文件数量已经超出系统限制的${symbol_escape}"+${symbol_dollar}(${symbol_escape}'${symbol_pound}"+id+"${symbol_escape}').uploadify(${symbol_escape}'settings${symbol_escape}',${symbol_escape}'queueSizeLimit${symbol_escape}')+${symbol_escape}"个文件！${symbol_escape}");");
				sb.append("break;");
				sb.append("case -110:"
				+"tip(${symbol_escape}"文件 [${symbol_escape}"+file.name+${symbol_escape}"] 大小超出系统限制的${symbol_escape}"+${symbol_dollar}(${symbol_escape}'${symbol_pound}"+id+"${symbol_escape}').uploadify(${symbol_escape}'settings${symbol_escape}',${symbol_escape}'fileSizeLimit${symbol_escape}')+${symbol_escape}"大小！${symbol_escape}");"
				+"break;"
				+"case -120:"
				+"tip(${symbol_escape}"文件 [${symbol_escape}"+file.name+${symbol_escape}"] 大小异常！${symbol_escape}");"
				+"break;"
				+"case -130:"
				+"tip(${symbol_escape}"文件 [${symbol_escape}"+file.name+${symbol_escape}"] 类型不正确！${symbol_escape}");"
				+"break;"
				+"}");
		       sb.append("},"
				+"onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) { "
				//+"tip('<span>文件上传成功:'+totalBytesUploaded/1024 + ' KB 已上传 ,总数' + totalBytesTotal/1024 + ' KB.</span>');"  	  	             
				+"}"
	   			+"});"
				+"});"
				+"function upload() {"
				+"	${symbol_dollar}(${symbol_escape}'${symbol_pound}"+id+"${symbol_escape}').uploadify('upload', '*');"
				+"		return flag;"
				+"}"
				+"function cancel() {"
				+"${symbol_dollar}(${symbol_escape}'${symbol_pound}"+id+"${symbol_escape}').uploadify('cancel', '*');"				
				+"}"				
				+"</script>");	
		       sb.append("<span id=${symbol_escape}""+id+"span${symbol_escape}"><input type=${symbol_escape}"file${symbol_escape}" name=${symbol_escape}""+name+"${symbol_escape}" id=${symbol_escape}""+id+"${symbol_escape}" /></span>");
		       if(view)
		       {
		       sb.append("<span id=${symbol_escape}"viewmsg${symbol_escape}"></span>");
		       sb.append("<input type=${symbol_escape}"hidden${symbol_escape}" name=${symbol_escape}"fileKey${symbol_escape}" id=${symbol_escape}"fileKey${symbol_escape}" />");
		       }
		        
		return sb;
	}
	
	/**
	 * 获取上传路径,修改jsessionid传不到后台的bug jueyue --- 20130916
	 * @return
	 */
	private String getUploader() {
		return uploader+"&sessionId="+pageContext.getSession().getId()+"',";
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setFormData(String formData) {
		this.formData = formData;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}

	 
	
}
