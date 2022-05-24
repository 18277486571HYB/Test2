package com.hyb.test2.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    RedisTemplate<String,String> template;

    public  void setHash(String key,String key1,String value1,long timeout,TimeUnit timeUnit){
        //记录登录次数
        template.opsForHash().put(key,key1,value1);
//        System.out.println(redisTemplate.opsForHash().size("loginCount"));
        template.expire(key,timeout, timeUnit);
    }

    public void set(String key,String value,long timeout,TimeUnit timeUnit){
        template.opsForValue().set(key,value,timeout,timeUnit);
    }

    public void setCollectionSet(String key, String[] list, long timeout, TimeUnit timeUnit){

        template.opsForSet().add(key, list);
        template.expire(key,timeout,timeUnit);
    }



    public String get(String key){
        return template.opsForValue().get(key);
    }

    public void del(String key){
        template.delete(key);
    }


    public long countHash(String hashKey){
        return template.opsForHash().size(hashKey);
    }

    public boolean delHash(String hashKey){
        return Boolean.TRUE.equals(template.delete(hashKey));
    }


}
