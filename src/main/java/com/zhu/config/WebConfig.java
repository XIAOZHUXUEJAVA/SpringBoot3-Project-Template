package com.zhu.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 跨域配置类和日期序列化（转化成我们日常的那种格式）
 * @author xiaozhu
 * @date 2022年09月08日 22:13                          $
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {



    /*
     * 跨域配置
     * @author xiaozhu
     * @date 2022/10/3 23:33
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }




    /*
     * 将日期序列化
     * @author xiaozhu
     * @date 2022/10/3 23:33
     * @return org.springframework.http.converter.HttpMessageConverter
     */
    @Bean//使用@Bean注入fastJsonHttpMessageConvert
    public HttpMessageConverter<?> fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        
        // 设置支持的 MediaType
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        
        // 设置默认字符集为 UTF-8
        fastConverter.setDefaultCharset(StandardCharsets.UTF_8);
        
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        
        // 设置字符编码为 UTF-8
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        
        // 设置日期格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // 配置序列化特性：格式化输出、将Long转为String（避免前端精度丢失）
        fastJsonConfig.setWriterFeatures(
            JSONWriter.Feature.PrettyFormat,
            JSONWriter.Feature.WriteLongAsString
        );
        
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 添加 StringHttpMessageConverter 并设置为 UTF-8（用于处理纯文本响应）
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(0, stringConverter);
        
        // 添加 FastJson 转换器
        converters.add(1, fastJsonHttpMessageConverters());
    }

}
