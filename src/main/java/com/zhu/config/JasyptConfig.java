package com.zhu.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jasypt 加密配置类
 * 用于加密配置文件中的敏感信息（如数据库密码、Redis密码等）
 * 
 * @author xiaozhu
 */
@Configuration
public class JasyptConfig {

    /**
     * 配置 Jasypt 加密器
     * 
     * 使用方式：
     * 1. 在配置文件中使用 ENC(加密后的内容) 格式
     * 2. 启动时通过 -Djasypt.encryptor.password=your-secret-key 传入密钥
     * 3. 或在配置文件中设置 jasypt.encryptor.password（不推荐生产环境使用）
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        
        // 加密密钥（从环境变量或系统属性获取，不要硬编码）
        // 优先级：系统属性 > 环境变量 > 默认值
        String password = System.getProperty("jasypt.encryptor.password");
        if (password == null || password.isEmpty()) {
            password = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        }
        if (password == null || password.isEmpty()) {
            // 默认密钥，仅用于开发环境，生产环境必须通过环境变量或启动参数传入
            password = "xiaozhu";
        }
        config.setPassword(password);
        
        // 加密算法
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        
        // 密钥获取迭代次数
        config.setKeyObtentionIterations("1000");
        
        // 加密池大小
        config.setPoolSize("1");
        
        // 盐值生成器
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        
        // 初始化向量生成器
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        
        // 输出类型
        config.setStringOutputType("base64");
        
        encryptor.setConfig(config);
        return encryptor;
    }
}
