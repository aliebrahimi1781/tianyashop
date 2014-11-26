#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.core.util.StringUtil;

/**
 * 
 * @author  张代浩
 *
 */
public class FormValidationTag extends TagSupport {
	protected String formid = "formobj";// 表单FORM ID
	protected Boolean refresh = true;
	protected String callback;// 回调函数
	protected String beforeSubmit;// 提交前处理函数
	protected String btnsub = "btn_sub";// 以ID为标记触发提交事件
	protected String btnreset = "btn_reset";// 以ID为标记触发提交事件
	protected String layout = "div";// 表单布局
	protected String usePlugin;// 外调插件
	protected boolean dialog = true;// 是否是弹出窗口模式
	protected String action;// 表单提交路径
	protected String tabtitle;// 表单选项卡
	protected String tiptype = "4";//校验方式

	public void setTabtitle(String tabtitle) {
		this.tabtitle = tabtitle;
	}

	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}

	public void setBtnsub(String btnsub) {
		this.btnsub = btnsub;
	}

	public void setRefresh(Boolean refresh) {
		this.refresh = refresh;
	}

	public void setBtnreset(String btnreset) {
		this.btnreset = btnreset;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();
			if ("div".equals(layout)) {
				sb.append("<div id=${symbol_escape}"content${symbol_escape}">");
				sb.append("<div id=${symbol_escape}"wrapper${symbol_escape}">");
				sb.append("<div id=${symbol_escape}"steps${symbol_escape}">");
			}
			sb.append("<form id=${symbol_escape}"" + formid + "${symbol_escape}" action=${symbol_escape}"" + action + "${symbol_escape}" name=${symbol_escape}"" + formid + "${symbol_escape}" method=${symbol_escape}"post${symbol_escape}">");
			if ("btn_sub".equals(btnsub) && dialog)
				sb.append("<input type=${symbol_escape}"hidden${symbol_escape}" id=${symbol_escape}"" + btnsub + "${symbol_escape}" class=${symbol_escape}"" + btnsub + "${symbol_escape}"/>");
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();
			if (layout.equals("div")) {
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/Validform/css/divfrom.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"/>");
				if (tabtitle != null)
					sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/form.js${symbol_escape}"></script>");
			}
			sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/Validform/css/style.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"/>");
			sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/Validform/css/tablefrom.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"/>");
			sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/Validform_v5.3.1_min.js${symbol_escape}"></script>");
			sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/Validform_Datatype.js${symbol_escape}"></script>");
			sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/datatype.js${symbol_escape}"></script>");
			if (usePlugin != null) {
				if (usePlugin.indexOf("jqtransform") >= 0) {
					sb.append("<SCRIPT type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/plugin/jqtransform/jquery.jqtransform.js${symbol_escape}"></SCRIPT>");
					sb.append("<LINK rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/Validform/plugin/jqtransform/jqtransform.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></LINK>");
				}
				if (usePlugin.indexOf("password") >= 0) {
					sb.append("<SCRIPT type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js${symbol_escape}"></SCRIPT>");
				}
			}
			sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">");
			sb.append("${symbol_dollar}(function(){");
			sb.append("${symbol_dollar}(${symbol_escape}"${symbol_pound}" + formid + "${symbol_escape}").Validform({");
			if(this.getTiptype()!=null && !"".equals(this.getTiptype())){
				sb.append("tiptype:"+this.getTiptype()+",");
			}else{
				sb.append("tiptype:1,");
			}
//			sb.append("tiptype:function(msg,o,cssctl){");
//			sb.append("if(!o.obj.is(${symbol_escape}"form${symbol_escape}")){");
//			sb.append("	var objtip=o.obj.parent().find(${symbol_escape}".Validform_checktip${symbol_escape}");");
//			sb.append("	cssctl(objtip,o.type);");
//			sb.append("	objtip.text(msg);");
//			sb.append("	var infoObj=o.obj.parent().find(${symbol_escape}".Validform_checktip${symbol_escape}");");
//			sb.append("	if(o.type==2){");
//			sb.append("		infoObj.hide();infoObj.show();");
//			sb.append("		infoObj.fadeOut(8000);");
//			sb.append("	}else{");
//			sb.append("		infoObj.hide();");
//			sb.append("		var left=o.obj.offset().left;");
//			sb.append("		var top=o.obj.offset().top;");
//			sb.append("		infoObj.css({	");
//			sb.append("			left:left+85,");
//			sb.append("			top:top-10");
//			sb.append("		}).show().animate({");
//			sb.append("			top:top-5");
//			sb.append("		},200);infoObj.fadeOut(8000);");
//			sb.append("	}");
//			sb.append("}");
//			sb.append("},");
			sb.append("btnSubmit:${symbol_escape}"${symbol_pound}" + btnsub + "${symbol_escape}",");
			sb.append("btnReset:${symbol_escape}"${symbol_pound}" + btnreset + "${symbol_escape}",");
			sb.append("ajaxPost:true,");
			if (beforeSubmit != null) {
				sb.append("beforeSubmit:function(curform){var tag=false;");
				sb.append("return " + beforeSubmit );
				if(beforeSubmit.indexOf("(") < 0){
					sb.append("(curform);");
				}
				sb.append("},");
			}
			if (usePlugin != null) {
				StringBuffer passsb = new StringBuffer();
				if (usePlugin.indexOf("password") >= 0) {
					passsb.append("passwordstrength:{");
					passsb.append("minLen:6,");
					passsb.append("maxLen:18,");
					passsb.append("trigger:function(obj,error)");
					passsb.append("{");
					passsb.append("if(error)");
					passsb.append("{");
					passsb.append("obj.parent().next().find(${symbol_escape}".Validform_checktip${symbol_escape}").show();");
					passsb.append("obj.find(${symbol_escape}".passwordStrength${symbol_escape}").hide();");
					passsb.append("}");
					passsb.append("else");
					passsb.append("{");
					passsb.append("${symbol_dollar}(${symbol_escape}".passwordStrength${symbol_escape}").show();");
					passsb.append("obj.parent().next().find(${symbol_escape}".Validform_checktip${symbol_escape}").hide();");
					passsb.append("}");
					passsb.append("}");// trigger结尾
					passsb.append("}");// passwordstrength结尾
				}
				StringBuffer jqsb = new StringBuffer();
				if (usePlugin.indexOf("jqtransform") >= 0) {
					if (usePlugin.indexOf("password") >= 0) {
						sb.append(",");
					}
					jqsb.append("jqtransform :{selector:${symbol_escape}"select${symbol_escape}"}");
				}
				sb.append("usePlugin:{");
				if (usePlugin.indexOf("password") >= 0) {
					sb.append(passsb);
				}
				if (usePlugin.indexOf("jqtransform") >= 0) {
					sb.append(jqsb);
				}
				sb.append("},");
			}
			sb.append("callback:function(data){");
			if (dialog) {
				if(callback!=null&&callback.contains("@Override")){//复写默认callback
					sb.append(callback.replaceAll("@Override", "") + "(data);");
				}else{
					sb.append("var win = frameElement.api.opener;");
					//先判断是否成功，成功再刷新父页面，否则return false    
					// 如果不成功，返回值接受使用data.msg. 原有的data.responseText会报null 
					sb.append("if(data.success==true){frameElement.api.close();win.tip(data.msg);}else{if(data.responseText==''||data.responseText==undefined){${symbol_dollar}.messager.alert('错误', data.msg);${symbol_dollar}.Hidemsg();}else{try{var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'),data.responseText.indexOf('错误信息')); ${symbol_dollar}.messager.alert('错误',emsg);${symbol_dollar}.Hidemsg();}catch(ex){${symbol_dollar}.messager.alert('错误',data.responseText+${symbol_escape}"${symbol_escape}");${symbol_dollar}.Hidemsg();}} return false;}");
					//
					if (refresh) {
						sb.append("win.reloadTable();");
					}
					if (StringUtil.isNotEmpty(callback)) {
						sb.append("win."+ callback + "(data);");
					}
				}
				//失败tip不提示
				//sb.append("win.tip(data.msg);");
			} else {
				sb.append("" + callback + "(data);");
			}
			sb.append("}" + "});" + "});" + "</script>");
			sb.append("");
			sb.append("</form>");
			if ("div".equals(layout)) {
				sb.append("</div>");
				if (tabtitle != null) {
					String[] tabtitles = tabtitle.split(",");
					sb.append("<div id=${symbol_escape}"navigation${symbol_escape}" style=${symbol_escape}"display: none;${symbol_escape}">");
					sb.append("<ul>");
					for (String string : tabtitles) {
						sb.append("<li>");
						sb.append("<a href=${symbol_escape}"${symbol_pound}${symbol_escape}">" + string + "</a>");
						sb.append("</li>");
					}
					sb.append("</ul>");
					sb.append("</div>");
				}
				sb.append("</div></div>");
			}
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public void setUsePlugin(String usePlugin) {
		this.usePlugin = usePlugin;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void setBeforeSubmit(String beforeSubmit) {
		this.beforeSubmit = beforeSubmit;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getTiptype() {
		return tiptype;
	}

	public void setTiptype(String tiptype) {
		this.tiptype = tiptype;
	}
	
}
