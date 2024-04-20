package gui.pantallas.perfil;

import lombok.Data;

@Data
public class PerfilState {

    private final boolean nombreCambiado;
    private final boolean monederoAnadido;
    private final boolean alergenoAnadido;
    private final String error;

}