package org.example.graphql_davidroldan.domain.servicios.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.repositories.ActorRepository;
import org.example.graphql_davidroldan.domain.modelo.Actor;
import org.example.graphql_davidroldan.domain.modelo.errores.NotFoundException;
import org.example.graphql_davidroldan.domain.modelo.mappers.ActorEntityMapper;
import org.example.graphql_davidroldan.domain.servicios.ActoresService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ActorServiceImpl implements ActoresService {

    private final ActorRepository actorRepository;
    private final ActorEntityMapper actorMapper;

    @Override
    public List<Actor> getAll() {
        return actorRepository.findAll().stream().map(actorMapper::toActor).toList();
    }

    @Override
    public List<Actor> getActoresPelicula(long directorId) {
        return actorRepository.findAllByPeliculasActuadasId(directorId).stream().map(actorMapper::toActor).toList();
    }

    @Override
    public Boolean delete(long id) {
        if (actorRepository.findById(id).isPresent()) {
            actorRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(Constantes.EL_ACTOR_NO_EXISTE);
        }
    }
}
