package org.example.authenticationserver_davidroldan.domain.modelo;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CredentialsDTO {
    private String username;
    private String password;
}
