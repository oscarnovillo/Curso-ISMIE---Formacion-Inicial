package org.example.graphql_davidroldan.domain.servicios;

import org.example.graphql_davidroldan.domain.modelo.Director;
import org.example.graphql_davidroldan.domain.modelo.graphql.DirectorInput;
import org.example.graphql_davidroldan.domain.modelo.graphql.UpdateDirectorInput;

import java.util.List;

public interface DirectorService {
    List<Director> getAll();

    Director getById(long id);

    Director getByPeliculaId(long id);

    Director add(DirectorInput directorInput);

    Director update(UpdateDirectorInput updateDirectorInput);

    Boolean delete(long id);
}
