# Keycloak Custom Verification Plugins

Keycloak plugins for choosing verification methods (SMS / OTP) and dynamically controlling authentication flows.

Keycloak自定义插件，用于验证方法选择（短信/SMS 和 OTP），实现高度自定义的认证流程控制。

## 📚 Project Introduction | 项目简介

This project contains four integrated Keycloak plugins:

该项目包含四个相关联的Keycloak插件：

| Plugin Name                | Description (EN)                           | 描述 (中文)                                     |
| -------------------------- | ------------------------------------------ | ----------------------------------------------- |
| kc-choose-method-spi       | Allow users to choose between SMS and OTP. | 弹出验证方式选择页面，可选择 SMS 或 OTP         |
| kc-choose-recovery-method  | Store user’s selection into authNote.      | 将用户选择给存入 authNote，为后续分支控制做准备 |
| auth-note-condition        | Conditional execution based on authNote.   | 根据 authNote 值判断是否执行子流程              |
| keycloak-sms-authenticator | Handle SMS code sending and verification.  | 发送短信验证码并进行校验                        |

## 🚀 Features | 功能特性

- Support SMS and OTP two-factor authentication.
- Allow dynamic user selection.
- Fully integrated with Keycloak 26.0.5 authentication flow.
- Easy to extend and maintain.
- 支持短信和动态两种验证方式
- 允许用户自选验证方法
- 完美集成到 Keycloak 26.0.5 认证流程
- 易于扩展和维护

## 🛠️ Build & Deployment | 构建与部署

### Build All Plugins | 构建所有插件

```
# For each plugin
mvn clean package
```

- The JAR files will be located in each plugin's `/target/` directory.
- 构建后，JAR 文件生成在各自 plugin 项目的 `/target/` 目录下，例如：

```
- auth-note-condition-1.0.0.jar
- kc-choose-method-spi-1.0.0.jar
- kc-choose-recovery-method-1.0.0.jar
- keycloak-sms-authenticator-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```

### Deployment | 部署

```
cp *.jar /app/keycloak/providers/
cd /app/keycloak/
./bin/kc.sh build
./bin/kc.sh start
```

Copy all built jar files into Keycloak `providers/`, rebuild, and restart.

将所有构建好的JAR文件复制到 Keycloak `providers/`，执行 build，重启Keycloak。

## 🌈 Flow Diagram | 流程图

```
flowchart TD
    A(Choose Method V2) --> B(Choose Recovery Method)
    B --> C{authSelection}
    C -->|SMS| D(SMS Flow)
    C -->|OTP| E(2FA Flow)
    D --> F(Send SMS & Verify)
    E --> G(OTP Form Verify)
```

## 🖋️ Configuration Steps | 配置流程

1. **Create custom authentication flow**: `reset-credentials-custom`
2. **Add executions**:
   - Choose Method V2 (kc-choose-method-spi)
   - Choose Recovery Method (kc-choose-recovery-method)
   - Verification Flow (subflow, REQUIRED)
3. **Under Verification Flow**, add two alternative subflows:
   - SMS-Flow (ALTERNATIVE)
     - Add Execution: Execution Based On Auth Condition (auth-note-condition)
     - Add Execution: SMS Authenticator (keycloak-sms-authenticator)
   - 2FA-Flow (ALTERNATIVE)
     - Add Execution: Execution Based On Auth Condition (auth-note-condition)
     - Add Execution: Conditional OTP Form

## 👀 Screenshots | 界面演示

- 验证方式选择页 (Choose Method Page)
- 短信验证码输入 (SMS Verification Page)
- OTP码输入页 (OTP Verification Page)

## 📊 Dependencies | 依赖关系

| Plugin                     | Depends On                | 依赖           |
| -------------------------- | ------------------------- | -------------- |
| kc-choose-method-spi       | standalone                | 单独使用       |
| kc-choose-recovery-method  | kc-choose-method-spi      | 需要先选择方法 |
| auth-note-condition        | kc-choose-recovery-method | 根据选择判断   |
| keycloak-sms-authenticator | auth-note-condition       | 通过条件执行   |

## 📢 Contact & Support | 联系与支持

Feel free to open an issue or submit a pull request if you have any questions or improvements!

如有任何疑问或优化建议，欢迎提 Issue 或 Pull Request！

