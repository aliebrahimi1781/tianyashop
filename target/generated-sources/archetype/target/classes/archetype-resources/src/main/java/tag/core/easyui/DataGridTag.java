#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.web.system.pojo.base.TSType;
import ${package}.web.system.pojo.base.TSTypegroup;
import ${package}.web.system.service.SystemService;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import ${package}.core.constant.Globals;
import ${package}.core.util.ApplicationContextUtil;
import ${package}.core.util.DataUtils;
import ${package}.core.util.ResourceUtil;
import ${package}.core.util.StringUtil;
import ${package}.core.util.oConvertUtils;
import ${package}.tag.vo.easyui.ColumnValue;
import ${package}.tag.vo.easyui.DataGridColumn;
import ${package}.tag.vo.easyui.DataGridUrl;
import ${package}.tag.vo.easyui.OptTypeDirection;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 
 * 类描述：DATAGRID标签处理类
 * 
 @author 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
@SuppressWarnings({"serial","rawtypes","unchecked","static-access"})
public class DataGridTag extends TagSupport {
	protected String fields = "";// 显示字段
	protected String searchFields = "";// 查询字段  Author:qiulu  Date:20130618 for：添加对区间查询的支持
	protected String name;// 表格标示
	protected String title;// 表格标示
	protected String idField="id";// 主键字段
	protected boolean treegrid = false;// 是否是树形列表
	protected List<DataGridUrl> urlList = new ArrayList<DataGridUrl>();// 列表操作显示
	protected List<DataGridUrl> toolBarList = new ArrayList<DataGridUrl>();// 工具条列表
	protected List<DataGridColumn> columnList = new ArrayList<DataGridColumn>();// 列表操作显示
	protected List<ColumnValue> columnValueList = new ArrayList<ColumnValue>();// 值替换集合
	protected List<ColumnValue> columnStyleList = new ArrayList<ColumnValue>();// 颜色替换集合
	public Map<String, Object> map;// 封装查询条件
	private String actionUrl;// 分页提交路径
	public int allCount;
	public int curPageNo;
	public int pageSize = 10;
	public boolean pagination = true;// 是否显示分页
	private String width;
	private String height;
	private boolean checkbox = false;// 是否显示复选框
	private boolean showPageList = true;// 定义是否显示页面列表
	private boolean openFirstNode = false;//是不是展开第一个节点
	private boolean fit = true;// 是否允许表格自动缩放，以适应父容器
	private boolean fitColumns = true;// 当为true时，自动展开/合同列的大小，以适应的宽度，防止横向滚动.
	private String sortName;//定义的列进行排序
	private String sortOrder = "asc";//定义列的排序顺序，只能是"递增"或"降序".
	private boolean showRefresh = true;// 定义是否显示刷新按钮
	private boolean showText = true;// 定义是否显示刷新按钮
	private String style = "easyui";// 列表样式easyui,datatables
	private String onLoadSuccess;// 数据加载完成调用方法
	private String onClick;// 单击事件调用方法
	private String onDblClick;// 双击事件调用方法
	private String queryMode = "single";//查询模式
	private String entityName;//对应的实体对象
	private String rowStyler;//rowStyler函数
	private String extendParams;//扩展参数,easyui有的,但是jeecg没有的参数进行扩展
	private boolean autoLoadData=true; // 列表是否自动加载数据
	//private boolean frozenColumn=false; // 是否是冰冻列    默认不是
	//json转换中的系统保留字
	protected static Map<String,String> syscode = new HashMap<String,String>();
	static{
		syscode.put("class", "clazz");
	}
	
	@Autowired
	private static SystemService systemService;
	
