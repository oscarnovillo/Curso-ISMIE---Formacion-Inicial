package dao.impl;

import dao.DaoClientes;
import dao.DataBase;
import domain.modelo.Cliente;
import domain.modelo.ClienteEspacial;
import domain.modelo.ClienteNormal;
import domain.modelo.Ingrediente;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class DaoClientesImpl implements DaoClientes {

    private final DataBase bd;

    @Inject
    public DaoClientesImpl(DataBase bd) {
        this.bd = bd;
    }


    @Override
    public boolean addCliente(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        clientes.put(cliente.getDni(), cliente);
        return bd.writeJSONClientes(clientes);
    }

    @Override
    public boolean deleteCLiente(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        clientes.remove(cliente.getDni());
        return bd.writeJSONClientes(clientes);
    }

    @Override
    public boolean existeCliente(Cliente c) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.containsValue(c);
    }

    @Override
    public boolean setNombreCliente(Cliente cliente, String nombre) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        clientes.get(cliente.getDni()).setNombre(nombre);
        return bd.writeJSONClientes(clientes);
    }

    @Override
    public String getNombreCliente(Cliente c) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(c.getDni()).getNombre();
    }

    @Override
    public Collection<Cliente> getClientList() {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.values();
    }

    @Override
    public boolean anadirAlergeno(Ingrediente alergeno, Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        clientes.get(cliente.getDni()).getAlergenos().add(alergeno);
        return bd.writeJSONClientes(clientes);
    }

    @Override
    public boolean tieneAlergia(Ingrediente alergeno, Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(cliente.getDni()).getAlergenos().contains(alergeno);
    }

    @Override
    public int getMediaDescuento() {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return ((int) clientes.values().stream()
                .filter(ClienteEspacial.class::isInstance)
                .mapToInt(cliente -> ((ClienteEspacial) cliente).getPorcentajeDescuento())
                .average().orElse(0.0));
    }

    @Override
    public void setDescuento(int porcentajeDescuento) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        clientes.values().forEach(cliente -> {
            if (cliente instanceof ClienteEspacial) {
                ((ClienteEspacial) cliente).setPorcentajeDescuento(porcentajeDescuento);
            }
        });
        bd.writeJSONClientes(clientes);
    }

    @Override
    public List<Ingrediente> getAlergenos(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(cliente.getDni()).getAlergenos();
    }

    @Override
    public Cliente getCliente(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(cliente.getDni());
    }

}