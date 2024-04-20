package domain.modelo;

import domain.modelo.common.Constantes;

import java.util.Objects;

public class Monedero implements Clonable<Monedero> {

    private final int numeroMonedero;
    private double importe;

    public Monedero(int numeroMonedero, double importe) {
        this.numeroMonedero = numeroMonedero;
        this.importe = importe;
    }

    public Monedero(int numeroMonedero) {
        this.numeroMonedero = numeroMonedero;
    }

    public int getNumeroMonedero() {
        return numeroMonedero;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return Constantes.NUMERO_MONEDERO + numeroMonedero +
                Constantes.IMPORTE + importe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monedero monedero = (Monedero) o;
        return numeroMonedero == monedero.numeroMonedero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroMonedero);
    }

    @Override
    public Monedero clonar() {
        return new Monedero(this.numeroMonedero, this.importe);
    }

}