	public void setOnLoadSuccess(String onLoadSuccess) {
		this.onLoadSuccess = onLoadSuccess;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTreegrid(boolean treegrid) {
		this.treegrid = treegrid;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFit(boolean fit) {
		this.fit = fit;
	}

	public void setShowPageList(boolean showPageList) {
		this.showPageList = showPageList;
	}

	public void setShowRefresh(boolean showRefresh) {
		this.showRefresh = showRefresh;
	}
	/**
	 * 设置询问操作URL
	 */
	public void setConfUrl(String url, String title, String message, String exp,String operationCode) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setType(OptTypeDirection.Confirm);
		dataGridUrl.setMessage(message);
		dataGridUrl.setExp(exp);
		installOperationCode(dataGridUrl, operationCode,urlList);
	}

	/**
	 * 设置删除操作URL
	 */
	public void setDelUrl(String url, String title, String message, String exp, String funname,String operationCode) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setType(OptTypeDirection.Del);
		dataGridUrl.setMessage(message);
		dataGridUrl.setExp(exp);
		dataGridUrl.setFunname(funname);
		installOperationCode(dataGridUrl, operationCode,urlList);
	}
	/**
	 * 设置默认操作URL
	 */
	public void setDefUrl(String url, String title, String exp,String operationCode) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setType(OptTypeDirection.Deff);
		dataGridUrl.setExp(exp);
		installOperationCode(dataGridUrl, operationCode,urlList);
		
	}
	/**
	 * 设置工具条
	 * @param height2 
	 * @param width2 
	 */
	public void setToolbar(String url, String title, String icon, String exp,String onclick, String funname,String operationCode, String width2, String height2) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setType(OptTypeDirection.ToolBar);
		dataGridUrl.setIcon(icon);
		dataGridUrl.setOnclick(onclick);
		dataGridUrl.setExp(exp);
		dataGridUrl.setFunname(funname);
		dataGridUrl.setWidth(String.valueOf(width2));
		dataGridUrl.setHeight(String.valueOf(height2));
		installOperationCode(dataGridUrl, operationCode,toolBarList);
		
	}

	/**
	 * 设置自定义函数操作URL
	 */
	public void setFunUrl(String title, String exp, String funname,String operationCode) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setType(OptTypeDirection.Fun);
		dataGridUrl.setExp(exp);
		dataGridUrl.setFunname(funname);
		installOperationCode(dataGridUrl, operationCode,urlList);
		
	}

	/**
	 * 设置自定义函数操作URL
	 */
	public void setOpenUrl(String url, String title, String width, String height, String exp,String operationCode, String openModel) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setWidth(width);
		dataGridUrl.setHeight(height);
		dataGridUrl.setType(OptTypeDirection.valueOf(openModel));
		dataGridUrl.setExp(exp);
		installOperationCode(dataGridUrl, operationCode,urlList);
		
	}

	/**
	 * 
	 * <b>Summary: </b> setColumn(设置字段)
	 * 
	 * @param name
	 * @param text
	 * @param value
	 */
	public void setColumn(String title, String field, Integer width, String rowspan, 
			String colspan, String align, boolean sortable, boolean checkbox, 
			String formatter, boolean hidden, String replace, 
			String treefield, boolean image,String imageSize, 
			boolean query, String url, String funname, 
			String arg,String queryMode, String dictionary,
			boolean frozenColumn,String extend,
			String style,String downloadName,boolean isAuto,String extendParams) {
		DataGridColumn dataGridColumn = new DataGridColumn();
		dataGridColumn.setAlign(align);
		dataGridColumn.setCheckbox(checkbox);
		dataGridColumn.setColspan(colspan);
		dataGridColumn.setField(field);
		dataGridColumn.setFormatter(formatter);
		dataGridColumn.setHidden(hidden);
		dataGridColumn.setRowspan(rowspan);
		dataGridColumn.setSortable(sortable);
		dataGridColumn.setTitle(title);
		dataGridColumn.setWidth(width);
		dataGridColumn.setTreefield(treefield);
		dataGridColumn.setImage(image);
		dataGridColumn.setImageSize(imageSize);
		dataGridColumn.setReplace(replace);
		dataGridColumn.setQuery(query);
		dataGridColumn.setUrl(url);
		dataGridColumn.setFunname(funname);
		dataGridColumn.setArg(arg);
		dataGridColumn.setQueryMode(queryMode);
		dataGridColumn.setDictionary(dictionary);
		dataGridColumn.setFrozenColumn(frozenColumn);
		dataGridColumn.setExtend(extend);
		dataGridColumn.setStyle(style);
		dataGridColumn.setDownloadName(downloadName);
		dataGridColumn.setAutocomplete(isAuto);
		dataGridColumn.setExtendParams(extendParams);
		columnList.add(dataGridColumn);
		if (field != "opt") {
			fields += field + ",";
			if ("group".equals(queryMode)) {
				searchFields += field + "," + field + "_begin," + field + "_end,";
			} else {
				searchFields += field + ",";
			}
		}
		if (replace != null) {
			String[] test = replace.split(",");
			String text = "";
			String value = "";
			for (String string : test) {
				text += string.substring(0, string.indexOf("_")) + ",";
				value += string.substring(string.indexOf("_") + 1) + ",";
			}
			setColumn(field, text, value);

		}
		if (!StringUtils.isBlank(dictionary)) {
			if(dictionary.contains(",")){
				String[] dic = dictionary.split(",");
				String text = "";
				String value = "";
				String sql = "select " + dic[1] + " as field," + dic[2]
						+ " as text from " + dic[0];
				systemService = ApplicationContextUtil.getContext().getBean(
						SystemService.class);
				List<Map<String, Object>> list = systemService.findForJdbc(sql);
				for (Map<String, Object> map : list){
					text += map.get("text") + ",";
					value += map.get("field") + ",";
				}
				if(list.size()>0)
					setColumn(field, text, value);
			}else{
				String text = "";
				String value = "";
				List<TSType> typeList = TSTypegroup.allTypes.get(dictionary.toLowerCase());
				if (typeList != null && !typeList.isEmpty()) {
					for (TSType type : typeList) {
						text += type.getTypename() + ",";
						value += type.getTypecode() + ",";
					}
				setColumn(field, text, value);
				}
			}
		}
		if(StringUtil.isNotEmpty(style)){
			String[] temp = style.split(",");
			String text = "";
			String value = "";
			if(temp.length == 1&&temp[0].indexOf("_")==-1){
				text = temp[0];
			}else{
				for (String string : temp) {
					text += string.substring(0, string.indexOf("_")) + ",";
					value += string.substring(string.indexOf("_") + 1) + ",";
				}
			}
			setStyleColumn(field, text, value);
		}
	}
	
	/**
	 * 设置 颜色替换值
	 * @param field
	 * @param text
	 * @param value
	 */
	private void setStyleColumn(String field, String text, String value) {
		ColumnValue columnValue = new ColumnValue();
		columnValue.setName(field);
		columnValue.setText(text);
		columnValue.setValue(value);
		columnStyleList.add(columnValue);
	}

	/**
	 * 
	 * <b>Summary: </b> setColumn(设置字段替换值)
	 * 
	 * @param name
	 * @param text
	 * @param value
	 */
	public void setColumn(String name, String text, String value) {
		ColumnValue columnValue = new ColumnValue();
		columnValue.setName(name);
		columnValue.setText(text);
		columnValue.setValue(value);
		columnValueList.add(columnValue);
	}

	public int doStartTag() throws JspTagException {
		// 清空资源
		urlList.clear();
		toolBarList.clear();
		columnValueList.clear();
		columnStyleList.clear();
		columnList.clear();
		fields = "";
		searchFields = "";
		return EVAL_PAGE;
	}

	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			if (style.equals("easyui")) {
				out.print(end().toString());
			} else {
				out.print(datatables().toString());
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * datatables构造方法
	 * 
	 * @return
	 */
	public StringBuffer datatables() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">");
		sb.append("${symbol_dollar}(document).ready(function() {");
		sb.append("var oTable = ${symbol_dollar}(${symbol_escape}'${symbol_pound}userList${symbol_escape}').dataTable({");
		// sb.append(
		// "${symbol_escape}"sDom${symbol_escape}" : ${symbol_escape}"<${symbol_escape}'row${symbol_escape}'<${symbol_escape}'span6${symbol_escape}'l><${symbol_escape}'span6${symbol_escape}'f>r>t<${symbol_escape}'row${symbol_escape}'<${symbol_escape}'span6${symbol_escape}'i><${symbol_escape}'span6${symbol_escape}'p>>${symbol_escape}",");
		sb.append("${symbol_escape}"bProcessing${symbol_escape}" : true,");// 当datatable获取数据时候是否显示正在处理提示信息"
		sb.append("${symbol_escape}"bPaginate${symbol_escape}" : true,"); // 是否分页"
		sb.append("${symbol_escape}"sPaginationType${symbol_escape}" : ${symbol_escape}"full_numbers${symbol_escape}",");// 分页样式full_numbers,"
		sb.append("${symbol_escape}"bFilter${symbol_escape}" : true,");// 是否使用内置的过滤功能"
		sb.append("${symbol_escape}"bSort${symbol_escape}" : true, ");// 排序功能"
		sb.append("${symbol_escape}"bAutoWidth${symbol_escape}" : true,");// 自动宽度"
		sb.append("${symbol_escape}"bLengthChange${symbol_escape}" : true,");// 是否允许用户自定义每页显示条数"
		sb.append("${symbol_escape}"bInfo${symbol_escape}" : true,");// 页脚信息"
		sb.append("${symbol_escape}"sAjaxSource${symbol_escape}" : ${symbol_escape}"userController.do?test${symbol_escape}",");
		sb.append("${symbol_escape}"bServerSide${symbol_escape}" : true,");// 指定从服务器端获取数据
		sb.append("${symbol_escape}"oLanguage${symbol_escape}" : {" + "${symbol_escape}"sLengthMenu${symbol_escape}" : ${symbol_escape}" _MENU_ 条记录${symbol_escape}"," + "${symbol_escape}"sZeroRecords${symbol_escape}" : ${symbol_escape}"没有检索到数据${symbol_escape}"," + "${symbol_escape}"sInfo${symbol_escape}" : ${symbol_escape}"第 _START_ 至 _END_ 条数据 共 _TOTAL_ 条${symbol_escape}"," + "${symbol_escape}"sInfoEmtpy${symbol_escape}" : ${symbol_escape}"没有数据${symbol_escape}"," + "${symbol_escape}"sProcessing${symbol_escape}" : ${symbol_escape}"正在加载数据...${symbol_escape}"," + "${symbol_escape}"sSearch${symbol_escape}" : ${symbol_escape}"搜索${symbol_escape}"," + "${symbol_escape}"oPaginate${symbol_escape}" : {" + "${symbol_escape}"sFirst${symbol_escape}" : ${symbol_escape}"首页${symbol_escape}"," + "${symbol_escape}"sPrevious${symbol_escape}" : ${symbol_escape}"前页${symbol_escape}", " + "${symbol_escape}"sNext${symbol_escape}" : ${symbol_escape}"后页${symbol_escape}"," + "${symbol_escape}"sLast${symbol_escape}" : ${symbol_escape}"尾页${symbol_escape}"" + "}" + "},"); // 汉化
		// 获取数据的处理函数 ${symbol_escape}"data${symbol_escape}" : {_dt_json : JSON.stringify(aoData)},
		sb.append("${symbol_escape}"fnServerData${symbol_escape}" : function(sSource, aoData, fnCallback, oSettings) {");
		// + "${symbol_escape}"data${symbol_escape}" : {_dt_json : JSON.stringify(aoData)},"
		sb.append("oSettings.jqXHR = ${symbol_dollar}.ajax({" + "${symbol_escape}"dataType${symbol_escape}" : ${symbol_escape}'json${symbol_escape}'," + "${symbol_escape}"type${symbol_escape}" : ${symbol_escape}"POST${symbol_escape}"," + "${symbol_escape}"url${symbol_escape}" : sSource," + "${symbol_escape}"data${symbol_escape}" : aoData," + "${symbol_escape}"success${symbol_escape}" : fnCallback" + "});},");
		sb.append("${symbol_escape}"aoColumns${symbol_escape}" : [ ");
		int i = 0;
		for (DataGridColumn column : columnList) {
			i++;
			sb.append("{");
			sb.append("${symbol_escape}"sTitle${symbol_escape}":${symbol_escape}"" + column.getTitle() + "${symbol_escape}"");
			if (column.getField().equals("opt")) {
				sb.append(",${symbol_escape}"mData${symbol_escape}":${symbol_escape}"" + idField + "${symbol_escape}"");
				sb.append(",${symbol_escape}"sWidth${symbol_escape}":${symbol_escape}"20%${symbol_escape}"");
				sb.append(",${symbol_escape}"bSortable${symbol_escape}":false");
				sb.append(",${symbol_escape}"bSearchable${symbol_escape}":false");
				sb.append(",${symbol_escape}"mRender${symbol_escape}" : function(data, type, rec) {");
				this.getOptUrl(sb);
				sb.append("}");
			} else {
				int colwidth = (column.getWidth() == null) ? column.getTitle().length() * 15 : column.getWidth();
				sb.append(",${symbol_escape}"sName${symbol_escape}":${symbol_escape}"" + column.getField() + "${symbol_escape}"");
				sb.append(",${symbol_escape}"mDataProp${symbol_escape}":${symbol_escape}"" + column.getField() + "${symbol_escape}"");
				sb.append(",${symbol_escape}"mData${symbol_escape}":${symbol_escape}"" + column.getField() + "${symbol_escape}"");
				sb.append(",${symbol_escape}"sWidth${symbol_escape}":${symbol_escape}"" + colwidth + "${symbol_escape}"");
				sb.append(",${symbol_escape}"bSortable${symbol_escape}":" + column.isSortable() + "");
				sb.append(",${symbol_escape}"bVisible${symbol_escape}":" + column.isHidden() + "");
				sb.append(",${symbol_escape}"bSearchable${symbol_escape}":" + column.isQuery() + "");
			}
			sb.append("}");
			if (i < columnList.size())
				sb.append(",");
		}

		sb.append("]" + "});" + "});" + "</script>");
		sb.append("<table width=${symbol_escape}"100%${symbol_escape}"  class=${symbol_escape}"" + style + "${symbol_escape}" id=${symbol_escape}"" + name + "${symbol_escape}" toolbar=${symbol_escape}"${symbol_pound}" + name + "tb${symbol_escape}"></table>");
		return sb;

	}

	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * easyui构造方法
	 * 
	 * @return
	 */
	public StringBuffer end() {
		String grid = "";
		StringBuffer sb = new StringBuffer();
		width = (width == null) ? "auto" : width;
		height = (height == null) ? "auto" : height;
		sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">");
		sb.append("${symbol_dollar}(function(){");
		sb.append(this.getNoAuthOperButton());
		if (treegrid) {
			grid = "treegrid";
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}').treegrid({");
			sb.append("idField:'id',");
			sb.append("treeField:'text',");
		} else {
			grid = "datagrid";
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}').datagrid({");
			sb.append("idField: '" + idField + "',");
		}
		if (title != null) {
			sb.append("title: ${symbol_escape}'" + title + "${symbol_escape}',");
		}
		
		if(autoLoadData)
		   sb.append("url:${symbol_escape}'" + actionUrl + "&field=" + fields + "${symbol_escape}',");
		else
			sb.append("url:${symbol_escape}'',");
		if(StringUtils.isNotEmpty(rowStyler)){
			sb.append("rowStyler: function(index,row){ return "+rowStyler+"(index,row);},");
		}
		if(StringUtils.isNotEmpty(extendParams)){
			sb.append(extendParams);
		}
		if (fit) {
			sb.append("fit:true,");
		} else {
			sb.append("fit:false,");
		}
		sb.append("loadMsg: ${symbol_escape}'数据加载中...${symbol_escape}',");
		sb.append("pageSize: " + pageSize + ",");
		sb.append("pagination:" + pagination + ",");
		sb.append("pageList:[" + pageSize * 1 + "," + pageSize * 2 + "," + pageSize * 3 + "],");
		if(StringUtils.isNotBlank(sortName)){
			sb.append("sortName:'" +sortName +"',");
		}
		sb.append("sortOrder:'" + sortOrder + "',");
		sb.append("rownumbers:true,");
		sb.append("singleSelect:" + !checkbox + ",");
		if (fitColumns) {
			sb.append("fitColumns:true,");
		} else {
			sb.append("fitColumns:false,");
		}
		sb.append("showFooter:true,");
		sb.append("frozenColumns:[[");
		this.getField(sb,0);
		sb.append("]],");
		
		sb.append("columns:[[");
		this.getField(sb);
		sb.append("]],");
		sb.append("onLoadSuccess:function(data){${symbol_dollar}(${symbol_escape}"${symbol_pound}"+name+"${symbol_escape}")."+grid+"(${symbol_escape}"clearSelections${symbol_escape}");");
		if(openFirstNode&&treegrid){
			sb.append(" if(data==null){");
			sb.append(" var firstNode = ${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}').treegrid('getRoots')[0];");
			sb.append(" ${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}').treegrid('expand',firstNode.id)}");
		}
		if (StringUtil.isNotEmpty(onLoadSuccess)) {
			sb.append(onLoadSuccess + "(data);");
		}
		sb.append("},");
		if (StringUtil.isNotEmpty(onDblClick)) {
			sb.append("onDblClickRow:function(rowIndex,rowData){" + onDblClick + "(rowIndex,rowData);},");
		}
		if (treegrid) {
			sb.append("onClickRow:function(rowData){");
		}
		else {
			sb.append("onClickRow:function(rowIndex,rowData){");
		}
		/**行记录赋值*/
		sb.append("rowid=rowData.id;");
		sb.append("gridname=${symbol_escape}'"+name+"${symbol_escape}';");
		if (StringUtil.isNotEmpty(onClick)) {
			if (treegrid) {
				sb.append("" + onClick + "(rowData);");
			}else{
				sb.append("" + onClick + "(rowIndex,rowData);");
			}
		}
		sb.append("}");
		sb.append("});");
		this.setPager(sb, grid);
		sb.append("});");
		sb.append("function reloadTable(){");
		sb.append("try{");
		sb.append("	${symbol_dollar}(${symbol_escape}'${symbol_pound}${symbol_escape}'+gridname).datagrid(${symbol_escape}'reload${symbol_escape}');" );
		sb.append("	${symbol_dollar}(${symbol_escape}'${symbol_pound}${symbol_escape}'+gridname).treegrid(${symbol_escape}'reload${symbol_escape}');" );
		sb.append("}catch(ex){}");
		sb.append("}");
		sb.append("function reload" + name + "(){" + "${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}')." + grid + "(${symbol_escape}'reload${symbol_escape}');" + "}");
		sb.append("function get" + name + "Selected(field){return getSelected(field);}");
		sb.append("function getSelected(field){" + "var row = ${symbol_dollar}(${symbol_escape}'${symbol_pound}${symbol_escape}'+gridname)." + grid + "(${symbol_escape}'getSelected${symbol_escape}');" + "if(row!=null)" + "{" + "value= row[field];" + "}" + "else" + "{" + "value=${symbol_escape}'${symbol_escape}';" + "}" + "return value;" + "}");
		sb.append("function get" + name + "Selections(field){" + "var ids = [];" + "var rows = ${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}')." + grid + "(${symbol_escape}'getSelections${symbol_escape}');" + "for(var i=0;i<rows.length;i++){" + "ids.push(rows[i][field]);" + "}" + "ids.join(${symbol_escape}',${symbol_escape}');" + "return ids" + "};");
		sb.append("function getSelectRows(){");
		sb.append("	return ${symbol_dollar}(${symbol_escape}'${symbol_pound}"+name+"${symbol_escape}').datagrid('getChecked');");
		sb.append("}");
		if (columnList.size() > 0) {
			sb.append("function " + name + "search(){");
			sb.append("var queryParams=${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}').datagrid('options').queryParams;");
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "tb${symbol_escape}').find('*').each(function(){queryParams[${symbol_dollar}(this).attr('name')]=${symbol_dollar}(this).val();});");
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',pageNumber:1});" + "}");
			
			//高级查询执行方法
			sb.append("function dosearch(params){");
			sb.append("var jsonparams=${symbol_dollar}.parseJSON(params);");
			sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',queryParams:jsonparams});" + "}");
			
			 //searchbox框执行方法
			  searchboxFun(sb,grid);
			//生成重置按钮功能js
			  
			//回车事件
			sb.append("function EnterPress(e){");
			sb.append("var e = e || window.event;");
			sb.append("if(e.keyCode == 13){ ");
			sb.append(name+"search();");
			sb.append("}}");
				
			sb.append("function searchReset(name){");
			sb.append(" ${symbol_dollar}(${symbol_escape}"${symbol_pound}${symbol_escape}"+name+${symbol_escape}"tb${symbol_escape}").find(${symbol_escape}":input${symbol_escape}").val(${symbol_escape}"${symbol_escape}");");
			String func = name.trim() + "search();";
			sb.append(func);
			sb.append("}");
		}
		sb.append("</script>");
		sb.append("<table width=${symbol_escape}"100%${symbol_escape}"   id=${symbol_escape}"" + name + "${symbol_escape}" toolbar=${symbol_escape}"${symbol_pound}" + name + "tb${symbol_escape}"></table>");
		sb.append("<div id=${symbol_escape}"" + name + "tb${symbol_escape}" style=${symbol_escape}"padding:3px; height: auto${symbol_escape}">");
		if(hasQueryColum(columnList)){
			sb.append("<div name=${symbol_escape}"searchColums${symbol_escape}">");
			//如果表单是组合查询
			if("group".equals(getQueryMode())){
				for (DataGridColumn col : columnList) {
					if (col.isQuery()) {
						sb.append("<span style=${symbol_escape}"display:-moz-inline-box;display:inline-block;${symbol_escape}">");
						sb.append("<span style=${symbol_escape}"vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ${symbol_escape}" title=${symbol_escape}""+col.getTitle()+"${symbol_escape}">"+col.getTitle()+"：</span>");
						if("single".equals(col.getQueryMode())){
							if(!StringUtil.isEmpty(col.getReplace())){
								sb.append("<select name=${symbol_escape}""+col.getField().replaceAll("_","${symbol_escape}${symbol_escape}.")+"${symbol_escape}" WIDTH=${symbol_escape}"100${symbol_escape}" style=${symbol_escape}"width: 104px${symbol_escape}"> ");
								sb.append("<option value =${symbol_escape}"${symbol_escape}" >---请选择---</option>");
								String[] test = col.getReplace().split(",");
								String text = "";
								String value = "";
								for (String string : test) {
									text = string.split("_")[0];
									value =string.split("_")[1];
									sb.append("<option value =${symbol_escape}""+value+"${symbol_escape}">"+text+"</option>");
								}
								sb.append("</select>");
							}else if(!StringUtil.isEmpty(col.getDictionary())){
								if(col.getDictionary().contains(",")){
									String[] dic = col.getDictionary().split(",");
									String sql = "select " + dic[1] + " as field," + dic[2]
											+ " as text from " + dic[0];
									systemService = ApplicationContextUtil.getContext().getBean(
											SystemService.class);
									List<Map<String, Object>> list = systemService.findForJdbc(sql);
									sb.append("<select name=${symbol_escape}""+col.getField().replaceAll("_","${symbol_escape}${symbol_escape}.")+"${symbol_escape}" WIDTH=${symbol_escape}"100${symbol_escape}" style=${symbol_escape}"width: 104px${symbol_escape}"> ");
									sb.append("<option value =${symbol_escape}"${symbol_escape}" >---请选择---</option>");
									for (Map<String, Object> map : list){
										sb.append(" <option value=${symbol_escape}""+map.get("field")+"${symbol_escape}">");
										sb.append(map.get("text"));
										sb.append(" </option>");
									}
									sb.append("</select>");
								}else{
									Map<String, List<TSType>> typedatas = TSTypegroup.allTypes;
									List<TSType> types = typedatas.get(col.getDictionary().toLowerCase());
									sb.append("<select name=${symbol_escape}""+col.getField().replaceAll("_","${symbol_escape}${symbol_escape}.")+"${symbol_escape}" WIDTH=${symbol_escape}"100${symbol_escape}" style=${symbol_escape}"width: 104px${symbol_escape}"> ");
									sb.append("<option value =${symbol_escape}"${symbol_escape}" >---请选择---</option>");
									for (TSType type : types) {
										sb.append(" <option value=${symbol_escape}""+type.getTypecode()+"${symbol_escape}">");
										sb.append(type.getTypename());
										sb.append(" </option>");
									}
									sb.append("</select>");
								}
							}else if(col.isAutocomplete()){
								sb.append(getAutoSpan(col.getField().replaceAll("_","${symbol_escape}${symbol_escape}."),extendAttribute(col.getExtend())));
							}else{
								sb.append("<input onkeypress=${symbol_escape}"EnterPress(event)${symbol_escape}" onkeydown=${symbol_escape}"EnterPress()${symbol_escape}"  type=${symbol_escape}"text${symbol_escape}" name=${symbol_escape}""+col.getField().replaceAll("_","${symbol_escape}${symbol_escape}.")+"${symbol_escape}"  "+extendAttribute(col.getExtend())+" style=${symbol_escape}"width: 100px${symbol_escape}" />");
							}
						}else if("group".equals(col.getQueryMode())){
							sb.append("<input type=${symbol_escape}"text${symbol_escape}" name=${symbol_escape}""+col.getField()+"_begin${symbol_escape}"  style=${symbol_escape}"width: 94px${symbol_escape}" "+extendAttribute(col.getExtend())+"/>");
							sb.append("<span style=${symbol_escape}"display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;${symbol_escape}">~</span>");
							sb.append("<input type=${symbol_escape}"text${symbol_escape}" name=${symbol_escape}""+col.getField()+"_end${symbol_escape}"  style=${symbol_escape}"width: 94px${symbol_escape}" "+extendAttribute(col.getExtend())+"/>");
						}
						sb.append("</span>");
					}
				}
			}
			sb.append("</div>");
		}
		if(toolBarList.size()==0 && !hasQueryColum(columnList)){
			sb.append("<div style=${symbol_escape}"height:0px;${symbol_escape}" >");
		}else{
			sb.append("<div style=${symbol_escape}"height:30px;${symbol_escape}" class=${symbol_escape}"datagrid-toolbar${symbol_escape}">");
		}
		sb.append("<span style=${symbol_escape}"float:left;${symbol_escape}" >");
		if(toolBarList.size()>0)
		{
			for (DataGridUrl toolBar : toolBarList) {
				sb.append("<a href=${symbol_escape}"${symbol_pound}${symbol_escape}" class=${symbol_escape}"easyui-linkbutton${symbol_escape}" plain=${symbol_escape}"true${symbol_escape}" icon=${symbol_escape}""+toolBar.getIcon()+"${symbol_escape}" ");
				if(StringUtil.isNotEmpty(toolBar.getOnclick()))
				{
					sb.append("onclick="+toolBar.getOnclick()+"");
				}
				else {
					sb.append("onclick=${symbol_escape}""+toolBar.getFunname()+"(");
					if(!toolBar.getFunname().equals("doSubmit"))
					{
					sb.append("${symbol_escape}'"+toolBar.getTitle()+"${symbol_escape}',");
					}
					String width = toolBar.getWidth().contains("%")?"'"+toolBar.getWidth()+"'":toolBar.getWidth();
					String height = toolBar.getHeight().contains("%")?"'"+toolBar.getHeight()+"'":toolBar.getHeight();
					sb.append("${symbol_escape}'"+toolBar.getUrl()+"${symbol_escape}',${symbol_escape}'"+name+"${symbol_escape}',"+width+","+height+")${symbol_escape}"");
				}
				sb.append(">"+toolBar.getTitle()+"</a>");
			}
		}
		sb.append("</span>");
		if("group".equals(getQueryMode()) && hasQueryColum(columnList)){//如果表单是组合查询
			sb.append("<span style=${symbol_escape}"float:right${symbol_escape}">");
			sb.append("<a href=${symbol_escape}"${symbol_pound}${symbol_escape}" class=${symbol_escape}"easyui-linkbutton${symbol_escape}" iconCls=${symbol_escape}"icon-search${symbol_escape}" onclick=${symbol_escape}""+name+"search()${symbol_escape}">查询</a>");
			sb.append("<a href=${symbol_escape}"${symbol_pound}${symbol_escape}" class=${symbol_escape}"easyui-linkbutton${symbol_escape}" iconCls=${symbol_escape}"icon-reload${symbol_escape}" onclick=${symbol_escape}"searchReset('"+name+"')${symbol_escape}">重置</a>");
			sb.append("</span>");
		}else if("single".equals(getQueryMode())&& hasQueryColum(columnList)){//如果表单是单查询
			sb.append("<span style=${symbol_escape}"float:right${symbol_escape}">");
			sb.append("<input id=${symbol_escape}""+name+"searchbox${symbol_escape}" class=${symbol_escape}"easyui-searchbox${symbol_escape}"  data-options=${symbol_escape}"searcher:"+name+"searchbox,prompt:${symbol_escape}'请输入关键字${symbol_escape}',menu:${symbol_escape}'${symbol_pound}"+name+"mm${symbol_escape}'${symbol_escape}"></input>");
			sb.append("<div id=${symbol_escape}""+name+"mm${symbol_escape}" style=${symbol_escape}"width:120px${symbol_escape}">");
			for (DataGridColumn col : columnList) {
				if (col.isQuery()) {
					sb.append("<div data-options=${symbol_escape}"name:${symbol_escape}'"+col.getField().replaceAll("_","${symbol_escape}${symbol_escape}.")+"${symbol_escape}',iconCls:${symbol_escape}'icon-ok${symbol_escape}' "+extendAttribute(col.getExtend())+" ${symbol_escape}">"+col.getTitle()+"</div>  ");
				}
			}
			sb.append("</div>");
			sb.append("</span>");
		}
		sb.append("</div>");
		return sb;
	}

	/**
	 * 生成扩展属性
	 * @param field
	 * @return
	 */
	private String extendAttribute(String field) {
		if(StringUtil.isEmpty(field)){
			return "";
		}
		field = dealSyscode(field,1);
		StringBuilder re = new StringBuilder();
		try{
			JSONObject obj = JSONObject.fromObject(field);
			Iterator it = obj.keys();
			while(it.hasNext()){
				String key = String.valueOf(it.next());
				JSONObject nextObj =((JSONObject)obj.get(key));
				Iterator itvalue =nextObj.keys();
				re.append(key+"="+"${symbol_escape}"");
				if(nextObj.size()<=1){
					String onlykey = String.valueOf(itvalue.next());
					if("value".equals(onlykey)){
						re.append(nextObj.get(onlykey)+"");
					}else{
						re.append(onlykey+":"+nextObj.get(onlykey)+"");
					}
				}else{
					while(itvalue.hasNext()){
						String multkey = String.valueOf(itvalue.next());
						String multvalue = nextObj.getString(multkey);
						re.append(multkey+":"+multvalue+",");
					}
					re.deleteCharAt(re.length()-1);
				}
				re.append("${symbol_escape}" ");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return dealSyscode(re.toString(), 2);
	}
	/**
	 * 处理否含有json转换中的保留字
	 * @param field
	 * @param flag 1:转换 2:还原
	 * @return
	 */
	private String dealSyscode(String field,int flag) {
		String change = field;
		Iterator it = syscode.keySet().iterator();
		while(it.hasNext()){
			String key = String.valueOf(it.next());
			String value = String.valueOf(syscode.get(key));
			if(flag==1){
				change = field.replaceAll(key, value);
			}else if(flag==2){
				change = field.replaceAll(value, key);
			}
		}
		return change;
	}
	/**
	 * 判断是否存在查询字段
	 * @return hasQuery true表示有查询字段,false表示没有
	 */
	protected boolean hasQueryColum(List<DataGridColumn> columnList ) {
		boolean hasQuery = false;
		for (DataGridColumn col : columnList) {
			if(col.isQuery()){
				hasQuery =  true;
			}
		}
		return hasQuery;
	}
	/**
	 * 拼装操作地址
	 * 
	 * @param sb
	 */
	protected void getOptUrl(StringBuffer sb) {
		//注：操作列表会带入合计列中去，故加此判断
		sb.append("if(!rec.id){return '';}");
		List<DataGridUrl> list = urlList;
		sb.append("var href='';");
		for (DataGridUrl dataGridUrl : list) {
			String url = dataGridUrl.getUrl();
			MessageFormat formatter = new MessageFormat("");
			if (dataGridUrl.getValue() != null) {
				String[] testvalue = dataGridUrl.getValue().split(",");
				List value = new ArrayList<Object>();
				for (String string : testvalue) {
					value.add("${symbol_escape}"+rec." + string + " +${symbol_escape}"");
				}
				url = formatter.format(url, value.toArray());
			}
			if (url != null && dataGridUrl.getValue() == null) {

				url = formatUrl(url);
			}
			String exp = dataGridUrl.getExp();// 判断显示表达式
			if (StringUtil.isNotEmpty(exp)) {
				String[] ShowbyFields = exp.split("&&");
				for (String ShowbyField : ShowbyFields) {
					int beginIndex = ShowbyField.indexOf("${symbol_pound}");
					int endIndex = ShowbyField.lastIndexOf("${symbol_pound}");
					String exptype = ShowbyField.substring(beginIndex + 1, endIndex);// 表达式类型
					String field = ShowbyField.substring(0, beginIndex);// 判断显示依据字段
					String[] values = ShowbyField.substring(endIndex + 1, ShowbyField.length()).split(",");// 传入字段值
					String value = "";
					for (int i = 0; i < values.length; i++) {
						value += "'" + "" + values[i] + "" + "'";
						if (i < values.length - 1) {
							value += ",";
						}
					}
					if ("eq".equals(exptype)) {
						sb.append("if(${symbol_dollar}.inArray(rec." + field + ",[" + value + "])>=0){");
					}
					if ("ne".equals(exptype)) {
						sb.append("if(${symbol_dollar}.inArray(rec." + field + ",[" + value + "])<0){");
					}
					if ("empty".equals(exptype) && value.equals("'true'")) {
						sb.append("if(rec." + field + "==''){");
					}
					if ("empty".equals(exptype) && value.equals("'false'")) {
						sb.append("if(rec." + field + "!=''){");
					}
				}
			}

			if (OptTypeDirection.Confirm.equals(dataGridUrl.getType())) {
				sb.append("href+=${symbol_escape}"[<a href=${symbol_escape}'${symbol_pound}${symbol_escape}' onclick=confirm(${symbol_escape}'" + url + "${symbol_escape}',${symbol_escape}'" + dataGridUrl.getMessage() + "${symbol_escape}',${symbol_escape}'"+name+"${symbol_escape}')> ${symbol_escape}";");
			}
			if (OptTypeDirection.Del.equals(dataGridUrl.getType())) {
				sb.append("href+=${symbol_escape}"[<a href=${symbol_escape}'${symbol_pound}${symbol_escape}' onclick=delObj(${symbol_escape}'" + url + "${symbol_escape}',${symbol_escape}'"+name+"${symbol_escape}')>${symbol_escape}";");
			}
			if (OptTypeDirection.Fun.equals(dataGridUrl.getType())) {
				String name = TagUtil.getFunction(dataGridUrl.getFunname());
				String parmars = TagUtil.getFunParams(dataGridUrl.getFunname());
				sb.append("href+=${symbol_escape}"[<a href=${symbol_escape}'${symbol_pound}${symbol_escape}' onclick=" + name + "(" + parmars + ")>${symbol_escape}";");
			}
			if (OptTypeDirection.OpenWin.equals(dataGridUrl.getType())) {
				sb.append("href+=${symbol_escape}"[<a href=${symbol_escape}'${symbol_pound}${symbol_escape}' onclick=openwindow('" + dataGridUrl.getTitle() + "','" + url + "','"+name+"'," + dataGridUrl.getWidth() + "," + dataGridUrl.getHeight() + ")>${symbol_escape}";");
			}															//update-end--Author:liuht  Date:20130228 for：弹出窗口设置参数不生效
			if (OptTypeDirection.Deff.equals(dataGridUrl.getType())) {
				sb.append("href+=${symbol_escape}"[<a href=${symbol_escape}'" + url + "' title=${symbol_escape}'"+dataGridUrl.getTitle()+"${symbol_escape}'>${symbol_escape}";");
			}
			if (OptTypeDirection.OpenTab.equals(dataGridUrl.getType())) {
				sb.append("href+=${symbol_escape}"[<a href=${symbol_escape}'${symbol_pound}${symbol_escape}' onclick=addOneTab('" + dataGridUrl.getTitle() + "','" + url  + "')>${symbol_escape}";");
			}
			sb.append("href+=${symbol_escape}"" + dataGridUrl.getTitle() + "</a>]${symbol_escape}";");

			if (StringUtil.isNotEmpty(exp)) {
				for (int i = 0; i < exp.split("&&").length; i++) {
					sb.append("}");
				}

			}
		}
		sb.append("return href;");
	}

	/**
	 * 列自定义函数
	 * 
	 * @param sb
	 * @param column
	 */
	protected void getFun(StringBuffer sb, DataGridColumn column) {
		String url = column.getUrl();
		url = formatUrl(url);
		sb.append("var href=${symbol_escape}"<a style=${symbol_escape}'color:red${symbol_escape}' href=${symbol_escape}'${symbol_pound}${symbol_escape}' onclick=" + column.getFunname() + "('" + column.getTitle() + "','" + url + "')>${symbol_escape}";");
		sb.append("return href+value+${symbol_escape}'</a>${symbol_escape}';");

	}

	/**
	 * 格式化URL
	 * 
	 * @return
	 */
	protected String formatUrl(String url) {
		MessageFormat formatter = new MessageFormat("");
		String parurlvalue = "";
		if (url.indexOf("&") >= 0) {
			String beforeurl = url.substring(0, url.indexOf("&"));// 截取请求地址
			String parurl = url.substring(url.indexOf("&") + 1, url.length());// 截取参数
			String[] pras = parurl.split("&");
			List value = new ArrayList<Object>();
			int j = 0;
			for (int i = 0; i < pras.length; i++) {
				if (pras[i].indexOf("{") >= 0 || pras[i].indexOf("${symbol_pound}") >= 0) {
					String field = pras[i].substring(pras[i].indexOf("{") + 1, pras[i].lastIndexOf("}"));
					parurlvalue += "&" + pras[i].replace("{" + field + "}", "{" + j + "}");
					value.add("${symbol_escape}"+rec." + field + " +${symbol_escape}"");
					j++;
				} else {
					parurlvalue += "&" + pras[i];
				}
			}
			url = formatter.format(beforeurl + parurlvalue, value.toArray());
		}
		return url;

	}
	/**
	 * 拼接字段  普通列
	 * 
	 * @param sb
	 * 
	 */
	 protected void getField(StringBuffer sb){
		 getField(  sb,1);
	 }
	/**
	 * 拼接字段
	 * 
	 * @param sb
	 * @frozen 0 冰冻列    1 普通列
	 */
	protected void getField(StringBuffer sb,int frozen) {
		// 复选框
		if (checkbox&&frozen==0) {
			sb.append("{field:${symbol_escape}'ck${symbol_escape}',checkbox:${symbol_escape}'true${symbol_escape}'},");
		}
		int i = 0;
		for (DataGridColumn column : columnList) {
			i++;
			if((column.isFrozenColumn()&&frozen==0)||(!column.isFrozenColumn()&&frozen==1)){ 
			String field;
			if (treegrid) {
				field = column.getTreefield();
			} else {
				field = column.getField();
			}
			sb.append("{field:${symbol_escape}'" + field + "${symbol_escape}',title:${symbol_escape}'" + column.getTitle() + "${symbol_escape}'");
			if(column.getWidth() != null){
				sb.append(",width:"+column.getWidth());
			}
			if (column.getAlign()!=null){
				sb.append(",align:${symbol_escape}'" + column.getAlign() + "${symbol_escape}'");
			}
			if(StringUtils.isNotEmpty(column.getExtendParams())){
				sb.append(","+column.getExtendParams().substring(0,
						column.getExtendParams().length()-1));
			}
			// 隐藏字段
			if (!column.isHidden()) {
				sb.append(",hidden:true");
			}
			if (!treegrid) {
				// 字段排序
				if ((column.isSortable()) && (field.indexOf("_") <= 0 && field != "opt")) {
					sb.append(",sortable:" + column.isSortable() + "");
				}
			}
			// 显示图片
			if (column.isImage()) {
				sb.append(",formatter:function(value,rec,index){");
				sb.append(" return '<img border=${symbol_escape}"0${symbol_escape}" src=${symbol_escape}"'+value+'${symbol_escape}"/>';}");
			}
            // 自定义显示图片
            if (column.getImageSize() != null) {
                    String[] tld = column.getImageSize().split(",");
                    sb.append(",formatter:function(value,rec,index){");
                    sb.append(" return '<img width=${symbol_escape}"" + tld[0]
                                    + "${symbol_escape}" height=${symbol_escape}"" + tld[1]
                                    + "${symbol_escape}" border=${symbol_escape}"0${symbol_escape}" src=${symbol_escape}"'+value+'${symbol_escape}"/>';}");
                    tld = null;
            }
            if(column.getDownloadName() != null){
            	sb.append(",formatter:function(value,rec,index){");
                sb.append(" return '<a target=${symbol_escape}"_blank${symbol_escape}" href=${symbol_escape}"'+value+'${symbol_escape}">"
                		+ column.getDownloadName() + "</a>';}");
            }
			// 自定义链接
			if (column.getUrl() != null) {
				sb.append(",formatter:function(value,rec,index){");
				this.getFun(sb, column);
				sb.append("}");
			}
			if(column.getFormatter()!=null)
			{
				sb.append(",formatter:function(value,rec,index){");
				sb.append(" return new Date().format('"+column.getFormatter()+"',value);}");
			}
			// 加入操作
			if (column.getField().equals("opt")) {
				sb.append(",formatter:function(value,rec,index){");
				// sb.append("return ${symbol_escape}"");
				this.getOptUrl(sb);
				sb.append("}");
			}
			// 值替換
			if (columnValueList.size() > 0 && !column.getField().equals("opt")) {
				String testString = "";
				for (ColumnValue columnValue : columnValueList) {
					if (columnValue.getName().equals(column.getField())) {
						String[] value = columnValue.getValue().split(",");
						String[] text = columnValue.getText().split(",");
						sb.append(",formatter:function(value,rec,index){");
						sb.append("var valArray = value.split(${symbol_escape}",${symbol_escape}");");
						sb.append("if(valArray.length > 1){");
						sb.append("var checkboxValue = ${symbol_escape}"${symbol_escape}";");
						sb.append("for(var k=0; k<valArray.length; k++){");
						for(int j = 0; j < value.length; j++){
							sb.append("if(valArray[k] == '" + value[j] + "'){ checkboxValue = checkboxValue + ${symbol_escape}'" + text[j] + "${symbol_escape}' + ','}");
						}
						sb.append("}");
						sb.append("return checkboxValue.substring(0,checkboxValue.length-1);");
						sb.append("}");
						sb.append("else{");
						for (int j = 0; j < value.length; j++) {
							testString += "if(value=='" + value[j] + "'){return ${symbol_escape}'" + text[j] + "${symbol_escape}'}";
						}
						sb.append(testString);
						sb.append("else{return value}");
						sb.append("}");
						sb.append("}");
					}
				}

			}
			// 背景设置
			if (columnStyleList.size() > 0 && !column.getField().equals("opt")) {
				String testString = "";
				for (ColumnValue columnValue : columnStyleList) {
					if (columnValue.getName().equals(column.getField())) {
						String[] value = columnValue.getValue().split(",");
						String[] text = columnValue.getText().split(",");
						sb.append(",styler:function(value,rec,index){");
						if((value.length == 0||StringUtils.isEmpty(value[0]))&&text.length==1){
							if(text[0].indexOf("(")>-1){
								testString = " return ${symbol_escape}'" + text[0].replace("(", "(value,rec,index") + "${symbol_escape}'";
							}else{
								testString = " return ${symbol_escape}'" + text[0] + "${symbol_escape}'";
							}
						}else{
							for (int j = 0; j < value.length; j++) {
								testString += "if(value=='" + value[j] + "'){return ${symbol_escape}'" + text[j] + "${symbol_escape}'}";
							}
						}
						sb.append(testString);
						sb.append("}");
					}
				}
				
			}
			sb.append("}");
			// 去除末尾,
			if (i < columnList.size()) {
				sb.append(",");
			}
		}
		}
	}
	/**
	 * 设置分页条信息
	 * 
	 * @param sb
	 */
	protected void setPager(StringBuffer sb, String grid) {
		sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}')." + grid + "(${symbol_escape}'getPager${symbol_escape}').pagination({");
		sb.append("beforePageText:${symbol_escape}'${symbol_escape}'," + "afterPageText:${symbol_escape}'/{pages}${symbol_escape}',");
		if (showText) {
			sb.append("displayMsg:${symbol_escape}'{from}-{to}共{total}条${symbol_escape}',");
		} else {
			sb.append("displayMsg:${symbol_escape}'${symbol_escape}',");
		}
		if (showPageList == true) {
			sb.append("showPageList:true,");
		} else {
			sb.append("showPageList:false,");
		}
		sb.append("showRefresh:" + showRefresh + "");
		sb.append("});");// end getPager
		sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}')." + grid + "(${symbol_escape}'getPager${symbol_escape}').pagination({");
		sb.append("onBeforeRefresh:function(pageNumber, pageSize){ ${symbol_dollar}(this).pagination(${symbol_escape}'loading${symbol_escape}');${symbol_dollar}(this).pagination(${symbol_escape}'loaded${symbol_escape}'); }");
		sb.append("});");
	}
	//列表查询框函数
	protected void searchboxFun(StringBuffer sb,String grid)
	{
		sb.append("function "+name+"searchbox(value,name){");
		sb.append("var queryParams=${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}').datagrid('options').queryParams;");
		sb.append("queryParams[name]=value;queryParams.searchfield=name;${symbol_dollar}(${symbol_escape}'${symbol_pound}" + name + "${symbol_escape}')." + grid + "(${symbol_escape}'reload${symbol_escape}');}");
		sb.append("${symbol_dollar}(${symbol_escape}'${symbol_pound}"+name+"searchbox${symbol_escape}').searchbox({");
		sb.append("searcher:function(value,name){");
		sb.append(""+name+"searchbox(value,name);");
		sb.append("},");
		sb.append("menu:${symbol_escape}'${symbol_pound}"+name+"mm${symbol_escape}',");
		sb.append("prompt:${symbol_escape}'请输入查询关键字${symbol_escape}'");
		sb.append("});");
	}
  
	public String getNoAuthOperButton(){
		List<String> nolist = (List<String>) super.pageContext.getRequest().getAttribute("noauto_operationCodes");
		StringBuffer sb = new StringBuffer();
		if(ResourceUtil.getSessionUserName().getUserName().equals("admin")|| !Globals.BUTTON_AUTHORITY_CHECK){
		}else{
			if(nolist!=null&&nolist.size()>0){
				for(String s:nolist){
					sb.append("${symbol_dollar}('${symbol_pound}" + name + "tb${symbol_escape}').find(${symbol_escape}""+s.replaceAll(" ", "")+"${symbol_escape}").hide();");
				}
			}
		}
		${package}.core.util.LogUtil.info("----getNoAuthOperButton-------"+sb.toString());
		return sb.toString(); 
	}
	
	
	/**
	 * 描述：组装菜单按钮操作权限
	 * dateGridUrl：url
	 * operationCode：操作码
	 * optList： 操作列表
	 * @version 1.0
	 */
	private void installOperationCode(DataGridUrl dataGridUrl,String operationCode,List optList){
		if(ResourceUtil.getSessionUserName().getUserName().equals("admin")|| !Globals.BUTTON_AUTHORITY_CHECK){
			optList.add(dataGridUrl);
		}else if(!oConvertUtils.isEmpty(operationCode)){
			Set<String> operationCodes = (Set<String>) super.pageContext.getRequest().getAttribute("operationCodes");
			if (null!=operationCodes) {
				for (String MyoperationCode : operationCodes) {
					if(MyoperationCode.equals(operationCode)){
						optList.add(dataGridUrl);
					}
				}
			}
		}else {
			optList.add(dataGridUrl);
		}
	}
	
	/**
	 * 获取自动补全的panel
	 * @param filed
	 * @author JueYue
	 * @return
	 */
	private String getAutoSpan(String filed,String extend){
		String id = filed.replaceAll("${symbol_escape}${symbol_escape}.","_");
		StringBuffer nsb = new StringBuffer();
		nsb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">");
		nsb.append("${symbol_dollar}(document).ready(function() {") 
		.append("${symbol_dollar}(${symbol_escape}"${symbol_pound}"+getEntityName()+"_"+id+"${symbol_escape}").autocomplete(${symbol_escape}"commonController.do?getAutoList${symbol_escape}",{")
		.append("max: 5,minChars: 2,width: 200,scrollHeight: 100,matchContains: true,autoFill: false,extraParams:{")
        .append("featureClass : ${symbol_escape}"P${symbol_escape}",style : ${symbol_escape}"full${symbol_escape}",	maxRows : 10,labelField : ${symbol_escape}""+filed+"${symbol_escape}",valueField : ${symbol_escape}""+filed+"${symbol_escape}",")
		.append("searchField : ${symbol_escape}""+filed+"${symbol_escape}",entityName : ${symbol_escape}""+getEntityName()+"${symbol_escape}",trem: function(){return ${symbol_dollar}(${symbol_escape}"${symbol_pound}"+getEntityName()+"_"+id+"${symbol_escape}").val();}}");
		nsb.append(",parse:function(data){return jeecgAutoParse.call(this,data);}");
		nsb.append(",formatItem:function(row, i, max){return row['"+filed+"'];} ");
		nsb.append("}).result(function (event, row, formatted) {");
		nsb.append("${symbol_dollar}(${symbol_escape}"${symbol_pound}"+getEntityName()+"_"+id+"${symbol_escape}").val(row['"+filed+"']);}); });")
        .append("</script>")
        .append("<input type=${symbol_escape}"text${symbol_escape}" id=${symbol_escape}""+getEntityName()+"_"+id+"${symbol_escape}" name=${symbol_escape}""+filed+"${symbol_escape}" datatype=${symbol_escape}"*${symbol_escape}" "+extend+" nullmsg=${symbol_escape}"${symbol_escape}" errormsg=${symbol_escape}"输入错误${symbol_escape}"/>");
		return nsb.toString();
	}
	/**
	 * 获取实体类名称,没有这根据规则设置
	 * @return
	 */
	private String getEntityName() {
		if(StringUtils.isEmpty(entityName)){
			entityName = actionUrl.substring(0,actionUrl.indexOf("Controller"));
			entityName = (entityName.charAt(0)+"").toUpperCase()+entityName.substring(1)+"Entity";
		}
		return entityName;
	}
	
	public boolean isFitColumns() {
		return fitColumns;
	}

	public void setFitColumns(boolean fitColumns) {
		this.fitColumns = fitColumns;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	public boolean isAutoLoadData() {
		return autoLoadData;
	}

	public void setAutoLoadData(boolean autoLoadData) {
		this.autoLoadData = autoLoadData;
	}

	public void setOpenFirstNode(boolean openFirstNode) {
		this.openFirstNode = openFirstNode;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setRowStyler(String rowStyler) {
		this.rowStyler = rowStyler;
	}

	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}

}
