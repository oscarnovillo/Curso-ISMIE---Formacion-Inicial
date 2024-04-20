package main.streams;


import pedidos.dao.modelo.*;
import pedidos.servicios.ServiciosPedido;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StreamsPedidos {
    ServiciosPedido sp = new ServiciosPedido();
    List<PedidoCompuesto> pedidos = sp.getTodosPedidos();

    // un map con nombre de producto y cantidad de veces pedido
    public void productosAgrupadosPorCantidadDeVecesPedidos() {
        System.out.println("\nProductos agrupados por numero de veces pedidos --------------------------------------------------------------------------------------------------------------");
        Map<String, Integer> resultMap = new HashMap<>();
        pedidos.stream()
                .flatMap(p -> p.getPedidosSimples().stream())
                .flatMap(p -> p.getLineasPedido().stream())
                .map(LineaPedido::getProducto)
                .forEach(producto -> {
                    int vecesPedido = pedidos.stream()
                            .flatMap(pc -> pc.getPedidosSimples().stream())
                            .flatMap(ps -> ps.getLineasPedido().stream())
                            .filter(lp -> lp.getProducto().equals(producto))
                            .mapToInt(LineaPedido::getCantidad).sum();
                    resultMap.put(producto.getNombre(), vecesPedido);
                });
        System.out.println(resultMap);
    }

    public void clienteQueMasDineroSehaGastado() {
        System.out.println("\nCliente que mas dinero se ha gastado --------------------------------------------------------------------------------------------------------------");
        Cliente cliente = sp.getTodosClientes().stream()
                .max((o1, o2) -> {
                    int gastadoCliente1 = pedidos.stream()
                            .filter(p -> p.getCliente().equals(o1))
                            .mapToInt(PedidoCompuesto::getTotalFactura)
                            .sum();
                    int gastadoCliente2 = pedidos.stream()
                            .filter(p -> p.getCliente().equals(o2))
                            .mapToInt(PedidoCompuesto::getTotalFactura)
                            .sum();
                    return Integer.compare(gastadoCliente1, gastadoCliente2);
                }).orElse(null);
        System.out.println(cliente);
    }

    // La cantidad media de producto por pedido simple, sumando todas las lineas de pedido de cada simple
    public void lacantidadMediaPedidaDeCadaProductoEnCadaPedidoCompuesto() {
        System.out.println("\nCantidad media pedida de cada producto en cada pedido compuesto --------------------------------------------------------------------------------------------------------------");
        pedidos.stream()
                .flatMap(pc -> pc.getPedidosSimples().stream())
                .flatMap(ps -> ps.getLineasPedido().stream())
                .map(LineaPedido::getProducto)
                .forEach(producto -> {
                    System.out.println(producto.getNombre());
                    double mediaPedida = pedidos.stream()
                            .flatMap(pc -> pc.getPedidosSimples().stream())
                            .flatMap(ps -> ps.getLineasPedido().stream())
                            .filter(lp -> lp.getProducto().equals(producto))
                            .mapToInt(LineaPedido::getCantidad)
                            .average().orElse(-1);
                    System.out.println("Media: " + mediaPedida);
                });
    }


    public void pedidoSimpleConMasLineasdePedido() {
        System.out.println("\nPedido simple con mas lineas de pedido --------------------------------------------------------------------------------------------------------------");
        PedidoSimple pedidoSimple = pedidos.stream()
                .flatMap(pc -> pc.getPedidosSimples().stream())
                .reduce((pc1, pc2) -> pc1.getLineasPedido().size() > pc2.getLineasPedido().size() ? pc1 : pc2)
                .orElse(null);
        System.out.println(pedidoSimple);
    }


    public void todoelDineroFacturadoEnTotalentodosLosPedidos() {
        System.out.println("\nTodo el dinero facturado en total en todos los pedidos --------------------------------------------------------------------------------------------------------------");
        int dineroFacturado = pedidos.stream()
                .filter(pc -> pc.getEstado() == Estado.COBRADO)
                .flatMap(pc -> pc.getPedidosSimples().stream())
                .flatMap(ps -> ps.getLineasPedido().stream())
                .mapToInt(LineaPedido::getPrecioTotal)
                .sum();
        System.out.println(dineroFacturado);
    }
}
