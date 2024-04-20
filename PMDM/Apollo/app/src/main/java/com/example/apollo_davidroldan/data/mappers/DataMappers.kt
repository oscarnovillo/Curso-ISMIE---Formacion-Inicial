package com.example.apollo_davidroldan.data.mappers

import com.example.apollo_davidroldan.domain.modelo.Actor
import com.example.apollo_davidroldan.domain.modelo.Director
import com.example.apollo_davidroldan.domain.modelo.Genero
import com.example.apollo_davidroldan.domain.modelo.Pelicula
import com.example.apollo_davidroldan.domain.modelo.Premio
import com.serverschema.ActoresQuery
import com.serverschema.AddPremioMutation
import com.serverschema.DirectoresQuery
import com.serverschema.GetActoresPeliculaQuery
import com.serverschema.GetDirectorPorPeliculaQuery
import com.serverschema.GetGenerosPeliculaQuery
import com.serverschema.GetPremioPorIdQuery
import com.serverschema.PeliculasQuery
import com.serverschema.PremiosQuery
import com.serverschema.UpdatePremioMutation

fun PeliculasQuery.GetPelicula.toPelicula(): Pelicula {
    return Pelicula(
        id = id.toInt(),
        titulo = titulo,
        anoLanzamiento = anoLanzamiento,
        duracion = duracion
    )
}

fun GetDirectorPorPeliculaQuery.GetDirectorPorPelicula.toDirector(): Director {
    return Director(
        id = id.toInt(),
        nombre = nombre,
        nacionalidad = nacionalidad,
        fechaNacimiento = fechaNacimiento
    )
}

fun GetActoresPeliculaQuery.GetActoresPelicula.toActor(): Actor {
    return Actor(
        id = id.toInt(),
        nombre = nombre,
        nacionalidad = nacionalidad,
        fechaNacimiento = fechaNacimiento
    )
}

fun GetGenerosPeliculaQuery.GetGenerosPelicula.toGenero(): Genero {
    return Genero(
        id = id.toInt(),
        nombre = nombre
    )
}

fun PremiosQuery.GetPremio.toPremio(): Premio {
    return Premio(
        id = id.toInt(),
        nombre = nombre,
        categoria = categoria,
        ano = ano,
        pelicula = Pelicula(
            id = peliculaGanadora.id.toInt(),
            titulo = peliculaGanadora.titulo,
            anoLanzamiento = peliculaGanadora.anoLanzamiento,
            duracion = peliculaGanadora.duracion
        )
    )
}

fun GetPremioPorIdQuery.GetPremioPorId.toPremio(): Premio {
    return Premio(
        id = id.toInt(),
        nombre = nombre,
        categoria = categoria,
        ano = ano,
        pelicula = Pelicula(
            id = peliculaGanadora.id.toInt(),
            titulo = peliculaGanadora.titulo,
            anoLanzamiento = peliculaGanadora.anoLanzamiento,
            duracion = peliculaGanadora.duracion
        )
    )
}

fun AddPremioMutation.AddPremio.toPremio(): Premio {
    return Premio(
        id = id.toInt(),
        nombre = nombre,
        categoria = categoria,
        ano = ano,
        pelicula = Pelicula(
            id = peliculaGanadora.id.toInt(),
            titulo = peliculaGanadora.titulo,
            anoLanzamiento = peliculaGanadora.anoLanzamiento,
            duracion = peliculaGanadora.duracion
        )
    )
}

fun UpdatePremioMutation.UpdatePremio.toPremio(): Premio {
    return Premio(
        id = id.toInt(),
        nombre = nombre,
        categoria = categoria,
        ano = ano,
        pelicula = Pelicula(
            id = peliculaGanadora.id.toInt(),
            titulo = peliculaGanadora.titulo,
            anoLanzamiento = peliculaGanadora.anoLanzamiento,
            duracion = peliculaGanadora.duracion
        )
    )
}

fun ActoresQuery.GetActore.toActorName(): String {
    return nombre
}

fun DirectoresQuery.GetDirectore.toDirectorName(): String {
    return nombre
}