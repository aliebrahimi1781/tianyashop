#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.core.util.oConvertUtils;
import ${package}.tag.vo.easyui.Tab;


/**
 * 
 * 类描述：选项卡标签
 * 
 * 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class TabsTag extends TagSupport {
	private String id;// 容器ID
	private String width;// 宽度
	private String heigth;// 高度
	private boolean plain;// 简洁模式
	private boolean fit = true;// 铺满浏览器
	private boolean border;// 边框
	private String scrollIncrement;// 滚动增量
	private String scrollDuration;// 滚动时间
	private boolean tools;// 工具栏
	private boolean tabs = true;// 是否创建父容器
	private boolean iframe = true;// 是否是iframe方式
	private String tabPosition = "top";// 选项卡位置

	public void setIframe(boolean iframe) {
		this.iframe = iframe;
	}

	public void setTabs(boolean tabs) {
		this.tabs = tabs;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeigth(String heigth) {
		this.heigth = heigth;
	}

	public void setPlain(boolean plain) {
		this.plain = plain;
	}

	public void setFit(boolean fit) {
		this.fit = fit;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public void setScrollIncrement(String scrollIncrement) {
		this.scrollIncrement = scrollIncrement;
	}

	public void setScrollDuration(String scrollDuration) {
		this.scrollDuration = scrollDuration;
	}

	public void setTools(boolean tools) {
		this.tools = tools;
	}

	public void setTabPosition(String tabPosition) {
		this.tabPosition = tabPosition;
	}

	public void setTabList(List<Tab> tabList) {
		this.tabList = tabList;
	}

	private List<Tab> tabList = new ArrayList<Tab>();

	public int doStartTag() throws JspTagException {
		tabList.clear();
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
		if (iframe) {
			sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">");
			sb.append("${symbol_dollar}(function(){");
			if (tabList.size() > 0) {
				for (Tab tab : tabList) {
					sb.append("add" + id + "(${symbol_escape}'" + tab.getTitle() + "${symbol_escape}',${symbol_escape}'" + tab.getHref() + "${symbol_escape}',${symbol_escape}'" + tab.getId() + "${symbol_escape}',${symbol_escape}'" + tab.getIcon() + "${symbol_escape}',${symbol_escape}'" + tab.isClosable() + "${symbol_escape}');");
				}
			}
			sb.append("function add" + id + "(title,url,id,icon,closable) {");
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + id + "${symbol_escape}').tabs(${symbol_escape}'add${symbol_escape}',{");
			sb.append("id:id,");
			sb.append("title:title,");
			if (iframe) {
				sb.append("content:createFrame" + id + "(id),");
			} else {
				sb.append("href:url,");
			}
			sb.append("closable:closable=(closable =='false')?false : true,");
			sb.append("icon:icon");
			sb.append("});");
			sb.append("}");
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + id + "${symbol_escape}').tabs(");
			sb.append("{");
			sb.append("onSelect : function(title) {");
			sb.append("var p = ${symbol_dollar}(this).tabs(${symbol_escape}'getTab${symbol_escape}', title);");
			if (tabList.size() > 0) {
				for (Tab tab : tabList) {
					sb.append("if (title == ${symbol_escape}'" + tab.getTitle() + "${symbol_escape}') {");
					sb.append("p.find(${symbol_escape}'iframe${symbol_escape}').attr(${symbol_escape}'src${symbol_escape}',");
					sb.append("${symbol_escape}'" + tab.getHref() + "${symbol_escape}');}");
				}
			}
			sb.append("}");
			sb.append("});");

			sb.append("function createFrame" + id + "(id)");
			sb.append("{");
			sb.append("var s = ${symbol_escape}'<iframe id=${symbol_escape}"${symbol_escape}'+id+${symbol_escape}'${symbol_escape}" scrolling=${symbol_escape}"no${symbol_escape}" frameborder=${symbol_escape}"0${symbol_escape}"  src=${symbol_escape}"about:jeecg${symbol_escape}" width=${symbol_escape}""+oConvertUtils.getString(width, "100%")+"${symbol_escape}" height=${symbol_escape}""+oConvertUtils.getString(heigth, "99.5%")+"${symbol_escape}"></iframe>${symbol_escape}';");
			sb.append("return s;");
			sb.append("}");
			sb.append("});");
			sb.append("</script>");
		}
		if (tabs) {
				//增加width属性，fit属性之前写死，改为由页面设定，不填默认true
			sb.append("<div id=${symbol_escape}"" + id + "${symbol_escape}" tabPosition=${symbol_escape}"" + tabPosition + "${symbol_escape}" border=flase style=${symbol_escape}"margin:0px;padding:0px;overflow:hidden;width:"+oConvertUtils.getString(width, "auto")+";${symbol_escape}" class=${symbol_escape}"easyui-tabs${symbol_escape}" fit=${symbol_escape}""+fit+"${symbol_escape}">");
			if (!iframe) {
				for (Tab tab : tabList) {
					if (tab.getHref() != null) {
						sb.append("<div title=${symbol_escape}"" + tab.getTitle() + "${symbol_escape}" href=${symbol_escape}"" + tab.getHref() + "${symbol_escape}" style=${symbol_escape}"margin:0px;padding:0px;overflow:hidden;${symbol_escape}"></div>");
					} else {
						sb.append("<div title=${symbol_escape}"" + tab.getTitle() + "${symbol_escape}"  style=${symbol_escape}"margin:0px;padding:0px;overflow:hidden;${symbol_escape}">");
						sb.append("<iframe id=${symbol_escape}"${symbol_escape}'"+tab.getId()+"${symbol_escape}'${symbol_escape}" scrolling=${symbol_escape}"no${symbol_escape}" frameborder=${symbol_escape}"0${symbol_escape}"  src=${symbol_escape}""+tab.getIframe()+"${symbol_escape}" width=${symbol_escape}""+oConvertUtils.getString(tab.getWidth(), "100%")+"${symbol_escape}" height=${symbol_escape}""+oConvertUtils.getString(tab.getHeigth(), "99.5%")+"${symbol_escape}"></iframe>${symbol_escape}';");
						sb.append("</div>");
					}

				}
			}
			sb.append("</div>");
			
		}
		return sb;
	}

	public void setTab(String id, String title,String iframe, String href, String iconCls, boolean cache, String content, String width, String heigth, boolean closable) {
		Tab tab = new Tab();
		tab.setId(id);
		tab.setTitle(title);
		tab.setHref(href);
		tab.setCache(cache);
		tab.setIframe(iframe);
		tab.setContent(content);
		tab.setHeigth(heigth);
		tab.setIcon(iconCls);
		tab.setWidth(width);
		tab.setClosable(closable);
		tabList.add(tab);
	}

}
