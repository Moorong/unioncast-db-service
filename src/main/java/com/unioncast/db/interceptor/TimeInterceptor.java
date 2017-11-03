package com.unioncast.db.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangzhe
 * @date 2016/11/7 15:41
 * 检测SQL语句方法执行耗时的spring切面类
 */
@Aspect
@Configuration
public class TimeInterceptor {

    private static Logger logger = LogManager.getLogger(TimeInterceptor.class);

    // 一秒钟，即1000ms
    //private static final long ONE_MINUTE = 1000;

    // service层的统计耗时切面，类型必须为final String类型的,注解里要使用的变量只能是静态常量类型的
    //public static final String POINT = "execution (* com.unioncast.db.rdbms.core.dao.*.*(..))";


    @Pointcut("execution(* com.unioncast.db.rdbms.core.dao.commonDBDao.impl.*.*(..))||execution(* com.unioncast.db.rdbms.core.dao.adxDBDao.impl.*.*(..))||execution(* com.unioncast.db.rdbms.common.dao.*.*(..))")
    //@Pointcut("execution(* com.unioncast.db.rdbms.core.dao..*.*(..))")
    //@Pointcut("execution(* com.unioncast.db.rdbms.common.dao.GeneralDao.*(..))")

    public void excudeService() {
    }

    /**
     * 统计方法执行耗时Around环绕通知
     *
     * @param joinPoint
     * @return
     */
    @Around("excudeService()")
    public Object timeAround(ProceedingJoinPoint joinPoint) {
        //System.err.println ("切面执行了!!!");
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();

        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable e) {
            /*logger.info("统计SQL方法执行耗时环绕通知出错", e);*/
        }

        long endTime = System.currentTimeMillis();
        // 获取执行的方法名

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

        // 打印耗时的信息
        this.printExecTime(methodName, startTime, endTime);

        return obj;
    }

    /**
     * 打印方法执行耗时的信息
     *
     * @param methodName
     * @param startTime
     * @param endTime
     */
    private void printExecTime(String methodName, long startTime, long endTime) {
        long diffTime = endTime - startTime;
        logger.info("-----" + methodName + " SQL语句方法执行耗时：" + diffTime + " ms");
//        if (diffTime > ONE_MINUTE) {
//            logger.warn("-----" + methodName + " 方法执行耗时：" + diffTime + " ms");
//        }
    }

}