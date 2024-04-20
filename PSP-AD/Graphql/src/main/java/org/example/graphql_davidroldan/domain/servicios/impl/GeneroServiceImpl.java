package org.example.graphql_davidroldan.domain.servicios.impl;

import lombok.RequiredArgsConstructor;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.repositories.GeneroRepository;
import org.example.graphql_davidroldan.domain.modelo.Genero;
import org.example.graphql_davidroldan.domain.modelo.errores.NotFoundException;
import org.example.graphql_davidroldan.domain.modelo.mappers.GeneroEntityMapper;
import org.example.graphql_davidroldan.domain.servicios.GeneroService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroServiceImpl implements GeneroService {

    private final GeneroRepository generoRepository;
    private final GeneroEntityMapper generoMapper;

    @Override
    public List<Genero> getAll() {
        return generoRepository.findAll().stream().map(generoMapper::toGenero).toList();
    }

    @Override
    public List<Genero> getGenerosPelicula(long directorId) {
        return generoRepository.findAllByPeliculasId(directorId).stream().map(generoMapper::toGenero).toList();
    }

    @Override
    public Boolean delete(long id) {
        if (generoRepository.findById(id).isPresent()) {
            generoRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(Constantes.LA_PELICULA_NO_EXISTE);
        }
    }
}
