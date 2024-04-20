package org.example.graphql_davidroldan.domain.servicios.impl;

import lombok.RequiredArgsConstructor;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.repositories.DirectorRepository;
import org.example.graphql_davidroldan.domain.modelo.Director;
import org.example.graphql_davidroldan.domain.modelo.errores.NotFoundException;
import org.example.graphql_davidroldan.domain.modelo.graphql.DirectorInput;
import org.example.graphql_davidroldan.domain.modelo.graphql.UpdateDirectorInput;
import org.example.graphql_davidroldan.domain.modelo.mappers.DirectorEntityMapper;
import org.example.graphql_davidroldan.domain.servicios.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorEntityMapper directorMapper;

    public List<Director> getAll() {
        return directorRepository.findAll().stream().map(directorMapper::toDirector).toList();
    }

    public Director getById(long id) {
        return directorMapper.toDirector(directorRepository.findById(id).orElse(null));
    }

    @Override
    public Director getByPeliculaId(long id) {
        return directorMapper.toDirector(directorRepository.findByPeliculasDirigidasId(id));
    }

    public Director add(DirectorInput directorInput) {
        return directorMapper.toDirector(directorRepository.save(directorMapper.toDirectorEntity(directorInput)));
    }

    public Boolean delete(long id) {
        if (directorRepository.findById(id).isPresent()) {
            directorRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(Constantes.EL_DIRECTOR_NO_EXISTE);
        }
    }

    public Director update(UpdateDirectorInput updateDirectorInput) {
        return directorMapper.toDirector(directorRepository.save(directorMapper.toDirectorEntity(updateDirectorInput)));
    }


}
