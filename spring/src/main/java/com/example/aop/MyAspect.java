package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component // 1. 交给 Spring 管理
@Aspect    // 2. 声明这是一个切面类
public class MyAspect {
    // 3. 切点表达式抽取（优化），直接锁定你的 Target 类的 save 方法
    @Pointcut("execution(* com.example.aop.Target.save(..))")
    public void pointCut() {}

    // 前置增强：在目标方法执行前执行
    // 4. 前置通知：引用上面的切点
    @Before("pointCut()")
    public void before() {
        System.out.println("【前置增强】权限校验...");
    }

    // 5. 后置通知：引用上面的切点
    @AfterReturning("pointCut()")
    public void afterReturning() {
        System.out.println("【后置增强】记录日志...");
    }

    // 环绕增强：目标方法前后都执行
    // 6. 环绕通知：引用上面的切点
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("【环绕增强】目标方法前...");
        Object proceed = pjp.proceed(); // 执行目标方法
        System.out.println("【环绕增强】目标方法后...");
        return proceed;
    }

    // 异常抛出增强
    @AfterThrowing("pointCut()")
    public void afterThrowing() {
        System.out.println("【异常增强】出错了，回滚...");
    }

    // 最终增强（无论是否异常都执行）
    @After("pointCut()")
    public void after() {
        System.out.println("【最终增强】释放资源...");
    }
}
