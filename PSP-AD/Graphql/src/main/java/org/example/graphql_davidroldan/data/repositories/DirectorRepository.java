package org.example.graphql_davidroldan.data.repositories;

import org.example.graphql_davidroldan.data.modelo.DirectorEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends ListCrudRepository<DirectorEntity, Long> {
    DirectorEntity findByPeliculasDirigidasId(Long peliculaId);
}
