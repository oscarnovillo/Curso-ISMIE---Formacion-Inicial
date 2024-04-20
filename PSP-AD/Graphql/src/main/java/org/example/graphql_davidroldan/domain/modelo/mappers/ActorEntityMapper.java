package org.example.graphql_davidroldan.domain.modelo.mappers;

import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.modelo.ActorEntity;
import org.example.graphql_davidroldan.domain.modelo.Actor;
import org.example.graphql_davidroldan.domain.modelo.graphql.ActorInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = Constantes.SPRING)
public interface ActorEntityMapper {

    ActorEntity toActorEntity(Actor actor);
    Actor toActor(ActorEntity actorEntity);
    Actor toActor(ActorInput actorInput);
}
