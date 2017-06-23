package com.commons.monitor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Map;
import java.util.Set;

/**
 * @author jiayongming
 *         该类实现了org.aopalliance.intercept.MethodInterceptor接口
 *         记录方法的运行时间,单位为毫秒
 *         日志标志为 invokeTimeMonitor
 *         Logger.getLogger("invokeTimeMonitor");
 */
@Slf4j(topic = "invokeTimeMonitor")
public class MethodInvokeTime implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 用StopWatch 计时
        StopWatch clock = new StopWatch();
        // 计时开始
        clock.start();

        StringBuilder sb = new StringBuilder();
        sb.append("method[");
        // 监控的类名.方法名
        String className = invocation.getMethod().getDeclaringClass().getName();
        sb.append(className);
        sb.append(".");
        sb.append(invocation.getMethod().getName());
        sb.append("(");
        Object result;
        try {
            // 这个是我们监控的bean的执行并返回结果
            result = invocation.proceed();
        } catch (Throwable e) {
            // 监控的参数
            Object[] objs = invocation.getArguments();
            log.error(sb.toString() + this.getString(objs) + ")]：InvokeError.", e);
            throw e;
        }

        // 计时结束
        clock.stop();

        log.info("{})]：InvokeTime(millisecond): {}", sb.toString(), clock.getTime());

        return result;
    }

    /**
     * 获取执行方法的参数
     */
    public String getString(Object[] objs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = objs.length; i < len; i++) {
            if (objs[i] instanceof String) {
                sb.append("String类型：");
                sb.append(objs[i].toString());
            } else if (objs[i] instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) objs[i];
                final Set<String> set = map.keySet();
                sb.append("Map类型：");
                for (String str : set) {
                    sb.append(str);
                    sb.append("=");
                    sb.append(map.get(str));
                }
            } else if (objs[i] instanceof Integer) {
                sb.append("整数类型：");
                sb.append(objs[i].toString());
            } else {
                sb.append(objs[i].toString());
            }
        }
        return sb.toString();
    }

}
