package com.skiffboy.mine.admin.config;


import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.skiffboy.mine.admin.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Duration;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public AjaxJson bindExceptionHandler(final BindException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" ; "));
        return AjaxJson.getError(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxJson handler(final MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" ; "));
        return AjaxJson.getError(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxJson handler(final ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(" ; "));
        return AjaxJson.getError(message);
    }

    // 全局异常拦截（拦截项目中的所有异常）
    @ExceptionHandler
    public AjaxJson handlerException(Exception e) {
        // 打印堆栈，以供调试
        if (log.isDebugEnabled()) {
            log.error(e.getMessage(), e);
        }

        // 不同异常返回不同状态码
        AjaxJson aj;
        if (e instanceof NotLoginException ee) {
            // 如果是未登录异常
            aj = AjaxJson.getNotLogin().setMessage(ee.getMessage());
        } else if (e instanceof NotRoleException ee) {
            // 如果是角色异常
            aj = AjaxJson.getNotJur("无此角色：" + ee.getRole());
        } else if (e instanceof NotPermissionException ee) {
            // 如果是权限异常
            aj = AjaxJson.getNotJur("无此权限：" + ee.getPermission());
        } else if (e instanceof DisableServiceException ee) {
            // 如果是权限异常
            Duration duration = Duration.ofSeconds(ee.getDisableTime());
            String restTimeString;
            if (duration.toMinutesPart() > 0) {
                restTimeString = String.format("%d分%d秒", duration.toMinutesPart(), duration.toSecondsPart());
            } else {
                restTimeString = ee.getDisableTime() + "秒";
            }
            aj = AjaxJson.getNotJur(String.format("该账号已被限制登录! (请在 %s 后重试)", restTimeString));
        } else if (e instanceof BusinessException ee) {
            // 如果是权限异常
            aj = AjaxJson.getError(ee.getResponse().getMessage());
        } else {
            // 普通异常, 输出：500 + 异常信息
            aj = AjaxJson.getError(e.getMessage());
            aj.setCause(e.getCause() != null ? e.getCause().getMessage() : null);
        }

        // 返回给前端
        return aj;
    }

}
