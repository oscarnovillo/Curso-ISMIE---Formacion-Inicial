package domain.modelo;

import domain.modelo.common.Constantes;

import java.time.LocalDate;
import java.util.List;

public class ProductoPerecedero extends Producto {

    private final LocalDate caducidad;

    public ProductoPerecedero(String nombre, double precio, int stock, List<Ingrediente> ingredientes, LocalDate caducidad) {
        super(nombre, precio, stock, ingredientes);
        this.caducidad = caducidad;
        this.productType = Constantes.PRODUCTO_PERECEDERO;
    }

    public ProductoPerecedero(int id, String nombre, double precio, int stock, List<Ingrediente> ingredientes, LocalDate caducidad) {
        super(id, nombre, precio, stock, ingredientes);
        this.caducidad = caducidad;
        this.productType = Constantes.PRODUCTO_PERECEDERO;
    }

    public LocalDate getCaducidad() {
        return caducidad;
    }

    @Override
    public String toString() {
        return super.toString() +
                " Caducidad=" + caducidad;
    }

    @Override
    public ProductoPerecedero clonar() {
        return new ProductoPerecedero(super.getId(), super.getNombre(), super.getPrecio(),
                super.getStock(), super.getIngredientes(), this.caducidad);
    }

}