package org.example.authenticationserver_davidroldan.data.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.authenticationserver_davidroldan.common.Constantes;

@Data
@Entity
@Table(name = Constantes.CREDENTIALS)
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Constantes.ID, nullable = false)
    private int id;
    @Column(name = Constantes.USERNAME, nullable = false, unique = true)
    private String username;
    @Column(name = Constantes.PASSWORD, nullable = false)
    private String password;
    @Column(name = Constantes.ROLE, nullable = false)
    private String role;
}
