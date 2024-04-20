package domain.services.impl;

import dao.DaoClientes;
import dao.DaoProductos;
import domain.modelo.*;
import domain.services.ServicesProductos;
import domain.services.ServicesProductosPerecederos;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServicesProductosImpl implements ServicesProductos {

    private final DaoClientes daoClientes;
    private final DaoProductos daoProductos;
    private final ServicesProductosPerecederos scProductosPerecederos;

    @Inject
    public ServicesProductosImpl(DaoClientes daoClientes, DaoProductos daoProductos,
                                 ServicesProductosPerecederos scProductosPerecederos) {
        this.daoClientes = daoClientes;
        this.daoProductos = daoProductos;
        this.scProductosPerecederos = scProductosPerecederos;
    }

    @Override
    public boolean scAnadirProducto(Producto p) {
        boolean caducado = false;
        if (p instanceof ProductoPerecedero) {
            caducado = daoProductos.getCaducidad(((ProductoPerecedero) p)).isBefore(LocalDate.now());
        }
        if (p.getPrecio() > 0 &&
                p.getStock() > 0 &&
                !daoProductos.existeProducto(p) &&
                !caducado
        ) {
            return daoProductos.addProduct(p);
        }
        return false;
    }

    @Override
    public boolean scEliminarProducto(Producto p) {
        return daoProductos.deleteProduct(p);
    }

    @Override
    public boolean scExisteProducto(Producto producto) {
        return daoProductos.existeProducto(producto);
    }

    @Override
    public int scGetProductStock(Producto producto) {
        return daoProductos.getStockProduct(producto);
    }

    @Override
    public boolean scSetPrecioProducto(Producto p, double precioProducto) {
        boolean criteriosCorrectos = (precioProducto > 0 &&
                daoProductos.existeProducto(p));
        return criteriosCorrectos &&
                daoProductos.setProductPrize(p, precioProducto);
    }

    @Override
    public boolean scAumentarStock(Producto p, int nuevasUnidades) {
        boolean criteriosCorrectos = (nuevasUnidades > 0 &&
                daoProductos.existeProducto(p));
        return (criteriosCorrectos &&
                daoProductos.addProductStock(p, nuevasUnidades));
    }

    @Override
    public boolean scDisminuirStock(Producto producto, int unidadesAEliminar) {
        if (daoProductos.existeProducto(producto)) {
            int stockActual = daoProductos.getStockProduct(producto);
            boolean criteriosCorrectos = (unidadesAEliminar > 0 &&
                    unidadesAEliminar <= stockActual);
            return (criteriosCorrectos &&
                    daoProductos.reduceProductStock(producto, unidadesAEliminar));
        }
        return false;
    }

    @Override
    public List<Producto> scGetProductList() {
        return daoProductos.getProductList()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Producto> scGetProductListSortName() {
        return daoProductos.getProductList()
                .stream().sorted(Comparator.comparing(Producto::getNombre))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Producto> scGetProductListIngrediente(Ingrediente ingredienteABuscar) {
        return daoProductos.getProductList()
                .stream()
                .filter(producto -> producto.getIngredientes()
                        .stream()
                        .anyMatch(ingrediente -> ingrediente.getNombre().equalsIgnoreCase(ingredienteABuscar.getNombre())))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Producto> scGetProductosDisponiblesNoCaducadosAlergenos(Cliente cliente) {
        Cliente c = daoClientes.getCliente(cliente);
        return daoProductos.getProductList()
                .stream()
                .filter(producto -> {
                    boolean valido;
                    if (producto instanceof ProductoPerecedero) {
                        valido = ((ProductoPerecedero) producto).getCaducidad().isAfter(LocalDate.now());
                    } else {
                        valido = true;
                    }
                    return valido;
                })
                .filter(producto -> producto.getStock() > 0)
                .filter(producto -> producto.getIngredientes()
                        .stream()
                        .noneMatch(ingrediente -> c.getAlergenos().contains(ingrediente)))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Producto> scGetProductsWithIngredientsClient(Cliente cliente) {
        Cliente c = daoClientes.getCliente(cliente);
        return daoProductos.getProductList()
                .stream()
                .filter(producto -> {
                    boolean valido;
                    if (producto instanceof ProductoPerecedero) {
                        valido = ((ProductoPerecedero) producto).getCaducidad().isAfter(LocalDate.now());
                    } else {
                        valido = true;
                    }
                    return valido;
                })
                .filter(producto -> producto.getStock() > 0)
                .filter(producto -> producto.getIngredientes()
                        .stream()
                        .noneMatch(ingrediente -> c.getAlergenos().contains(ingrediente)))
                .filter(producto -> !producto.getIngredientes().isEmpty())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Producto> scGetProductsWithIngredientsAdmin() {
        return daoProductos.getProductList().stream()
                .filter(producto -> !producto.getIngredientes().isEmpty())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Producto> scBuscarProducto(String nombre) {
        nombre = nombre.trim();
        String finalNombre = nombre;
        return daoProductos.getProductList()
                .stream()
                .filter(producto -> producto.getNombre().contains(finalNombre.toUpperCase()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Producto> scBuscarProductoDisponiblesNoCaducados(Cliente cliente, String nombre) {
        nombre = nombre.trim();
        String finalNombre = nombre;
        Cliente c = daoClientes.getCliente(cliente);
        return daoProductos.getProductList()
                .stream()
                .filter(producto -> {
                    boolean valido;
                    if (producto instanceof ProductoPerecedero) {
                        valido = ((ProductoPerecedero) producto).getCaducidad().isAfter(LocalDate.now());
                    } else {
                        valido = true;
                    }
                    return valido;
                })
                .filter(producto -> producto.getStock() > 0)
                .filter(producto -> producto.getIngredientes()
                        .stream()
                        .noneMatch(ingrediente -> c.getAlergenos().contains(ingrediente)))
                .filter(producto -> producto.getNombre().contains(finalNombre.toUpperCase()) && producto.getStock() > 0)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean scContieneAlergenos(Producto producto, Cliente cliente) {
        Cliente c = daoClientes.getCliente(cliente);
        return (producto.getIngredientes().stream()
                .anyMatch(ingrediente -> c.getAlergenos().contains(ingrediente)));
    }

    @Override
    public List<String> scGetListaProductosSortAmountBought() {
        return daoClientes.getClientList().stream()
                .flatMap(cliente -> cliente.getComprasCliente().stream())
                .flatMap(Collection::stream)
                .collect(Collectors
                        .groupingBy(lineaCompra -> lineaCompra.getProducto().toStringNoStock(),
                                Collectors.summingInt(LineaCompra::getCantidad)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(productoDoubleEntry -> productoDoubleEntry.getKey() +
                        " Cantidad comprada: " + productoDoubleEntry.getValue())
                .collect(Collectors.toUnmodifiableList());
    }
}