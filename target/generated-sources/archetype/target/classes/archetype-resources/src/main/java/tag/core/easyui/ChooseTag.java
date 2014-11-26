#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.core.util.StringUtil;
import ${package}.core.util.UUIDGenerator;


/**
 * 
 * 类描述：选择器标签
 * 
 * @author:  张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class ChooseTag extends TagSupport {
	protected String hiddenName;
	protected String textname;//显示文本框字段
	protected String icon;
	protected String title;
	protected String url;
	protected String top;
	protected String left;
	protected String width;
	protected String height;
	protected String name;
	protected String hiddenid;// 隐藏框取值ID
	protected Boolean isclear = false;
	protected String fun;//自定义函数
	protected String inputTextname;

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
		String methodname = UUIDGenerator.generate().replaceAll("-", "");
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=${symbol_escape}"${symbol_pound}${symbol_escape}" class=${symbol_escape}"easyui-linkbutton${symbol_escape}" plain=${symbol_escape}"true${symbol_escape}" icon=${symbol_escape}"" + icon + "${symbol_escape}" onClick=${symbol_escape}"choose_"+methodname+"()${symbol_escape}">选择</a>");
		if (isclear&&StringUtil.isNotEmpty(textname)) {
			sb.append("<a href=${symbol_escape}"${symbol_pound}${symbol_escape}" class=${symbol_escape}"easyui-linkbutton${symbol_escape}" plain=${symbol_escape}"true${symbol_escape}" icon=${symbol_escape}"icon-redo${symbol_escape}" onClick=${symbol_escape}"clearAll_"+methodname+"();${symbol_escape}">清空</a>");
		}
		sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">");
		sb.append("var windowapi = frameElement.api, W = windowapi.opener;");
		sb.append("function choose_"+methodname+"(){");
		sb.append("if(typeof(windowapi) == 'undefined'){");
			sb.append("${symbol_dollar}.dialog({");
			sb.append("content: ${symbol_escape}'url:"+url+"${symbol_escape}',");
			sb.append("zIndex: 2100,");
			if (title != null) {
				sb.append("title: ${symbol_escape}'" + title + "${symbol_escape}',");
			}
			sb.append("lock : true,");
			if (width != null) {
				sb.append("width :${symbol_escape}'" + width + "${symbol_escape}',");
			} else {
				sb.append("width :400,");
			}
			if (height != null) {
				sb.append("height :${symbol_escape}'" + height + "${symbol_escape}',");
			} else {
				sb.append("height :350,");
			}
			if (left != null) {
				sb.append("left :${symbol_escape}'" + left + "${symbol_escape}',");
			} else {
				sb.append("left :'85%',");
			}
			if (top != null) {
				sb.append("top :${symbol_escape}'" + top + "${symbol_escape}',");
			} else {
				sb.append("top :'65%',");
			}
			sb.append("opacity : 0.4,");
			sb.append("button : [ {");
			sb.append("name : ${symbol_escape}'确认${symbol_escape}',");
			sb.append("callback : clickcallback_"+methodname+",");
			sb.append("focus : true");
			sb.append("}, {");
			sb.append("name : ${symbol_escape}'取消${symbol_escape}',");
			sb.append("callback : function() {");
			sb.append("}");
			sb.append("} ]");
			sb.append("});");
		sb.append("}else{");
			sb.append("${symbol_dollar}.dialog({");
			sb.append("content: ${symbol_escape}'url:"+url+"${symbol_escape}',");
			sb.append("zIndex: 2100,");
			if (title != null) {
				sb.append("title: ${symbol_escape}'" + title + "${symbol_escape}',");
			}
			sb.append("lock : true,");
			sb.append("parent:windowapi,");
			if (width != null) {
				sb.append("width :${symbol_escape}'" + width + "${symbol_escape}',");
			} else {
				sb.append("width :400,");
			}
			if (height != null) {
				sb.append("height :${symbol_escape}'" + height + "${symbol_escape}',");
			} else {
				sb.append("height :350,");
			}
			if (left != null) {
				sb.append("left :${symbol_escape}'" + left + "${symbol_escape}',");
			} else {
				sb.append("left :'85%',");
			}
			if (top != null) {
				sb.append("top :${symbol_escape}'" + top + "${symbol_escape}',");
			} else {
				sb.append("top :'65%',");
			}
			sb.append("opacity : 0.4,");
			sb.append("button : [ {");
			sb.append("name : ${symbol_escape}'确认${symbol_escape}',");
			sb.append("callback : clickcallback_"+methodname+",");
			sb.append("focus : true");
			sb.append("}, {");
			sb.append("name : ${symbol_escape}'取消${symbol_escape}',");
			sb.append("callback : function() {");
			sb.append("}");
			sb.append("} ]");
			sb.append("});");
			sb.append("}");
		sb.append("}");
		clearAll(sb,methodname);
		callback(sb,methodname);
		sb.append("</script>");
		return sb;
	}
	/**
	 * 清除
	 * @param sb
	 */
	private void clearAll(StringBuffer sb,String methodname) {
		String[] textnames=null;
		String[] inputTextnames=null;
		textnames = textname.split(",");
		if(StringUtil.isNotEmpty(inputTextname)){
			inputTextnames = inputTextname.split(",");
		}else{
			inputTextnames = textnames;
		}
		if (isclear&&StringUtil.isNotEmpty(textname)) {
			sb.append("function clearAll_"+methodname+"(){");
			for (int i = 0; i < textnames.length; i++) {
				inputTextnames[i]=inputTextnames[i].replaceAll("${symbol_escape}${symbol_escape}[", "${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}[").replaceAll("${symbol_escape}${symbol_escape}]", "${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}]").replaceAll("${symbol_escape}${symbol_escape}.", "${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}.");
				sb.append("if(${symbol_dollar}(${symbol_escape}'${symbol_pound}" + inputTextnames[i] + "${symbol_escape}').length>=1){");
				sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + inputTextnames[i] + "${symbol_escape}').val('');");
				sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + inputTextnames[i] + "${symbol_escape}').blur();");
				sb.append("}");
				sb.append("if(${symbol_dollar}(${symbol_escape}"input[name='" + inputTextnames[i] + "']${symbol_escape}").length>=1){");
				sb.append("${symbol_dollar}(${symbol_escape}"input[name='" + inputTextnames[i] + "']${symbol_escape}").val('');");
				sb.append("${symbol_dollar}(${symbol_escape}"input[name='" + inputTextnames[i] + "']${symbol_escape}").blur();");
				sb.append("}");
			}
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + hiddenName + "${symbol_escape}').val(${symbol_escape}"${symbol_escape}");");
			sb.append("}");
			}
	}
	/**
	 * 点击确定回填
	 * @param sb
	 */
	private void callback(StringBuffer sb,String methodname) {
		sb.append("function clickcallback_"+methodname+"(){");
		sb.append("iframe = this.iframe.contentWindow;");
		String[] textnames=null;
		String[] inputTextnames=null;
		if(StringUtil.isNotEmpty(textname))
		{
		textnames = textname.split(",");
		if(StringUtil.isNotEmpty(inputTextname)){
			inputTextnames = inputTextname.split(",");
		}else{
			inputTextnames = textnames;
		}
		for (int i = 0; i < textnames.length; i++) {
			inputTextnames[i]=inputTextnames[i].replaceAll("${symbol_escape}${symbol_escape}[", "${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}[").replaceAll("${symbol_escape}${symbol_escape}]", "${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}]").replaceAll("${symbol_escape}${symbol_escape}.", "${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}.");
			sb.append("var " + textnames[i] + "=iframe.get" + name + "Selections(${symbol_escape}'" + textnames[i] + "${symbol_escape}');	");
			sb.append("if(${symbol_dollar}(${symbol_escape}'${symbol_pound}" + inputTextnames[i] + "${symbol_escape}').length>=1){");
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + inputTextnames[i] + "${symbol_escape}').val(" + textnames[i] + ");");
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + inputTextnames[i] + "${symbol_escape}').blur();");
			sb.append("}");
			sb.append("if(${symbol_dollar}(${symbol_escape}"input[name='" + inputTextnames[i] + "']${symbol_escape}").length>=1){");
			sb.append("${symbol_dollar}(${symbol_escape}"input[name='" + inputTextnames[i] + "']${symbol_escape}").val(" + textnames[i] + ");");
			sb.append("${symbol_dollar}(${symbol_escape}"input[name='" + inputTextnames[i] + "']${symbol_escape}").blur();");
			sb.append("}");
		}
		}
		if(StringUtil.isNotEmpty(hiddenName)){
			sb.append("var id =iframe.get" + name + "Selections(${symbol_escape}'" + hiddenid + "${symbol_escape}');");
			sb.append("if (id!== undefined &&id!=${symbol_escape}"${symbol_escape}"){");
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + hiddenName + "${symbol_escape}').val(id);");
			sb.append("}");
		}
		if(StringUtil.isNotEmpty(fun))
		{
		sb.append(""+fun+"();");//执行自定义函数
		}
		sb.append("}");
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setTextname(String textname) {
		this.textname = textname;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setIsclear(Boolean isclear) {
		this.isclear = isclear;
	}

	public void setHiddenid(String hiddenid) {
		this.hiddenid = hiddenid;
	}
	public void setFun(String fun) {
		this.fun = fun;
	}

	public String getInputTextname() {
		return inputTextname;
	}

	public void setInputTextname(String inputTextname) {
		this.inputTextname = inputTextname;
	}

}
