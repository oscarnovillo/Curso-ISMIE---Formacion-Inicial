package domain.services.impl;

import dao.DaoMonederos;
import domain.modelo.Cliente;
import domain.modelo.Monedero;
import domain.services.ServicesMonederos;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesMonederosImpl implements ServicesMonederos {

    private final DaoMonederos daoMonederos;

    @Inject
    public ServicesMonederosImpl(DaoMonederos daoMonederos) {
        this.daoMonederos = daoMonederos;
    }

    @Override
    public boolean scExisteMonedero(Monedero monedero) {
        return daoMonederos.existeMonedero(monedero);
    }

    @Override
    public boolean scAnadirMonedero(Monedero monedero, Cliente cliente) {
        if (!daoMonederos.existeMonedero(monedero)
                && monedero.getNumeroMonedero() > 0
                && monedero.getImporte() > 0) {
            return daoMonederos.addMonederoCliente(monedero, cliente);
        }
        return false;
    }

    @Override
    public List<Monedero> scGetListaMonederosCliente(Cliente cliente) {
        return daoMonederos.getMonederosClienteList(cliente);
    }
}
