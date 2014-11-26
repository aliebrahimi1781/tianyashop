#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.util.Map;

/**
 * 
 * @author  张代浩
 *
 */
public class PagerUtil {
	private int curPageNO = 1; // 当前页
	private int rowsCount; // 记录行数
	private int pageCount; // 页数
	private String actionUrl;// 目标ACTION
	private Map<String, Object> map;// 封装查询条件
	public PagerUtil(int curPageNo, int allCount, int pageSize, Map<String, Object> map,String actionUrl) {
		this.curPageNO = curPageNo;
		this.rowsCount = allCount;
		this.map = map;
		this.actionUrl=actionUrl;
		this.pageCount = (int) Math.ceil((double) allCount / pageSize);	
	}
	// 第一页
	public int first() {
		return 1;
	}

	// 最后一页
	public int last() {
		return pageCount;
	}

	// 上一页
	public int previous() {
		return (curPageNO - 1 < 1) ? 1 : curPageNO - 1;
	}

	// 下一页
	public int next() {
		return (curPageNO + 1 > pageCount) ? pageCount : curPageNO + 1;
	}

	// 第一页
	public boolean isFirst() {
		return (curPageNO == 1) ? true : false;
	}

	// 最后一页
	public boolean isLast() {
		return (curPageNO == pageCount) ? true : false;
	}
	protected StringBuffer getStrByImage(StringBuffer sb) {
		String join = getJoin();
		String conditions = getConditions();
		sb.append("<script language='javascript'>" + "${symbol_escape}n");
		sb.append("function commonSubmit(val){" + "${symbol_escape}n");
		// 校验是否全由数字组成
		sb.append("var patrn=/^[0-9]{1,20}${symbol_dollar}/;" + "${symbol_escape}n");
		sb.append("if (!patrn.exec(val)){" + "${symbol_escape}n");
		sb.append(" alert(${symbol_escape}"请输入有效页号！${symbol_escape}");" + "${symbol_escape}n");
		sb.append(" return false ;" + "${symbol_escape}n");
		sb.append(" }else{" + "${symbol_escape}n");
		sb.append("    document.getElementById('pageGoto').href='" + actionUrl + join + "curPageNO='+val+'" + conditions + "';" + "${symbol_escape}n");
		sb.append("    return true ;" + "${symbol_escape}n");
		sb.append("} " + "${symbol_escape}n");
		sb.append(" }" + "${symbol_escape}n");
		sb.append("</script>" + "${symbol_escape}n");
		sb.append("&nbsp;<span class=pageArea id=pageArea>共<b>" + rowsCount + "</b>条&nbsp;当前第" + curPageNO + "/" + pageCount + "页&nbsp;&nbsp;&nbsp;");
		if (isFirst())
			sb.append("<a class=${symbol_escape}"pageFirstDisable${symbol_escape}"  title=${symbol_escape}"首页${symbol_escape}" onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}">&nbsp;</a><a class=${symbol_escape}"pagePreviousDisable${symbol_escape}" title=${symbol_escape}"上一页${symbol_escape}"  onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}">&nbsp;</a>");
		else {
			sb.append("<a href='" + actionUrl + join + "curPageNO=1" + conditions + "' class=pageFirst title=首页 onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}"></a>");
			sb.append("<a class=${symbol_escape}"pagePrevious${symbol_escape}" href='" + actionUrl + join + "curPageNO=" + previous() + conditions + "' title=${symbol_escape}"上一页${symbol_escape}"  onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}")${symbol_escape}">&nbsp;</a>");
		}
		if (curPageNO - pageCount == 0 || pageCount == 0 || pageCount == 1)
			sb.append("<a class=pageNextDisable  title=下一页 onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}">&nbsp;<a class=pageLastDisable title=尾页 onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}">&nbsp;</a>&nbsp;");
		else {
			sb.append("<a class=pageNext href='" + actionUrl + join + "curPageNO=" + next() + conditions + "' title=下一页 onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}")${symbol_escape}">&nbsp;</a>");
			sb.append("<a class=pageLast href='" + actionUrl + join + "curPageNO=" + pageCount + conditions + "' title=尾页 onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}" )${symbol_escape}">&nbsp;</a>");
		}

		if (pageCount == 1 || pageCount == 0) {
			sb.append(" &nbsp;转到:<input class=${symbol_escape}"SmallInput${symbol_escape}" type=text style=TEXT-ALIGN: center maxLength=4 name=${symbol_escape}"pageroffsetll${symbol_escape}" size=2 onKeyPress=${symbol_escape}"if (event.keyCode == 13) return commonSubmit(document.all.pageroffsetll.value)${symbol_escape}" > 页&nbsp;");
			sb.append("<A class=pageGoto id=pageGoto title=转到 onclick='return commonSubmit()'>&nbsp;</A>&nbsp;&nbsp;</span>");
		} else {
			sb.append(" &nbsp;转到:<input class=SmallInput type=text style=TEXT-ALIGN: center maxLength=4 name=${symbol_escape}"pageroffsetll${symbol_escape}" size=2 onKeyPress=${symbol_escape}"if (event.keyCode == 13) return commonSubmit(document.all.pageroffsetll.value)${symbol_escape}" > 页&nbsp;");
			sb.append("<A  class=pageGoto id=pageGoto title=转到 onclick='commonSubmit(document.all.pageroffsetll.value)'>&nbsp;</A>&nbsp;</span");
		}
		return sb;
	}

	protected StringBuffer getStr(StringBuffer sb) {
		String join = getJoin();
		String conditions = getConditions();

		String str = "";
		str += "";
		if (isFirst())
			sb.append("第" + curPageNO + "页&nbsp;共" + pageCount + "页&nbsp;首页 ");
		else {
			sb.append("第" + curPageNO + "页&nbsp;共" + pageCount + "页&nbsp;<a href='" + actionUrl + join + "curPageNO=1" + conditions + "'>首页</a>&nbsp;");
			sb.append("<a href='" + actionUrl + join + "curPageNO=" + previous() + conditions + "' onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}" alt=${symbol_escape}"上一页${symbol_escape}">上一页</a>&nbsp;");
		}
		if (isLast() || rowsCount == 0)
			sb.append("尾页&nbsp;");
		else {
			sb.append("<a href='" + actionUrl + join + "curPageNO=" + next() + conditions + "' onMouseMove=${symbol_escape}"style.cursor='hand'${symbol_escape}" >下一页</a>&nbsp;");
			sb.append("<a href='" + actionUrl + join + "curPageNO=" + pageCount + conditions + "'>尾页</a>&nbsp;");
		}
		sb.append("&nbsp;共" + rowsCount + "条记录&nbsp;");

		str += "&nbsp;转到<select name='page' onChange=${symbol_escape}"location='" + actionUrl + join + "curPageNO='+this.options[this.selectedIndex].value${symbol_escape}">";
		int begin = (curPageNO > 10) ? curPageNO - 10 : 1;
		int end = (pageCount - curPageNO > 10) ? curPageNO + 10 : pageCount;
		for (int i = begin; i <= end; i++) {
			if (i == curPageNO)
				str += "<option value='" + i + "' selected>第" + i + "页</option>";
			else
				str += "<option value='" + i + "'>第" + i + "页</option>";
		}
		str += "</select>";
		sb.append(str);
		return sb;
	}
/**
  * <b>Summary: </b>
  *     getConditions(拼接组合查询条件)
  * @return
 */
	protected String getConditions() {
		String conditions = "";
		if (map != null) {

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				conditions += "&" + entry.getKey() + "=" + entry.getValue();
			}
		}
		return conditions;

	}
/**
 * 
  * <b>Summary: </b>
  *     getJoin(判断连接符)
  * @return
 */
	protected String getJoin() {
		String join = "";
		if (actionUrl.indexOf("?") == -1) {
			join = "?";
		} else {
			join = "&";
		}
		return join;

	}

}
