package com.example.recovery;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.jboss.logging.Logger;

import jakarta.ws.rs.core.MultivaluedMap;

public class ChooseRecoveryMethodAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(ChooseRecoveryMethodAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formParams = context.getHttpRequest().getDecodedFormParameters();
        String selectedMethod = formParams.getFirst("method");

        if (selectedMethod != null && !selectedMethod.isEmpty()) {
            logger.infof("[ChooseRecoveryMethodAuthenticator] 用户选择的方法为: %s", selectedMethod);
            AuthenticationSessionModel session = context.getAuthenticationSession();
            session.setAuthNote("authSelection", selectedMethod);  // 写入 authNote 供后续判断
            context.success();
        } else {
            logger.info("[ChooseRecoveryMethodAuthenticator] 显示验证码方式选择页面 choose-recovery-method.ftl");
            context.challenge(context.form().createForm("choose-recovery-method.ftl"));
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // 不再使用这个方法，逻辑全部移入 authenticate()
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(org.keycloak.models.KeycloakSession session, org.keycloak.models.RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(org.keycloak.models.KeycloakSession session, org.keycloak.models.RealmModel realm, UserModel user) {}

    @Override
    public void close() {}
}

