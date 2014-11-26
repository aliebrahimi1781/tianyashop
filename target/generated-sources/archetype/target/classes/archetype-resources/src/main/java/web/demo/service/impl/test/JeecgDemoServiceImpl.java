#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.impl.test;

import ${package}.web.demo.service.test.JeecgDemoServiceI;

import ${package}.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("jeecgDemoService")
@Transactional
public class JeecgDemoServiceImpl extends CommonServiceImpl implements JeecgDemoServiceI {
	
}
