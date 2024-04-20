package org.example.graphql_davidroldan.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.domain.modelo.Actor;
import org.example.graphql_davidroldan.domain.servicios.ActoresService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActoresRest {
    private final ActoresService actoresService;

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public List<Actor> getActores() {
        return actoresService.getAll();
    }

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public List<Actor> getActoresPelicula(@Argument Long peliculaId) {
        return actoresService.getActoresPelicula(peliculaId);
    }

    @MutationMapping
    @Secured(Constantes.ROLE_ADMIN)
    public void deleteActor(@Argument Long id) {
        actoresService.delete(id);
    }


}
