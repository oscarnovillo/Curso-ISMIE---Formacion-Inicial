package gui.pantallas.compras_realizadas;

import lombok.Data;

@Data
public class ComprasRealizadasState {

    private final boolean verProductos;
    private final boolean verPerfil;
    private final String error;
}
