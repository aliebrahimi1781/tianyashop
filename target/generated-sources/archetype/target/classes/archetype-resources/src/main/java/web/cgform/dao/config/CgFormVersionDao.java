#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.dao.config;

import ${package}.web.cgform.entity.config.CgFormHeadEntity;

import ${package}.minidao.annotation.Arguments;
import ${package}.minidao.annotation.MiniDao;
/**
 * 
 * @Title:CgFormFieldDao
 * @description:
 * @author 赵俊夫
 * @date Aug 24, 2013 11:33:33 AM
 * @version V1.0
 */
@MiniDao
public interface CgFormVersionDao {
	@Arguments("tableName")
	public String  getCgFormVersionByTableName(String tableName);
	@Arguments("id")
	public String  getCgFormVersionById(String id);
	@Arguments({"newVersion","formId"})
	public void  updateVersion(String newVersion,String formId);
	
	@Arguments({"id"})
	public CgFormHeadEntity  getCgFormById(String id);
}
