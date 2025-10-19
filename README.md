# SpringBoot 3 项目开发模板

> 一个开箱即用的 SpringBoot 3 快速开发模板，集成常用技术栈和最佳实践，拉取即用。

## 📋 项目简介

本项目是一个基于 **Spring Boot 3.2.0** 和 **Java 17** 的企业级开发模板，集成了主流技术栈和开发规范，旨在帮助开发者快速搭建项目骨架，专注于业务开发。

## 🚀 技术栈

### 核心框架
- **Spring Boot**: 3.2.0
- **Java**: 17
- **Maven**: 项目管理工具

### 数据层
- **MyBatis Plus**: 3.5.7 - 增强版 MyBatis，简化 CRUD 操作
- **MySQL**: 数据库（使用 `mysql-connector-j` 驱动）
- **Redis**: 分布式缓存

### 安全认证
- **Spring Security**: 安全框架
- **JWT**: 0.12.5 - JSON Web Token 认证

### 工具库
- **Lombok**: 简化实体类开发
- **FastJSON2**: 2.0.52 - 阿里巴巴 JSON 处理库（支持 Spring Boot 3）
- **Jasypt**: 3.0.5 - 配置文件加密

### 文档与接口
- **SpringDoc OpenAPI**: 2.3.0 - API 文档（替代 Springfox，支持 Spring Boot 3）

### 文件处理
- **阿里云 OSS**: 3.18.1 - 对象存储服务
- **EasyExcel**: 4.0.3 - Excel 导入导出

### 其他
- **Spring AOP**: 面向切面编程
- **Spring Validation**: 参数校验

## 📁 项目结构

```
src/main/java/com/zhu/
├── annoation/          # 自定义注解（如 @SystemLog）
├── aspect/             # AOP 切面（如日志切面）
├── common/             # 公共模块
│   ├── constants/      # 常量定义
│   ├── enums/          # 枚举类（如响应状态码）
│   └── result/         # 统一响应结果封装
├── config/             # 配置类
│   ├── FastJsonRedisSerializer.java  # Redis 序列化配置
│   ├── JasyptConfig.java             # 配置加密
│   ├── MybatisPlusConfig.java        # MyBatis Plus 配置
│   ├── RedisConfig.java              # Redis 配置
│   └── WebConfig.java                # Web 配置
├── controller/         # 控制器层
├── converter/          # 对象转换器（DTO/VO/Entity 转换）
├── domain/             # 领域模型
│   ├── dto/            # 数据传输对象（请求参数）
│   ├── entity/         # 实体类（数据库映射）
│   └── vo/             # 视图对象（响应数据）
├── handler/            # 处理器
│   └── exception/      # 全局异常处理
├── mapper/             # MyBatis Mapper 接口
├── security/           # Spring Security 配置
├── service/            # 服务层
│   └── impl/           # 服务实现类
└── utils/              # 工具类
```

## 🎯 核心功能

### 1. 统一响应封装
使用 `ResponseResult<T>` 统一封装接口响应：
```java
// 成功响应
return ResponseResult.okResult(data);

// 失败响应
return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
```

### 2. 全局异常处理
通过 `GlobalExceptionHandler` 统一处理异常：
- 系统异常（`SystemException`）
- 参数校验异常（`MethodArgumentNotValidException`）
- 资源未找到异常（`NoResourceFoundException`）
- 其他通用异常

### 3. 字典数据管理
支持数据库动态管理字典数据，内置缓存机制：
```java
// 获取字典描述
String typeDesc = DictUtils.getTypeDesc("0");  // 普通用户
String statusDesc = DictUtils.getStatusDesc("0");  // 正常
String sexDesc = DictUtils.getSexDesc("1");  // 女
```
详见：[字典功能使用说明](docs/字典功能使用说明.md)

### 4. 对象转换规范
使用 `Converter` 模式进行对象转换：
```java
// DTO -> Entity
User user = UserConverter.toEntity(userAddDTO);

// Entity -> VO（自动填充业务描述字段）
UserVO userVO = UserConverter.toVO(user);
```

### 5. 日志记录
使用 `@SystemLog` 注解记录接口日志：
```java
@SystemLog(businessName = "用户查询")
@GetMapping("/list")
public ResponseResult getUserList() {
    // ...
}
```

### 6. 配置文件加密
使用 Jasypt 加密敏感配置：
```yaml
spring:
  datasource:
    password: ENC(加密后的密码)
```

## 📝 编码规范

### 1. 分层规范
- **Controller**: 仅负责接收请求、参数校验、调用 Service、返回响应
- **Service**: 业务逻辑处理
- **Mapper**: 数据访问层，仅负责数据库操作
- **Converter**: 对象转换逻辑
- **Utils**: 通用工具类，无状态

