#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.dao.test;

import java.util.List;
import java.util.Map;

import ${package}.web.demo.entity.test.JeecgMinidaoEntity;

import ${package}.minidao.annotation.Arguments;
import ${package}.minidao.annotation.MiniDao;
import ${package}.minidao.annotation.ResultType;
import ${package}.minidao.annotation.Sql;
import ${package}.minidao.hibernate.MiniDaoSupportHiber;

/**
 * Minidao例子
 * @author fancq
 * 
 */
@MiniDao
public interface JeecgMinidaoDao extends MiniDaoSupportHiber<JeecgMinidaoEntity> {
	@Arguments({"jeecgMinidao", "page", "rows"})
	public List<Map> getAllEntities(JeecgMinidaoEntity jeecgMinidao, int page, int rows);

	@Arguments({"jeecgMinidao", "page", "rows"})
	@ResultType("${package}.web.demo.entity.test.JeecgMinidaoEntity")
	public List<JeecgMinidaoEntity> getAllEntities2(JeecgMinidaoEntity jeecgMinidao, int page, int rows);

	//@Arguments("id")
	//JeecgMinidaoEntity getJeecgMinidao(String id);

	@Sql("SELECT count(*) FROM jeecg_minidao")
	Integer getCount();

	@Sql("SELECT SUM(salary) FROM jeecg_minidao")
	Integer getSumSalary();

	/*@Arguments("jeecgMinidao")
	int update(JeecgMinidaoEntity jeecgMinidao);

	@Arguments("jeecgMinidao")
	void insert(JeecgMinidaoEntity jeecgMinidao);

	@Arguments("jeecgMinidao")
	void delete(JeecgMinidaoEntity jeecgMinidao);*/
}
