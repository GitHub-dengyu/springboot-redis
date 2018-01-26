package com.huato.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching  //启用缓存
public class RedisCacheConfig extends CachingConfigurerSupport {

    //Spring注入,在application.properties中进行配置
    @Autowired
    private volatile JedisConnectionFactory jedisConnectionFactory;

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate){
        CacheManager cacheManager = new RedisCacheManager(redisTemplate);
        return cacheManager;
    }

    //redisTemplate模板操作类
    @Bean
    public RedisTemplate<String,Object> redisTemplate() {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        //解决乱码问题，是不是你在redis客户端发现有十六进制的东东或者有乱码，这个就派上用场了
        //设置key序列化方式
        RedisSerializer<String> redisStringSerializer = new StringRedisSerializer() ;
        redisTemplate.setKeySerializer(redisStringSerializer);
        redisTemplate.setHashKeySerializer(redisStringSerializer);
        //设置value的序列化方式  json方式存放
        RedisSerializer<Object> redisSerializer = new GenericJackson2JsonRedisSerializer() ;
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        return redisTemplate;
    }

    //重写定义自己的key生成策略的
    @Override
    public KeyGenerator keyGenerator() {
        System.out.println("RedisCacheManager keyGenerator");
        //lamda表达式
        return (o,method,objects) -> {
            StringBuffer sb = new StringBuffer();
            // 类名 + 方法名 + 参数名
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object obj:objects) {
                sb.append(obj.toString());
            }
            System.out.println("keyGenerator" + sb.toString());
            return  sb.toString();
        };
        //匿名内部类
        /*return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuffer sb = new StringBuffer();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj:objects) {
                    sb.append(obj.toString());
                }
                System.out.println("keyGenerator" + sb.toString());
                return  sb.toString();
            }
        };*/
    }
}
