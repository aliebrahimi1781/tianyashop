#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.test;

import ${package}.web.demo.entity.test.TFinanceEntity;
import ${package}.web.demo.entity.test.TFinanceFilesEntity;

import ${package}.core.common.service.CommonService;

public interface TFinanceServiceI extends CommonService{

	void deleteFile(TFinanceFilesEntity file);

	void deleteFinance(TFinanceEntity finance);

}
