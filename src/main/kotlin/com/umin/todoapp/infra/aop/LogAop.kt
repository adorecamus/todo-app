package com.umin.todoapp.infra.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LogAop {

    private val logger = LoggerFactory.getLogger("Controller Logger")

    @Pointcut("execution(* com.umin.todoapp.domain.*.controller.*.*(..))")
    fun cut() {}

    @Before("cut()")
    fun before(joinPoint: JoinPoint) {

        val methodName = joinPoint.signature.name
        val methodArguments = joinPoint.args

        logger.info("Method Name: $methodName | Arguments: ${methodArguments.joinToString(", ")}")
    }

    @AfterReturning(pointcut = "cut()", returning = "obj")
    fun afterReturning(joinPoint: JoinPoint, obj: Any) {
        logger.info("return {}", obj)
    }

    @AfterThrowing(pointcut = "cut()", throwing = "e")
    fun afterThrowing(joinPoint: JoinPoint, e: Exception) {
        logger.error(e.toString())
    }

}