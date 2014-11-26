#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.system.service;

import java.util.List;

import ${package}.web.system.pojo.base.TSAttachment;

import ${package}.core.common.service.CommonService;

/**
 * 
 * @author  张代浩
 *
 */
public interface DeclareService extends CommonService{
	
	public List<TSAttachment> getAttachmentsByCode(String businessKey,String description);
	
}
