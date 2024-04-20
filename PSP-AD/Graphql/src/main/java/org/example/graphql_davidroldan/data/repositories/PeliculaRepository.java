package org.example.graphql_davidroldan.data.repositories;

import org.example.graphql_davidroldan.data.modelo.PeliculaEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends ListCrudRepository<PeliculaEntity, Long> {
}
