package dao;

import domain.modelo.Producto;
import domain.modelo.ProductoPerecedero;

import java.time.LocalDate;
import java.util.List;

public interface DaoProductos {

    boolean addProduct(Producto p);

    boolean deleteProduct(Producto p);

    boolean existeProducto(Producto p);

    boolean setProductPrize(Producto p, double precio);

    boolean addProductStock(Producto p, int stock);

    boolean reduceProductStock(Producto producto, int stock);

    int getStockProduct(Producto producto);

    List<Producto> getProductList();

    LocalDate getCaducidad(ProductoPerecedero perecedero);
}
