#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.system.dao.repair;

import ${package}.minidao.annotation.MiniDao;

/**
 * 工程修复
 * @author JueYue
 * @date 2013-11-11
 * @version 1.0
 */
@MiniDao
public interface RepairDao {
	
	public void batchRepairTerritory();

}
