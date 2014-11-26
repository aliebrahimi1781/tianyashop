#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.test;

import java.util.List;

import ${package}.web.demo.entity.test.JeecgOrderCustomEntity;
import ${package}.web.demo.entity.test.JeecgOrderMainEntity;
import ${package}.web.demo.entity.test.JeecgOrderProductEntity;

import ${package}.core.common.service.CommonService;


public interface JeecgOrderMainServiceI extends CommonService{
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(JeecgOrderMainEntity jeecgOrderMain,List<JeecgOrderProductEntity> jeecgOrderProducList,List<JeecgOrderCustomEntity> jeecgOrderCustomList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(JeecgOrderMainEntity jeecgOrderMain,List<JeecgOrderProductEntity> jeecgOrderProducList,List<JeecgOrderCustomEntity> jeecgOrderCustomList,boolean jeecgOrderCustomShow) ;
	public void delMain (JeecgOrderMainEntity jeecgOrderMain);
}
