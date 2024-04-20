package org.example.graphql_davidroldan.domain.modelo.mappers;

import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.modelo.PeliculaEntity;
import org.example.graphql_davidroldan.domain.modelo.Pelicula;
import org.example.graphql_davidroldan.domain.modelo.graphql.PeliculaInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = Constantes.SPRING)
public interface PeliculaEntityMapper {
    PeliculaEntity toPeliculaEntity(Pelicula pelicula);
    Pelicula toPelicula(PeliculaEntity peliculaEntity);
    Pelicula toPelicula(PeliculaInput peliculaInput);
}
