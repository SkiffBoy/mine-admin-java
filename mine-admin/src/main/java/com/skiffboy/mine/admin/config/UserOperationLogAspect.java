package com.skiffboy.mine.admin.config;

import com.skiffboy.mine.admin.mapper.logstash.UserOperationLogMapper;
import com.skiffboy.mine.admin.model.entity.UserOperationLogEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.util.RequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class UserOperationLogAspect {
    private final UserOperationLogMapper userOperationLogMapper;

    public UserOperationLogAspect(UserOperationLogMapper userOperationLogMapper) {
        this.userOperationLogMapper = userOperationLogMapper;
    }

    @Pointcut("@annotation(io.swagger.v3.oas.annotations.Operation)")
    public void operationLogPointcut() {

    }

    @Around("operationLogPointcut()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        if (!CurrentUser.isLogin()) {
            return pjp.proceed();
        }
        Method originMethod = resolveMethod(pjp);

        Object result;

        // 保存操作日志
        UserOperationLogEntity log = new UserOperationLogEntity();
        try {
            log.setCreatedAt(new Date());
            HttpServletRequest request = RequestUtil.getRequest();
            Operation annotation = originMethod.getAnnotation(Operation.class);
            String uri = request.getRequestURI().substring(request.getContextPath().length());
            log.setUsername(CurrentUser.getUsername());
            log.setMethod(request.getMethod());
            log.setRouter(uri);
            log.setServiceName(annotation.summary());
            log.setIp(CurrentUser.getIp());

            // 执行方法
            result = pjp.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            userOperationLogMapper.insert(log);
        }

        return result;
    }

    protected Method resolveMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        Method method = getDeclaredMethodFor(targetClass, signature.getName(),
                signature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("Cannot resolve target method: " + signature.getMethod().getName());
        }
        return method;
    }

    /**
     * Get declared method with provided name and parameterTypes in given class and its super classes.
     * All parameters should be valid.
     *
     * @param clazz          class where the method is located
     * @param name           method name
     * @param parameterTypes method parameter type list
     * @return resolved method, null if not found
     */
    private Method getDeclaredMethodFor(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethodFor(superClass, name, parameterTypes);
            }
        }
        return null;
    }

}
