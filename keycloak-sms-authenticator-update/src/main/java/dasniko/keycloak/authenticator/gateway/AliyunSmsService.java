package dasniko.keycloak.authenticator.gateway;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliyunSmsService implements SmsService {
    private final Client client;
    private final String templateCode;
    private final String signName;

    public AliyunSmsService(String accessKeyId, String accessKeySecret, String templateCode, String signName) throws Exception {
        Config aliyunConfig = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint("dysmsapi.aliyuncs.com");
        this.client = new Client(aliyunConfig);
        this.templateCode = templateCode;
        this.signName = signName;
        log.info("初始化AliyunSmsService，使用AccessKeyId: {}", accessKeyId);
    }

    @Override
    public void send(String phoneNumber, String message) {
        try {
            SendSmsRequest request = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumber)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam(String.format("{\"code\":\"%s\"}", message));

            SendSmsResponse response = client.sendSmsWithOptions(request, new RuntimeOptions());
            log.info("短信发送成功，响应码: {}, 响应消息: {}",
                    response.getBody().getCode(), response.getBody().getMessage());
        } catch (Exception e) {
            log.error("发送短信到 {} 失败，错误信息: {}", phoneNumber, e.getMessage(), e);
        }
    }
}

