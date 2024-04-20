package dao.impl;

import dao.DaoMonederos;
import dao.DataBase;
import domain.modelo.Cliente;
import domain.modelo.Monedero;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DaoMonederosImpl implements DaoMonederos {

    private final DataBase bd;

    @Inject
    public DaoMonederosImpl(DataBase bd) {
        this.bd = bd;
    }

    @Override
    public boolean addMonederoCliente(Monedero monedero, Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        clientes.get(cliente.getDni()).getMonederosCliente().add(monedero);
        return bd.writeJSONClientes(clientes);
    }

    @Override
    public boolean existeMonedero(Monedero monedero) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        boolean existeMonedero = false;
        for (Cliente cliente : clientes.values()) {
            if (cliente.getMonederosCliente().contains(monedero)) {
                existeMonedero = true;
                break;
            }
        }
        return existeMonedero;
    }

    @Override
    public double getSaldoTotal(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(cliente.getDni()).getMonederosCliente().stream().mapToDouble(Monedero::getImporte).sum();
    }

    @Override
    public List<Monedero> getMonederosClienteList(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(cliente.getDni()).getMonederosCliente().stream()
                .map(Monedero::clonar)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean restarDineroMonederos(Cliente cliente, double importe) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        if (getSaldoTotal(cliente) >= importe) {
            for (Monedero monedero : clientes.get(cliente.getDni()).getMonederosCliente()) {
                if (monedero.getImporte() >= 0) {
                    monedero.setImporte(monedero.getImporte() - importe);
                }
                if (monedero.getImporte() < 0) {
                    importe = Math.abs(monedero.getImporte());
                    monedero.setImporte(0);
                } else {
                    importe = 0;
                }
            }
        }
        return bd.writeJSONClientes(clientes) && importe == 0;
    }

}