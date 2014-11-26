#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.dao.config;

import java.util.List;
import java.util.Map;

import ${package}.minidao.annotation.Arguments;
import ${package}.minidao.annotation.MiniDao;

/**
 * 
 * @Title:CgFormFieldDao
 * @description:
 * @author 张代浩
 * @date Aug 24, 2013 11:33:33 AM
 * @version V1.0
 */
@MiniDao
public interface CgFormFieldDao {
	
	@Arguments("tableName")
	public List<Map<String, Object>> getCgFormFieldByTableName(String tableName);
	
	@Arguments("tableName")
	public List<Map<String, Object>> getCgFormHiddenFieldByTableName(String tableName);
	
}
