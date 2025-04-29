# Keycloak 2FA SMS Authenticator

This plugin is for configuring Alibaba Cloud's SMS service

This file needs to be modified to configure the parameters of the Alibaba Cloud SMS gateway

src\main\java\dasniko\keycloak\authenticator\gateway\SmsServiceFactory.java

```

                AliyunSmsService aliyunSmsService = new AliyunSmsService(
                    "access_key_id",
                    "access_key_secret",
                    "template_code",
                    "sign_name"
                );
```

