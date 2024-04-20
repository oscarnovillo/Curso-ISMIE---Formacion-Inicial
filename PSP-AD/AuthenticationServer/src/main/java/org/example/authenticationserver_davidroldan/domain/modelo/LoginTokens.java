package org.example.authenticationserver_davidroldan.domain.modelo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginTokens {
    private String accessToken;
    private String refreshToken;
}
