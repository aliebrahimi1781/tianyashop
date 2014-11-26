#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.test;

import ${package}.web.demo.entity.test.JeecgJdbcEntity;
import net.sf.json.JSONObject;

import ${package}.core.common.model.json.DataGrid;
import ${package}.core.common.service.CommonService;

public interface JeecgJdbcServiceI extends CommonService{
	public void getDatagrid1(JeecgJdbcEntity pageObj, DataGrid dataGrid);
	public void getDatagrid2(JeecgJdbcEntity pageObj, DataGrid dataGrid);
	public JSONObject getDatagrid3(JeecgJdbcEntity pageObj, DataGrid dataGrid);
	public void listAllByJdbc(DataGrid dataGrid);
}
