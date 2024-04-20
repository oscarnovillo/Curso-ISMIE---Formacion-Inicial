package domain.services;

import domain.modelo.Cliente;
import domain.modelo.Ingrediente;
import domain.modelo.Producto;

import java.util.List;

public interface ServicesProductos {
    boolean scAnadirProducto(Producto p);

    boolean scEliminarProducto(Producto p);

    boolean scExisteProducto(Producto producto);

    int scGetProductStock(Producto producto);

    boolean scSetPrecioProducto(Producto p, double precioProducto);

    boolean scAumentarStock(Producto p, int nuevasUnidades);

    boolean scDisminuirStock(Producto producto, int unidadesAEliminar);

    boolean scContieneAlergenos(Producto producto, Cliente cliente);

    List<Producto> scGetProductList();

    List<Producto> scGetProductListSortName();

    List<Producto> scGetProductsWithIngredientsAdmin();

    List<Producto> scGetProductListIngrediente(Ingrediente ingredienteABuscar);

    List<Producto> scGetProductosDisponiblesNoCaducadosAlergenos(Cliente cliente);

    List<Producto> scGetProductsWithIngredientsClient(Cliente cliente);

    List<Producto> scBuscarProducto(String nombre);

    List<Producto> scBuscarProductoDisponiblesNoCaducados(Cliente cliente, String nombre);

    List<String> scGetListaProductosSortAmountBought();
}
