#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.idea.huodong.service.impl;



import ${package}.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.idea.huodong.service.PrizeRecordServiceI;

@Service("prizeRecordService")
@Transactional
public class PrizeRecordServiceImpl extends CommonServiceImpl implements PrizeRecordServiceI {
	
}