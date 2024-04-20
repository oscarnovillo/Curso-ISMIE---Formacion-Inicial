package org.example.graphql_davidroldan.domain.servicios;

import org.example.graphql_davidroldan.domain.modelo.Pelicula;

import java.util.List;


public interface PeliculasService {
    List<Pelicula> getAll();
    Pelicula getById(long id);
    Boolean delete(long id);
}
