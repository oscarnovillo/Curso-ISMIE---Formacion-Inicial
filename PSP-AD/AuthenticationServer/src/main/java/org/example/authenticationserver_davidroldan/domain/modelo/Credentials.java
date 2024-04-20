package org.example.authenticationserver_davidroldan.domain.modelo;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Credentials {
    private int id;
    private String username;
    private String password;
    private String role;
}
