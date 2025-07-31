# EMP-Script-Web - 企业级 Web 应用快速开发框架

## 项目简介

EMP-Script-Web 是一个专为企业级 Web 应用设计的快速开发框架，提供完整的 Web 开发解决方案。该框架支持多种企业级功能，包括用户认证、权限管理、消息通知、任务调度、文档管理等核心模块，旨在帮助开发者快速构建高质量的企业级 Web 应用系统。

## 官方信息

**官方网站**: https://www.gdxsoft.com  
**GitHub**: https://github.com/gdx1231/emp-script-web  
**许可证**: MIT License

## Maven 依赖

```xml
<dependency>
    <groupId>com.gdxsoft.easyweb</groupId>
    <artifactId>emp-script-web</artifactId>
    <version>1.1.10</version>
</dependency>
```

## 核心特性

### 🚀 快速开发
- **模块化架构**: 高度模块化的组件设计
- **配置驱动**: 基于 XML 配置的开发模式
- **代码生成**: 自动化代码生成工具
- **可视化开发**: 所见即所得的开发体验

### 🔐 安全认证
- **用户认证**: 多种登录方式支持（用户名/密码、短信验证码、第三方登录）
- **JWT 令牌**: JSON Web Token 认证机制
- **权限控制**: 细粒度的权限管理系统
- **Session 管理**: 安全的会话管理

### 📱 多平台支持
- **响应式设计**: 支持桌面、平板、手机等多种设备
- **微信集成**: 微信公众号、小程序开发支持
- **原生 App**: 支持原生移动应用集成
- **跨浏览器**: 兼容主流浏览器

### 🛠 企业级功能
- **消息系统**: 短信、邮件、站内消息通知
- **任务调度**: 定时任务和异步任务处理
- **文档管理**: 文件上传、下载、在线预览
- **日志审计**: 完整的操作日志记录
- **数据同步**: 多系统数据同步机制

## 技术架构

### 开发环境
- **Java版本**: JDK 1.8+
- **构建工具**: Maven 3.x
- **编码格式**: UTF-8

### 核心依赖
- **EMP Script**: 核心脚本引擎 (v1.1.10)
- **EMP Script Utils**: 工具类库 (v1.1.10)
- **微信 SDK**: 微信开发支持 (v1.0.0+)
- **Google Guava**: Google 核心库 (v32.0.0)
- **JWT**: 身份认证令牌 (v4.4.0)
- **Swagger**: API 文档注解 (v1.5.20)

### 第三方集成
- **短信服务**: 腾讯云短信、阿里云短信
- **域名服务**: 阿里云域名管理
- **二维码**: Google ZXing 二维码生成
- **日历**: iCal4j 日历处理

## 项目结构

```
emp-script-web/
├── src/main/java/
│   └── com/gdxsoft/
│       ├── api/                    # API 接口层
│       │   ├── Auth.java          # 身份认证
│       │   ├── AuthUser.java      # 认证用户
│       │   └── JwtUtils.java      # JWT 工具类
│       ├── chatRoom/              # 聊天室功能
│       ├── dev/                   # 开发工具
│       ├── message/               # 消息处理
│       ├── project/               # 项目管理
│       ├── spider/                # 爬虫工具
│       └── web/                   # Web 核心模块
│           ├── acl/               # 访问控制
│           ├── app/               # 应用框架
│           │   └── App.java       # 应用主类
│           ├── dao/               # 数据访问层
│           ├── doc/               # 文档管理
│           ├── domain/            # 域名管理
│           ├── fileViewer/        # 文件查看器
│           ├── http/              # HTTP 处理
│           ├── job/               # 任务管理
│           ├── log/               # 日志管理
│           ├── message/           # 消息服务
│           ├── module/            # 模块管理
│           ├── qrcode/            # 二维码服务
│           ├── schedule/          # 调度服务
│           ├── shortUrl/          # 短网址服务
│           ├── site/              # 站点管理
│           ├── uploadResources/   # 资源上传
│           ├── user/              # 用户管理
│           │   ├── DoLogin.java   # 登录处理
│           │   ├── RegisterOrLogin.java # 注册登录
│           │   ├── SmsValid.java  # 短信验证
│           │   └── ValidBase.java # 验证基类
│           └── weixin/            # 微信集成
└── readme/
    └── 说明.docx                   # 项目说明文档
```

