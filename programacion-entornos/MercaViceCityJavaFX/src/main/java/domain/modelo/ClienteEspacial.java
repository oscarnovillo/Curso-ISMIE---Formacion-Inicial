package domain.modelo;

import domain.modelo.common.Constantes;

import java.util.List;
import java.util.Set;

public class ClienteEspacial extends Cliente {

    private int porcentajeDescuento;

    public ClienteEspacial(String dni) {
        super(dni);
        this.clientType = Constantes.CLIENTE_ESPACIAL;
    }

    public ClienteEspacial(String dni, String nombre, Set<Monedero> monederoCliente,
                           List<LineaCompra> compraActual,
                           List<List<LineaCompra>> comprasCliente,
                           List<Ingrediente> alergenos,
                           int porcentajeDescuento) {
        super(dni, nombre, monederoCliente, compraActual, comprasCliente, alergenos);
        this.porcentajeDescuento = porcentajeDescuento;
        this.clientType = Constantes.CLIENTE_ESPACIAL;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public ClienteEspacial clonar() {
        return new ClienteEspacial(super.getDni(), super.getNombre(), super.getMonederosCliente(),
                super.getCompraActual(), super.getComprasCliente(),
                super.getAlergenos(), this.porcentajeDescuento);
    }

}