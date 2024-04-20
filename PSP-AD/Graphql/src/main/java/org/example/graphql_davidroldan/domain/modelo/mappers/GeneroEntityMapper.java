package org.example.graphql_davidroldan.domain.modelo.mappers;

import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.modelo.GeneroEntity;
import org.example.graphql_davidroldan.domain.modelo.Genero;
import org.example.graphql_davidroldan.domain.modelo.graphql.GeneroInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = Constantes.SPRING)
public interface GeneroEntityMapper {
    GeneroEntity toGeneroEntity(Genero genero);
    Genero toGenero(GeneroEntity generoEntity);
    Genero toGenero(GeneroInput generoInput);
}
