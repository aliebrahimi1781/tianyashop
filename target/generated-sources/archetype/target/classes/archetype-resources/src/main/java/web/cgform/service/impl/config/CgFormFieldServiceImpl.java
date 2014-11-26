#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.service.impl.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ${package}.web.cgform.common.CgAutoListConstant;
import ${package}.web.cgform.dao.config.CgFormFieldDao;
import ${package}.web.cgform.dao.config.CgFormVersionDao;
import ${package}.web.cgform.entity.config.CgFormFieldEntity;
import ${package}.web.cgform.entity.config.CgFormHeadEntity;
import ${package}.web.cgform.entity.config.CgSubTableVO;
import ${package}.web.cgform.entity.enhance.CgformEnhanceJsEntity;
import ${package}.web.cgform.exception.BusinessException;
import ${package}.web.cgform.exception.DBException;
import ${package}.web.cgform.service.cgformftl.CgformFtlServiceI;
import ${package}.web.cgform.service.config.CgFormFieldServiceI;
import ${package}.web.cgform.service.config.DbTableHandleI;
import ${package}.web.cgform.service.enhance.CgformEnhanceJsServiceI;
import ${package}.web.cgform.service.impl.config.util.DbTableProcess;
import ${package}.web.cgform.service.impl.config.util.DbTableUtil;
import ${package}.web.cgform.util.PublicUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import ${package}.core.annotation.Ehcache;
import ${package}.core.common.service.impl.CommonServiceImpl;
import ${package}.core.util.MyBeanUtils;
import ${package}.core.util.StringUtil;
import ${package}.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.star.uno.RuntimeException;

