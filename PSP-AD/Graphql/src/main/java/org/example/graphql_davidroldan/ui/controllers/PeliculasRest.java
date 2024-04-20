package org.example.graphql_davidroldan.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.domain.modelo.Pelicula;
import org.example.graphql_davidroldan.domain.servicios.PeliculasService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeliculasRest {
    private final PeliculasService peliculasService;

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public List<Pelicula> getPeliculas() {
        return peliculasService.getAll();
    }

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public Pelicula getPeliculaPorId(@Argument Long id) {
        return peliculasService.getById(id);
    }

    @MutationMapping
    @Secured(Constantes.ROLE_ADMIN)
    public void deletePelicula(@Argument Long id) {
        peliculasService.delete(id);
    }

}
