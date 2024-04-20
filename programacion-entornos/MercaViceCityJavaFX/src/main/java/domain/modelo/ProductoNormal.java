package domain.modelo;


import domain.modelo.common.Constantes;

import java.time.LocalDate;
import java.util.List;

public class ProductoNormal extends Producto {

    private final LocalDate caducidad;

    public ProductoNormal(int id) {
        super(id);
        this.caducidad = null;
        this.productType = Constantes.PRODUCTO_NORMAL;
    }

    public ProductoNormal(String nombre, double precio, int stock, List<Ingrediente> ingredientes) {
        super(nombre, precio, stock, ingredientes);
        this.caducidad = null;
        this.productType = Constantes.PRODUCTO_NORMAL;
    }

    public ProductoNormal(int id, String nombre, double precio, int stock, List<Ingrediente> ingredientes) {
        super(id, nombre, precio, stock, ingredientes);
        this.caducidad = null;
        this.productType = Constantes.PRODUCTO_NORMAL;
    }

    public LocalDate getCaducidad() {
        return caducidad;
    }

    @Override
    public ProductoNormal clonar() {
        return new ProductoNormal(super.getId(), super.getNombre(), super.getPrecio(), super.getStock(), super.getIngredientes());
    }

}