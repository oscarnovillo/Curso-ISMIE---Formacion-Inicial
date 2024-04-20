package domain.services.impl;

import dao.DaoClientes;
import dao.DaoCompras;
import dao.DaoMonederos;
import domain.modelo.*;
import domain.services.ServicesCompras;
import domain.services.ServicesProductos;
import domain.services.ServicesProductosPerecederos;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesComprasImpl implements ServicesCompras {

    private final DaoCompras daoCompras;
    private final DaoMonederos daoMonederos;
    private final DaoClientes daoClientes;
    private final ServicesProductos scProductos;
    private final ServicesProductosPerecederos scProductosPerecederos;

    @Inject
    public ServicesComprasImpl(DaoCompras daoCompras, DaoMonederos daoMonederos,
                               DaoClientes daoClientes, ServicesProductos scProductos,
                               ServicesProductosPerecederos scProductosPerecederos) {
        this.daoCompras = daoCompras;
        this.daoMonederos = daoMonederos;
        this.daoClientes = daoClientes;
        this.scProductos = scProductos;
        this.scProductosPerecederos = scProductosPerecederos;
    }

    @Override
    public boolean scAddProductoCompraCliente(Cliente cliente, LineaCompra lineaCompra) {
        boolean caducado = lineaCompra.getProducto() instanceof ProductoPerecedero
                && scProductosPerecederos.productoCaducado((ProductoPerecedero) lineaCompra.getProducto());
        if (scProductos.scExisteProducto(lineaCompra.getProducto())
                && scProductos.scGetProductStock(lineaCompra.getProducto()) >= lineaCompra.getCantidad()
                && !scProductos.scContieneAlergenos(lineaCompra.getProducto(), cliente)
                && !caducado) {
            scProductos.scDisminuirStock(lineaCompra.getProducto(), lineaCompra.getCantidad());
            return daoCompras.addProductoCompra(lineaCompra, cliente);
        }
        return false;
    }

    @Override
    public boolean scPagarCompra(Cliente cliente) {
        Cliente c = daoClientes.getCliente(cliente);
        double costeCompra;
        if (cliente instanceof ClienteEspacial) {
            costeCompra = daoCompras.getCosteCompra(cliente)
                    - (((double) ((ClienteEspacial) c).getPorcentajeDescuento() / 100) * daoCompras.getCosteCompra(cliente));
        } else {
            costeCompra = daoCompras.getCosteCompra(cliente);
        }
        return (daoMonederos.getSaldoTotal(cliente) > costeCompra)
                && daoMonederos.restarDineroMonederos(cliente, costeCompra)
                && daoCompras.guardarCompraLimpiarCarrito(cliente);
    }

    @Override
    public List<LineaCompra> scGetCarrito(Cliente cliente) {
        return daoCompras.getCarrito(cliente);
    }

    @Override
    public List<List<LineaCompra>> scGetCompras(Cliente cliente) {
        return daoCompras.getCompras(cliente);
    }

}
