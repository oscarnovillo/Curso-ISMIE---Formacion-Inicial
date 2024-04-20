package org.example.graphql_davidroldan.ui.controllers;

import lombok.RequiredArgsConstructor;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.domain.modelo.Premio;
import org.example.graphql_davidroldan.domain.modelo.graphql.PremioInput;
import org.example.graphql_davidroldan.domain.modelo.graphql.UpdatePremioInput;
import org.example.graphql_davidroldan.domain.servicios.PremiosService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PremiosRest {
    private final PremiosService premiosService;

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public List<Premio> getPremios() {
        return premiosService.getAll();
    }

    @QueryMapping
    @Secured({Constantes.ROLE_USER, Constantes.ROLE_ADMIN})
    public Premio getPremioPorId(@Argument Long id) {
        return premiosService.getById(id);
    }

    @MutationMapping
    @Secured(Constantes.ROLE_ADMIN)
    public Premio addPremio(@Argument PremioInput premioInput) {
        return premiosService.add(premioInput);
    }

    @MutationMapping
    @Secured(Constantes.ROLE_ADMIN)
    public Premio updatePremio(@Argument UpdatePremioInput updatePremioInput) {
        return premiosService.update(updatePremioInput);
    }

    @MutationMapping
    @Secured(Constantes.ROLE_ADMIN)
    public void deletePremio(@Argument Long id) {
        premiosService.delete(id);
    }
}
