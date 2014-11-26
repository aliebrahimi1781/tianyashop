#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.service.enhance;

import ${package}.web.cgform.entity.enhance.CgformEnhanceJsEntity;

import ${package}.core.common.service.CommonService;

public interface CgformEnhanceJsServiceI extends CommonService{

	/**
	 * 根据cgJsType和formId查找数据
	 * @param cgJsType
	 * @param formId
	 * @return
	 */
	public CgformEnhanceJsEntity getCgformEnhanceJsByTypeFormId(String cgJsType,String formId);
}
