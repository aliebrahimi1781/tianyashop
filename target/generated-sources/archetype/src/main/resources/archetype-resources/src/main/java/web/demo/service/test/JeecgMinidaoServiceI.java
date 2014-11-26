#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.test;

import java.util.List;

import ${package}.web.demo.entity.test.JeecgMinidaoEntity;

/**
 * Minidao例子
 * @author fancq
 *
 */
public interface JeecgMinidaoServiceI {
	public List<JeecgMinidaoEntity> listAll(JeecgMinidaoEntity jeecgMinidao, int page, int rows);
	
	public JeecgMinidaoEntity getEntity(Class clazz, String id);
	
	public void insert(JeecgMinidaoEntity jeecgMinidao);
	
	public void update(JeecgMinidaoEntity jeecgMinidao);
	
	public void delete(JeecgMinidaoEntity jeecgMinidao);
	
	public void deleteAllEntitie(List<JeecgMinidaoEntity> entities);
	
	public Integer getCount();
	
	public Integer getSumSalary();
}
