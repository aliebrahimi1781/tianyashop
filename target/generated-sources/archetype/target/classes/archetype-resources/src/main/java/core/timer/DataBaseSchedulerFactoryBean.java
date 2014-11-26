#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.timer;

import ${package}.web.system.pojo.base.TSTimeTaskEntity;
import ${package}.web.system.service.TimeTaskServiceI;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
/**
 * 读取数据库 然后判断是否启动任务
 * @author JueYue
 * @date 2013-9-22
 * @version 1.0
 */
public class DataBaseSchedulerFactoryBean extends SchedulerFactoryBean {
	
	@Autowired
	private TimeTaskServiceI timeTaskService;
	/**
	 * 读取数据库判断是否开始定时任务
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		String[] trigerrNames = this.getScheduler().getTriggerNames(Scheduler.DEFAULT_GROUP);
		TSTimeTaskEntity task;
		
		for (String trigerrName : trigerrNames) {
			task = timeTaskService.findUniqueByProperty(TSTimeTaskEntity.class,"taskId",trigerrName);
			//数据库查询不到的定时任务或者定时任务的运行状态不为1时，都停止
			//TASK ${symbol_pound}327 定时器任务默认未启动 
			if(task==null || !"1".equals(task.getIsStart())){
				this.getScheduler().pauseTrigger(trigerrName,Scheduler.DEFAULT_GROUP);
			}
		}
	}

}
