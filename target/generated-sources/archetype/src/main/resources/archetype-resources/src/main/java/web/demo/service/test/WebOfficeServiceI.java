#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.test;

import ${package}.web.demo.entity.test.WebOfficeEntity;

import ${package}.core.common.service.CommonService;
import org.springframework.web.multipart.MultipartFile;

public interface WebOfficeServiceI extends CommonService{
	public void saveObj(WebOfficeEntity docObj, MultipartFile file);
}
