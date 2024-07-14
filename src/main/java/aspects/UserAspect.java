package aspects;

import dao.AuditDao;
import dto.Audit;
import dto.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

/**
 * Аспект для действий пользователя
 */
@Aspect
public class UserAspect {
    /**
     * Срез для бронирования
     */
    @Pointcut("public execution(services.BookingService.book(..))")
    public void bookPointcut() {
    }

    /**
     * Срез для отмены бронироавния
     */
    @Pointcut("public execution(services.BookingService.cancelBooking(..))")
    public void cancelPointcut() {
    }

    /**
     * Срез для входа
     */
    @Pointcut("public execution(services.AuthorizationService.login(..))")
    public void loginPointcut() {}

    private final AuditDao auditDao = new AuditDao();

    /**
     * Добавления аудита для бронирования
     * @param joinPoint
     */
    @Before("bookPointcut()")
    public void bookBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Audit audit = new Audit();
        audit.setUser((User) args[0]);
        audit.setDate(new Date());
        audit.setCommand("Бронирование");
        auditDao.createAudit(audit);
        System.out.println("Бронирование пользователем:" + audit.getUser().getUsername());
    }

    /**
     * Добавление аудита для отмены бронирования
     * @param joinPoint
     */
    @Before("cancelPointcut()")
    public void cancelBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Audit audit = new Audit();
        audit.setUser((User) args[0]);
        audit.setDate(new Date());
        audit.setCommand("Отмена");
        auditDao.createAudit(audit);
        System.out.println("Отмена бронирования пользователем:" + audit.getUser().getUsername());
    }

    /**
     * Аудит при входе пользвателя
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("loginPointcut()")
    public Object loginAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Audit audit = new Audit();
        audit.setDate(new Date());
        audit.setCommand("Вход");
        Object result = joinPoint.proceed(args);
        audit.setUser((User) result);
        auditDao.createAudit(audit);
        return result;
    }
}
