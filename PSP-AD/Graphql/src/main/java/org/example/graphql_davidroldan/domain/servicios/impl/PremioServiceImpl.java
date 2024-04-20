package org.example.graphql_davidroldan.domain.servicios.impl;

import lombok.RequiredArgsConstructor;
import org.example.graphql_davidroldan.common.Constantes;
import org.example.graphql_davidroldan.data.modelo.PeliculaEntity;
import org.example.graphql_davidroldan.data.modelo.PremioEntity;
import org.example.graphql_davidroldan.data.repositories.PeliculaRepository;
import org.example.graphql_davidroldan.data.repositories.PremioRepository;
import org.example.graphql_davidroldan.domain.modelo.Premio;
import org.example.graphql_davidroldan.domain.modelo.errores.NotFoundException;
import org.example.graphql_davidroldan.domain.modelo.graphql.PremioInput;
import org.example.graphql_davidroldan.domain.modelo.graphql.UpdatePremioInput;
import org.example.graphql_davidroldan.domain.modelo.mappers.PremioEntityMapper;
import org.example.graphql_davidroldan.domain.servicios.PremiosService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PremioServiceImpl implements PremiosService {

    private final PremioRepository premioRepository;
    private final PremioEntityMapper premioMapper;
    private final PeliculaRepository peliculaRepository;

    @Override
    public List<Premio> getAll() {
        return premioRepository.findAll().stream().map(premioMapper::toPremio).toList();
    }

    @Override
    public Premio getById(long id) {
        return premioMapper.toPremio(premioRepository.findById(id).orElse(null));
    }

    @Override
    public Premio add(PremioInput premio) {
        PeliculaEntity pelicula = peliculaRepository.findById(premio.peliculaGanadoraId()).orElse(null);
        PremioEntity premioEntity = new PremioEntity(null, premio.nombre(), premio.categoria(), premio.ano(), pelicula);
        return premioMapper.toPremio(premioRepository.save(premioEntity));
    }

    @Override
    public Premio update(UpdatePremioInput premio) {
        PeliculaEntity pelicula = peliculaRepository.findById(premio.peliculaGanadoraId()).orElse(null);
        if (pelicula==null) {
            throw new NotFoundException(Constantes.LA_PELICULA_NO_EXISTE);
        }
        if (premioRepository.findById(premio.id()).isEmpty()) {
            throw new NotFoundException(Constantes.EL_PREMIO_NO_EXISTE);
        }
        PremioEntity premioEntity = new PremioEntity(premio.id(), premio.nombre(), premio.categoria(), premio.ano(), pelicula);
        return premioMapper.toPremio(premioRepository.save(premioEntity));
    }

    @Override
    public Boolean delete(long id) {
        PremioEntity premio = premioRepository.findById(id).orElse(null);
        if (premio!=null) {
            premio.setPeliculaGanadora(null);
            premioRepository.save(premio);
            premioRepository.delete(premio);
            return true;
        } else {
            throw new NotFoundException(Constantes.EL_PREMIO_NO_EXISTE);
        }
    }
}
