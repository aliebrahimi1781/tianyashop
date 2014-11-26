#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.idea.huodong.service.impl;



import ${package}.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.idea.huodong.service.HdRecordServiceI;

@Service("hdRecordService")
@Transactional
public class HdRecordServiceImpl extends CommonServiceImpl implements HdRecordServiceI {
	
}