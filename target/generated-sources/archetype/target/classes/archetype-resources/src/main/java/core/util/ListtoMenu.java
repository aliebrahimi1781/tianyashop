#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import ${package}.web.system.pojo.base.TSFunction;

/**
 * 动态菜单栏生成
 * 
 * @author 张代浩
 * 
 */
public class ListtoMenu {
	/**
	 * 拼装easyui菜单JSON方式
	 * 
	 * @param set
	 * @param set1
	 * @return
	 */
	public static String getMenu(List<TSFunction> set, List<TSFunction> set1) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{${symbol_escape}"menus${symbol_escape}":[");
		for (TSFunction node : set) {
			String iconClas = "default";// 权限图标样式
			if (node.getTSIcon() != null) {
				iconClas = node.getTSIcon().getIconClas();
			}
			buffer.append("{${symbol_escape}"menuid${symbol_escape}":${symbol_escape}"" + node.getId() + "${symbol_escape}",${symbol_escape}"icon${symbol_escape}":${symbol_escape}""
					+ iconClas + "${symbol_escape}"," + "${symbol_escape}"menuname${symbol_escape}":${symbol_escape}""
					+ node.getFunctionName() + "${symbol_escape}",${symbol_escape}"menus${symbol_escape}":[");
			iterGet(set1, node.getId(), buffer);
			buffer.append("]},");
		}
		buffer.append("]}");

		// 将,${symbol_escape}n]替换成${symbol_escape}n]

		String tmp = buffer.toString();

		tmp = tmp.replaceAll(",${symbol_escape}n]", "${symbol_escape}n]");
		tmp = tmp.replaceAll(",]}", "]}");
		return tmp;

	}

	static int count = 0;

	/**
	 * @param args
	 */

	static void iterGet(List<TSFunction> set1, String pid, StringBuffer buffer) {

		for (TSFunction node : set1) {

			// 查找所有父节点为pid的所有对象，然后拼接为json格式的数据
			count++;
			if (node.getTSFunction().getId().equals(pid))

			{
				buffer.append("{${symbol_escape}"menuid${symbol_escape}":${symbol_escape}"" + node.getId()
						+ " ${symbol_escape}",${symbol_escape}"icon${symbol_escape}":${symbol_escape}"" + node.getTSIcon().getIconClas()
						+ "${symbol_escape}"," + "${symbol_escape}"menuname${symbol_escape}":${symbol_escape}"" + node.getFunctionName()
						+ "${symbol_escape}",${symbol_escape}"url${symbol_escape}":${symbol_escape}"" + node.getFunctionUrl() + "${symbol_escape}"");
				if (count == set1.size()) {
					buffer.append("}${symbol_escape}n");
				}
				buffer.append("},${symbol_escape}n");

			}
		}

	}

	/**
	 * 拼装Bootstarp菜单
	 * 
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getBootMenu(List<TSFunction> pFunctions,
			List<TSFunction> functions) {
		StringBuffer menuString = new StringBuffer();
		menuString.append("<ul>");
		for (TSFunction pFunction : pFunctions) {
			menuString
					.append("<li><a href=${symbol_escape}"${symbol_pound}${symbol_escape}"><span class=${symbol_escape}"icon16 icomoon-icon-stats-up${symbol_escape}"></span><b>"
							+ pFunction.getFunctionName() + "</b></a>");
			int submenusize = pFunction.getTSFunctions().size();
			if (submenusize == 0) {
				menuString.append("</li>");
			}
			if (submenusize > 0) {
				menuString.append("<ul class=${symbol_escape}"sub${symbol_escape}">");
			}
			for (TSFunction function : functions) {

				if (function.getTSFunction().getId().equals(pFunction.getId())) {
					menuString
							.append("<li><a href=${symbol_escape}""
									+ function.getFunctionUrl()
									+ "${symbol_escape}" target=${symbol_escape}"contentiframe${symbol_escape}"><span class=${symbol_escape}"icon16 icomoon-icon-file${symbol_escape}"></span>"
									+ function.getFunctionName() + "</a></li>");
				}
			}
			if (submenusize > 0) {
				menuString.append("</ul></li>");
			}
		}
		menuString.append("</ul>");
		return menuString.toString();

	}

	/**
	 * 拼装EASYUI菜单
	 * 
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getEasyuiMenu(List<TSFunction> pFunctions,
			List<TSFunction> functions) {
		StringBuffer menuString = new StringBuffer();
		for (TSFunction pFunction : pFunctions) {
			menuString.append("<div  title=${symbol_escape}"" + pFunction.getFunctionName()
					+ "${symbol_escape}" iconCls=${symbol_escape}"" + pFunction.getTSIcon().getIconClas()
					+ "${symbol_escape}">");
			int submenusize = pFunction.getTSFunctions().size();
			if (submenusize == 0) {
				menuString.append("</div>");
			}
			if (submenusize > 0) {
				menuString.append("<ul>");
			}
			for (TSFunction function : functions) {

				if (function.getTSFunction().getId().equals(pFunction.getId())) {
					String icon = "folder";
					if (function.getTSIcon() != null) {
						icon = function.getTSIcon().getIconClas();
					}
					// menuString.append("<li><div> <a class=${symbol_escape}""+function.getFunctionName()+"${symbol_escape}" iconCls=${symbol_escape}""+icon+"${symbol_escape}" target=${symbol_escape}"tabiframe${symbol_escape}"  href=${symbol_escape}""+function.getFunctionUrl()+"${symbol_escape}"> <span class=${symbol_escape}"icon "+icon+"${symbol_escape}" >&nbsp;</span> <span class=${symbol_escape}"nav${symbol_escape}">"+function.getFunctionName()+"</span></a></div></li>");
					menuString.append("<li><div onclick=${symbol_escape}"addTab(${symbol_escape}'"
							+ function.getFunctionName() + "${symbol_escape}',${symbol_escape}'"
							+ function.getFunctionUrl() + "&clickFunctionId="
							+ function.getId() + "${symbol_escape}',${symbol_escape}'" + icon
							+ "${symbol_escape}')${symbol_escape}"  title=${symbol_escape}"" + function.getFunctionName()
							+ "${symbol_escape}" url=${symbol_escape}"" + function.getFunctionUrl()
							+ "${symbol_escape}" iconCls=${symbol_escape}"" + icon + "${symbol_escape}"> <a class=${symbol_escape}""
							+ function.getFunctionName()
							+ "${symbol_escape}" href=${symbol_escape}"${symbol_pound}${symbol_escape}" > <span class=${symbol_escape}"icon " + icon
							+ "${symbol_escape}" >&nbsp;</span> <span class=${symbol_escape}"nav${symbol_escape}" >"
							+ function.getFunctionName()
							+ "</span></a></div></li>");
				}
			}
			if (submenusize > 0) {
				menuString.append("</ul></div>");
			}
		}
		return menuString.toString();

	}

	/**
	 * 拼装EASYUI 多级 菜单
	 * 
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getEasyuiMultistageMenu(
			Map<Integer, List<TSFunction>> map) {
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(0);
		for (TSFunction function : list) {
			menuString.append("<div   title=${symbol_escape}"" + function.getFunctionName()
					+ "${symbol_escape}" iconCls=${symbol_escape}"" + function.getTSIcon().getIconClas()
					+ "${symbol_escape}">");
			int submenusize = function.getTSFunctions().size();
			if (submenusize == 0) {
				menuString.append("</div>");
			}
			if (submenusize > 0) {
				menuString.append("<ul>");
			}
			menuString.append(getChild(function,1,map));
			if (submenusize > 0) {
				menuString.append("</ul></div>");
			}
		}
		return menuString.toString();
	}

    /**
     * 拼装EASYUI 多级 菜单  下级菜单为树形
     * @param map  the map of Map<Integer, List<TSFunction>>
     * @param style 样式：easyui-经典风格、shortcut-shortcut风格
     * @return
     */
	public static String getEasyuiMultistageTree(Map<Integer, List<TSFunction>> map, String style) {
		if(map==null||map.size()==0||!map.containsKey(0)){return "不具有任何权限,${symbol_escape}n请找管理员分配权限";}
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(0);
        int curIndex = 0;
        if ("easyui".equals(style)) {
            for (TSFunction function : list) {
                if(curIndex == 0) { // 第一个菜单，默认展开
                    menuString.append("<li>");
                } else {
                    menuString.append("<li state='closed'>");
                }
                menuString.append("<span>").append(function.getFunctionName()).append("</span>");
                int submenusize = function.getTSFunctions().size();
                if (submenusize == 0) {
                    menuString.append("</li>");
                }
                if (submenusize > 0) {
                    menuString.append("<ul>");
                }
                menuString.append(getChildOfTree(function,1,map));
                if (submenusize > 0) {
                    menuString.append("</ul></li>");
                }
                curIndex++;
            }
        } else if("shortcut".equals(style)) {
            for (TSFunction function : list) {
                menuString.append("<div   title=${symbol_escape}"" + function.getFunctionName()
                        + "${symbol_escape}" iconCls=${symbol_escape}"" + function.getTSIcon().getIconClas()
                        + "${symbol_escape}">");
                int submenusize = function.getTSFunctions().size();
                if (submenusize == 0) {
                    menuString.append("</div>");
                }
                if (submenusize > 0) {
                    menuString.append("<ul class=${symbol_escape}"easyui-tree tree-lines${symbol_escape}"  fit=${symbol_escape}"false${symbol_escape}" border=${symbol_escape}"false${symbol_escape}">");
                }
                menuString.append(getChildOfTree(function,1,map));
                if (submenusize > 0) {
                    menuString.append("</ul></div>");
                }
            }
        }

		return menuString.toString();
	}

	/**
	 * 获取顶级菜单的下级菜单-----面板式菜单
	 * @param parent
	 * @param level
	 * @param map
	 * @return
	 */
	private static String getChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(level);
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				if(function.getTSFunctions().size()==0||!map.containsKey(level+1)){
					menuString.append(getLeaf(function));
				}else if(map.containsKey(level+1)){
					menuString.append("<div  class=${symbol_escape}"easyui-accordion${symbol_escape}"  fit=${symbol_escape}"false${symbol_escape}" border=${symbol_escape}"false${symbol_escape}">");
					menuString.append("<div></div>");//easy ui 默认展开第一级,所以这里设置一个控制,就不展开了
					menuString.append("<div title=${symbol_escape}"" + function.getFunctionName()
							+ "${symbol_escape}" iconCls=${symbol_escape}"" + function.getTSIcon().getIconClas()
							+ "${symbol_escape}"><ul>");
					menuString.append(getChild(function,level+1,map));
					menuString.append("</ul></div>");
					menuString.append("</div>");
				}
			}
		}
		return menuString.toString();
	}
	/**
	 * 获取树形菜单
	 * @param parent
	 * @param level
	 * @param map
	 * @return
	 */
	private static String getChildOfTree(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(level);
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				if(function.getTSFunctions().size()==0||!map.containsKey(level+1)){
					menuString.append(getLeafOfTree(function));
				}else if(map.containsKey(level+1)){
					menuString.append("<li state=${symbol_escape}"closed${symbol_escape}" iconCls=${symbol_escape}"" + function.getTSIcon().getIconClas()+"${symbol_escape}" ><span>"+function.getFunctionName()+"</span>");
					menuString.append("<ul >");
					menuString.append(getChildOfTree(function,level+1,map));
					menuString.append("</ul></li>");
				}
			}
		}
		return menuString.toString();
	}
	/**
	 * 获取叶子节点
	 * @param function
	 * @return
	 */
	private static String getLeaf(TSFunction function){
		StringBuffer menuString = new StringBuffer();
		String icon = "folder";
		if (function.getTSIcon() != null) {
			icon = function.getTSIcon().getIconClas();
		}
		menuString.append("<li><div onclick=${symbol_escape}"addTab(${symbol_escape}'");
		menuString.append(function.getFunctionName());
		menuString.append("${symbol_escape}',${symbol_escape}'");
		menuString.append(function.getFunctionUrl());
		menuString.append("&clickFunctionId=");
		menuString.append(function.getId());
		menuString.append("${symbol_escape}',${symbol_escape}'");
		menuString.append(icon);
		menuString.append("${symbol_escape}')${symbol_escape}"  title=${symbol_escape}"");
		menuString.append(function.getFunctionName());
		menuString.append("${symbol_escape}" url=${symbol_escape}"");
		menuString.append(function.getFunctionUrl());
		menuString.append("${symbol_escape}" iconCls=${symbol_escape}"");
		menuString.append(icon);
		menuString.append("${symbol_escape}"> <a class=${symbol_escape}"");
		menuString.append(function.getFunctionName());
		menuString.append("${symbol_escape}" href=${symbol_escape}"${symbol_pound}${symbol_escape}" > <span class=${symbol_escape}"icon ");
		menuString.append(icon);
		menuString.append("${symbol_escape}" >&nbsp;</span> <span class=${symbol_escape}"nav${symbol_escape}" >");
		menuString.append(function.getFunctionName());
		menuString.append("</span></a></div></li>");
		return menuString.toString();
	}
	/**
	 * 获取叶子节点  ------树形菜单的叶子节点
	 * @param function
	 * @return
	 */
	private static String getLeafOfTree(TSFunction function){
		StringBuffer menuString = new StringBuffer();
		String icon = "folder";
		if (function.getTSIcon() != null) {
			icon = function.getTSIcon().getIconClas();
		}
		menuString.append("<li iconCls=${symbol_escape}"");
		menuString.append(icon);
		menuString.append("${symbol_escape}"> <a onclick=${symbol_escape}"addTab(${symbol_escape}'");
		menuString.append(function.getFunctionName());
		menuString.append("${symbol_escape}',${symbol_escape}'");
		menuString.append(function.getFunctionUrl());
		menuString.append("&clickFunctionId=");
		menuString.append(function.getId());
		menuString.append("${symbol_escape}',${symbol_escape}'");
		menuString.append(icon);
		menuString.append("${symbol_escape}')${symbol_escape}"  title=${symbol_escape}"");
		menuString.append(function.getFunctionName());
		menuString.append("${symbol_escape}" url=${symbol_escape}"");
		menuString.append(function.getFunctionUrl());
		menuString.append("${symbol_escape}" href=${symbol_escape}"${symbol_pound}${symbol_escape}" ><span class=${symbol_escape}"nav${symbol_escape}" >");
		menuString.append(function.getFunctionName());
		menuString.append("</span></a></li>");
		return menuString.toString();
	}
	/**
	 * 拼装bootstrap头部菜单
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getBootstrapMenu(Map<Integer, List<TSFunction>> map) {
		StringBuffer menuString = new StringBuffer();
		menuString.append("<ul class=${symbol_escape}"nav${symbol_escape}">");
		List<TSFunction> pFunctions = (List<TSFunction>) map.get(0);
		if(pFunctions==null || pFunctions.size()==0){
			return "";
		}
		for (TSFunction pFunction : pFunctions) {
			//是否有子菜单
			boolean hasSub = pFunction.getTSFunctions().size()==0?false:true;
			
			//绘制一级菜单
			menuString.append("	<li class=${symbol_escape}"dropdown${symbol_escape}"> ");
			menuString.append("		<a href=${symbol_escape}"javascript:;${symbol_escape}" class=${symbol_escape}"dropdown-toggle${symbol_escape}" data-toggle=${symbol_escape}"dropdown${symbol_escape}"> ");
			menuString.append("			<span class=${symbol_escape}"bootstrap-icon${symbol_escape}" style=${symbol_escape}"background-image: url('"+pFunction.getTSIcon().getIconPath()+"')${symbol_escape}"></span> "+pFunction.getFunctionName()+" ");
			if(hasSub){
				menuString.append("			<b class=${symbol_escape}"caret${symbol_escape}"></b> ");
			}
			menuString.append("		</a> ");
			//绘制二级菜单
			if(hasSub){
				menuString.append(getBootStrapChild(pFunction, 1, map));
			}
			menuString.append("	</li> ");
		}
		menuString.append("</ul>");
		return menuString.toString();
	}
	/**
	* @Title: getBootStrapChild
	* @Description: 递归获取bootstrap的子菜单
	*  @param parent
	*  @param level
	*  @param map
	* @return String    
	* @throws
	 */
	private static String getBootStrapChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(level);
		if(list==null || list.size()==0){
			return "";
		}
		menuString.append("		<ul class=${symbol_escape}"dropdown-menu${symbol_escape}"> ");
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				boolean hasSub = function.getTSFunctions().size()!=0 && map.containsKey(level+1);
				String menu_url = function.getFunctionUrl();
				if(StringUtils.isNotEmpty(menu_url)){
					menu_url += "&clickFunctionId="+function.getId();
				}
				menuString.append("		<li onclick=${symbol_escape}"showContent(${symbol_escape}'"+function.getFunctionName()+"${symbol_escape}',${symbol_escape}'"+menu_url+"${symbol_escape}')${symbol_escape}"  title=${symbol_escape}""+function.getFunctionName()+"${symbol_escape}" url=${symbol_escape}""+function.getFunctionUrl()+"${symbol_escape}" ");
				if(hasSub){
					menuString.append(" class=${symbol_escape}"dropdown-submenu${symbol_escape}"");
				}
				menuString.append(" > ");
				menuString.append("			<a href=${symbol_escape}"javascript:;${symbol_escape}"> ");
				menuString.append("				<span class=${symbol_escape}"bootstrap-icon${symbol_escape}" style=${symbol_escape}"background-image: url('"+function.getTSIcon().getIconPath()+"')${symbol_escape}"></span>		 ");
				menuString.append(function.getFunctionName());
				menuString.append("			</a> ");
				if(hasSub){
					menuString.append(getBootStrapChild(function,level+1,map));
				}
				menuString.append("		</li> ");
			}
		}
		menuString.append("		</ul> ");
		return menuString.toString();
	}
	/**
	 * 拼装webos头部菜单
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getWebosMenu(Map<Integer, List<TSFunction>> map) {
		StringBuffer menuString = new StringBuffer();
		StringBuffer DeskpanelString = new StringBuffer();
		StringBuffer dataString = new StringBuffer();
		String menu = "";
		String desk = "";
		String data = "";
		
		//menu的全部json，这里包括对菜单的展示及每个二级菜单的点击出详情
//		menuString.append("[");
		menuString.append("{");
		//绘制data.js数组，用于替换data.js中的app:{//桌面1 'dtbd':{ appid:'2534',,······
		dataString.append("{app:{");
		//绘制Deskpanel数组，用于替换webos-core.js中的Icon1:['dtbd','sosomap','jinshan'],······
		DeskpanelString.append("{");
		
		List<TSFunction> pFunctions = (List<TSFunction>) map.get(0);
		if(pFunctions==null || pFunctions.size()==0){
			return "";
		}
		int n = 1;
		for (TSFunction pFunction : pFunctions) {
			//是否有子菜单
			boolean hasSub = pFunction.getTSFunctions().size()==0?false:true;
			//绘制一级菜单
//			menuString.append("{ ");
			menuString.append("${symbol_escape}""+ pFunction.getId() + "${symbol_escape}":");
			menuString.append("{${symbol_escape}"id${symbol_escape}":${symbol_escape}""+pFunction.getId()+"${symbol_escape}",${symbol_escape}"name${symbol_escape}":${symbol_escape}""+pFunction.getFunctionName()+"${symbol_escape}",${symbol_escape}"path${symbol_escape}":${symbol_escape}""+pFunction.getTSIcon().getIconPath()+"${symbol_escape}",${symbol_escape}"level${symbol_escape}":${symbol_escape}""+pFunction.getFunctionLevel()+"${symbol_escape}",");
			menuString.append("${symbol_escape}"child${symbol_escape}":{");

			//绘制Deskpanel数组
			DeskpanelString.append("Icon"+n+":[");
			
			//绘制二级菜单
			if(hasSub){
//				menuString.append(getWebosChild(pFunction, 1, map));
				DeskpanelString.append(getWebosDeskpanelChild(pFunction, 1, map));
				dataString.append(getWebosDataChild(pFunction, 1, map));
			}
			DeskpanelString.append("],");
			menuString.append("}},");
			n++;
		}

		menu = menuString.substring(0, menuString.toString().length()-1);
//		menu += "]";
		menu += "}";
		
		data = dataString.substring(0, dataString.toString().length()-1);
		data += "}}";
		
		desk = DeskpanelString.substring(0, DeskpanelString.toString().length()-1);
		desk += "}";
		
		//初始化为1，需减少一个个数。
		n = n-1;
		
//		${package}.core.util.LogUtil.info("-------------------");
//		${package}.core.util.LogUtil.info(menu+"${symbol_dollar}${symbol_dollar}"+desk+"${symbol_dollar}${symbol_dollar}"+data+"${symbol_dollar}${symbol_dollar}"+"{${symbol_escape}"total${symbol_escape}":"+n+"}");
		return menu+"${symbol_dollar}${symbol_dollar}"+desk+"${symbol_dollar}${symbol_dollar}"+data+"${symbol_dollar}${symbol_dollar}"+n;
	}
	/**
	 * @Title: getWebosChild
	 * @Description: 递归获取Webos的子菜单
	 *  @param parent
	 *  @param level
	 *  @param map
	 * @return String    
	 * @throws
	 */
	private static String getWebosChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer menuString = new StringBuffer();
		String menu = "";
		List<TSFunction> list = map.get(level);
		if(list==null || list.size()==0){
			return "";
		}
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				boolean hasSub = function.getTSFunctions().size()!=0 && map.containsKey(level+1);
//				String menu_url = function.getFunctionUrl();
//				if(StringUtils.isNotEmpty(menu_url)){
//					menu_url += "&clickFunctionId="+function.getId();
//				}
				menuString.append("${symbol_escape}""+ function.getId() + "${symbol_escape}":");
				menuString.append("{${symbol_escape}"id${symbol_escape}":${symbol_escape}""+function.getId()+"${symbol_escape}",${symbol_escape}"name${symbol_escape}":${symbol_escape}""+function.getFunctionName()+"${symbol_escape}",${symbol_escape}"path${symbol_escape}":${symbol_escape}""+function.getTSIcon().getIconPath()+"${symbol_escape}",${symbol_escape}"url${symbol_escape}":${symbol_escape}""+function.getFunctionUrl()+"${symbol_escape}",${symbol_escape}"level${symbol_escape}":${symbol_escape}""+function.getFunctionLevel()+"${symbol_escape}"}");
				
				if(hasSub){
					menuString.append("${symbol_escape}"child${symbol_escape}":{");
					menuString.append(getWebosChild(function,level+1,map));
					menuString.append("	} ");
				}
				menuString.append(",");
			}
		}
		menu = menuString.substring(0, menuString.toString().length()-1);
		return menu;
	}
	private static String getWebosDeskpanelChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer DeskpanelString = new StringBuffer();
		String desk = "";
		List<TSFunction> list = map.get(level);
		if(list==null || list.size()==0){
			return "";
		}
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				DeskpanelString.append("'"+function.getId()+"',");
			}
		}
		desk = DeskpanelString.substring(0, DeskpanelString.toString().length()-1);
		return desk;
	}
	private static String getWebosDataChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer dataString = new StringBuffer();
		String data = "";
		List<TSFunction> list = map.get(level);
		if(list==null || list.size()==0){
			return "";
		}
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				dataString.append("'"+function.getId()+"':{ ");
				dataString.append("appid:'"+function.getId()+"',");
				dataString.append("url:'"+function.getFunctionUrl()+"',");
