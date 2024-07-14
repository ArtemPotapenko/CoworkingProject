package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

/**
 * Класс для логирования
 */
@Aspect
public class Logger {
    @Pointcut("execution(..)")
    public void bookPointcut() {
    }

    @Around("bookPointcut")
    public void aroundBook(ProceedingJoinPoint joinPoint) throws Throwable {
        Date startTime = new Date();
        joinPoint.proceed();
        Date endTime = new Date();
        long elapsedTime = endTime.getTime() - startTime.getTime();
        System.out.println("Время работы метода " + joinPoint.getSignature().getDeclaringTypeName() + ": " + elapsedTime);
    }

}
