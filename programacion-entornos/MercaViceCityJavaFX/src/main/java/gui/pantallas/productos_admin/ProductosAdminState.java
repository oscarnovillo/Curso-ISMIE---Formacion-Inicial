package gui.pantallas.productos_admin;

import lombok.Data;

@Data
public class ProductosAdminState {

    private final boolean productoActualizado;
    private final boolean productoEliminado;
    private final boolean productoAgregado;
    private final String error;

}
