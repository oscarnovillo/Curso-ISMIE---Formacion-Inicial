package gui.pantallas.clientes_admin;

import lombok.Data;

@Data
public class ClientesAdminState {

    private final boolean clienteActualizado;
    private final boolean clienteEliminado;
    private final boolean clienteRegistrado;
    private final String error;

}
