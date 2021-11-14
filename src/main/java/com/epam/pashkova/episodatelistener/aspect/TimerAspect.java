package com.epam.pashkova.episodatelistener.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimerAspect.class);

    @Pointcut("within(com.epam.pashkova.episodatelistener.controller..*)")
    public void controllerPointCut() {
    }

    @Around("controllerPointCut()")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        StopWatch stopWatch = new StopWatch(methodName);
        stopWatch.start();
        Object returnObject;
        try {
            returnObject = proceedingJoinPoint.proceed();
        } finally {
            stopWatch.stop();
            LOGGER.info("[{}] Execution time: {}", methodName, stopWatch.getTotalTimeSeconds());
        }
        return returnObject;
    }
}
