package domain.services.impl;

import dao.DaoClientes;
import dao.DaoCompras;
import domain.modelo.Cliente;
import domain.modelo.Ingrediente;
import domain.services.ServicesClientes;
import jakarta.inject.Inject;
import java.util.Collections;
import tui.common.Constantes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ServicesClientesImpl implements ServicesClientes {

    private final DaoClientes daoClientes;
    private final DaoCompras daoCompras;

    @Inject
    public ServicesClientesImpl(DaoClientes daoClientes, DaoCompras daoCompras) {
        this.daoClientes = daoClientes;
        this.daoCompras = daoCompras;
    }

    @Override
    public boolean scRegistrarCliente(Cliente cliente) {
        if (!cliente.getDni().equalsIgnoreCase(Constantes.ADMIN)) {
            return !daoClientes.existeCliente(cliente) &&
                    daoClientes.addCliente(cliente);
        }
        return false;
    }

    @Override
    public boolean scExisteCliente(Cliente cliente) {
        return daoClientes.existeCliente(cliente);
    }

    @Override
    public boolean scIsAdmin(Cliente cliente) {
        return cliente.getDni().equals(Constantes.ADMIN);
    }

    @Override
    public boolean scSetNombre(Cliente cliente, String nombre) {
        if (daoClientes.existeCliente(cliente)
                && !nombre.equals("")) {
            return daoClientes.setNombreCliente(cliente, nombre);
        }
        return false;
    }

    @Override
    public String scGetNombre(Cliente cliente) {
        return daoClientes.getNombreCliente(cliente);
    }

    @Override
    public boolean scEliminarCliente(Cliente cliente) {
        boolean criteriosCorrectos = daoClientes.existeCliente(cliente);
        return (criteriosCorrectos &&
                daoClientes.deleteCLiente(cliente));
    }

    @Override
    public List<Cliente> scGetClientListSortDni() {
        return daoClientes.getClientList()
                .stream().sorted(Comparator.comparing(Cliente::getDni))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean scAnadirAlergeno(Ingrediente alergeno, Cliente cliente) {
        if (daoClientes.existeCliente(cliente)) {
            return daoClientes.anadirAlergeno(alergeno, cliente);
        } else {
            return false;
        }
    }

    @Override
    public Double getCosteCompras(Cliente cliente) {
        return cliente.getComprasCliente()
                .stream().mapToDouble(lineaCompraList -> lineaCompraList
                        .stream().mapToDouble(daoCompras::getCosteLineaCompra).sum()).sum();
    }

    @Override
    public List<Cliente> scGetClientListSortGasto() {
        return daoClientes.getClientList().stream()
                .sorted((o1, o2) -> this.getCosteCompras(o2).compareTo(this.getCosteCompras(o1)))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean scEsAlergico(Ingrediente ingrediente, Cliente cliente) {
        if (daoClientes.existeCliente(cliente)) {
            return daoClientes.tieneAlergia(ingrediente, cliente);
        } else {
            return false;
        }
    }

    @Override
    public List<Ingrediente> scGetAlergenos(Cliente cliente) {
        if (daoClientes.existeCliente(cliente)) {
            return daoClientes.getAlergenos(cliente);
        } else {
            return Collections.emptyList();
        }
    }
}