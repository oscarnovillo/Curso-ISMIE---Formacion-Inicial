package dao;

import domain.modelo.Cliente;
import domain.modelo.LineaCompra;

import java.util.List;

public interface DaoCompras {
    boolean addProductoCompra(LineaCompra lineaCompra, Cliente cliente);

    double getCosteCompra(Cliente cliente);

    Double getCosteLineaCompra(LineaCompra lineaCompra);

    boolean guardarCompraLimpiarCarrito(Cliente cliente);

    List<LineaCompra> getCarrito(Cliente cliente);

    List<List<LineaCompra>> getCompras(Cliente cliente);
}
