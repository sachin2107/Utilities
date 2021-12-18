package com.informatica.college.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
	
	@Pointcut(value="execute(* com.informatica.college.*.*.*(..)) && !within(com.informatica.college.constants)")
	public void servicePointcut() {
		
	}
	
	public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		String arg = null;
		try {
			String clsName = pjp.getTarget().getClass().toString();
			String methodName = pjp.getSignature().getName();
			int argNumbers = pjp.getArgs().length;
			Object[] argArray = pjp.getArgs();
			
			if(logger.isDebugEnabled()) {
				logger.debug("Debug enabled: with argument size=[{}]",argNumbers);
			}
			
			result = pjp.proceed();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
