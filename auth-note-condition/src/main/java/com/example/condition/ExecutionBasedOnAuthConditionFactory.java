package com.example.condition;

import org.keycloak.Config;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

public class ExecutionBasedOnAuthConditionFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

    public static final String ID = "execution-based-on-auth";  
    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    static {
        ProviderConfigProperty authNoteProperty = new ProviderConfigProperty();
        authNoteProperty.setName("auth-method");
        authNoteProperty.setLabel("Auth Selection Value");
        authNoteProperty.setHelpText("Match this with the value of authNote 'authSelection'. Example: otp or sms.");
        authNoteProperty.setType(ProviderConfigProperty.STRING_TYPE);
        configProperties.add(authNoteProperty);
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new ExecutionBasedOnAuthCondition();
    }

    @Override
    public void init(Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}

    @Override
    public String getId() {
        return ID; 
    }

    @Override
    public String getDisplayType() {
        return "Condition - execution based on Auth Selection";
    }

    @Override
    public String getReferenceCategory() {
        return "condition";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getHelpText() {
        return "Executes conditionally based on the authNote named 'authSelection'.";
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[] {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED,
            AuthenticationExecutionModel.Requirement.CONDITIONAL
        };
    }
}

