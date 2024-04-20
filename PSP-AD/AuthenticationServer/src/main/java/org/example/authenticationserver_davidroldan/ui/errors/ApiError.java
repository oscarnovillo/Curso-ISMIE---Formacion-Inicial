package org.example.authenticationserver_davidroldan.ui.errors;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiError {

    private String mensaje;

    private LocalDateTime fecha;
}
