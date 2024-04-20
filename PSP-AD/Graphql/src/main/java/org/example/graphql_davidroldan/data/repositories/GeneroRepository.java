package org.example.graphql_davidroldan.data.repositories;

import org.example.graphql_davidroldan.data.modelo.GeneroEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneroRepository extends ListCrudRepository<GeneroEntity, Long> {
    List<GeneroEntity> findAllByPeliculasId(long peliculaId);
}
