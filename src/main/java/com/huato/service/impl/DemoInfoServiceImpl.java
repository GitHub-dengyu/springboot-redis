package com.huato.service.impl;

import com.huato.entity.UserInfo;
import com.huato.repository.UserInfoRepository;
import com.huato.service.DemoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class DemoInfoServiceImpl implements DemoInfoService {
    @Autowired
    private UserInfoRepository demoInfoRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    @Cacheable(value = "demoInfo",key = "#id+''")
    public UserInfo findById(long id) {
        System.err.println("---从数据库中获取的id  "+id);
        return demoInfoRepository.findDemoInfo(id);
    }

    @Override
    @CacheEvict(value ="demoInfo",key="#id+''")
    public void deleteFromCache(long id) {
        System.err.println("从缓存删除数据");
    }

    @Override
    public void test() {
        ValueOperations<String,String > valueOperations = redisTemplate.opsForValue();
        valueOperations.set("key","random"+Math.random());
        System.out.println(valueOperations.get("key"));

    }
}
