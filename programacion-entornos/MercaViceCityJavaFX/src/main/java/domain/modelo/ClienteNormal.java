package domain.modelo;


import domain.modelo.common.Constantes;

import java.util.List;
import java.util.Set;

public class ClienteNormal extends Cliente {

    private int descuento = 0;

    public ClienteNormal(String dni) {
        super(dni);
        this.clientType = Constantes.CLIENTE_NORMAL;
    }

    public ClienteNormal(String dni, String nombre, Set<Monedero> monederoCliente,
                         List<LineaCompra> compraActual,
                         List<List<LineaCompra>> comprasCliente, List<Ingrediente> alergenos) {
        super(dni, nombre, monederoCliente, compraActual, comprasCliente, alergenos);
        this.clientType = Constantes.CLIENTE_NORMAL;
    }

    public int getPorcentajeDescuento() {
        return descuento;
    }

    @Override
    public ClienteNormal clonar() {
        return new ClienteNormal(super.getDni(), super.getNombre(), this.listaMonederosClonada(),
                this.listaCompraClonada(), this.listaComprasClonada(), this.listaAlergenos());
    }

}