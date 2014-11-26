#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.impl.test;

import java.util.Date;

import org.springframework.stereotype.Service;

import ${package}.web.demo.service.test.TaskDemoServiceI;
@Service("taskDemoService")
public class TaskDemoServiceImpl implements TaskDemoServiceI {

	
	public void work() {
		${package}.core.util.LogUtil.info(new Date().getTime());
		${package}.core.util.LogUtil.info("----------任务测试-------");
	}

}