### 2. 命名规范
- **实体类（Entity）**: 与数据库表对应，使用 `@TableName` 注解
- **DTO（Data Transfer Object）**: 接收请求参数，命名以 `DTO` 结尾
- **VO（View Object）**: 返回给前端的数据，命名以 `VO` 结尾
- **Mapper**: 接口命名以 `Mapper` 结尾
- **Service**: 接口命名以 `Service` 结尾，实现类以 `ServiceImpl` 结尾

### 3. 注解使用规范
- **实体类**: 使用 `@Data`、`@AllArgsConstructor`、`@NoArgsConstructor`（Lombok）
- **参数校验**: 使用 `@NotBlank`、`@NotNull` 等 Validation 注解
- **API 文档**: 使用 `@Schema` 注解（SpringDoc）
- **事务管理**: 在 Service 层使用 `@Transactional`

### 4. 异常处理规范
- 业务异常抛出 `SystemException`，由全局异常处理器统一处理
- 避免在 Controller 中使用 try-catch
- 异常信息使用枚举 `AppHttpCodeEnum` 统一管理

### 5. 对象转换规范
- 禁止在 Controller/Service 中直接进行对象转换
- 统一使用 `Converter` 类进行转换
- 简单属性拷贝使用 `BeanCopyUtils.copyBean()`
- 复杂转换逻辑（如添加业务描述字段）在 Converter 中处理

### 6. 日志规范
- 使用 `@Slf4j` 注解（Lombok）
- 日志级别：
  - `error`: 系统错误、异常
  - `warn`: 警告信息
  - `info`: 关键业务流程
  - `debug`: 调试信息

## 🔧 快速开始

### 1. 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+

### 2. 配置修改
修改 `src/main/resources/application-dev.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  
  data:
    redis:
      host: localhost
      port: 6379
```

### 3. 加密敏感配置（可选）
如果需要加密数据库密码、Redis 密码等敏感信息：

1. 修改 `src/main/java/com/zhu/utils/JasyptEncryptorUtil.java` 中的明文密码
2. 运行 `JasyptEncryptorUtil.main()` 方法生成加密密文
3. 将加密结果复制到配置文件中，格式：`password: ENC(加密后的内容)`
4. 确保 `jasypt.encryptor.password` 配置正确（与 JasyptEncryptorUtil 中的 SECRET_KEY 一致）

```bash
# 运行加密工具
mvn exec:java -Dexec.mainClass="com.zhu.utils.JasyptEncryptorUtil"
```

### 4. 初始化数据库
执行 `sql/sys_dict.sql` 初始化字典表和数据。
执行 `sql/sys_user.sql` 初始化用户表和数据。

### 5. 运行项目
```bash
mvn clean install
mvn spring-boot:run
```

### 6. 访问接口文档
启动后访问：`http://localhost:9090/swagger-ui.html`

## 📦 Maven 依赖说明

| 依赖 | 版本 | 说明 |
|------|------|------|
| spring-boot-starter-web | 3.2.0 | Web 开发基础 |
| spring-boot-starter-security | 3.2.0 | 安全框架 |
| spring-boot-starter-data-redis | 3.2.0 | Redis 支持 |
| mybatis-plus-spring-boot3-starter | 3.5.7 | MyBatis 增强 |
| mysql-connector-j | - | MySQL 驱动 |
| lombok | - | 简化开发 |
| fastjson2 | 2.0.52 | JSON 处理 |
| jjwt | 0.12.5 | JWT 认证 |
| springdoc-openapi-starter-webmvc-ui | 2.3.0 | API 文档 |
| aliyun-sdk-oss | 3.18.1 | 阿里云 OSS |
| easyexcel | 4.0.3 | Excel 处理 |
| jasypt-spring-boot-starter | 3.0.5 | 配置加密 |

## 🌟 最佳实践

### 1. 多环境配置
项目支持多环境配置：
- `application.yml`: 公共配置
- `application-dev.yml`: 开发环境
- `application-prod.yml`: 生产环境

通过 `spring.profiles.active` 切换环境。

### 2. MyBatis Plus 配置
- 自动填充：创建时间、更新时间
- 逻辑删除：使用 `delFlag` 字段
- 主键策略：自增
- 驼峰命名转换：自动开启

### 3. Redis 序列化
使用 FastJSON2 作为 Redis 序列化方式，提升性能。

### 4. 参数校验
在 DTO 中使用 Validation 注解，Controller 使用 `@Valid` 触发校验。

## 📖 扩展建议

### 添加新模块
1. 创建 Entity（对应数据库表）
2. 创建 Mapper 接口
3. 创建 DTO 和 VO
4. 创建 Converter
5. 创建 Service 和 ServiceImpl
6. 创建 Controller

### 添加新字典
参考 [字典功能使用说明](docs/字典功能使用说明.md)

## 📄 License

MIT License

## 👤 作者

xiaozhu

---

**注意**: 本项目为开发模板，请根据实际业务需求进行调整和扩展。
