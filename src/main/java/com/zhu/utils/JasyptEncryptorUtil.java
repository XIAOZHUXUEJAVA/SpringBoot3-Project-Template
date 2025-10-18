package com.zhu.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * Jasypt 加密工具类
 * 用于生成加密后的配置值
 *
 * 使用方法：
 * 1. 修改 SECRET_KEY 为你的密钥（与配置类中的密钥保持一致）
 * 2. 运行 main 方法，输入需要加密的内容
 * 3. 将加密后的结果复制到配置文件中，格式为：ENC(加密结果)
 * 
 * @author xiaozhu
 */
public class JasyptEncryptorUtil {

    /**
     * 加密密钥（必须与 JasyptConfig 中的密钥一致）
     * 生产环境建议使用复杂的密钥，并通过环境变量传入
     */
    private static final String SECRET_KEY = "xiaozhu";

    /**
     * 加密方法
     *
     * @param plainText 明文
     * @return 加密后的密文
     */
    public static String encrypt(String plainText) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(SECRET_KEY);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);
        return encryptor.encrypt(plainText);
    }

    /**
     * 解密方法
     *
     * @param encryptedText 密文
     * @return 解密后的明文
     */
    public static String decrypt(String encryptedText) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(SECRET_KEY);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);
        return encryptor.decrypt(encryptedText);
    }

    /**
     * 测试方法
     * 运行此方法来加密你的敏感信息
     */
    public static void main(String[] args) {
        // 需要加密的敏感信息
        String dbPassword = "123456";
        String redisPassword = "123456";

        // 加密
        String encryptedDbPassword = encrypt(dbPassword);
        String encryptedRedisPassword = encrypt(redisPassword);

        System.out.println("=".repeat(80));
        System.out.println("Jasypt 加密结果");
        System.out.println("=".repeat(80));
        System.out.println();

        System.out.println("数据库密码加密结果：");
        System.out.println("明文: " + dbPassword);
        System.out.println("密文: " + encryptedDbPassword);
        System.out.println("配置文件中使用: ENC(" + encryptedDbPassword + ")");
        System.out.println();

        System.out.println("Redis密码加密结果：");
        System.out.println("明文: " + redisPassword);
        System.out.println("密文: " + encryptedRedisPassword);
        System.out.println("配置文件中使用: ENC(" + encryptedRedisPassword + ")");
        System.out.println();

        System.out.println("=".repeat(80));
        System.out.println("验证解密：");
        System.out.println("=".repeat(80));
        System.out.println("数据库密码解密: " + decrypt(encryptedDbPassword));
        System.out.println("Redis密码解密: " + decrypt(encryptedRedisPassword));
        System.out.println();

        System.out.println("=".repeat(80));
        System.out.println("使用说明：");
        System.out.println("=".repeat(80));
        System.out.println("1. 将上面的加密结果复制到 application-dev.yml 中");
        System.out.println("2. 格式示例: password: ENC(加密后的内容)");
        System.out.println("3. 启动应用时需要传入密钥:");
        System.out.println("   方式一：在配置文件中添加 jasypt.encryptor.password: " + SECRET_KEY);
        System.out.println("   方式二：启动参数 -Djasypt.encryptor.password=" + SECRET_KEY);
        System.out.println("   方式三：环境变量 JASYPT_ENCRYPTOR_PASSWORD=" + SECRET_KEY);
        System.out.println("=".repeat(80));
    }
}
