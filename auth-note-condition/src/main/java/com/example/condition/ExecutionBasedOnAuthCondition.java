package com.example.condition;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class ExecutionBasedOnAuthCondition implements Authenticator {

    private static final Logger logger = Logger.getLogger(ExecutionBasedOnAuthCondition.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        String expected = config != null && config.getConfig() != null
                ? config.getConfig().get("auth-method")
                : null;

        String actual = context.getAuthenticationSession().getAuthNote("authSelection");

        logger.infof("[ExecutionBasedOnAuthCondition] authNote(authSelection): %s", actual);
        logger.infof("[ExecutionBasedOnAuthCondition] expected(auth-method): %s", expected);

        if (expected != null && expected.equalsIgnoreCase(actual)) {
            logger.info("[ExecutionBasedOnAuthCondition] 条件满足，继续执行子流程");
            context.success();
        } else {
            logger.info("[ExecutionBasedOnAuthCondition] 条件不满足，跳过当前执行");
            context.attempted();
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // No action needed
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // No required actions
    }

    @Override
    public void close() {
        // Nothing to clean up
    }
}

