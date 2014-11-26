#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.test;

import ${package}.core.common.service.CommonService;
import org.springframework.web.multipart.MultipartFile;

public interface JeecgBlobDataServiceI extends CommonService{
	public void saveObj(String documentTitle, MultipartFile file);

}
