package org.example.graphql_davidroldan.domain.servicios;

import org.example.graphql_davidroldan.domain.modelo.Premio;
import org.example.graphql_davidroldan.domain.modelo.graphql.PremioInput;
import org.example.graphql_davidroldan.domain.modelo.graphql.UpdatePremioInput;

import java.util.List;

public interface PremiosService {
    List<Premio> getAll();

    Premio getById(long id);

    Premio add(PremioInput premio);

    Premio update(UpdatePremioInput premio);

    Boolean delete(long id);
}
