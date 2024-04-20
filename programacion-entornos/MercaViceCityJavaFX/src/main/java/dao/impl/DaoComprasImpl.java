package dao.impl;

import dao.DaoCompras;
import dao.DataBase;
import domain.modelo.Cliente;
import domain.modelo.LineaCompra;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DaoComprasImpl implements DaoCompras {

    private final DataBase bd;

    @Inject
    public DaoComprasImpl(DataBase bd) {
        this.bd = bd;
    }

    @Override
    public boolean addProductoCompra(LineaCompra lineaCompra, Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        clientes.get(cliente.getDni()).getCompraActual().add(lineaCompra);
        return bd.writeJSONClientes(clientes);
    }

    @Override
    public double getCosteCompra(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(cliente.getDni()).getCompraActual().
                stream().mapToDouble(this::getCosteLineaCompra).sum();
    }

    @Override
    public Double getCosteLineaCompra(LineaCompra lineaCompra) {
        return lineaCompra.getProducto().getPrecio() * lineaCompra.getCantidad();
    }

    @Override
    public boolean guardarCompraLimpiarCarrito(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        List<LineaCompra> ticket = getCarrito(cliente);
        clientes.get(cliente.getDni()).getCompraActual().clear();
        clientes.get(cliente.getDni()).getComprasCliente().add(ticket);
        return bd.writeJSONClientes(clientes);
    }

    @Override
    public List<LineaCompra> getCarrito(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(cliente.getDni()).getCompraActual().stream()
                .map(LineaCompra::clonar)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<List<LineaCompra>> getCompras(Cliente cliente) {
        Map<String, Cliente> clientes = bd.readJSONClientes();
        return clientes.get(cliente.getDni()).getComprasCliente();
    }

}