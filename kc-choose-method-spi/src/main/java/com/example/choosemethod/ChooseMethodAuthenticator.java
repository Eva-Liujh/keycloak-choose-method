package com.example.choosemethod;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class ChooseMethodAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(ChooseMethodAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.info("【ChooseMethodAuthenticator】执行 authenticate()，渲染选择页面 choose-method.ftl");

        Response challenge = context.form()
                .setAttribute("chooseMethod", true)
                .createForm("choose-recovery-method.ftl");

        context.challenge(challenge);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String method = formData.getFirst("method");

        logger.infof("【ChooseMethodAuthenticator】用户选择的方法：%s", method);

        if ("sms".equalsIgnoreCase(method)) {
            context.getAuthenticationSession().setAuthNote("auth_type", "sms");
        } else if ("otp".equalsIgnoreCase(method)) {
            context.getAuthenticationSession().setAuthNote("auth_type", "otp");
        } else {
            logger.warn("【ChooseMethodAuthenticator】无效 method，触发失败页面");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
                    context.form().setError("invalid_method").createErrorPage(Response.Status.BAD_REQUEST));
            return;
        }

        context.success();
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // 无需实现
    }

    @Override
    public void close() {
        // 无需实现
    }
}

