package dao.impl;

import dao.DaoProductos;
import dao.DataBase;
import domain.modelo.Ingrediente;
import domain.modelo.Producto;
import domain.modelo.ProductoNormal;
import domain.modelo.ProductoPerecedero;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoProductosImpl implements DaoProductos {

    private final DataBase bd;

    @Inject
    public DaoProductosImpl(DataBase bd) {
        this.bd = bd;
    }


    @Override
    public boolean addProduct(Producto p) {
        List<Producto> inventario = bd.readJSONProductos();
        boolean operacionRealizada = false;
        if (!inventario.contains(p)) {
            int idProduct = inventario.stream().map(Producto::getId).max(Integer::compare).orElse(0);
            idProduct++;
            p.setId(idProduct);
            inventario.add(p);
            operacionRealizada = true;
        }
        return operacionRealizada && bd.writeJSONProductos(inventario);
    }

    @Override
    public boolean deleteProduct(Producto p) {
        List<Producto> inventario = bd.readJSONProductos();
        return inventario.remove(p) && bd.writeJSONProductos(inventario);
    }

    @Override
    public boolean existeProducto(Producto p) {
        List<Producto> inventario = bd.readJSONProductos();
        return inventario.contains(p);
    }

    @Override
    public boolean setProductPrize(Producto p, double precio) {
        List<Producto> inventario = bd.readJSONProductos();
        int productIndex = inventario.indexOf(p);
        boolean productoEncontardo = productIndex != -1;
        if (productoEncontardo) {
            Producto productoBD = inventario.get(productIndex);
            productoBD.setPrecio(precio);
        }
        return productoEncontardo && bd.writeJSONProductos(inventario);
    }

    @Override
    public boolean addProductStock(Producto p, int stock) {
        List<Producto> inventario = bd.readJSONProductos();
        int productIndex = inventario.indexOf(p);
        boolean productoEncontardo = productIndex != -1;
        if (productoEncontardo) {
            Producto productoBD = inventario.get(productIndex);
            productoBD.setStock(this.getStockProduct(p) + stock);
        }
        return productoEncontardo && bd.writeJSONProductos(inventario);
    }

    @Override
    public boolean reduceProductStock(Producto producto, int stock) {
        List<Producto> inventario = bd.readJSONProductos();
        int productIndex = inventario.indexOf(producto);
        boolean productoEncontardo = productIndex != -1;
        if (productoEncontardo) {
            Producto p = inventario.get(productIndex);
            p.setStock(this.getStockProduct(p) - stock);
        }
        return productoEncontardo && bd.writeJSONProductos(inventario);
    }

    @Override
    public int getStockProduct(Producto producto) {
        List<Producto> inventario = bd.readJSONProductos();
        int productIndex = inventario.indexOf(producto);
        boolean productoEncontardo = productIndex != -1;
        if (productoEncontardo) {
            Producto p = inventario.get(productIndex);
            return p.getStock();
        }
        return 0;
    }

    @Override
    public List<Producto> getProductList() {
        return bd.readJSONProductos();
    }

    @Override
    public LocalDate getCaducidad(ProductoPerecedero perecedero) {
        LocalDate caducidad = null;
        List<Producto> inventario = bd.readJSONProductos();
        int productIndex = inventario.indexOf(perecedero);
        boolean productoEncontardo = productIndex != -1;
        if (productoEncontardo) {
            ProductoPerecedero p = (ProductoPerecedero) inventario.get(productIndex);
            caducidad = p.getCaducidad();
        }
        if (caducidad == null) {
            caducidad = perecedero.getCaducidad();
        }
        return caducidad;
    }

}