package org.example.graphql_davidroldan.domain.modelo.mappers;

import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.modelo.DirectorEntity;
import org.example.graphql_davidroldan.domain.modelo.Director;
import org.example.graphql_davidroldan.domain.modelo.graphql.DirectorInput;
import org.example.graphql_davidroldan.domain.modelo.graphql.UpdateDirectorInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = Constantes.SPRING)
public interface DirectorEntityMapper {
    DirectorEntity toDirectorEntity(Director director);
    Director toDirector(DirectorEntity directorEntity);
    Director toDirector(DirectorInput directorInput);
    DirectorEntity toDirectorEntity(DirectorInput directorInput);
    DirectorEntity toDirectorEntity(UpdateDirectorInput updateDirectorInput);
}