@Service("cgFormFieldService")
@Transactional
public class CgFormFieldServiceImpl extends CommonServiceImpl implements
		CgFormFieldServiceI {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CgFormFieldServiceImpl.class);
	//同步方式：普通同步
	private static final String SYN_NORMAL = "normal";
	//同步方式：强制同步
	private static final String SYN_FORCE = "force";
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CgFormVersionDao cgFormVersionDao;
	@Autowired
	private CgformFtlServiceI cgformFtlService;
	@Autowired
	private CgformEnhanceJsServiceI cgformEnhanceJsService;
	@Autowired
	private CgFormFieldDao cgFormFieldDao;

	
	public synchronized void updateTable(CgFormHeadEntity t, String sign) {
		CgFormFieldEntity column;
		boolean databaseFieldIsChange = false;
		for (int i = 0; i < t.getColumns().size(); i++) {
			column = t.getColumns().get(i);
			if(oConvertUtils.isEmpty(column.getFieldName())){
				continue;
			}
			column.setTable(t);
			// 设置checkbox的值
			PublicUtil.judgeCheckboxValue(column,"isNull,isShow,isShowList,isQuery,isKey");
			if (oConvertUtils.isEmpty(column.getId())) {
				databaseFieldIsChange = true;
				this.save(column);
			} else {
				CgFormFieldEntity c = this.getEntity(CgFormFieldEntity.class,column.getId());
				if (!databaseFieldIsChange && databaseFieldIsChange(c, column)) {
					databaseFieldIsChange = true;
				}
				try {
					MyBeanUtils.copyBeanNotNull2Bean(column, c);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
				this.saveOrUpdate(c);
			}
		}
		t.setIsDbSynch(databaseFieldIsChange ? "N" : t.getIsDbSynch());
		
		//表单配置修改，版本号未升级
		Integer newVerion = Integer.parseInt(t.getJformVersion())+1;
		t.setJformVersion(newVerion.toString());
		this.saveOrUpdate(t);
	}

	/**
	 * 判断数据库字段是否修改了
	 * 
	 * @param oldColumn
	 * @param newColumn
	 * @return
	 */
	private boolean databaseFieldIsChange(CgFormFieldEntity oldColumn,
			CgFormFieldEntity newColumn) {
			if (!PublicUtil.compareValue(oldColumn.getFieldName(), newColumn.getFieldName())
					|| !PublicUtil.compareValue(oldColumn.getContent(),newColumn.getContent())
					|| !PublicUtil.compareValue(oldColumn.getLength(),newColumn.getLength())
					|| !PublicUtil.compareValue(oldColumn.getPointLength(),newColumn.getPointLength())
					|| !PublicUtil.compareValue(oldColumn.getType(),newColumn.getType())
					|| !PublicUtil.compareValue(oldColumn.getIsNull(),newColumn.getIsNull())
					|| !PublicUtil.compareValue(oldColumn.getOrderNum(),newColumn.getOrderNum())
					|| !PublicUtil.compareValue(oldColumn.getIsKey(),newColumn.getIsKey())
					|| !PublicUtil.compareValue(oldColumn.getMainTable(),newColumn.getMainTable())
					|| !PublicUtil.compareValue(oldColumn.getMainField(),newColumn.getMainField())
					||!PublicUtil.compareValue(oldColumn.getFieldDefault(),newColumn.getFieldDefault())
			) 
		{
			return true;
		}
		return false;
	}
	
	
	
	public void saveTable(CgFormHeadEntity cgFormHead) {
		cgFormHead.setJformVersion("1");
		cgFormHead.setIsDbSynch("N");
		cgFormHead.setId((String) this.getSession().save(cgFormHead));
		CgFormFieldEntity column;
		for (int i = 0; i < cgFormHead.getColumns().size(); i++) {
			column = cgFormHead.getColumns().get(i);
			PublicUtil.judgeCheckboxValue(column,
					"isNull,isShow,isShowList,isQuery,isKey");
			column.setTable(cgFormHead);
			this.save(column);
		}
	}

	/**
	 * 重载创建表
	 * 
	 * @param cgFormHead
	 * @param a
	 */
	
	public void saveTable(CgFormHeadEntity cgFormHead, String a) {
		cgFormHead.setId((String) this.getSession().save(cgFormHead));
		CgFormFieldEntity column;
		for (int i = 0; i < cgFormHead.getColumns().size(); i++) {
			column = cgFormHead.getColumns().get(i);
			PublicUtil.judgeCheckboxValue(column,
					"isNull,isShow,isShowList,isQuery,isKey");
			column.setTable(cgFormHead);
			this.save(column);
		}
	}

	
	public Boolean judgeTableIsExit(String tableName) {
		Connection conn = null;
		ResultSet rs = null;
		String tableNamePattern = tableName;
		try {
			String[] types = { "TABLE" };
			conn = SessionFactoryUtils.getDataSource(
					getSession().getSessionFactory()).getConnection();
			String dataBaseType = DbTableUtil.getDataType(getSession());
			if("ORACLE".equals(dataBaseType)){
				tableNamePattern = tableName.toUpperCase();
				//由于PostgreSQL是大小写敏感的，并默认对SQL语句中的数据库对象名称转换为小写
			}else if ("POSTGRESQL".equals(dataBaseType)){
				tableNamePattern = tableName.toLowerCase();
			}
			DatabaseMetaData dbMetaData = conn.getMetaData();
			rs = dbMetaData.getTables(null,null,tableNamePattern, types);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}finally{//关闭连接
			try {
				if(rs!=null){rs.close();}
				if(conn!=null){conn.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 同步数据库
	 * 
	 * @throws BusinessException
	 */
	public boolean dbSynch(CgFormHeadEntity cgFormHead,String synMethod)
			throws BusinessException {
		try {
			if(SYN_NORMAL.equals(synMethod)){
				//普通方式同步
				// step.1判断表是否存在
				if (judgeTableIsExit(cgFormHead.getTableName())) {
					// 更新表操作
					DbTableProcess dbTableProcess = new DbTableProcess(getSession());
					List<String> updateTable = dbTableProcess.updateTable(
							cgFormHead, getSession());
					for (String sql : updateTable) {
						if(StringUtils.isNotEmpty(sql)){
							this.executeSql(sql);
						}
					}
				} else {
					// 不存在的情况下，创建新表
					try {
						DbTableProcess.createOrDropTable(cgFormHead, getSession());
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
						throw new BusinessException("同步失败:创建新表出错");
					}
				}
				cgFormHead.setIsDbSynch("Y");
				this.saveOrUpdate(cgFormHead);	
			}else if(SYN_FORCE.equals(synMethod)){
				//强制方式同步
				try {
					try{
						String sql = getTableUtil().dropTableSQL(cgFormHead.getTableName());
						this.executeSql(sql);
					}catch (Exception e) {
						//部分数据库在没有表而执行删表语句时会报错
						logger.error(e.getMessage());
					}
					DbTableProcess.createOrDropTable(cgFormHead, getSession());
					cgFormHead.setIsDbSynch("Y");
					this.saveOrUpdate(cgFormHead);
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					throw new BusinessException("同步失败:创建新表出错");
				}
			}
		}catch (BusinessException e) {
			logger.error(e.getMessage(),e);
			throw new BusinessException(e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BusinessException("同步失败:数据库不支持本次修改,如果不需要保留数据,请尝试强制同步");
		}
		return true;
	}

	
	public void deleteCgForm(CgFormHeadEntity cgFormHead) {
		if (judgeTableIsExit(cgFormHead.getTableName())) {
			String sql = getTableUtil().dropTableSQL(cgFormHead.getTableName());
			this.executeSql(sql);
		}
		this.delete(cgFormHead);

	}

	/**
	 * 获取数据操作接口
	 * 
	 * @return
	 */
	private DbTableHandleI getTableUtil() {
		return DbTableUtil.getTableHandle(getSession());
	}

	
	public List<Map<String, Object>> getCgFormFieldByTableName(String tableName) {
		List<Map<String, Object>> list = cgFormFieldDao.getCgFormFieldByTableName(tableName);
		return list;
	}

	private List<Map<String, Object>> getCgFormHiddenFieldByTableName(
			String tableName) {
		List<Map<String, Object>> list = cgFormFieldDao.getCgFormHiddenFieldByTableName(tableName);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				if ("id".equalsIgnoreCase((String) map.get("field_name"))) {
					list.remove(map);
					break;
				}
			}
		} else {
			list = new ArrayList<Map<String, Object>>();
		}
		return list;
	}

	
	public Map<String, CgFormFieldEntity> getCgFormFieldByFormId(String formid) {
		StringBuilder hql = new StringBuilder("");
		hql.append("from CgFormFieldEntity f");
		hql.append(" where f.table.id=? ");
		List<CgFormFieldEntity> list = this.findHql(hql.toString(), formid);
		Map<String, CgFormFieldEntity> map = new HashMap<String, CgFormFieldEntity>();
		if (list != null && list.size() > 0) {
			for (CgFormFieldEntity po : list) {
				map.put(po.getFieldName(), po);
			}
		}
		return map;
	}

	
	public Map<String, CgFormFieldEntity> getAllCgFormFieldByTableName(
			String tableName) {
		StringBuilder hql = new StringBuilder("");
		hql.append("from CgFormFieldEntity f");
		hql.append(" where f.table.tableName=? ");
		List<CgFormFieldEntity> list = this.findHql(hql.toString(), tableName);
		Map<String, CgFormFieldEntity> map = new HashMap<String, CgFormFieldEntity>();
		if (list != null && list.size() > 0) {
			for (CgFormFieldEntity po : list) {
				map.put(po.getFieldName(), po);
			}
		}
		return map;
	}

	
	@Ehcache
	public Map<String, CgFormFieldEntity> getAllCgFormFieldByTableName(
			String tableName, String version) {
		StringBuilder hql = new StringBuilder("");
		hql.append("from CgFormFieldEntity f");
		hql.append(" where f.table.tableName=? ");
		List<CgFormFieldEntity> list = this.findHql(hql.toString(), tableName);
		Map<String, CgFormFieldEntity> map = new HashMap<String, CgFormFieldEntity>();
		if (list != null && list.size() > 0) {
			for (CgFormFieldEntity po : list) {
				map.put(po.getFieldName(), po);
			}
		}
		return map;
	}

	
	public CgFormHeadEntity getCgFormHeadByTableName(String tableName) {
		StringBuilder hql = new StringBuilder("");
		hql.append("from CgFormHeadEntity f");
		hql.append(" where f.tableName=? ");
		List<CgFormHeadEntity> list = this.findHql(hql.toString(), tableName);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
	public List<Map<String, Object>> getSubTableData(String mainTableName,
			String subTableName, Object mainTableId) {
		StringBuilder sql1 = new StringBuilder("");
		sql1.append("select f.* from cgform_field f ,cgform_head h");
		sql1.append(" where f.table_id = h.id ");
		sql1.append(" and h.table_name=? ");
		sql1.append(" and f.main_table=? ");
		List<Map<String, Object>> list = this.findForJdbc(sql1.toString(),
				subTableName, mainTableName);

		StringBuilder sql2 = new StringBuilder("");
		sql2.append("select sub.* from ").append(subTableName).append(" sub ");
		sql2.append(", ").append(mainTableName).append(" main ");
		sql2.append("where 1=1 ");
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				if (map.get("main_field") != null) {
					sql2.append(" and sub.")
							.append((String) map.get("field_name")).append("=")
							.append("main.")
							.append((String) map.get("main_field"));
				}
			}
		}
		sql2.append(" and main.id= ? ");
		List<Map<String, Object>> subTableDataList = this.findForJdbc(
				sql2.toString(), mainTableId);
		return subTableDataList;
	}

	
	public boolean appendSubTableStr4Main(CgFormHeadEntity entity) {
		// step.1 获取本表的名称
		String thisSubTable = entity.getTableName();
		List<CgFormFieldEntity> columns = entity.getColumns();
		// step.2 扫描字段配置，循环处理填有主表以及主表字段的条目
		for (CgFormFieldEntity fieldE : columns) {
			String mainT = fieldE.getMainTable();
			String mainF = fieldE.getMainField();
			if (!StringUtil.isEmpty(mainT) && !StringUtil.isEmpty(mainF)) {
				CgFormHeadEntity mainE = this.getCgFormHeadByTableName(mainT);
				if (mainE == null) {
					continue;
				}
				// step.4 追加处理主表的附表串
				String subTableStr = String
						.valueOf(mainE.getSubTableStr() == null ? "" : mainE
								.getSubTableStr());
				// step.5 判断是否已经存在于附表串
				if (!subTableStr.contains(thisSubTable)) {
					// step.6 追加到附表串
					if (!StringUtil.isEmpty(subTableStr)) {
						subTableStr += "," + thisSubTable;
					} else {
						subTableStr += thisSubTable;
					}
					mainE.setSubTableStr(subTableStr);
					logger.info("--主表" + mainE.getTableName() + "的附表串："
							+ mainE.getSubTableStr());
				}
				// step.7 更新主表的表配置
				this.updateTable(mainE, "sign");
			}
		}
		return true;
	}

	
	public boolean removeSubTableStr4Main(CgFormHeadEntity entity) {
		if (entity == null) {
			return false;
		}
		// step.1 获取本表的名称
		String thisSubTable = entity.getTableName();
		List<CgFormFieldEntity> columns = entity.getColumns();
		// step.2 扫描字段配置，循环处理填有主表以及主表字段的条目
		for (CgFormFieldEntity fieldE : columns) {
			String mainT = fieldE.getMainTable();
			String mainF = fieldE.getMainField();
			if (!StringUtil.isEmpty(mainT) && !StringUtil.isEmpty(mainF)) {
				CgFormHeadEntity mainE = this.getCgFormHeadByTableName(mainT);
				if (mainE == null) {
					continue;
				}
				// step.4 追加处理主表的附表串
				String subTableStr = String
						.valueOf(mainE.getSubTableStr() == null ? "" : mainE
								.getSubTableStr());
				// step.5 判断是否已经存在于附表串
				if (subTableStr.contains(thisSubTable)) {
					// step.6 剔除主表的附表串
					if (subTableStr.contains(thisSubTable + ",")) {
						subTableStr = subTableStr.replace(thisSubTable + ",",
								"");
					} else if(subTableStr.contains(","+thisSubTable)){
						subTableStr = subTableStr.replace("," + thisSubTable,
								"");
					} else{
						subTableStr = subTableStr.replace(thisSubTable,"");
					}
					mainE.setSubTableStr(subTableStr);
					logger.info("--主表" + mainE.getTableName() + "的附表串："
							+ mainE.getSubTableStr());
					// step.7 更新主表的表配置,不用更新,因为hibernate是读取的缓存,所以和那边操作的对象是一个的
					//this.updateTable(mainE, null);
				}
			}
		}
		return true;
	}

	
	public void sortSubTableStr(CgFormHeadEntity entity) {
		if (entity == null)
			return;
		CgFormHeadEntity main = null;
		List<CgFormFieldEntity> columns = entity.getColumns();
		for (CgFormFieldEntity fieldE : columns) {
			String mainT = fieldE.getMainTable();
			String mainF = fieldE.getMainField();
			if (!StringUtil.isEmpty(mainT) && !StringUtil.isEmpty(mainF)) {
				CgFormHeadEntity mainE = this.getCgFormHeadByTableName(mainT);
				if (mainE == null) {
					continue;
				}
				main = mainE;
			}
		}
		if(main==null){
			return;
		}
		String subTableStr = main.getSubTableStr();
		if(StringUtils.isNotEmpty(subTableStr)){
			String [] subTables = subTableStr.split(",");
			if (subTables.length <= 1)
				return;
			List<CgFormHeadEntity> subList = new ArrayList<CgFormHeadEntity>();
			for(String subTable : subTables){
				CgFormHeadEntity sub = this.getCgFormHeadByTableName(subTable);
				subList.add(sub);
			}
			Collections.sort(subList, new Comparator<CgFormHeadEntity>() {
				public int compare(CgFormHeadEntity arg0, CgFormHeadEntity arg1) {
					Integer tabOrder0 = arg0.getTabOrder();
					if (tabOrder0 == null) {
						tabOrder0 = 0;
					}
					Integer tabOrder1 = arg1.getTabOrder();
					if (tabOrder1 == null) {
						tabOrder1 = 0;
					}
					return tabOrder0.compareTo(tabOrder1);
				}
			});
			subTableStr = "";
			for (CgFormHeadEntity sub : subList) {
				subTableStr += sub.getTableName() + ",";
			}
			subTableStr = subTableStr.substring(0, subTableStr.length() - 1);
			main.setSubTableStr(subTableStr);
			this.updateTable(main, "sign");
		}
	}

	
	public String getCgFormVersionByTableName(String tableName) {
		String version = cgFormVersionDao
				.getCgFormVersionByTableName(tableName);
		return StringUtil.isEmpty(version) ? "0" : version;

	}

	
	public String getCgFormVersionById(String id) {
		String version = cgFormVersionDao.getCgFormVersionById(id);
		return StringUtil.isEmpty(version) ? "0" : version;
	}

	/**
	 * 获取表单配置
	 * 
	 * @param tableName
	 * @param version
	 * @return
	 */
	@Ehcache
	public Map<String, Object> getFtlFormConfig(String tableName, String version) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> field = new HashMap<String, Object>();
		CgFormHeadEntity head = this.getCgFormHeadByTableName(tableName,
				version);
		data.put("head", head);
		if (head.getJformType() == CgAutoListConstant.JFORM_TYPE_MAIN_TALBE) {
			CgSubTableVO subtableVo = new CgSubTableVO();
			String subTableStr = head.getSubTableStr();
			if (StringUtils.isNotEmpty(subTableStr)) {
				String[] subTables = subTableStr.split(",");
				List<Map<String, Object>> subTalbeFieldList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> subTalbeHiddenFieldList = new ArrayList<Map<String, Object>>();
				for (String subTable : subTables) {
					subTalbeFieldList = this
							.getCgFormFieldByTableName(subTable);
					subTalbeHiddenFieldList = this
							.getCgFormHiddenFieldByTableName(subTable);
					CgFormHeadEntity subhead = this
							.getCgFormHeadByTableName(subTable);
					subtableVo = new CgSubTableVO();
					subtableVo.setHead(subhead);
					subtableVo.setFieldList(subTalbeFieldList);
					subtableVo.setHiddenFieldList(subTalbeHiddenFieldList);
					field.put(subTable, subtableVo);
				}
			}
		}
		// 装载附表表单配置
		data.put("field", field);
		data.put("tableName", tableName);
		List<Map<String, Object>> fieldList = null;
		if (head.getJformType() == CgAutoListConstant.JFORM_TYPE_MAIN_TALBE) {
			// 查询主表或单表表单配置
			fieldList = this.getCgFormFieldByTableName(tableName);
		} else {
			Map<String, Object> cgformFtlEntity = cgformFtlService
					.getCgformFtlByTableName(tableName);
			if (cgformFtlEntity == null) {
				// 查询主表或单表表单配置
				fieldList = this.getCgFormFieldByTableName(tableName);
			}
		}
		if (fieldList != null) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> textareaList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : fieldList) {
				if (!"textarea".equals((String) map.get("show_type"))) {
					list.add(map);
				} else {
					textareaList.add(map);
				}
			}
			data.put("columns", list);
			data.put("columnsarea", textareaList);
		}
		// 隐藏字段 剔除id
		List<Map<String, Object>> hiddenFieldList = getCgFormHiddenFieldByTableName(tableName);
		data.put("columnhidden", hiddenFieldList);
		// js增强
		String jsCode = "";
		CgformEnhanceJsEntity jsEnhance = cgformEnhanceJsService
				.getCgformEnhanceJsByTypeFormId("form", head.getId());
		if (jsEnhance != null) {
			jsCode = jsEnhance.getCgJsStr();
		}
		data.put("js_plug_in", jsCode);
		return data;
	}

	/**
	 * 根据tableName 获取表单配置 根据版本号缓存
	 * 
	 * @param tableName
	 * @param version
	 * @return
	 */
	@Ehcache
	public CgFormHeadEntity getCgFormHeadByTableName(String tableName,
			String version) {
		StringBuilder hql = new StringBuilder("");
		hql.append("from CgFormHeadEntity f");
		hql.append(" where f.tableName=? ");
		List<CgFormHeadEntity> list = this.findHql(hql.toString(), tableName);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
	public synchronized boolean updateVersion(String formId) {
		try {
			int newV = Integer.parseInt(cgFormVersionDao
					.getCgFormVersionById(formId)) + 1;
			cgFormVersionDao.updateVersion(String.valueOf(newV + ""), formId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return false;
		}
		return true;
	}

	
	public List<CgFormFieldEntity> getHiddenCgFormFieldByTableName(
			String tableName) {
		StringBuilder hql = new StringBuilder("");
		hql.append("from CgFormFieldEntity f");
		hql.append(" where f.isShow !='Y' and f.table.tableName=? ");
		List<CgFormFieldEntity> list = this.findHql(hql.toString(), tableName);
		if (list != null && list.size() > 0) {
			for (CgFormFieldEntity po : list) {
				if ("id".equalsIgnoreCase(po.getFieldName())) {
					list.remove(po);
					break;
				}
			}
		} else {
			list = new ArrayList<CgFormFieldEntity>();
		}
		return list;
	}

	
	public boolean checkTableExist(String tableName) {
		boolean result =true;
		try{
			this.findForJdbc("select count(*) from "+tableName);
		}catch(Exception e){
			result =false;
		}
		return result;
	}

}