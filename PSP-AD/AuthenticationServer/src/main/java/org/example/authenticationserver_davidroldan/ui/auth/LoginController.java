package org.example.authenticationserver_davidroldan.ui.auth;

import lombok.RequiredArgsConstructor;
import org.example.authenticationserver_davidroldan.common.Constantes;
import org.example.authenticationserver_davidroldan.domain.modelo.Credentials;
import org.example.authenticationserver_davidroldan.domain.modelo.CredentialsDTO;
import org.example.authenticationserver_davidroldan.domain.modelo.LoginTokens;
import org.example.authenticationserver_davidroldan.domain.servicios.ServiceCredentials;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final ServiceCredentials serviceCredentials;

    @PostMapping(Constantes.PATHLOGIN)
    public LoginTokens doLogin(@RequestBody CredentialsDTO c) {
        return serviceCredentials.doLogin(c.getUsername(), c.getPassword());
    }

    @PostMapping(Constantes.PATHREGISTER)
    public Boolean singUp(@RequestBody CredentialsDTO c) {
        return serviceCredentials.singUp(new Credentials(0, c.getUsername(), c.getPassword(), Constantes.USER));
    }

    @GetMapping(Constantes.PATHREFRESH)
    public LoginTokens refreshToken(@RequestParam(name = Constantes.TOKEN) String token) {
        LoginTokens c = serviceCredentials.refreshToken(token);
        if (c != null) {
            return new LoginTokens(c.getAccessToken(), c.getRefreshToken());
        } else {
            return null;
        }
    }

}
