package org.example.interceptor;

import com.google.common.collect.ImmutableList;
import org.apache.tomcat.util.buf.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.Enum.LimitType;
import org.example.annotaions.Limit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * 限流切面实现
 */

@Aspect
@Configuration
public class LimitInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LimitInterceptor.class);

    private static final String UNKNOWN = "unKnown";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public LimitInterceptor(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    @Around("execution(public * *(..)) && @annotation(org.example.annotaions.Limit)")
    public Object interceptor(ProceedingJoinPoint pjp){
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();

        String key ;
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();

        switch (limitType){
            case CUSTOMER:
                key = limitAnnotation.key();
                break;
            case IP:
                key = getIpAddress();
                break;
            default:
                key = method.getName().toUpperCase();
        }


        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(Collections.singleton(limitAnnotation.prefix())),key);


        try {
            String luaScript = buildLuaScript();
            RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
            Long count = redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
            logger.info("Access try count is {} for name={} and key = {}", count, name, key);
            if (count != null && count <= limitCount) {
                return pjp.proceed();
            } else {
                throw new RuntimeException("You have been dragged into the blacklist");
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }
    }

    public String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }


    /**
     * 获取IP地址
     */

    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = extractIp(request.getHeader("X-Forwarded-For"));
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = extractIp(request.getHeader("Proxy-Client-IP"));
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = extractIp(request.getHeader("WL-Proxy-Client-IP"));
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                // 如果是本地环回接口，表示是在服务器本地访问
                // 这时可以尝试获取服务器外网IP
                // 这个值可能根据部署环境不同而不同
                ip = "服务器外网IP";
            }
        }
        return ip;
    }

    private String extractIp(String ip) {
        if (ip != null && !ip.isEmpty() && !UNKNOWN.equalsIgnoreCase(ip)) {
            if (ip.contains(",")) {
                return ip.split(",")[0]; // 返回第一个IP
            } else {
                return ip;
            }
        }
        return null;
    }
}
