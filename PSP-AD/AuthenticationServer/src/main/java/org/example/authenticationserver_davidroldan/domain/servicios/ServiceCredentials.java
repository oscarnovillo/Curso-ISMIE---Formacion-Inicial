package org.example.authenticationserver_davidroldan.domain.servicios;

import org.example.authenticationserver_davidroldan.domain.modelo.Credentials;
import org.example.authenticationserver_davidroldan.domain.modelo.LoginTokens;

public interface ServiceCredentials {
    LoginTokens doLogin(String username, String password);
    Boolean singUp(Credentials c);
    LoginTokens refreshToken(String refreshtoken);
}
