package domain.modelo;

import java.util.Objects;

public class Ingrediente implements Clonable<Ingrediente> {

    private final String nombre;

    public Ingrediente(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingrediente that = (Ingrediente) o;
        return nombre.equalsIgnoreCase(that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public Ingrediente clonar() {
        return new Ingrediente(this.nombre);
    }

}