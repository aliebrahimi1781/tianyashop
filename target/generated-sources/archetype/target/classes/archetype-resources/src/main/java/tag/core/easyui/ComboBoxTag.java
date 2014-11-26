#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.core.common.model.json.ComboBox;


/**
 * 
 * 类描述：下拉选择框标签
 * 
 * @author:  张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class ComboBoxTag extends TagSupport {
	protected String id;// ID
	protected String text;// 显示文本
	protected String url;//远程数据
	protected String name;//控件名称
	protected Integer width;//宽度
	protected Integer listWidth;//下拉框宽度
	protected Integer listHeight;//下拉框高度
	protected boolean editable;//定义是否可以直接到文本域中键入文本
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
		ComboBox comboBox=new ComboBox();
		comboBox.setText(text);
		comboBox.setId(id);
		sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">"
				+"${symbol_dollar}(function() {"
				+"${symbol_dollar}(${symbol_escape}'${symbol_pound}"+name+"${symbol_escape}').combobox({"
				+"url:${symbol_escape}'"+url+"&id="+id+"&text="+text+"${symbol_escape}',"
				+"editable:${symbol_escape}'false${symbol_escape}',"
				+"valueField:${symbol_escape}'id${symbol_escape}',"
				+"textField:${symbol_escape}'text${symbol_escape}'," 
				+"width:${symbol_escape}'"+width+"${symbol_escape}'," 
				+"listWidth:${symbol_escape}'"+listWidth+"${symbol_escape}'," 
				+"listHeight:${symbol_escape}'"+listWidth+"${symbol_escape}'," 
				+"onChange:function(){"
				+"var val = ${symbol_dollar}(${symbol_escape}'${symbol_pound}"+name+"${symbol_escape}').combobox(${symbol_escape}'getValues${symbol_escape}');"
				+"${symbol_dollar}(${symbol_escape}'${symbol_pound}"+name+"hidden${symbol_escape}').val(val);"
				+"}"
				+"});"
				+"});"
				+"</script>");
		sb.append("<input type=${symbol_escape}"hidden${symbol_escape}" name=${symbol_escape}""+name+"${symbol_escape}" id=${symbol_escape}""+name+"hidden${symbol_escape}" > "
				+"<input class=${symbol_escape}"easyui-combobox${symbol_escape}" "
				+"multiple=${symbol_escape}"true${symbol_escape}" panelHeight=${symbol_escape}"auto${symbol_escape}" name=${symbol_escape}""+name+"name${symbol_escape}" id=${symbol_escape}""+name+"${symbol_escape}" >");
		return sb;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
