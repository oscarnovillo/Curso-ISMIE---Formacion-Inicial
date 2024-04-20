package gui.pantallas.carrito;

import lombok.Data;

@Data
public class CarritoState {

    private final boolean compraRealizada;
    private final boolean verProductos;
    private final String error;

}
