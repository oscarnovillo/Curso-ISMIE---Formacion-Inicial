package domain.modelo;

import domain.modelo.common.Constantes;

import java.time.LocalDate;
import java.util.Objects;

public class LineaCompra implements Clonable<LineaCompra> {

    private final Producto producto;
    private final int cantidad;

    public LineaCompra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return producto.getPrecio();
    }

    public int getId() {
        return producto.getId();
    }

    public String getNombre() {
        return producto.getNombre();
    }

    public String getProductType() {
        return producto.getProductType();
    }

    public LocalDate getCaducidad() {
        if (producto instanceof ProductoPerecedero) {
            return ((ProductoPerecedero) producto).getCaducidad();
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaCompra that = (LineaCompra) o;
        return cantidad == that.cantidad && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, cantidad);
    }

    @Override
    public String toString() {
        String nombreMostrar = producto.getNombre().toLowerCase();
        nombreMostrar = nombreMostrar.substring(0, 1).toUpperCase().concat(nombreMostrar.substring(1));
        return producto.getId() + Constantes.PUNTO +
                nombreMostrar +
                Constantes.PRECIO + producto.getPrecio() +
                Constantes.CANTIDAD + cantidad;
    }

    @Override
    public LineaCompra clonar() {
        return new LineaCompra(this.producto, this.cantidad);
    }

}