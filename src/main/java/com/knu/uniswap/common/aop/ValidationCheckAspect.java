package com.knu.uniswap.common.aop;

import com.knu.uniswap.common.constant.ErrorMessage;
import com.knu.uniswap.common.exception.ValidationException;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
@Aspect
public class ValidationCheckAspect {

    @Around("execution(* com.knu.uniswap..controller..*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg: args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error: bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errorMap);
                }

            }
        }

        return proceedingJoinPoint.proceed();
    }

}
