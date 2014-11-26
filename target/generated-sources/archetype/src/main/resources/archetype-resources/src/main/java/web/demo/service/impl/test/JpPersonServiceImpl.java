#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.web.demo.service.test.JpPersonServiceI;
import ${package}.core.common.service.impl.CommonServiceImpl;

@Service("jpPersonService")
@Transactional
public class JpPersonServiceImpl extends CommonServiceImpl implements JpPersonServiceI {
	
}