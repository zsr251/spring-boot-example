package com.jc.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by jasonzhu on 2017/7/27.
 */
@Configuration
@EnableCaching
public class CacheConfig {
    /*
    @Cacheable  在方法执行前spring先检查缓存中是否有数据，如果有直接返回缓存数据，如果没有，调用方法将方法返回值放入缓存
    @CachePut   无论怎样，都会将方法的返回值放入缓存
    @CacheEvict 将一条或多条数据从缓存中删除
    @Caching    通过该注解组合多个注解策略在一个方法上
     */
    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

}
