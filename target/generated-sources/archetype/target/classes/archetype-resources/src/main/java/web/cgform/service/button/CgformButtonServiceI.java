#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.service.button;

import java.util.List;

import ${package}.web.cgform.entity.button.CgformButtonEntity;

import ${package}.core.common.service.CommonService;

/**
 * 
 * @author  张代浩
 *
 */
public interface CgformButtonServiceI extends CommonService{
	
	/**
	 * 查询按钮list
	 * @param formId
	 * @return
	 */
	public List<CgformButtonEntity> getCgformButtonListByFormId(String formId);

	/**
	 * 校验按钮唯一性
	 * @param cgformButtonEntity
	 * @return
	 */
	public List<CgformButtonEntity> checkCgformButton(CgformButtonEntity cgformButtonEntity);
	
	
}
