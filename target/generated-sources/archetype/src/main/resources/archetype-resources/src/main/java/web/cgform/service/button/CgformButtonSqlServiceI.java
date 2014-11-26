#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.service.button;

import java.util.List;

import ${package}.web.cgform.entity.button.CgformButtonEntity;
import ${package}.web.cgform.entity.button.CgformButtonSqlEntity;

import ${package}.core.common.service.CommonService;

public interface CgformButtonSqlServiceI extends CommonService{

	/**
	 * 校验按钮sql增强唯一性
	 * @param cgformButtonEntity
	 * @return
	 */
	public List<CgformButtonSqlEntity> checkCgformButtonSql(CgformButtonSqlEntity cgformButtonSqlEntity);
	
	/**
	 * 根据buttonCode和formId初始化数据
	 * @param buttonCode
	 * @param formId
	 * @return
	 */
	public CgformButtonSqlEntity getCgformButtonSqlByCodeFormId(String buttonCode,String formId);
}
