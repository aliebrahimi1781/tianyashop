#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.impl.test;

import ${package}.web.demo.service.test.JeecgMatterBomServiceI;

import ${package}.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <li>类型名称：
 * <li>说明：物料Bom业务接口实现类
 * <li>创建人： 温俊
 * <li>创建日期：2013-8-12
 * <li>修改人： 
 * <li>修改日期：
 */
@Service("jeecgMatterBomService")
@Transactional
public class JeecgMatterBomServiceImpl extends CommonServiceImpl implements
		JeecgMatterBomServiceI {

}
