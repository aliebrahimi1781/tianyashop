#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.core.util.PropertiesUtil;
import ${package}.core.util.StringUtil;

/**
 * 
 * @author  张代浩
 *
 */
public class CkeditorTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	protected String name;// 属性名称
	protected String value;// 默认值
	protected boolean isfinder;// 是否加载ckfinder(默认true)
	protected String type;// 其它属性(用法:height:400,uiColor:'${symbol_pound}9AB8F3' 用,分割)

	public boolean isIsfinder() {
		return isfinder;
	}

	public void setIsfinder(boolean isfinder) {
		this.isfinder = isfinder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

		sb.append("<textarea id=${symbol_escape}"" + name + "_text${symbol_escape}" name=${symbol_escape}"" + name + "${symbol_escape}">"
				+ value + "</textarea>");
		sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">var ckeditor_" + name
				+ "=CKEDITOR.replace(${symbol_escape}"" + name + "_text${symbol_escape}",{");
		if (isfinder) {
			PropertiesUtil util = new PropertiesUtil("sysConfig.properties");
			sb.append("filebrowserBrowseUrl:"
					+ util.readProperty("filebrowserBrowseUrl") + ",");
			sb.append("filebrowserImageBrowseUrl:"
					+ util.readProperty("filebrowserImageBrowseUrl") + ",");
			sb.append("filebrowserFlashBrowseUrl:"
					+ util.readProperty("filebrowserFlashBrowseUrl") + ",");
			sb.append("filebrowserUploadUrl:"
					+ util.readProperty("filebrowserUploadUrl") + ",");
			sb.append("filebrowserImageUploadUrl:"
					+ util.readProperty("filebrowserImageUploadUrl") + ",");
			sb.append("filebrowserFlashUploadUrl:"
					+ util.readProperty("filebrowserFlashUploadUrl") + "");
		}
		if (isfinder && StringUtil.isNotEmpty(type))
			sb.append(",");
		if (StringUtil.isNotEmpty(type))
			sb.append(type);
		sb.append("});");
		if (isfinder) {
			sb.append("CKFinder.SetupCKEditor(ckeditor_" + name + ");");
		}
		sb.append("</script>");
		return sb;
	}
}
