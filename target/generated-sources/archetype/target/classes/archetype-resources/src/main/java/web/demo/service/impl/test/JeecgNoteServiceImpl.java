#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.web.demo.service.test.JeecgNoteServiceI;
import ${package}.core.common.service.impl.CommonServiceImpl;

@Service("jeecgNoteService")
@Transactional
public class JeecgNoteServiceImpl extends CommonServiceImpl implements JeecgNoteServiceI {
	
}