//				dataString.append(getIconandName(function.getFunctionName()));
				dataString.append(getIconAndNameForDesk(function));
				dataString.append("asc :"+function.getFunctionOrder());
				dataString.append(" },");
			}
		}
//		data = dataString.substring(0, dataString.toString().length()-1);
		data = dataString.toString();
		return data;
	}

    private static String getIconAndNameForDesk(TSFunction function) {
        StringBuffer dataString = new StringBuffer();

        String colName = function.getTSIconDesk() == null ? null : function.getTSIconDesk().getIconPath();
        colName = (colName == null || colName.equals("")) ? "plug-in/sliding/icon/default.png" : colName;
        String functionName = function.getFunctionName();

        dataString.append("icon:'" + colName + "',");
        dataString.append("name:'"+functionName+"',");

        return dataString.toString();
    }
    @Deprecated
	private static String getIconandName(String functionName) {
		StringBuffer dataString = new StringBuffer();
		
		if("online开发".equals(functionName)){
			dataString.append("icon:'Customize.png',");
		}else if("表单配置".equals(functionName)){
			dataString.append("icon:'Applications Folder.png',");
		}else if("动态表单配置".equals(functionName)){
			dataString.append("icon:'Documents Folder.png',");
		}else if("用户分析".equals(functionName)){
			dataString.append("icon:'User.png',");
		}else if("报表分析".equals(functionName)){
			dataString.append("icon:'Burn.png',");
		}else if("用户管理".equals(functionName)){
			dataString.append("icon:'Finder.png',");
		}else if("数据字典".equals(functionName)){
			dataString.append("icon:'friendnear.png',");
		}else if("角色管理".equals(functionName)){
			dataString.append("icon:'friendgroup.png',");
		}else if("菜单管理".equals(functionName)){
			dataString.append("icon:'kaikai.png',");
		}else if("图标管理".equals(functionName)){
			dataString.append("icon:'kxjy.png',");
		}else if("表单验证".equals(functionName)){
			dataString.append("icon:'qidianzhongwen.png',");
		}else if("一对多模型".equals(functionName)){
			dataString.append("icon:'qqread.png',");
		}else if("特殊布局".equals(functionName)){
			dataString.append("icon:'xiami.png',");
		}else if("在线word".equals(functionName)){
			dataString.append("icon:'musicbox.png',");
		}else if("多附件管理".equals(functionName)){
			dataString.append("icon:'vadio.png',");
		}else if("数据监控".equals(functionName)){
			dataString.append("icon:'Super Disk.png',");
		}else if("定时任务".equals(functionName)){
			dataString.append("icon:'Utilities.png',");
		}else if("系统日志".equals(functionName)){
			dataString.append("icon:'fastsearch.png',");
		}else if("在线维护".equals(functionName)){
			dataString.append("icon:'Utilities Folder.png',");
		}else{
			dataString.append("icon:'folder_o.png',");
		}
		dataString.append("name:'"+functionName+"',");
		
		return dataString.toString();
	}
}