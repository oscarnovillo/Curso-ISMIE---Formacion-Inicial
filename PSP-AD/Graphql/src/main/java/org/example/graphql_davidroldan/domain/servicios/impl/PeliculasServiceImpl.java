package org.example.graphql_davidroldan.domain.servicios.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.repositories.ActorRepository;
import org.example.graphql_davidroldan.data.repositories.GeneroRepository;
import org.example.graphql_davidroldan.data.repositories.PeliculaRepository;
import org.example.graphql_davidroldan.domain.modelo.Pelicula;
import org.example.graphql_davidroldan.domain.modelo.errores.NotFoundException;
import org.example.graphql_davidroldan.domain.modelo.errores.ValidationException;
import org.example.graphql_davidroldan.domain.modelo.mappers.PeliculaEntityMapper;
import org.example.graphql_davidroldan.domain.servicios.PeliculasService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PeliculasServiceImpl implements PeliculasService {

    private final PeliculaRepository peliculaRepository;
    private final PeliculaEntityMapper peliculaMapper;
    private final GeneroRepository generoRepository;
    private final ActorRepository actorRepository;

    @Override
    public List<Pelicula> getAll() {
        List<Pelicula> peliculas = peliculaRepository.findAll().stream().map(peliculaMapper::toPelicula).toList();
        log.error(peliculas);
        return peliculas;
    }

    @Override
    public Pelicula getById(long id) {
        return peliculaMapper.toPelicula(peliculaRepository.findById(id).orElse(null));
    }

    @Override
    public Boolean delete(long id) {
        if (peliculaRepository.findById(id).isPresent()) {
            peliculaRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(Constantes.LA_PELICULA_NO_EXISTE);
        }
    }

}
