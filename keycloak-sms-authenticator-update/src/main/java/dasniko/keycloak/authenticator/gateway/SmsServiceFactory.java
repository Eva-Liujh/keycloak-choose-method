package dasniko.keycloak.authenticator.gateway;

import dasniko.keycloak.authenticator.SmsConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class SmsServiceFactory {

    public static SmsService get(Map<String, String> config) {
        log.info("SmsServiceFactory 收到配置: {}", config);
        if (Boolean.parseBoolean(config.getOrDefault(SmsConstants.SIMULATION_MODE, "false"))) {
            return (phoneNumber, message) -> 
                log.warn(String.format("***** 模拟模式 ***** 将发送短信到 %s，内容: %s", phoneNumber, message));
        } else {
            try {
                log.info("使用阿里云短信服务...");
                AliyunSmsService aliyunSmsService = new AliyunSmsService(
                    "access_key_id",
                    "access_key_secret",
                    "template_code",
                    "sign_name"
                );
                log.info("AliyunSmsService 初始化成功！");
                return aliyunSmsService;
            } catch (Exception e) {
                log.error("初始化 AliyunSmsService 失败", e);
                throw new RuntimeException("初始化 AliyunSmsService 失败", e);
            }
        }
    }
}

