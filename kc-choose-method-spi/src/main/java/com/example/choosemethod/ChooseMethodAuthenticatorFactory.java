package com.example.choosemethod;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class ChooseMethodAuthenticatorFactory implements AuthenticatorFactory {

    public static final String ID = "choose-method-v2";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDisplayType() {
        return "Choose Method V2";
    }

    @Override
    public String getHelpText() {
        return "Allow user to choose SMS or OTP method";
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new ChooseMethodAuthenticator();
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // 无需实现
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // 无需实现
    }

    @Override
    public void close() {
        // 无需实现
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return List.of();
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.DISABLED
        };
    }
}

