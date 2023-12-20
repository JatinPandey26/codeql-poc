//package com.kipu_fav.write_module;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Slf4j
//@Component
//public class LogSanitizationAspect {
//
//
//
//    @Before("  execution(* com.kipu_fav.write_module.Service.*.*(..))")
//    public void sanitizeLogArguments(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        log.info("args: {}", args);
//        // Implement your logic to sanitize each argument in args array
//        sanitizeArgs(args);
//    }
//
//    @Before("within(com.kipu_fav.write_module.Controllers..*) && execution(* org.slf4j.Logger.info(String)) && args(logMessage)")
//    public void interceptLogStatement(JoinPoint joinPoint, String logMessage) {
//        // Modify the log message or perform any desired actions
//        String modifiedLogMessage = "Modified Log: " + logMessage;
//        log.info("here");
//        // Log the modified message
//        log.info(modifiedLogMessage);
//    }
//
//
//
//    private void sanitizeArgs(Object[] args) {
//        // Implement your sanitization logic for each argument here
//        // For example, iterate through args and replace sensitive information
//    }
//}
//