## 核心功能模块

### 👤 用户管理系统
- **用户注册**: 支持多种注册方式（邮箱、手机号、用户名）
- **用户登录**: 多种登录验证（密码、短信验证码、第三方）
- **权限管理**: 基于角色的权限控制（RBAC）
- **用户资料**: 用户信息管理和个性化设置

### 📊 应用框架
- **多设备支持**: 自动识别设备类型（PC、手机、平板、微信、小程序）
- **响应式布局**: 自适应不同屏幕尺寸
- **主题切换**: 支持多种UI主题
- **国际化**: 多语言支持

### 💬 消息通知系统
- **短信服务**: 集成腾讯云、阿里云短信服务
- **邮件服务**: SMTP 邮件发送功能
- **站内消息**: 实时消息通知
- **消息模板**: 可配置的消息模板

### 📋 任务调度系统
- **定时任务**: Cron 表达式定时任务
- **异步任务**: 后台异步任务处理
- **任务监控**: 任务执行状态监控
- **任务日志**: 详细的任务执行日志

### 📄 文档管理
- **文件上传**: 多种文件格式上传支持
- **在线预览**: 常见文档格式在线预览
- **文件下载**: 安全的文件下载机制
- **存储管理**: 灵活的文件存储策略

### 🔗 短网址服务
- **网址缩短**: 长网址转短网址
- **访问统计**: 短网址访问次数统计
- **有效期管理**: 短网址有效期控制
- **自定义域名**: 支持自定义短网址域名

## 快速开始

### 环境准备
1. **JDK 1.8** 或更高版本
2. **Maven 3.6** 或更高版本
3. **数据库**: MySQL/SQL Server/Oracle
4. **Web 容器**: Tomcat 9.0+ 或其他 Servlet 4.0 兼容容器

 

### 用户登录示例
```java
import com.gdxsoft.web.user.DoLogin;
import com.gdxsoft.easyweb.script.RequestValue;

// 创建登录处理器
DoLogin login = new DoLogin();
RequestValue rv = new RequestValue();

// 设置登录参数
rv.addValue("username", "admin");
rv.addValue("password", "123456");

// 执行登录
JSONObject result = login.doLogin(rv, response);
```


### 短信发送示例
```java
import com.gdxsoft.web.user.SmsValid;

// 发送验证码短信
SmsValid smsValid = new SmsValid();
boolean success = smsValid.sendValidCode("13800138000", "验证码");
```

### 二维码生成示例
```java
import com.gdxsoft.web.qrcode.QRCode;

// 生成二维码
QRCode qrCode = new QRCode();
BufferedImage qrImage = qrCode.createQRCode("https://www.gdxsoft.com", 300);
```


## 部署说明

### 开发环境
```bash
# 克隆项目
git clone https://github.com/gdx1231/emp-script-web.git

# 编译打包
mvn clean package

# 运行测试
mvn test
```

### 生产环境
1. **配置数据库连接**
2. **设置第三方服务密钥**（短信、邮件等）
3. **配置 Web 容器**（Tomcat、Jetty等）
4. **部署应用包**
5. **启动服务**

## 版本信息

- **当前版本**: 1.1.10
- **开发团队**: GDXSoft (郭磊、靳朝鹏)
- **官方网站**: https://www.gdxsoft.com
- **技术支持**: guolei@gdxsoft.com

## 许可证

本项目采用 **MIT License** 开源协议。详见 [LICENSE](https://github.com/gdx1231/emp-script-web/blob/main/LICENSE) 文件。

## 社区支持

- **GitHub Issues**: [提交问题和建议](https://github.com/gdx1231/emp-script-web/issues)
- **官方文档**: https://www.gdxsoft.com
- **技术交流**: 欢迎通过邮件或GitHub进行技术交流

