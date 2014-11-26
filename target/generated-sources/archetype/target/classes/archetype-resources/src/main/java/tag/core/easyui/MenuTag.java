#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.web.system.pojo.base.TSFunction;

import ${package}.core.util.ListtoMenu;


/**
 * 
 * 类描述：菜单标签
 * 
 * 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String style="easyui";//菜单样式
	protected List<TSFunction> parentFun;//一级菜单
	protected List<TSFunction> childFun;//二级菜单
	protected Map<Integer, List<TSFunction>> menuFun;//菜单Map
	
	
	public void setParentFun(List<TSFunction> parentFun) {
		this.parentFun = parentFun;
	}

	public void setChildFun(List<TSFunction> childFun) {
		this.childFun = childFun;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {	
		StringBuffer sb = new StringBuffer();
        if (style.equals("easyui")) {
            sb.append("<ul id=${symbol_escape}"nav${symbol_escape}" class=${symbol_escape}"easyui-tree tree-lines${symbol_escape}" fit=${symbol_escape}"true${symbol_escape}" border=${symbol_escape}"false${symbol_escape}">");
            sb.append(ListtoMenu.getEasyuiMultistageTree(menuFun, style));
            sb.append("</ul>");
        }
		if(style.equals("shortcut"))
//		{	sb.append("<div id=${symbol_escape}"nav${symbol_escape}" style=${symbol_escape}"display:none;${symbol_escape}" class=${symbol_escape}"easyui-accordion${symbol_escape}" fit=${symbol_escape}"true${symbol_escape}" border=${symbol_escape}"false${symbol_escape}">");
		{
            sb.append("<div id=${symbol_escape}"nav${symbol_escape}" style=${symbol_escape}"display:block;${symbol_escape}" class=${symbol_escape}"easyui-accordion${symbol_escape}" fit=${symbol_escape}"true${symbol_escape}" border=${symbol_escape}"false${symbol_escape}">");
			sb.append(ListtoMenu.getEasyuiMultistageTree(menuFun, style));
			sb.append("</div>");
		}
		if(style.equals("bootstrap"))
		{
			sb.append(ListtoMenu.getBootMenu(parentFun, childFun));
		}
		if(style.equals("json"))
		{
			sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">");
			sb.append("var _menus="+ListtoMenu.getMenu(parentFun, childFun));
			sb.append("</script>");
		}
		if(style.equals("june_bootstrap"))
		{
			sb.append(ListtoMenu.getBootstrapMenu(menuFun));
		}
		return sb;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public void setMenuFun(Map<Integer, List<TSFunction>> menuFun) {
		this.menuFun = menuFun;
	}

	

}
