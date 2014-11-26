#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.system.service;

import ${package}.core.common.service.CommonService;

/**
 * 
 * @author  张代浩
 *
 */
public interface MenuInitService extends CommonService{
	
	public void initMenu();
}
