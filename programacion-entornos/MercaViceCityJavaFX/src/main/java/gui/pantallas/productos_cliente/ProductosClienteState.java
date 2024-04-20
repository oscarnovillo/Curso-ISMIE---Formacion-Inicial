package gui.pantallas.productos_cliente;

import lombok.Data;

@Data
public class ProductosClienteState {

    private final boolean productoAgregadoALCarrito;
    private final boolean verCarrito;
    private final String error;

}

