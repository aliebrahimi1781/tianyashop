#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.idea.huodong.service.impl;

import ${package}.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.idea.huodong.service.HuodongServiceI;

@Service("huodongService")
@Transactional
public class HuodongServiceImpl extends CommonServiceImpl implements HuodongServiceI {
	
}