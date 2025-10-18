package com.zhu.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置类
 * 
 * @author zhu
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置 MyBatis Plus 拦截器
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 创建分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        
        // 设置请求的页面大于最大页后操作，true调回到首页，false继续请求（默认false）
        paginationInnerInterceptor.setOverflow(false);
        
        // 设置单页分页条数限制，默认无限制
        paginationInnerInterceptor.setMaxLimit(500L);
        
        // 添加分页拦截器
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        
        return interceptor;
    }
}
