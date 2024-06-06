package course.courseWork.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class TaskExecutionAspect {

    @Pointcut("execution(public * course.courseWork.controller.TaskController.addTask(..))")
    public void callAddTask() {
    }

    @Around(value = "callAddTask()")
    public Object aroundAddTask(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        logging(proceedingJoinPoint, executionTime);
        return result;
    }

    @Pointcut("execution(public * course.courseWork.controller.TaskController.deleteTaskByTitleAndUser(..))")
    public void callDeleteTaskByTitleAndUser() {
    }

    @Around(value = "callDeleteTaskByTitleAndUser()")
    public Object aroundDeleteTaskByTitleAndUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        logging(proceedingJoinPoint, executionTime);
        return result;
    }

    private static void logging(ProceedingJoinPoint proceedingJoinPoint, long executionTime) {
        log.info("User {}. Execution {} time: {} ms",
                SecurityContextHolder.getContext().getAuthentication().getName(),
                proceedingJoinPoint.getSignature().getDeclaringTypeName(), executionTime);
    }

}
