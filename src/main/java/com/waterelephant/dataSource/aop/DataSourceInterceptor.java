package com.waterelephant.dataSource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.waterelephant.dataSource.DataSource;
import com.waterelephant.dataSource.DataSourceHolderManager;

@Aspect
@Component
@Order(0)
public class DataSourceInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(DataSourceInterceptor.class);
	
	public DataSourceInterceptor() {
		logger.info("----动态数据源aop:Aspect 启动 ----");
	}
	
	@Pointcut("execution(* com.waterelephant.xg.service.*.*(..)) || execution(* com.waterelephant.mf.service.*.*(..))")
    public void sxfqDataSource(){};
    
    @Before("sxfqDataSource()")
    public void before(JoinPoint jp) {
        DataSourceHolderManager.set(DataSource.MASTER_NEW);
        logger.info("----{}.{}方法切换数据源到：{}----",jp.getTarget().getClass().getName(),jp.getSignature().getName(),DataSourceHolderManager.get().name());
    }
    
    
    @After("sxfqDataSource()")
    public void after(JoinPoint jp) {
        DataSourceHolderManager.remove();
        logger.info("----{}.{}方法执行完毕，切换到数据源:{} END----",jp.getTarget().getClass().getName(),jp.getSignature().getName(),DataSourceHolderManager.get().name());
    }
}
