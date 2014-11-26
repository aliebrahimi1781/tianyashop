#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.controller.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.activation.FileDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ${package}.web.cgform.common.CgAutoListConstant;
import ${package}.web.cgform.entity.config.CgFormFieldEntity;
import ${package}.web.cgform.entity.config.CgFormHeadEntity;
import ${package}.web.cgform.exception.BusinessException;
import ${package}.web.cgform.service.config.CgFormFieldServiceI;
import ${package}.web.cgform.service.impl.config.util.FieldNumComparator;
import ${package}.web.system.service.SystemService;

import org.apache.log4j.Logger;
import ${package}.codegenerate.database.JeecgReadTable;
import ${package}.core.common.controller.BaseController;
import ${package}.core.common.hibernate.qbc.CriteriaQuery;
import ${package}.core.common.model.json.AjaxJson;
import ${package}.core.common.model.json.DataGrid;
import ${package}.core.constant.Globals;
import ${package}.core.util.MyBeanUtils;
import ${package}.core.util.StringUtil;
import ${package}.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title: Controller
 * @Description: 智能表单配置
 * @author 屈然博
 * @date 2013-06-30 11:36:53
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/cgFormHeadController")
public class CgFormHeadController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CgFormHeadController.class);
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 自动生成表属性列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cgFormHeadList")
	public ModelAndView cgFormHead(HttpServletRequest request) {
		return new ModelAndView("jeecg/cgform/config/cgFormHeadList");
	}
	@RequestMapping(params = "goCgFormSynChoice")
	public ModelAndView goCgFormSynChoice(HttpServletRequest request) {
		return new ModelAndView("jeecg/cgform/config/cgformSynChoice");
	}

	@RequestMapping(params = "popmenulink")
	public ModelAndView popmenulink(ModelMap modelMap,
                                    @RequestParam String url,
                                    @RequestParam String title, HttpServletRequest request) {
        modelMap.put("title",title);
        modelMap.put("url",url);
		return new ModelAndView("jeecg/cgform/config/popmenulink");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(CgFormHeadEntity cgFormHead,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CgFormHeadEntity.class,
				dataGrid);
		// 查询条件组装器
		${package}.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				cgFormHead);
		this.cgFormFieldService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除自动生成表属性
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CgFormHeadEntity cgFormHead,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		cgFormHead = systemService.getEntity(CgFormHeadEntity.class,
				cgFormHead.getId());
		message = "删除成功";
		cgFormFieldService.deleteCgForm(cgFormHead);
		cgFormFieldService.removeSubTableStr4Main(cgFormHead);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}
	
	/**
	 * 移除自动生成表属性
	 * 
	 * @return
	 */
	@RequestMapping(params = "rem")
	@ResponseBody
	public AjaxJson rem(CgFormHeadEntity cgFormHead,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		cgFormHead = systemService.getEntity(CgFormHeadEntity.class,
				cgFormHead.getId());
		message = "移除成功";
		cgFormFieldService.delete(cgFormHead);
		cgFormFieldService.removeSubTableStr4Main(cgFormHead);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}
	
	/**
	 * 删除自动生成表属性
	 * 
	 * @return
	 */
	@RequestMapping(params = "delField")
	@ResponseBody
	public AjaxJson delField(CgFormFieldEntity cgFormField,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		cgFormField = systemService.getEntity(CgFormFieldEntity.class,
				cgFormField.getId());
		message = cgFormField.getFieldName()+"删除成功";
		cgFormFieldService.delete(cgFormField);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	
	/**
	 * 同步表单配置到数据库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doDbSynch")
	@ResponseBody
	public AjaxJson doDbSynch(CgFormHeadEntity cgFormHead,String synMethod,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		cgFormHead = systemService.getEntity(CgFormHeadEntity.class,
				cgFormHead.getId());
		//同步数据库
		try {
			boolean bl = cgFormFieldService.dbSynch(cgFormHead,synMethod);
			if(bl){
				//追加主表的附表串
				cgFormFieldService.appendSubTableStr4Main(cgFormHead);
				message = "同步成功";		
				j.setMsg(message);
			}else{
				message = "同步失败";		
				j.setMsg(message);
				return j;
			}
		} catch (BusinessException e) {
			j.setMsg(e.getMessage());
			return j;
		}
		return j;
	}
	
	
	
	/**
	 * 添加自动生成表属性
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CgFormHeadEntity cgFormHead,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		CgFormHeadEntity oldTable =cgFormFieldService.getEntity(CgFormHeadEntity.class, cgFormHead.getId());
		cgFormFieldService.removeSubTableStr4Main(oldTable);
		//step.1 如果数据表已经创建,则不能更改主键策略(后续通过强制同步解决此问题)
		/*if(cgFormHead.getId()!=null){
			boolean tableexist = cgFormFieldService.checkTableExist(cgFormHead.getTableName());
			if(tableexist){
				if(!cgFormHead.getJformPkType().equalsIgnoreCase(oldTable.getJformPkType())){
					if((cgFormHead.getJformPkType().equalsIgnoreCase("NATIVE")||cgFormHead.getJformPkType().equalsIgnoreCase("SEQUENCE"))
							&&(oldTable.getJformPkType().equalsIgnoreCase("NATIVE")||oldTable.getJformPkType().equalsIgnoreCase("SEQUENCE"))){
						//native和sequence可以互转
					}else{
						throw new ${package}.core.common.exception.BusinessException("数据表已经创建,不能更换主键策略");
					}
				}
			}
		}
		*/
		//step.2 判定表格是否存在
		CgFormHeadEntity table = judgeTableIsNotExit(cgFormHead,oldTable);
		//step.3 刷新orderNum并且去重复
		refreshFormFieldOrderNum(cgFormHead);
		
		
		if (StringUtil.isNotEmpty(cgFormHead.getId())&&table!=null) {
			List<CgFormFieldEntity>	formFieldEntities = table.getColumns();
			for (CgFormFieldEntity cgFormFieldEntity : formFieldEntities) {
				if (StringUtil.isEmpty(cgFormFieldEntity.getOldFieldName()) 
						&& StringUtil.isNotEmpty(cgFormFieldEntity.getFieldName())) {
					cgFormFieldEntity.setFieldName(cgFormFieldEntity.getFieldName().toLowerCase());
					cgFormFieldEntity.setOldFieldName(cgFormFieldEntity.getFieldName());
				}
				
			}
			cgFormFieldService.updateTable(table,null);
			cgFormFieldService.appendSubTableStr4Main(table);
			cgFormFieldService.sortSubTableStr(table);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} else if (StringUtil.isEmpty(cgFormHead.getId())&&table==null) {
			List<CgFormFieldEntity>	formFieldEntities = cgFormHead.getColumns();
			for (CgFormFieldEntity cgFormFieldEntity : formFieldEntities) {
				if (StringUtil.isEmpty(cgFormFieldEntity.getOldFieldName())) {
					cgFormFieldEntity.setFieldName(cgFormFieldEntity.getFieldName().toLowerCase());
					cgFormFieldEntity.setOldFieldName(cgFormFieldEntity.getFieldName());
				}
				
			}
			cgFormFieldService.saveTable(cgFormHead);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 设置OrderNum
	 * @param cgFormHead
	 */
	private void refreshFormFieldOrderNum(CgFormHeadEntity cgFormHead) {
		Collections.sort(cgFormHead.getColumns(),new FieldNumComparator());
		for(int i = 0;i<cgFormHead.getColumns().size();i++){
			cgFormHead.getColumns().get(i).setOrderNum(i+1);
		}
	}

	/**
	 * 判断这个表格是不是已经存在
	 * 
	 * @param cgFormHead
	 * @param oldTable 
	 * @return
	 */
	private CgFormHeadEntity judgeTableIsNotExit(CgFormHeadEntity cgFormHead, CgFormHeadEntity oldTable) {
		CgFormHeadEntity table = cgFormFieldService.findUniqueByProperty(CgFormHeadEntity.class, "tableName",cgFormHead.getTableName());
		if (StringUtil.isNotEmpty(cgFormHead.getId())) {
			if(table != null && !oldTable.getTableName().equals(cgFormHead.getTableName())){
				message = "重命名的表已经存在";
				table = null;
			}else{
				if(table == null){//重命名了表
					cgFormHead.setIsDbSynch("N");
				}
				table = table == null?oldTable:table;
				try {
					MyBeanUtils.copyBeanNotNull2Bean(cgFormHead, table);
				} catch (Exception e) {
					e.printStackTrace();
				}
				message = "修改成功";
			}
		} else {
			message = table != null? "表已经存在":"创建成功";
		}
		return table;
	}

	/**
	 * 自动生成表属性列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CgFormHeadEntity cgFormHead,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(cgFormHead.getId())) {
			cgFormHead = cgFormFieldService.getEntity(
					CgFormHeadEntity.class, cgFormHead.getId());
			//停用jform这个前缀
			//cgFormHead.setTableName(cgFormHead.getTableName().replace(CgAutoListConstant.jform_, ""));
			req.setAttribute("cgFormHeadPage", cgFormHead);
		}
		return new ModelAndView("jeecg/cgform/config/cgFormHead");
	}
	/**
	 * 获取表格具体的属性列
	 * 
	 * @return
	 */
	@RequestMapping(params = "getColumnList")
	@ResponseBody
	public List<CgFormFieldEntity> getColumnList(CgFormHeadEntity cgFormHead,String type,
			HttpServletRequest req) {
		
		List<CgFormFieldEntity> columnList = new ArrayList<CgFormFieldEntity>();
		if (StringUtil.isNotEmpty(cgFormHead.getId())) {
			CriteriaQuery cq = new CriteriaQuery(CgFormFieldEntity.class);
			cq.eq("table.id", cgFormHead.getId());
			cq.add();
			columnList = cgFormFieldService
					.getListByCriteriaQuery(cq, false);
			//对字段列按顺序排序
			Collections.sort(columnList,new FieldNumComparator());
		}else{
//			CgFormFieldEntity field = new CgFormFieldEntity();
//			field.setFieldName("id");
//			field.setLength(36);
//			field.setContent("主键");
//			field.setIsKey("Y");
//			field.setIsNull("N");
//			field.setOrderNum(1);
//			field.setType("string");
//			field.setPointLength(0);
//			field.setIsShow("N");
//			field.setIsShowList("N");
//			field.setFieldLength(120);
//			columnList.add(field);
			columnList=getInitDataList();
		}
		return columnList;
	}
	/**
	 * 添加初始化列
	 * @return
	 */
	private List<CgFormFieldEntity>  getInitDataList(){
		List<CgFormFieldEntity> columnList = new ArrayList<CgFormFieldEntity>();
		
		columnList.add(initCgFormFieldEntityId());
		columnList.add(initCgFormFieldEntityCreateUser());
		columnList.add(initCgFormFieldEntityCreateTime());
		columnList.add(initCgFormFieldEntityUpdateUser());
		columnList.add(initCgFormFieldEntityUpdateTime());
		return columnList;
	}
	/**
	 * 添加默认id
	 * @return
	 */
	private  CgFormFieldEntity  initCgFormFieldEntityId(){
		CgFormFieldEntity field = new CgFormFieldEntity();
		field.setFieldName("id");
		field.setLength(36);
		field.setContent("主键");
		field.setIsKey("Y");
		field.setIsNull("N");
		field.setOrderNum(1);
		field.setType("string");
		field.setPointLength(0);
		field.setIsShow("N");
		field.setIsShowList("N");
		field.setFieldLength(120);
		return field;
	}

	/**
	 * 添加默认创建人
	 * @return
	 */
	private  CgFormFieldEntity  initCgFormFieldEntityCreateUser(){
		CgFormFieldEntity field = new CgFormFieldEntity();
		field.setFieldName("create_name");
		field.setLength(50);
		field.setContent("创建人名称");
		field.setIsKey("N");
		field.setIsNull("Y");
		field.setOrderNum(2);
		field.setType("string");
		field.setPointLength(0);
		field.setIsShow("N");
		field.setIsShowList("N");
		field.setFieldLength(120);
		return field;
	}
	
	/**
	 * 添加默认创建时间
	 * @return
	 */
	private  CgFormFieldEntity  initCgFormFieldEntityCreateTime(){
		CgFormFieldEntity field = new CgFormFieldEntity();
		field.setFieldName("create_date");
		field.setLength(20);
		field.setContent("创建日期");
		field.setIsKey("N");
		field.setIsNull("Y");
		field.setOrderNum(3);
		field.setType("Date");
		field.setPointLength(0);
		field.setIsShow("N");
		field.setIsShowList("N");
		field.setFieldLength(120);
		return field;
	}
	/**
	 * 添加默认修改人
	 * @return
	 */
	private  CgFormFieldEntity  initCgFormFieldEntityUpdateUser(){
		CgFormFieldEntity field = new CgFormFieldEntity();
		field.setFieldName("update_name");
		field.setLength(50);
		field.setContent("修改人名称");
		field.setIsKey("N");
		field.setIsNull("Y");
		field.setOrderNum(4);
		field.setType("string");
		field.setPointLength(0);
		field.setIsShow("N");
		field.setIsShowList("N");
		field.setFieldLength(120);
		return field;
	}
	/**
	 * 添加默认修改时间
	 * @return
	 */
	private  CgFormFieldEntity  initCgFormFieldEntityUpdateTime(){
		CgFormFieldEntity field = new CgFormFieldEntity();
		field.setFieldName("update_date");
		field.setLength(20);
		field.setContent("修改日期");
		field.setIsKey("N");
		field.setIsNull("Y");
		field.setOrderNum(5);
		field.setType("Date");
		field.setPointLength(0);
		field.setIsShow("N");
		field.setIsShowList("N");
		field.setFieldLength(120);
		return field;
	}
	/**
	 * 判断表格是够已经创建
	 * 
	 * @return AjaxJson 中的success
	 */
	@RequestMapping(params = "checkIsExit")
	@ResponseBody
	public AjaxJson checkIsExit(String name,
			HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(cgFormFieldService.judgeTableIsExit(name));
		return j;
	}
	/**
	 * sql插件 页面跳转
	 * @return
	 */
	@RequestMapping(params = "sqlPlugin")
	public ModelAndView sqlPlugin(String id,HttpServletRequest request) {
		CgFormHeadEntity bean = cgFormFieldService.getEntity(
				CgFormHeadEntity.class, id);
		request.setAttribute("bean", bean);
		return new ModelAndView("jeecg/cgform/config/cgFormSqlPlugin");
	}
	/**
	 * sql 插件保存
	 * @param id 配置id
	 * @param sql_plug_in 插件内容
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "sqlPluginSave")
	@ResponseBody
	public AjaxJson sqlPluginSave(String id,String sql_plug_in,
			HttpServletRequest request) {
		CgFormHeadEntity bean = cgFormFieldService.getEntity(
				CgFormHeadEntity.class, id);
		//bean.setSqlPlugIn(sql_plug_in);
		cgFormFieldService.updateTable(bean,null);
		message = "保存成功";
		systemService.addLog(message, Globals.Log_Type_INSERT,
				Globals.Log_Leavel_INFO);
		AjaxJson j =  new AjaxJson();
		j.setMsg(message);
		return j;
	}
	/**
	 * js插件 页面跳转
	 * @return
	 */
	@RequestMapping(params = "jsPlugin")
	public ModelAndView jsPlugin(String id,HttpServletRequest request) {
		CgFormHeadEntity bean = cgFormFieldService.getEntity(
				CgFormHeadEntity.class, id);
		request.setAttribute("bean", bean);
		return new ModelAndView("jeecg/cgform/config/cgFormJsPlugin");
	}
	/**
	 * js 插件保存
	 * @param id 配置id
	 * @param js_plug_in 插件内容
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "jsPluginSave")
	@ResponseBody
	public AjaxJson jsPluginSave(String id,String js_plug_in,
			HttpServletRequest request) {
		CgFormHeadEntity bean = cgFormFieldService.getEntity(
				CgFormHeadEntity.class, id);
		//bean.setJsPlugIn(js_plug_in);停用jsPlugIn这个字段
		cgFormFieldService.updateTable(bean,null);
		message = "保存成功";
		systemService.addLog(message, Globals.Log_Type_INSERT,
				Globals.Log_Leavel_INFO);
		AjaxJson j =  new AjaxJson();
		j.setMsg(message);
		return j;
	}
	
}
