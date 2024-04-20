package org.example.graphql_davidroldan.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.domain.modelo.Director;
import org.example.graphql_davidroldan.domain.modelo.graphql.DirectorInput;
import org.example.graphql_davidroldan.domain.modelo.graphql.UpdateDirectorInput;
import org.example.graphql_davidroldan.domain.servicios.DirectorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DirectorRest {
    private final DirectorService directorService;

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public List<Director> getDirectores() {
        return directorService.getAll();
    }

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public Director getDirectorPorId(@Argument Long id) {
        return directorService.getById(id);
    }

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public Director getDirectorPorPelicula(@Argument Long peliculaId) {
        return directorService.getByPeliculaId(peliculaId);
    }

    @MutationMapping
    @Secured(Constantes.ROLE_ADMIN)
    public Director addDirector(@Argument DirectorInput directorInput) {
        return directorService.add(directorInput);
    }

    @MutationMapping
    @Secured(Constantes.ROLE_ADMIN)
    public void deleteDirector(@Argument Long id) {
        directorService.delete(id);
    }

    @MutationMapping
    @Secured(Constantes.ROLE_ADMIN)
    public Director updateDirector(@Argument UpdateDirectorInput updateDirectorInput) {
        return directorService.update(updateDirectorInput);
    }

}
