# keycloak-choose-method
Keycloak plugin for choosing verification method between SMS and OTP.

📚 Project Introduction | 项目简介
kc-choose-method-spi is a custom Keycloak Authenticator SPI plugin, which allows users to select a verification method (e.g., SMS or OTP) during the password reset or authentication process.
kc-choose-method-spi 是一个自定义的 Keycloak Authenticator SPI 插件，用于在密码重置或认证流程中，让用户自主选择验证码验证方式（如短信验证或 OTP 动态令牌验证）。


🚀 Features | 功能特性
Display a method selection page during authentication
Allow users to choose SMS or OTP
Seamlessly integrate into Keycloak authentication flows
Fully compatible with Keycloak 26.0.5

在认证流程中展示验证码方式选择页面
支持用户选择 短信验证 或 OTP动态令牌
可无缝集成到 Keycloak 认证流程
完全兼容 Keycloak 26.0.5 版本

🛠️ Build & Deployment | 构建与部署

Build | 构建
```
cd kc-choose-method-spi
mvn clean package
```
After successful build, you will get a JAR file under target/ directory.
构建成功后，JAR 文件会生成在 target/ 目录下。

Deployment | 部署
1. Copy the generated JAR to Keycloak's providers/ directory.
2. Restart Keycloak service.
```
cp target/kc-choose-method-spi-1.0.0.jar /app/keycloak/providers/
cd /app/keycloak/
bin/kc.sh build
bin/kc.sh start
```
将构建后的 JAR 文件复制到 Keycloak 的 providers/ 目录
执行 build 命令重新编译 Keycloak，最后重新启动


🔥 How It Works | 使用流程
在自定义认证流程中添加 Choose Method V2 和 Choose Recovery Method 两个执行器。
用户在选择页面点击按钮，选择 "SMS" 或 "OTP"。
认证流程根据用户选择动态跳转到对应子流程（短信验证码验证流程或 OTP 验证流程）。
在自定义认证流程中配置 Choose Method V2 和 Choose Recovery Method 两个节点。
用户在页面上选择 "短信验证" 或 "动态令牌验证"。
流程根据用户选择自动跳转至对应子流程执行验证。

📄 Directory Structure | 项目结构

kc-choose-method-spi/
├── pom.xml
└── src/
    └── main/
        ├── java/com/example/choosemethod/
        │   ├── ChooseMethodAuthenticator.java
        │   └── ChooseMethodAuthenticatorFactory.java
        └── resources/
            └── META-INF/services/
                └── org.keycloak.authentication.AuthenticatorFactory

📢 Note | 注意事项
This plugin must be used together with customized authentication flows in Keycloak.
The plugin is compatible with Keycloak Quarkus distribution (e.g., 17+ versions).

插件需要配合 Keycloak 自定义认证流程使用。
本插件适用于 Keycloak 的 Quarkus 版本（17及以上版本）。


