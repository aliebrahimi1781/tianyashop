#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job {

 
//    @Scheduled(cron="*/10 * * * * *") 
//    public void s10(){
//        ${package}.core.util.LogUtil.info("==== 十秒执行一次=======10s");
//    }
//    
//    @Scheduled(cron="0 */1 * * * *") 
//    public void m1(){
//        ${package}.core.util.LogUtil.info("1m");
//    }
    
    /**
     * 每天1点执行一次
     * */
    @Scheduled(cron="0 0 1 * * ?") 
    public void oneOClockPerDay(){
        ${package}.core.util.LogUtil.info("1h");
    }
    
    
    
}