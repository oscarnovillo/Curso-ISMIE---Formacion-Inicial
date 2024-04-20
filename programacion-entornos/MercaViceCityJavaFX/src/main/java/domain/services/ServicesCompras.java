package domain.services;

import domain.modelo.Cliente;
import domain.modelo.LineaCompra;

import java.util.List;

public interface ServicesCompras {
    boolean scAddProductoCompraCliente(Cliente cliente, LineaCompra lineaCompra);

    boolean scPagarCompra(Cliente cliente);

    List<LineaCompra> scGetCarrito(Cliente cliente);

    List<List<LineaCompra>> scGetCompras(Cliente cliente);
}
